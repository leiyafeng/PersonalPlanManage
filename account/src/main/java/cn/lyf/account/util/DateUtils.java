package cn.lyf.account.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    public static final String  YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
    public static final String  YYYYMMDD="yyyyMMdd";

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

}
