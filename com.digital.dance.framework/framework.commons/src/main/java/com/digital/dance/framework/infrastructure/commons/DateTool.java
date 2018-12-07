package com.digital.dance.framework.infrastructure.commons;

import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParsePosition;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *@author liuxiny
 *
 */
public class DateTool {
	private static Log logger = new Log(DateTool.class);

	public static final String P_DATE_COMPACT = "yyyyMMdd";
	public static final String P_YEARMONTH = "yyyyMM";
	public static final String P_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String P_DEFAULT = "yyyy-MM-dd";
	public static final String P_DAYPATH = "yyyy\\MM\\dd\\";
	public static final String P_DATESHORT = "yyMMdd";
	public static final String P_DATETIME_COMPACT = "yyyyMMddHHmmss";

	public static final String TS_FORMAT = getDatePattern() + " HH:mm:ss.S";
	private static final String MONTH_FORMAT = "yyyy-MM";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String HOUR_FORMAT = "HH:mm:ss";
	private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
	public static final String DAY_END_STRING_HHMMSS = " 23:59:59";
	private static SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");
	private static String defaultDatePattern = null;

	private static String timePattern = "HH:mm";
	private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat("HH:mm:ss");

	protected static SimpleDateFormat sdf_month_format = new SimpleDateFormat("yyyy-MM");

	private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static Calendar cale = Calendar.getInstance();

	private static final String[] MONTHS_STR_ARRY = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
			"Oct", "Nov", "Dec" };

	/**
	 *get year
	 *
	 *@param date
	 *@return
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 *get month
	 *
	 *@param date
	 *@return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 *get day
	 *
	 *@param date
	 *@return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 *get hour
	 *
	 *@param date
	 *@return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 *get minute
	 *
	 *@param date
	 *@return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 *get second
	 *
	 *@param date
	 *@return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 *get millis second
	 *
	 *@param date
	 *@return
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 *addDateWithDay
	 *
	 *@param date
	 *@param day
	 *@return
	 */
	public static Date addDateWithDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getMillis(date) + ((long) day) *24 *3600 *1000);
		return calendar.getTime();
	}

	/**
	 *addDateWithMilliSecond
	 *
	 *@param date
	 *@param day
	 *@return
	 */
	public static Date addDateWithMilliSecond(Date date, long milliSecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getMillis(date) + milliSecond);
		return calendar.getTime();
	}

	/**
	 *diffDate
	 *
	 *@param dateLeft
	 *@param dateRight
	 *@return
	 */
	public static int diffDate(Date dateLeft, Date dateRight) {
		return (int) ((getMillis(dateLeft) - getMillis(dateRight)) / (24 *3600 *1000));
	}

	/**
	 *diffDateInMillis
	 *
	 *@param dateLeft
	 *@param dateRight
	 *@return
	 */
	public static long diffDateInMillis(Date dateLeft, Date dateRight) {
		return (long) ((getMillis(dateLeft) - getMillis(dateRight)));
	}

	/**
	 *
	 *getDateTimeStr
	 *
	 *
	 *@param date
	 *
	 *@return yyyy-MM-dd HH:mm:ss
	 *
	 */
	public static String getDateTimeStr(Date date) {
		return formateDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 *getDateStr
	 *
	 *
	 *@param date
	 *
	 *@return yyyy-MM-dd
	 */
	public static String getDateStr(Date date) {
		return formateDate(date, "yyyy-MM-dd");
	}

	/**
	 *getCurrentDateTime
	 *
	 *
	 *@return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateTime() {
		return formateDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 *getCurrentDate
	 *
	 *
	 *@return yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		return formateDate(new Date(), "yyyy-MM-dd");
	}

	/**
	 *getDateFromLongStr
	 *
	 *@param strDate
	 *yyyy-MM-dd HH:mm:ss
	 *@return
	 */
	public static Date getDateFromLongStr(String strDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition position = new ParsePosition(0);
		Date strtodate = simpleDateFormat.parse(strDate, position);
		return strtodate;
	}

	/**
	 *getDateFromStr
	 *
	 *@param strDate
	 *yyyy-MM-dd
	 *
	 *@return
	 */
	public static Date getDateFromStr(String strDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition position = new ParsePosition(0);
		Date strtodate = simpleDateFormat.parse(strDate, position);
		return strtodate;
	}

	/**
	 *getYearDays
	 *
	 *@param date
	 *日期
	 *
	 *@return 返回 366或365
	 */
	public static int getYearDays(Date date) {
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		return gregorianCalendar.isLeapYear(gregorianCalendar.get(1)) ? 366 : '\u016D';
	}

	/**
	 *getMonthDays
	 *
	 *@param date
	 *@return 返回date 在月份的天数
	 */
	public static int getMonthDays(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(5);
	}

	/**
	 *compareDateTime，相等true，不等false
	 *
	 *@param dateLeft
	 *
	 *@param dateRight
	 *
	 *@return
	 */
	public static boolean compareDateTime(Date dateLeft, Date dateRight) {
		return !dateLeft.before(dateRight) && !dateRight.before(dateLeft);
	}

	/**
	 *compareDay，相等true，不等false
	 *
	 *@param dateLeft
	 *@param dateRight
	 *@return
	 */
	public static boolean compareDay(Date dateLeft, Date dateRight) {
		Calendar calalendar1 = Calendar.getInstance();
		calalendar1.setTime(dateLeft);
		Calendar calalendar2 = Calendar.getInstance();
		calalendar2.setTime(dateRight);
		return calalendar1.get(0) == calalendar2.get(0) && calalendar1.get(1) == calalendar2.get(1)
				&& calalendar1.get(6) == calalendar2.get(6);
	}

	/**
	 *parseDateUTC
	 *
	 *@param utcDate
	 *UTC格式的日期
	 *@return
	 */
	public static Date parseDateUTC(String utcDate) {
		utcDate = utcDate.substring(4);
		utcDate = utcDate.replace("UTC 0800 ", "");
		for (int i = 0; i < MONTHS_STR_ARRY.length; i++) {
			if (!utcDate.startsWith(MONTHS_STR_ARRY[i]))
				continue;
			utcDate = utcDate.replace(MONTHS_STR_ARRY[i], String.valueOf(i + 1));
			break;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd HH:mm:ss yyyy");
		try {
			return simpleDateFormat.parse(utcDate);
		} catch (ParseException e) {
			// e.printStackTrace();
			logger.error(DateTool.class + "-parseDateUTC:", e);
			return null;
		}

	}

	/**
	 *getEndDay
	 *
	 *
	 *@param date
	 *@return yyyy-MM-dd 23:59:59
	 */
	public static String getEndDay(Date date) {
		String dateStr = String.valueOf(getDateStr(date));
		return (new StringBuilder(dateStr)).append(" 23:59:59").toString();
	}

	/**
	 *getEndOfYearStr，格式为：2001-12-31
	 *
	 *@return
	 */
	public static String getEndOfYear() {
		Calendar calalendar1 = Calendar.getInstance();
		return (new StringBuilder(String.valueOf(calalendar1.get(1)))).append("-12-31").toString();
	}

	/**
	 *getSimpleDateTime with simple formatted string, such as '2012-09-14
	 *11:12:00'.
	 *
	 *@param date
	 *@return
	 */
	public static String getSimpleDateTime(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(P_DATETIME);
		return simpleDateFormat.format(date);
	}

	/**
	 *parseSimpleDateTime to Date object.
	 *
	 *@param dateTime
	 *@return
	 */
	public static Date parseSimpleDateTime(String dateTime) {
		if (dateTime == null || dateTime.trim().equals("")) {
			return null;
		}

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(P_DATETIME);
			return simpleDateFormat.parse(dateTime);
		} catch (ParseException e) {
			logger.error(DateTool.class + "-parseSimpleDateTime:", e);
			return null;
		}
	}

	/**
	 *get the DateTime Days Ago, e.g. 30 days ago (30 days).
	 *
	 *@param days
	 *@return
	 */
	public static Date getDateTimeDaysAgo(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.add(Calendar.DAY_OF_MONTH, 0 - days);

		return calendar.getTime();
	}

	/**
	 * 字条串转DATE
	 *
	 *@author niezm;
	 *@since 2015-3-27 下午5:07:48
	 */
	public static Date parseDate(String dateStr, String formate) {
		Date date = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formate);
			date = formatter.parse(dateStr);
		} catch (Exception e) {
			logger.error(DateTool.class + "-parseDate:", e);
			// e.printStackTrace();
		}
		return date;
	}

	/**
	 * DATE转字符串
	 *
	 *@author niezm;
	 *@since 2015-3-27 下午5:07:50
	 */
	public static String formateDate(Date date, String formate) {
		String dateStr = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
			dateStr = simpleDateFormat.format(date);
		} catch (Exception e) {
			logger.error(DateTool.class + "-formateDate:", e);
			// e.printStackTrace();
		}
		return dateStr;
	}

	public static String getMonthLastDay(int month) {
		Date date = new Date();
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

		int year = date.getYear() + 1900;
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			return day[1][month] + "";
		}
		return day[0][month] + "";
	}

	public static String getMonthLastDay(int year, int month) {
		int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };

		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			return day[1][month] + "";
		}
		return day[0][month] + "";
	}

	public static boolean isLeapyear(int year) {
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}

	public static Date counvertTimestampToDate(String aMask, Long timestamp) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (logger.isDebugEnabled())
			logger.debug("converting '" + timestamp + "' to date with mask '" + aMask + "'");
		try {
			date = df.parse(df.format(timestamp));
		} catch (ParseException pe) {
			logger.error("ParseException: " + pe);
			throw pe;
		}
		return date;
	}

	public static String getTimestamp() {
		Date date = cale.getTime();
		String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes()
				+ date.getSeconds() + date.getTime();

		return timestamp;
	}

	public static String getTimestamp(Date date) {
		String timestamp = "" + (date.getYear() + 1900) + date.getMonth() + date.getDate() + date.getMinutes()
				+ date.getSeconds() + date.getTime();

		return timestamp;
	}

	public static Date getStartTime() {
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(11, 0);
		todayStart.set(12, 0);
		todayStart.set(13, 0);
		todayStart.set(14, 0);
		return todayStart.getTime();
	}

	public static Date getEndTime() {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(11, 23);
		todayEnd.set(12, 59);
		todayEnd.set(13, 59);
		todayEnd.set(14, 999);
		return todayEnd.getTime();
	}

	public static String getDateTime() {
		try {
			return sdf_datetime_format.format(cale.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.getDateTime():" + e.getMessage());
		}
		return "";
	}

	public static String getDate() {
		try {
			return sdf_date_format.format(cale.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.getDate():" + e.getMessage());
		}
		return "";
	}

	public static String getTime() {
		String temp = " ";
		try {
			return temp + sdf_hour_format.format(cale.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.getTime():" + e.getMessage());
		}
		return "";
	}

	public static int getMargin(String date1, String date2) {
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdf_date_format.parse(date1, pos);
			Date dt2 = sdf_date_format.parse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			return (int) (l / 86400000L);
		} catch (Exception e) {
			logger.debug("DateTool.getMargin():" + e.toString());
		}
		return 0;
	}

	public static double getDoubleMargin(String date1, String date2) {
		try {
			ParsePosition pos = new ParsePosition(0);
			ParsePosition pos1 = new ParsePosition(0);
			Date dt1 = sdf_datetime_format.parse(date1, pos);
			Date dt2 = sdf_datetime_format.parse(date2, pos1);
			long l = dt1.getTime() - dt2.getTime();
			return l / 86400000.0D;
		} catch (Exception e) {
			logger.debug("DateTool.getMargin():" + e.toString());
		}
		return 0.0D;
	}

	public static int getMonthMargin(String date1, String date2) {
		try {
			int margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) *12;
			return margin + (Integer.parseInt(date2.substring(4, 7).replaceAll("-0", "-"))
					- Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
		} catch (Exception e) {
			logger.debug("DateTool.getMargin():" + e.toString());
		}
		return 0;
	}

	public static String addDay(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

			gCal.add(5, i);
			return sdf_date_format.format(gCal.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.addDay():" + e.toString());
		}
		return getDate();
	}

	public static String addMonth(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

			gCal.add(2, i);
			return sdf_date_format.format(gCal.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.addMonth():" + e.toString());
		}
		return getDate();
	}

	public static String addYear(String date, int i) {
		try {
			GregorianCalendar gCal = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)));

			gCal.add(1, i);
			return sdf_date_format.format(gCal.getTime());
		} catch (Exception e) {
			logger.debug("DateTool.addYear():" + e.toString());
		}
		return "";
	}

	public static int getMaxDay(int iyear, int imonth) {
		int day = 0;
		try {
			if ((imonth == 1) || (imonth == 3) || (imonth == 5) || (imonth == 7) || (imonth == 8) || (imonth == 10)
					|| (imonth == 12)) {
				day = 31;
			} else if ((imonth == 4) || (imonth == 6) || (imonth == 9) || (imonth == 11))
				day = 30;
			else if (((0 == iyear % 4) && (0 != iyear % 100)) || (0 == iyear % 400)) {
				day = 29;
			}
			return 28;
		} catch (Exception e) {
			logger.debug("DateTool.getMonthDay():" + e.toString());
		}
		return 1;
	}

	public String rollDate(String orgDate, int Type, int Span) {
		try {
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

	public static String rollDate(Calendar cal, int Type, int Span) {
		try {
			String temp = "";

			Calendar rolcale = cal;
			rolcale.add(Type, Span);
			return sdf_date_format.format(rolcale.getTime());
		} catch (Exception e) {
		}
		return "";
	}

	public static synchronized String getDatePattern() {
		defaultDatePattern = "yyyy-MM-dd";
		return defaultDatePattern;
	}

	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}
		return returnValue;
	}

	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	public Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	public static final String getDateTime(String aMask, Date aDate) {
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

	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (logger.isDebugEnabled())
			logger.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			logger.error("ParseException: " + pe);
			throw pe;
		}
		return date;
	}

	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;
		try {
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

	public static String getSimpleDateFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat();
		String NDateTime = formatter.format(new Date());
		return NDateTime;
	}

	public static int compareToCurTime(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return -1;
		}
		Date curTime = cale.getTime();
		String strCurTime = null;
		try {
			strCurTime = sdf_datetime_format.format(curTime);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("[Could not format '" + strDate + "' to a date, throwing exception:"
						+ e.getLocalizedMessage() + "]");
			}
		}
		if (StringUtils.isNotBlank(strCurTime)) {
			return strCurTime.compareTo(strDate);
		}
		return -1;
	}
}
