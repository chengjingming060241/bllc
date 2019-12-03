package com.gizwits.boot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhl on 2017/2/9.
 */
public class DateKit {
    /**
     * 获取某一天的开始时间
     * @param startTime
     * @return
     */
    public static Date getDayStartTime(Date startTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();
        return start;

    }

    /**
     *  获取某一天的结束时间
     * @param endTime
     * @return
     */
    public static Date getDayEndTime(Date endTime){
        Calendar calendar = Calendar.getInstance();
        if(null==endTime){
            endTime = new Date();
        }
        calendar.setTime(endTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);

        Date end = calendar.getTime();
        return end;
    }

    /**
     * 指定字符串格式化为日期
     * @param time
     * @param pattern
     * @return
     */
    public static Date formatString2Date(String time, String pattern) {
        if (time == null) return null;
        SimpleDateFormat sf=new SimpleDateFormat(pattern);
        Date times = null;
        try {
            times = sf.parse(time);
        } catch (ParseException e) {

        }
        return times;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转为日期
     * @param time
     * @return
     */
    public static Date formatString2DateByDateTimePattern(String time){
        return formatString2Date(time,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化为字符串 yyyy-MM-dd HH:mm:ss , 如果data为null,返回当前时间
     * @param date
     * @return
     */
    public static String getTimestampString(Date date) {
        String str = "";
        if (null == date) {
            str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        } else {
            str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        }
        return str;
    }

    /**
     * 增加天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date,int day){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,day);
        return cal.getTime();
    }

    /**
     * 增加小时
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date,int hour){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }


    /**
     * 增加分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date,int minute){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期中的天
     * @param date
     * @return
     */
    public static int getDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个日期之间的天数差
     * @param minDate
     * @param maxDate
     * @return
     */
    public static int differentDays(Date minDate,Date maxDate){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(minDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(maxDate);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2){
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++){
                if(i%4==0 && i%100!=0 || i%400==0){
                    timeDistance += 366;
                }else{
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }else{
            return day2-day1;
        }
    }

}
