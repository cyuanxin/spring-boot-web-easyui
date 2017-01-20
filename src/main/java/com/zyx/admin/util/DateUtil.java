/**
 *
 */
package com.zyx.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {
    public static Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static final String YYYY_MM_DD_MM_HH_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYYMMDDMMHHSSSSS = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";


    /**
     * 时间转换为dateFormat格式的字符串
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
    public static String dateToString(Date date){
        return dateToString(date, YYYY_MM_DD_MM_HH_SS);
    }

}
