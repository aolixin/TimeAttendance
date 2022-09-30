package cn.mr.clock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author 龙星洛洛
 *
 */
public class DateTimeUtil {
	/**
	 * 返回当前时间，格式为时:分:秒
	 * 
	 * @return 时:分:秒
	 */
	public static String timeNow() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * 返回当前时间，格式为年-月-日
	 * 
	 * @return 年-月-日
	 */
	public static String dateNow() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 返回当前时间，格式为年-月-日 时:分:秒
	 * 
	 * @return 年-月-日 时:分:秒
	 */
	public static String dateTimeNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * Date 对象转化成 String
	 * @param date 指定Date 对象
	 * @return
	 */
	public static String Stringof(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 返回包含年，月，日，时，分，秒的Integer[6]
	 * 
	 * @return 保存年，月，日，时，分，秒的数组
	 */
	public static Integer[] now() {
		Integer now[] = new Integer[6];
		Calendar c = Calendar.getInstance();
		now[0] = c.get(Calendar.YEAR);
		now[1] = c.get(Calendar.MONTH) + 1;
		now[2] = c.get(Calendar.DAY_OF_MONTH);
		now[3] = c.get(Calendar.HOUR_OF_DAY);
		now[4] = c.get(Calendar.MINUTE);
		now[5] = c.get(Calendar.SECOND);
		return now;
	}

	/**
	 * 返回指定月的天数
	 * 
	 * @param year  指定年
	 * @param month 指定月
	 * @return 这个月的天数
	 */
	public static int getLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		// 返回这个月的最后一天
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 把字符串转换成Date对象
	 * 
	 * @param datetime 指定字符串
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date dateOf(String datetime) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
	}

	/**
	 * 把年月日时字符串转换成Date对象
	 * 
	 * @param year  指定年
	 * @param month 指定月
	 * @param day   指定日
	 * @param time  指定时间,要符合HH:mm:ss格式
	 * @return Date对象
	 * @throws ParseException
	 */
	public static Date dateOf(int year, int month, int day, String time) throws ParseException {
		String datetime = String.format("%4d-%02d-%02d %s", year, month, day, time);
		return dateOf(datetime);
	}

	/**
	 * 检测字符串time是否符合 HH:mm:ss格式
	 * 
	 * @param time 指定字符串
	 * @return
	 */
	public static boolean checkTimeStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			sdf.parse(time);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	/**
	 * 返回当前星期几 从星期一到星期日，依次返回1 - 7
	 * @return
	 */
	public static int getNowWeek() {
		Calendar now = Calendar.getInstance();
		//一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 * 返回指定日期的星期几
	 * @param date 指定日期
	 * @return
	 */
	public static int getTimeWeek(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		//一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 *  返回指定年月日的星期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static int getDateWeek(int year,int month,int day) throws ParseException {
		Date date = dateOf(year, month, day, "0:0:0");
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		//一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 * timeOne 是否早于 timeTwo
	 * @param timeOne 时间1 格式为年-月-日 时:分:秒
	 * @param timeTwo 时间2 格式为年-月-日 时:分:秒
	 * @return
	 * @throws ParseException
	 */
	public static boolean before(String timeOne,String timeTwo) throws ParseException {
		Date dateOne = dateOf(timeOne);
		Date dateTwo = dateOf(timeTwo);
		return dateOne.before(dateTwo);
	}
	/**
	 * timeOne 是否晚于 timeTwo
	 * @param timeOne 时间1 格式为年-月-日 时:分:秒
	 * @param timeTwo 时间2 格式为年-月-日 时:分:秒
	 * @return
	 * @throws ParseException
	 */
	public static boolean after(String timeOne,String timeTwo) throws ParseException {
		Date dateOne = dateOf(timeOne);
		Date dateTwo = dateOf(timeTwo);
		return dateOne.after(dateTwo);
	}
	/**
	 * 返回昨天的日期
	 * 包括年 月 日
	 * @return
	 */
	public static Integer[] yesterday() {
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.DATE, -1);
        Integer[] yesterday = new Integer[6];
        yesterday[0] = c.get(Calendar.YEAR);
        yesterday[1] = c.get(Calendar.MONTH) + 1;
        yesterday[2] = c.get(Calendar.DAY_OF_MONTH);
		return yesterday;
	}
	
}
