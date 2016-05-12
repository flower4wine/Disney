package com.disney.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
/*	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";*/
	
	private static DateUtils date;

	public static SimpleDateFormat format = new SimpleDateFormat(DateFormat.SIMPLE);
	public static SimpleDateFormat format1 = new SimpleDateFormat(DateFormat.FULL);

	
	public static void main(String[] args) throws Exception {
		/*Date start = format1.parse("2016-01-31 11:59:59");
		Date end = format1.parse("2015-08-07 13:59:59");
		
		Date startEnd = DateUtils.getFinallyDate(start);
		Date endStart = DateUtils.getStartDate(end);
		
		long oneDay = 1000*24*60*60;
		start = DateUtils.longToDate(start.getTime()+oneDay);
		//
		System.out.println(DateUtils.dateToString(start, DateFormat.FULL));
		System.out.println(DateUtils.nextMonth(start));*/
	}
	
	//整数(秒数)转换为时分格式(xx天xx小时xx分钟)
	 public static String secToTime(int time) {  
	        String timeStr = null;  
	        int hour = 0;  
	        int minute = 0;  
	        int day = 0;
	        if (time <= 0)  
	            return "0分钟";  
	        else {  
	            minute = time / 60;  
	            if (minute < 60) {  
	                timeStr = unitFormat(minute) + "分钟";  
	            } else {  
	                hour = minute / 60;  
	                if (hour < 24){
	                	minute = minute % 60;  
		                timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分钟";
	                }else{
	                	day = hour / 24;
	                	hour = hour % 24;
	                	minute = minute % 60; 
		                timeStr = unitFormat(day) + "天" + unitFormat(hour) + "小时" + unitFormat(minute) + "分钟";
	                }
	            }  
	        }  
	        return timeStr;  
	 }
	 
	 public static String unitFormat(int i) {  
	        String retStr = null;  
	        if (i >= 0 && i < 10)  
	            retStr = "0" + Integer.toString(i);  
	        else  
	            retStr = "" + i;  
	        return retStr;  
	 }  
	
	
	public static Timestamp getNowTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static int distanceDays(Date start,Date end){
		return  (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public static int distanceHours(Date start,Date end){
		return (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60));
	}
	
	public static int distanceMinute(Date start,Date end){
		return (int) ((end.getTime() - start.getTime()) / (1000 * 60));
	}
	
	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return date1>date2: 1 | date1<date2: -1 | date1==date2: 0
	 */
	public static int compareDate(Date date1, Date date2) throws Exception {
		if (null == date1 || null == date2) {
			throw new Exception("date1 and date2 can not be null");
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		if (year1 > year2) {
			return 1;
		} else if (year1 < year2) {
			return -1;
		} else {
			if (month1 > month2) {
				return 1;
			} else if (month1 < month2) {
				return -1;
			} else {
				if (day1 > day2) {
					return 1;
				} else if (day1 < day2) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * 获得两个日期相差天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDateDifference(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			return 0;
		}

		try {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			int year1 = 0;
			int month1 = 0;
			int day1 = 0;

			c2.setTime(endDate);
			int year2 = c2.get(Calendar.YEAR);
			int month2 = c2.get(Calendar.MONTH);
			int day2 = c2.get(Calendar.DAY_OF_MONTH);

			int pointer = -1;
			int compareResult = compareDate(beginDate, endDate);
			switch (compareResult) {
			case 0:
				return 0;
			case -1:
				do {
					pointer++;
					c1.setTime(beginDate);
					c1.add(Calendar.DAY_OF_MONTH, pointer);
					year1 = c1.get(Calendar.YEAR);
					month1 = c1.get(Calendar.MONTH);
					day1 = c1.get(Calendar.DAY_OF_MONTH);
				} while (year1 != year2 || month1 != month2 || day1 != day2);
				return pointer;
			case 1:
				return 0;
			default:
				return 0;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 计算两个日期之间的天数差
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @author Bowen.Deng
	 * @creation 2015年9月1日 上午11:51:44
	 */
	public static int getDifferenceOfDays(Date start, Date end) {
		int differenceOfDays;
		if (start == null || end == null) {
			differenceOfDays = 0;
		} else {
			start = truncate(start, Calendar.DAY_OF_MONTH);
			end = truncate(end, Calendar.DAY_OF_MONTH);
			long startTime = start.getTime(), endTime = end.getTime();

			if (endTime < startTime) {
				differenceOfDays = 0;
			} else {
				differenceOfDays = (int) ((endTime - startTime) / (1000 * 3600 * 24)) + 1;
			}
		}
		return differenceOfDays;
	}



	public static Date longToDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		Date date = cal.getTime();
		return date;
	}

	public static void printDate(Date date) {
		/* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); */
		System.out.println(format1.format(date));
	}

	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		// printDate(cal.getTime());
		return cal.getTime();
	}

	public static Date nextDay(Date date) {
		long time = date.getTime() + 24 * 60 * 60 * 1000;
		return longToDate(time);
	}

	
	public static Date stringToDate(String stringDate,String dateFormat) {
		if (StringUtils.isEmpty(stringDate))
			return null;

		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		try {
			Date date = (Date) format.parse(stringDate);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * yyyy-MM-dd to date
	 * 
	 * @param stringDate
	 * @return
	 */
	public static Date stringToDate(String stringDate) {
		return stringToDate(stringDate, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获得下个月一号
	 * 
	 * @return
	 */
	public static Date nextMonthFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 日期加一个月
	 * 
	 * @return
	 */
	public static Date nextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/*public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}
*/
	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {
		/*
		 * int bDay=getDateField(begin,Calendar.DAY_OF_YEAR); int
		 * eDay=getDateField(end,Calendar.DAY_OF_YEAR); return (eDay-bDay)+;
		 */

		long time = getStartDate(end).getTime() - getStartDate(begin).getTime();
		int day = (int) (time / (24 * 60 * 60 * 1000));

		return day;
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得时间 小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		
		if(date == null){
			return -1;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/*private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}*/

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}

	public static Integer calcDateProcess(Date start, Date end) {

		if (start == null || end == null) {
			return 0;
		}

		int process = 0;
		Date today = new Date();

		int days = DateUtils.getDaysBetweenDate(start,
				DateUtils.stringToDate(DateUtils.dateToString(today, "yyyy-MM-dd")));
		int distance = DateUtils.getDaysBetweenDate(start, end);

		if (days <= 0) {
			process = 0;
		} else if (distance == 0) {
			process = 100;
		} else if (end.getTime() < today.getTime()) {
			process = 100;
		} else {
			double percent = (days * 1.0d) * 100 / distance;
			process = (int) percent;
		}

		return process;
	}

}
