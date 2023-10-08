package io.github.eternalstone.common.toolkit.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 9:48
 */
public class DateUtil {

    public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";

    public static final SimpleDateFormat YYYY_MM_DD_HHMMSS_SDF = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
    public static final SimpleDateFormat YYYY_MM_DD_SDF = new SimpleDateFormat(YYYY_MM_DD);
    public static final SimpleDateFormat YYYYMMDD_SDF = new SimpleDateFormat(YYYYMMDD);

    /**
     * 获取秒级时间戳
     *
     * @return 秒级时间戳
     */
    public static long getNowTime() {
        return (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取现在的时间的字符串格式
     *
     * @return 如 2020-10-10 10:10:10
     */
    public static String getNowDateTimeString(String format) {
        Date date = new Date();
        return YYYY_MM_DD_HHMMSS_SDF.format(date);
    }


    /**
     * Date类型转换为字符串格式
     * String startTime = DateUtil.convertDate2String(new Date(System.currentTimeMillis(),DateUtil.YYYY_MM_DD_HHMMSS_SDF)
     *
     * @param date   日期
     * @param format 格式
     * @return 字符串的时间
     */
    public static String convertDate2String(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将时间字符传转换成时间戳对象
     *
     * @param dateString 字符串格式的时间
     * @return 时间戳
     */
    public static int dateToTimestamp(String dateString) {
        Date date = convertString2Date(dateString, "yyyy-MM-dd HH:mm:ss");
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    /**
     * 参数的字符串使用指定的格式转换日期型的方法。
     *
     * @param str    转换的对象
     * @param format 日期时间格式的模式
     * @return 参数null的时候返回null，字符串时按照指定的日期时间格式实行Date型转换
     */
    public static Date convertString2Date(String str, String format) {
        if (str == null || "".equals(str)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(format).parse(str.trim()));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return calendar.getTime();
    }

    /**
     * 参数的日期被指定的日加算的方法。
     *
     * @param date 日期
     * @param day  加算的日
     * @return 加算后的日期
     */
    public static Date addDay(Date date, int day) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }


    /**
     * 参数的日期被指定的时加算的方法。
     *
     * @param date 日期
     * @param hour 加算的时
     * @return 加算后的日期
     */
    public static Date addHour(Date date, int hour) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 取得参数日期的时
     *
     * @param date 日期
     * @return 指定的时
     */
    public static int getHour(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取前后时间的差的方法
     *
     * @param stateDate 开始时间
     * @param endDate   结束时间
     * @return 前后时间的相差秒数
     */
    public static int dateDiff(Date stateDate, Date endDate) {
        return (int) ((endDate.getTime() - stateDate.getTime()) / (1000));
    }


    /**
     * 计算N个毫秒转换成天数,不足一天算一天
     *
     * @param mills 毫秒数
     * @return 天数
     */
    public static int daysOfMills(long mills) {
        long dayOfMills = 24 * 3600 * 1000;
        long remain = mills % dayOfMills;
        long days = mills / dayOfMills;
        if (remain != 0) {
            days++;
        }
        return (int) days;
    }

    /**
     * 获取某个时间的秒级时间戳
     *
     * @param localDateTime 时间
     * @return 时间戳
     */
    public static int secondsOfTime(LocalDateTime localDateTime) {
        return (int) localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 得到 多少前日期
     *
     * @return
     * @Author ccd
     * @Description //
     * @Date 21:39 2019/10/24
     * @Param
     **/
    public static Integer getBfDay(int dayNum, String format) {
        Date start = new Date();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, -1 * dayNum);
        return Integer.parseInt(convertDate2String(tempStart.getTime(), format));
    }

    /**
     * @return
     * @Author ccd
     * @Description // 获取多少前天
     * @Date 11:46 2019/11/30
     * @Param
     **/
    public static Date getBfDay(int dayNum) {
        Date start = new Date();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, -1 * dayNum);
        return tempStart.getTime();
    }

    /**
     * 获取相差n天的date
     *
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getDay(Date date, int dayNum) {
        return new Date(date.getTime() + dayNum * 24 * 3600000);
    }

    /**
     * 当天的开始时间秒数
     *
     * @return
     */
    public static int getTodayStartTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime time = now.atTime(0, 0, 0);
        return (int) time.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * @return
     * @Author ccd
     * @Description 获取最近多少天的
     * @Date 20:54 2019/10/16
     * @Param
     **/
    public static List<String> getBfDays(int dayNum, String format) {

        // 返回的日期集合
        List<String> days = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date start = new Date();

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        while (dayNum > 0) {
            dayNum--;
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, -1);
        }
        return days;
    }


    /**
     * 最近一个月的
     *
     * @param diffDay
     * @param format
     * @return
     */
    public static List<String> getMonthDates(Integer diffDay, String format) {
        List<String> dateList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        int dayNum = 30 / diffDay + 1;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        for (int i = 1; i <= dayNum; i++) {
            dateList.add(dateTimeFormatter.format(localDate));
            localDate = localDate.minusDays(diffDay);
        }
        return dateList;
    }

    /**
     * 最近多少天
     *
     * @param days
     * @param format
     * @return
     */
    public static List<String> getLastDates(Integer days, String format) {
        List<String> dateList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        for (int i = 1; i <= days; i++) {
            dateList.add(dateTimeFormatter.format(localDate));
            localDate = localDate.minusDays(1);
        }
        return dateList;
    }

    /**
     * 计算2个日期相差的天数
     * date2-date1
     *
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


    public static Integer getDateTimeStrFromUTC(String str) {
        str = str.replace("Z", " UTC");
        return parseDateTime(str, "yyyy-MM-dd'T'HH:mm:ss Z");
    }

    /**
     * 解析年月日时间
     *
     * @param str
     * @return
     */
    public static Integer getDateTimeStr(String str) {
        return parseDateTime(str, "MM月 dd，yyyy (HH:mm:ss)");
    }

    private static Integer parseDateTime(String str, String originalFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(originalFormat);
            Date date = simpleDateFormat.parse(str);
            return millsToSeconds(date.getTime());
        } catch (Exception ex) {

        }
        return 0;
    }

    /**
     * 毫秒转秒
     *
     * @param mills 毫秒数
     * @return 秒数
     */
    public static int millsToSeconds(long mills) {
        return Long.valueOf(mills / 1000).intValue();
    }

    /**
     * 通过时间字符串 ，获取unixtime 时间戳
     *
     * @param day    字符串时间
     * @param format 时间格式
     **/
    public static int getStartDayTime(String day, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date rsDate;
        try {
            rsDate = dateFormat.parse(day);
            return millsToSeconds(rsDate.getTime());
        } catch (ParseException e) {
        }
        return 0;
    }

    /**
     * 获取相差N个小时的时间
     *
     * @param start  开始时间
     * @param hour   相差的小时
     * @param format 格式 如yyyyMMddHH
     * @return 如 2000101010
     */
    public static Integer getDiffHourDate(Date start, int hour, String format) {
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.HOUR_OF_DAY, hour);
        return Integer.parseInt(convertDate2String(tempStart.getTime(), format));
    }

    /**
     * 时间戳转时间字符串
     * @param secondsDate
     * @return
     */
    public static String secondsToDateStr(Long secondsDate) {
        try {
            return YYYY_MM_DD_HHMMSS_SDF.format(new Date(secondsDate * 1000));
        }catch (Exception ex){

        }
        return null;
    }

    /**
     * 清除时间的时分秒
     * @param date
     * @return
     */
    public static Date clearHHmmss(Date date){
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setCalender(calendar, 0, 0, 0, 0);
        return calendar.getTime();
    }

    private static void setCalender(Calendar calendar, int hour, int minute, int second, int milliSecond) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
    }
}
