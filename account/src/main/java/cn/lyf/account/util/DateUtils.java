package cn.lyf.account.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    public static final String  YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
    public static final String  YYYYMMDD="yyyyMMdd";
    public static final String  YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        ParsePosition pos = new ParsePosition(8);
        Date currentTime = formatter.parse(dateString, pos);
        return currentTime;
    }
    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date());
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * date类型转指定格式String类型
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 比较两个时间大小（指定格式）
     * d1>d2时返回true，否则返回false
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean compareDate(Date d1,Date d2,String fomart){
        Boolean flag=false;
        String  begainDate = DateUtils.dateToString(d1,fomart);
        String  endDate = DateUtils.dateToString(d2,fomart);
        if(Integer.valueOf(endDate)<Integer.valueOf(begainDate)){
            flag = true;
        }
        return flag;

    }

    /**
     * 比较两个时间大小（指定格式）
     * d1>=d2时返回true，否则返回false
     * @param d1
     * @param d2
     * @param fomart
     * @return
     */
    public static Boolean compareDate1(Date d1,Date d2,String fomart){
        Boolean flag=false;
        String  begainDate = DateUtils.dateToString(d1,fomart);
        String  endDate = DateUtils.dateToString(d2,fomart);
        if(Integer.valueOf(endDate)<=Integer.valueOf(begainDate)){
            flag = true;
        }
        return flag;
    }

    /**
     * 获取某个日期的后一天日期
     * @param now
     * @return
     */
    public  static Date nextDay(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1当前的时间加一天
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 某个日期加上某个天数后的日期的毫秒数
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, Integer day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }



    public static Date stringToDate(String str,String format) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

}
