package com.cicada.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_MEDIUM = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_LONG_COMPACT = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_CONCRETE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT_YEAR_MONTH = "yyyyMM";
	public static final String DATE_FORMAT_YEAR_MONTH_DAY = "yyyyMMdd";
	public static final String DATE_FORMAT_RFC_822 = "EEE, d MMM yyyy HH:mm:ss Z";
	public static final String DATE_FORMAT_CUPS_DATE = "yyyyMMdd HH:mm:ss";
	public static final String DATE_FORMAT_COOKIE = "EEE, dd-MMM-yyyy HH:mm:ss 'GMT'";
	public static final String DATE_FORMAT_ORDER_TIPS = "HH:mm";
	public static final String DATE_FORMAT_MONTH_DAY_YEAR = "MM/dd/yyyy";
	
	private static final DateFormat DATE_FORMATTER_DEFAULT = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
	private static final DateFormat DATE_FORMATTER_MEDIUM = new SimpleDateFormat(DATE_FORMAT_MEDIUM);
	private static final DateFormat DATE_FORMATTER_LONG = new SimpleDateFormat(DATE_FORMAT_LONG);
	private static final DateFormat DATE_FORMATTER_LONG_COMPACT = new SimpleDateFormat(DATE_FORMAT_LONG_COMPACT);
	private static final DateFormat DATE_FORMATTER_CONCRETE = new SimpleDateFormat(DATE_FORMAT_CONCRETE);
	private static final DateFormat DATE_FORMATTER_YEAR_MONTH = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH);
	private static final DateFormat DATE_FORMATTER_YEAR_MONTH_DAY = new SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY);
	private static final DateFormat DATE_FORMATTER_RFC_822 = new SimpleDateFormat(DATE_FORMAT_RFC_822);
	private static final DateFormat DATE_FORMATTER_CUPS_DATE = new SimpleDateFormat(DATE_FORMAT_CUPS_DATE);
	private static final DateFormat DATE_FORMATTER_COOKIE = new SimpleDateFormat(DATE_FORMAT_COOKIE);
	private static final DateFormat DATE_FORMATTER_ORDER_TIPS = new SimpleDateFormat(DATE_FORMAT_ORDER_TIPS);
	private static final DateFormat DATE_FORMATTER_MONTH_DAY_YEAR = new SimpleDateFormat(DATE_FORMAT_MONTH_DAY_YEAR);
	
	public static String now() {
		return now(DATE_FORMAT_LONG);
	}

	public static String now(String dateFormat) {
		final DateFormat DF = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		return DF.format(cal.getTime());
	}

	public static Date parseDate(DateFormat formatter, String dateString, boolean throwExceptionOnError) {
		try {
			synchronized (formatter) {
				return formatter.parse(dateString);
			}
		} catch (Exception e) {
			final String msg = StringUtils.formatMessage("Parse date String \"{}\" failed.", dateString);
			if (log.isWarnEnabled()) {
				log.warn(msg, e);
			}
			if (throwExceptionOnError) {
				throw new RuntimeException(msg, e);
			} else {
				return null;
			}
		}
	}

	public static Date parseDateDefault(String dateString) {
		return parseDate(DATE_FORMATTER_DEFAULT, dateString, false);
	}

	public static Date parseDateMedium(String dateString) {

		return parseDate(DATE_FORMATTER_MEDIUM, dateString, false);
	}

	public static Date parseDateLong(String dateString) {
		return parseDate(DATE_FORMATTER_LONG, dateString, false);
	}

	public static Date parseDateLongCompact(String dateString) {
		return parseDate(DATE_FORMATTER_LONG_COMPACT, dateString, false);
	}

	public static Date parseDateConcrete(String dateString) {
		return parseDate(DATE_FORMATTER_CONCRETE, dateString, false);
	}

	public static Date parseDateYearMonth(String dateString) {
		return parseDate(DATE_FORMATTER_YEAR_MONTH, dateString, false);
	}

	public static Date parseDateYearMonthDay(String dateString) {
		return parseDate(DATE_FORMATTER_YEAR_MONTH_DAY, dateString, false);
	}

	public static Date parseDateRFC_822(String dateString) {
		return parseDate(DATE_FORMATTER_RFC_822, dateString, false);
	}

	public static Date parseDateCupsDate(String dateString) {
		return parseDate(DATE_FORMATTER_CUPS_DATE, dateString, false);
	}

	/**
	 * Parse date string and return date object, default format is: yyyy-mm-dd
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date parseDateAuto(String dateString) {
		if (isBlank(dateString)) {
			return null;
		} else {
			DateFormat[] formatters = new DateFormat[] { DATE_FORMATTER_MONTH_DAY_YEAR, DATE_FORMATTER_CUPS_DATE, DATE_FORMATTER_LONG,
					DATE_FORMATTER_LONG_COMPACT, DATE_FORMATTER_MEDIUM, DATE_FORMATTER_DEFAULT,
					DATE_FORMATTER_YEAR_MONTH, DATE_FORMATTER_YEAR_MONTH_DAY, DATE_FORMATTER_RFC_822 };

			Date result = null;
			for (DateFormat format : formatters) {
				result = parseDate(format, dateString, false);
				if (result != null) {
					break;
				}
			}

			if (result == null) {
				if (log.isWarnEnabled()) {
					log.warn(StringUtils.formatMessage("Auto parse date String \"{0}\" failed.", dateString));
				}
			}

			return result;
		}
	}

	/**
	 * Format a date object and return string representatives using the default format.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDefault(Date date) {
		return formatDate(date, DATE_FORMAT_DEFAULT);
	}

	public static String formatDateMedium(Date date) {
		return formatDate(date, DATE_FORMAT_MEDIUM);
	}

	public static String formatDateOrderTips(Date date) {
		return formatDate(date, DATE_FORMAT_ORDER_TIPS);
	}

	/**
	 * Format a date object and return string representatives using the long format.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateLong(Date date) {
		return formatDate(date, DATE_FORMAT_LONG);
	}

	public static String formatDateLongCompact(Date date) {
		return formatDate(date, DATE_FORMAT_LONG_COMPACT);
	}
	
	public static String formatDateConcrete(Date date) {
		return formatDate(date, DATE_FORMAT_CONCRETE);
	}

	public static String formatDateCupsDate(Date date) {
		return formatDate(date, DATE_FORMAT_CUPS_DATE);
	}

	/**
	 * Format a date object and return string representatives using the RFC 822 format.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateRFC822(Date date) {
		return formatDate(date, DATE_FORMAT_RFC_822);
	}

	public static String formatDateMonthYear(Date date) {
		return formatDate(date, DATE_FORMAT_YEAR_MONTH);
	}

	public static String formatDateMonthYearDAY(Date date) {
		return formatDate(date, DATE_FORMAT_YEAR_MONTH_DAY);
	}

	public static String formatDate4Cookie(int seconds) {
		Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
		calendar.add(Calendar.SECOND, seconds);
		synchronized (DATE_FORMATTER_COOKIE) {
			DATE_FORMATTER_COOKIE.setCalendar(calendar);
			return DATE_FORMATTER_COOKIE.format(calendar.getTime());
		}
	}

	/**
	 * Format a date object and return string representtatives using the given format.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(format)) {
			synchronized (DATE_FORMATTER_DEFAULT) {
				return DATE_FORMATTER_DEFAULT.format(date);
			}
		}
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * to the zero pointer in current day, which is like: 2009-12-12 00:00:00.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date toZeroInCurrentDay(Date date) {
		Date result = setHours(date, 0);
		result = setMinutes(result, 0);
		result = setSeconds(result, 0);
		result = setMilliseconds(result, 0);
		return result;
	}

	/**
	 * to the zero pointer in next day, which is like: 2009-12-13 00:00:00.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date toZeroInNextDay(Date date) {
		return addDays(toZeroInCurrentDay(date), 1);
	}

	/**
	 * return the time difference in milliseconds, which is: d1-d2
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long subtract(Date d1, Date d2) {
		return d1.getTime() - d2.getTime();
	}

	/**
	 * check if the credit card is expired
	 * 
	 * @param dateStr
	 *            : 1405 means 2014-05
	 * @return
	 */
	public static boolean isExpired(String dateStr) {
		if (StringUtils.isBlank(dateStr)) {
			return false;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Date date = sdf.parse("20" + dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); // next month
			cal.set(Calendar.DATE, 1);

			// System.out.println(cal.getTime());

			Calendar now = Calendar.getInstance();

			if (now.compareTo(cal) >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isInSameDay(Date d1, Date d2) {
		if(d1 == null && d2 == null){
			return true;
		}else if(d1 == null || d2 == null){
			return false;
		}
		d1 = toZeroInCurrentDay(d1);
		d2 = toZeroInCurrentDay(d2);
		if(d1.equals(d2)){
			return true;
		}else{
			return false;
		}
	}
	
}
