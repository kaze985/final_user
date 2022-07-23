package com.geekgame.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * SnowFlake 算法修改
 * 0 - 0000000000 00 - 0000000000 0000000000 0000000000 000000000 - 0000 - 00000000
 * 符号位 -12位年月位(表示yyMM,最大4096,即可用至2040年)-39位时间戳 （可用17年，即可用至2035年）-4位机器ID(最大16，即可部署16个节点)-8位序列号(z最大256)
 */
@Component
public class SnowflakeIdGenerator {

    // ==============================Fields===========================================
    /** 开始时间截 (2018-01-01) */
    private final long twepoch = 1514736000000L;

    /** 时间戳占的位数 */
    public static final long timestampBits = 39L;

    /** 机器id所占的位数 */
    public static final long workerIdBits = 4L;

    /** 支持的最大机器id，结果是15 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 序列在id中占的位数 */
    public static final long sequenceBits = 8L;

    /** 机器ID向左移6位 */
    private final long workerIdShift = sequenceBits;

    /** 时间截向左移12位(4+8) */
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    /** 年月标识左移51位(39 + 4 + 8)*/
    private final long yearMonthLeftShift = sequenceBits + workerIdBits + timestampBits;

    /** 生成序列的掩码，这里为255 */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~16) */
    @Value("${worker.id}")
    private long workerId;

    /** 毫秒内序列(0~256) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    // ==============================Methods==========================================
    @PostConstruct
    public void init(){
        System.out.println(workerId);
        if(this.workerId < 0 || this.workerId > maxWorkerId){
            throw new RuntimeException("workerId(" + this.workerId + ") is out of range [0, 15]");
        }
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId(long yyMM) {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        long preId = (yyMM << yearMonthLeftShift) | ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
        return preId;
    }

    /**
     * 获得不带年月位的id
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        long preId = ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
        return preId;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

}


