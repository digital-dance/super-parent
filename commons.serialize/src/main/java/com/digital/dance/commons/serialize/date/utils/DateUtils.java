package com.digital.dance.commons.serialize.date.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils
{
  private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

  private static String defaultDatePattern = null;

  private static String timePattern = "HH:mm";

  private static Calendar cale = Calendar.getInstance();

  public static final String TS_FORMAT = getDatePattern() + " HH:mm:ss.S";
  private static final String MONTH_FORMAT = "yyyy-MM";
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String HOUR_FORMAT = "HH:mm:ss";
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
  public static final String DAY_END_STRING_HHMMSS = " 23:59:59";
  private static SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");

  private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat("HH:mm:ss");

  protected static SimpleDateFormat sdf_month_format = new SimpleDateFormat("yyyy-MM");

  private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static String getDateTime()
  {
    try
    {
      return sdf_datetime_format.format(cale.getTime());
    } catch (Exception e) {
      logger.debug("DateUtil.getDateTime():" + e.getMessage());
    }return "";
  }

  public static String getDate()
  {
    try
    {
      return sdf_date_format.format(cale.getTime());
    } catch (Exception e) {
      logger.debug("DateUtil.getDate():" + e.getMessage());
    }return "";
  }

  public static String getTime()
  {
    String temp = " ";
    try {
      return temp + sdf_hour_format.format(cale.getTime());
    }
    catch (Exception e) {
      logger.debug("DateUtil.getTime():" + e.getMessage());
    }return "";
  }

  public static String getYear()
  {
    try
    {
      return String.valueOf(cale.get(1));
    } catch (Exception e) {
      logger.debug("DateUtil.getYear():" + e.getMessage());
    }return "";
  }

  public static String getMonth()
  {
    try
    {
      DecimalFormat df = new DecimalFormat();
      df.applyPattern("00;00");
      return df.format(cale.get(2) + 1);
    } catch (Exception e) {
      logger.debug("DateUtil.getMonth():" + e.getMessage());
    }return "";
  }

  public static String getDay()
  {
    try
    {
      return String.valueOf(cale.get(5));
    } catch (Exception e) {
      logger.debug("DateUtil.getDay():" + e.getMessage());
    }return "";
  }

  public static int getMargin(String date1, String date2)
  {
    try
    {
      ParsePosition pos = new ParsePosition(0);
      ParsePosition pos1 = new ParsePosition(0);
      Date dt1 = sdf_date_format.parse(date1, pos);
      Date dt2 = sdf_date_format.parse(date2, pos1);
      long l = dt1.getTime() - dt2.getTime();
      return (int)(l / 86400000L);
    }
    catch (Exception e) {
      logger.debug("DateUtil.getMargin():" + e.toString());
    }return 0;
  }

  public static double getDoubleMargin(String date1, String date2)
  {
    try
    {
      ParsePosition pos = new ParsePosition(0);
      ParsePosition pos1 = new ParsePosition(0);
      Date dt1 = sdf_datetime_format.parse(date1, pos);
      Date dt2 = sdf_datetime_format.parse(date2, pos1);
      long l = dt1.getTime() - dt2.getTime();
      return l / 86400000.0D;
    }
    catch (Exception e) {
      logger.debug("DateUtil.getMargin():" + e.toString());
    }return 0.0D;
  }

  public static int getMonthMargin(String date1, String date2)
  {
    try
    {
      int margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
      return margin + (Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-")) - Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
    }
    catch (Exception e)
    {
      logger.debug("DateUtil.getMargin():" + e.toString());
    }return 0;
  }

  public static String addDay(String date, int i)
  {
    try
    {
      GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

      gCal.add(5, i);
      return sdf_date_format.format(gCal.getTime());
    } catch (Exception e) {
      logger.debug("DateUtil.addDay():" + e.toString());
    }return getDate();
  }

  public static String addMonth(String date, int i)
  {
    try
    {
      GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

      gCal.add(2, i);
      return sdf_date_format.format(gCal.getTime());
    } catch (Exception e) {
      logger.debug("DateUtil.addMonth():" + e.toString());
    }return getDate();
  }

  public static String addYear(String date, int i)
  {
    try
    {
      GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

      gCal.add(1, i);
      return sdf_date_format.format(gCal.getTime());
    } catch (Exception e) {
      logger.debug("DateUtil.addYear():" + e.toString());
    }return "";
  }

  public static int getMaxDay(int iyear, int imonth)
  {
    int day = 0;
    try {
      if ((imonth == 1) || (imonth == 3) || (imonth == 5) || (imonth == 7) || (imonth == 8) || (imonth == 10) || (imonth == 12))
      {
        day = 31;
      } else if ((imonth == 4) || (imonth == 6) || (imonth == 9) || (imonth == 11))
        day = 30;
      else if (((0 == iyear % 4) && (0 != iyear % 100)) || (0 == iyear % 400)) {
        day = 29;
      }
      return 28;
    }
    catch (Exception e)
    {
      logger.debug("DateUtil.getMonthDay():" + e.toString());
    }return 1;
  }

  public String rollDate(String orgDate, int Type, int Span)
  {
    try
    {
      String temp = "";

      int iPos = 0;
      char seperater = '-';
      if ((orgDate == null) || (orgDate.length() < 6)) {
        return "";
      }

      iPos = orgDate.indexOf(seperater);
      int iyear;
      if (iPos > 0) {
        iyear = Integer.parseInt(orgDate.substring(0, iPos));
        temp = orgDate.substring(iPos + 1);
      } else {
        iyear = Integer.parseInt(orgDate.substring(0, 4));
        temp = orgDate.substring(4);
      }

      iPos = temp.indexOf(seperater);
      int imonth;
      if (iPos > 0) {
        imonth = Integer.parseInt(temp.substring(0, iPos));
        temp = temp.substring(iPos + 1);
      } else {
        imonth = Integer.parseInt(temp.substring(0, 2));
        temp = temp.substring(2);
      }

      imonth--;
      if ((imonth < 0) || (imonth > 11)) {
        imonth = 0;
      }

      int iday = Integer.parseInt(temp);
      if ((iday < 1) || (iday > 31)) {
        iday = 1;
      }
      Calendar orgcale = Calendar.getInstance();
      orgcale.set(iyear, imonth, iday);
      return rollDate(orgcale, Type, Span);
    } catch (Exception e) {
    }
    return "";
  }

  public static String rollDate(Calendar cal, int Type, int Span)
  {
    try {
      String temp = "";

      Calendar rolcale = cal;
      rolcale.add(Type, Span);
      return sdf_date_format.format(rolcale.getTime());
    } catch (Exception e) {
    }
    return "";
  }

  public static synchronized String getDatePattern()
  {
    defaultDatePattern = "yyyy-MM-dd";
    return defaultDatePattern;
  }

  public static final String getDate(Date aDate)
  {
    SimpleDateFormat df = null;
    String returnValue = "";
    if (aDate != null) {
      df = new SimpleDateFormat(getDatePattern());
      returnValue = df.format(aDate);
    }
    return returnValue;
  }

  public static String getTimeNow(Date theTime)
  {
    return getDateTime(timePattern, theTime);
  }

  public Calendar getToday()
    throws ParseException
  {
    Date today = new Date();
    SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
    String todayAsString = df.format(today);
    Calendar cal = new GregorianCalendar();
    cal.setTime(convertStringToDate(todayAsString));
    return cal;
  }

  public static final String getDateTime(String aMask, Date aDate)
  {
    SimpleDateFormat df = null;
    String returnValue = "";

    if (aDate == null) {
      logger.error("aDate is null!");
    } else {
      df = new SimpleDateFormat(aMask);
      returnValue = df.format(aDate);
    }
    return returnValue;
  }

  public static final String convertDateToString(Date aDate)
  {
    return getDateTime(getDatePattern(), aDate);
  }

  public static final Date convertStringToDate(String aMask, String strDate)
    throws ParseException
  {
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(aMask);

    if (logger.isDebugEnabled())
      logger.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
    try
    {
      date = df.parse(strDate);
    } catch (ParseException pe) {
      logger.error("ParseException: " + pe);
      throw pe;
    }
    return date;
  }

  public static Date convertStringToDate(String strDate)
    throws ParseException
  {
    Date aDate = null;
    try
    {
      if (logger.isDebugEnabled()) {
        logger.debug("converting date with pattern: " + getDatePattern());
      }
      aDate = convertStringToDate(getDatePattern(), strDate);
    } catch (ParseException pe) {
      logger.error("Could not convert '" + strDate + "' to a date, throwing exception");
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }
    return aDate;
  }

  public static String getSimpleDateFormat()
  {
    SimpleDateFormat formatter = new SimpleDateFormat();
    String NDateTime = formatter.format(new Date());
    return NDateTime;
  }

  public static int compareToCurTime(String strDate)
  {
    if (StringUtils.isBlank(strDate)) {
      return -1;
    }
    Date curTime = cale.getTime();
    String strCurTime = null;
    try {
      strCurTime = sdf_datetime_format.format(curTime);
    } catch (Exception e) {
      if (logger.isDebugEnabled()) {
        logger.debug("[Could not format '" + strDate + "' to a date, throwing exception:" + e.getLocalizedMessage() + "]");
      }
    }
    if (StringUtils.isNotBlank(strCurTime)) {
      return strCurTime.compareTo(strDate);
    }
    return -1;
  }

  public static Date addStartTime(Date param)
  {
    Date date = param;
    try {
      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);
      return date; } catch (Exception ex) {
    }
    return date;
  }

  public static Date addEndTime(Date param)
  {
    Date date = param;
    try {
      date.setHours(23);
      date.setMinutes(59);
      date.setSeconds(0);
      return date; } catch (Exception ex) {
    }
    return date;
  }

  public static String getMonthLastDay(int month)
  {
    Date date = new Date();
    int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

    int year = date.getYear() + 1900;
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
      return day[1][month] + "";
    }
    return day[0][month] + "";
  }

  public static String getMonthLastDay(int year, int month)
  {
    int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
      return day[1][month] + "";
    }
    return day[0][month] + "";
  }

  public static boolean isLeapyear(int year)
  {
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
      return true;
    }
    return false;
  }

  public static Date counvertTimestampToDate(String aMask, Long timestamp)
    throws ParseException
  {
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(aMask);

    if (logger.isDebugEnabled())
      logger.debug("converting '" + timestamp + "' to date with mask '" + aMask + "'");
    try
    {
      date = df.parse(df.format(timestamp));
    } catch (ParseException pe) {
      logger.error("ParseException: " + pe);
      throw pe;
    }
    return date;
  }

  public static String getTimestamp()
  {
    Date date = cale.getTime();
    String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes() + date.getSeconds() + date.getTime();

    return timestamp;
  }

  public static String getTimestamp(Date date)
  {
    String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes() + date.getSeconds() + date.getTime();

    return timestamp;
  }

  public static Date getStartTime()
  {
    Calendar todayStart = Calendar.getInstance();
    todayStart.set(11, 0);
    todayStart.set(12, 0);
    todayStart.set(13, 0);
    todayStart.set(14, 0);
    return todayStart.getTime();
  }

  public static Date getEndTime()
  {
    Calendar todayEnd = Calendar.getInstance();
    todayEnd.set(11, 23);
    todayEnd.set(12, 59);
    todayEnd.set(13, 59);
    todayEnd.set(14, 999);
    return todayEnd.getTime();
  }
}