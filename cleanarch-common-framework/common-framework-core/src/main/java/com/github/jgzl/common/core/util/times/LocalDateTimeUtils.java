package com.github.jgzl.common.core.util.times;

import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.core.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JDK1.8 日期转换工具类
 *
 * @author lihaifeng
 * @version 1.0.0
 */
public class LocalDateTimeUtils {

    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(TimeConstants.FORMAT_YYYY_MM_DD);
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM);
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);

    public static String now() {
        return format(LocalDateTime.now(), TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static String now(String format) {
        return format(LocalDateTime.now(), format);
    }

    /**
     * String 日期转换成格式为 yyyy-MM-dd HH:mm:ss 的 LocalDateTime
     *
     * @param dateTime 日期
     * @return 转换结果
     */
    public static LocalDateTime parse(String dateTime) {
        return parse(dateTime, TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * String 日期转换成指定格式的 LocalDateTime
     *
     * @param dateTime 日期
     * @param format   转换的日期格式
     * @return 转换结果
     */
    public static LocalDateTime parse(String dateTime, String format) {
        if (StringUtils.isBlank(format)) {
            throw new CheckedException("format 不能为空");
        }
        if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD, format)) {
            return LocalDateTime.parse(dateTime, YYYY_MM_DD);
        } else if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM, format)) {
            return LocalDateTime.parse(dateTime, YYYY_MM_DD_HH_MM);
        } else if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS, format)) {
            return LocalDateTime.parse(dateTime, YYYY_MM_DD_HH_MM_SS);
        } else {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(format));
        }
    }

    /**
     * String 日期转换成格式为 yyyy-MM-dd HH:mm:ss 的 LocalDateTime
     *
     * @param localDateTime 日期
     * @return 转换结果
     */
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }


    public static String format(LocalDateTime localDateTime, String format) {
        if (StringUtils.isBlank(format)) {
            throw new CheckedException("format 不能为空");
        }
        if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD, format)) {
            return localDateTime.format(YYYY_MM_DD);
        } else if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM, format)) {
            return localDateTime.format(YYYY_MM_DD_HH_MM);
        } else if (StringUtils.equals(TimeConstants.FORMAT_YYYY_MM_DD_HH_MM_SS, format)) {
            return localDateTime.format(YYYY_MM_DD_HH_MM_SS);
        } else {
            return localDateTime.format(DateTimeFormatter.ofPattern(format));
        }
    }


}
