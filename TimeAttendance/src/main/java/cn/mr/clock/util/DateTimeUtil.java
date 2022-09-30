package cn.mr.clock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ʱ�乤����
 * 
 * @author ��������
 *
 */
public class DateTimeUtil {
	/**
	 * ���ص�ǰʱ�䣬��ʽΪʱ:��:��
	 * 
	 * @return ʱ:��:��
	 */
	public static String timeNow() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * ���ص�ǰʱ�䣬��ʽΪ��-��-��
	 * 
	 * @return ��-��-��
	 */
	public static String dateNow() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * ���ص�ǰʱ�䣬��ʽΪ��-��-�� ʱ:��:��
	 * 
	 * @return ��-��-�� ʱ:��:��
	 */
	public static String dateTimeNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	/**
	 * Date ����ת���� String
	 * @param date ָ��Date ����
	 * @return
	 */
	public static String Stringof(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * ���ذ����꣬�£��գ�ʱ���֣����Integer[6]
	 * 
	 * @return �����꣬�£��գ�ʱ���֣��������
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
	 * ����ָ���µ�����
	 * 
	 * @param year  ָ����
	 * @param month ָ����
	 * @return ����µ�����
	 */
	public static int getLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		// ��������µ����һ��
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ���ַ���ת����Date����
	 * 
	 * @param datetime ָ���ַ���
	 * @return Date����
	 * @throws ParseException
	 */
	public static Date dateOf(String datetime) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
	}

	/**
	 * ��������ʱ�ַ���ת����Date����
	 * 
	 * @param year  ָ����
	 * @param month ָ����
	 * @param day   ָ����
	 * @param time  ָ��ʱ��,Ҫ����HH:mm:ss��ʽ
	 * @return Date����
	 * @throws ParseException
	 */
	public static Date dateOf(int year, int month, int day, String time) throws ParseException {
		String datetime = String.format("%4d-%02d-%02d %s", year, month, day, time);
		return dateOf(datetime);
	}

	/**
	 * ����ַ���time�Ƿ���� HH:mm:ss��ʽ
	 * 
	 * @param time ָ���ַ���
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
	 * ���ص�ǰ���ڼ� ������һ�������գ����η���1 - 7
	 * @return
	 */
	public static int getNowWeek() {
		Calendar now = Calendar.getInstance();
		//һ�ܵ�һ���Ƿ�Ϊ������
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//��ȡ�ܼ�
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//��һ�ܵ�һ��Ϊ�����죬��-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 * ����ָ�����ڵ����ڼ�
	 * @param date ָ������
	 * @return
	 */
	public static int getTimeWeek(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		//һ�ܵ�һ���Ƿ�Ϊ������
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//��ȡ�ܼ�
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//��һ�ܵ�һ��Ϊ�����죬��-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 *  ����ָ�������յ�����
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
		//һ�ܵ�һ���Ƿ�Ϊ������
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//��ȡ�ܼ�
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//��һ�ܵ�һ��Ϊ�����죬��-1
		if(isFirstSunday){
			weekDay = weekDay - 1;
		    if(weekDay == 0){
			    weekDay = 7;
			}
		}
		return weekDay;
	}
	/**
	 * timeOne �Ƿ����� timeTwo
	 * @param timeOne ʱ��1 ��ʽΪ��-��-�� ʱ:��:��
	 * @param timeTwo ʱ��2 ��ʽΪ��-��-�� ʱ:��:��
	 * @return
	 * @throws ParseException
	 */
	public static boolean before(String timeOne,String timeTwo) throws ParseException {
		Date dateOne = dateOf(timeOne);
		Date dateTwo = dateOf(timeTwo);
		return dateOne.before(dateTwo);
	}
	/**
	 * timeOne �Ƿ����� timeTwo
	 * @param timeOne ʱ��1 ��ʽΪ��-��-�� ʱ:��:��
	 * @param timeTwo ʱ��2 ��ʽΪ��-��-�� ʱ:��:��
	 * @return
	 * @throws ParseException
	 */
	public static boolean after(String timeOne,String timeTwo) throws ParseException {
		Date dateOne = dateOf(timeOne);
		Date dateTwo = dateOf(timeTwo);
		return dateOne.after(dateTwo);
	}
	/**
	 * �������������
	 * ������ �� ��
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
