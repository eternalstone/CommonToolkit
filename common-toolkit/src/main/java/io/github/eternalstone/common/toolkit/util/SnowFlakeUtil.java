package io.github.eternalstone.common.toolkit.util;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 雪花算法生成主键ID
 *
 * @author 老刘
 */
public class SnowFlakeUtil {
    /**
     * 开始时间截 (2015-01-01)
     */
    private static final long TW_EPOCH = 1414213562373L;

    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * 数据标识id所占的位数
     */
    private static final long DATACENTER_ID_BITS = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    public static long getWorkerId() {
        return WORKER_ID;
    }

    /**
     * 工作机器ID(0~31)
     */
    private static long WORKER_ID = 0;

    public static long getDatacenterId() {
        return DATACENTER_ID;
    }

    /**
     * 数据中心ID(0~31)
     */
    private static long DATACENTER_ID = 0;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static final SnowFlakeUtil SNOW_FLAKE_UTIL;

    static {
        // 生成worker_id
        long workId;
        try {
            StringBuilder sbInternet = new StringBuilder();
            Enumeration<NetworkInterface> enumInter = NetworkInterface.getNetworkInterfaces();
            while (enumInter.hasMoreElements()) {
                sbInternet.append(Arrays.toString(enumInter.nextElement().getHardwareAddress()));
            }
            //hashcode可能为负数,与运算去除符号
            int machinePiece = sbInternet.toString().hashCode() & Integer.MAX_VALUE;
            workId = machinePiece % 32;
        } catch (Exception e) {
            // 如果获取失败，则使用随机数备用
            workId = ThreadLocalRandom.current().nextLong(0, 31);
        }

        // 生成data_centerId
        int processPiece = ManagementFactory.getRuntimeMXBean().getName().hashCode() & 0xFFFF;
        long dataCenterId = processPiece % 32;

        SNOW_FLAKE_UTIL = new SnowFlakeUtil(workId, dataCenterId);
    }


    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowFlakeUtil(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker_Id can't be > %d or < 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("Datacenter_Id can't be > %d or < 0", MAX_DATACENTER_ID));
        }
        WORKER_ID = workerId;
        DATACENTER_ID = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format("Clock moved backwards. Refusing to generate_id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - TW_EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (DATACENTER_ID << DATACENTER_ID_SHIFT)
                | (WORKER_ID << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
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
     * 获取一个id
     *
     * @return id
     */
    public static synchronized long getId() {
        return SNOW_FLAKE_UTIL.nextId();
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 生成id列表
     *
     * @param num 想要的id个数
     * @return id列表
     */
    public static synchronized List<Long> getIdForList(int num) {
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            longList.add(SNOW_FLAKE_UTIL.nextId());
        }
        return longList;
    }
}