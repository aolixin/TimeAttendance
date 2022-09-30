package cn.mr.clock.pojo;
/**
 * 打卡时间类
 * @author 龙星洛洛
 *
 */
public class WorkTime {
	private String start;//打卡开始时间，最晚的打卡时间
	private String end;//打卡结束时间，最早的离开时间
	private int weekDay;//星期几
	
	/**
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param weekDay 星期几
	 */
	public WorkTime(String start, String end, int weekDay) {
		this.start = start;
		this.end = end;
		if(weekDay < 1 || weekDay >7) {
			//不在1 到 7范围内
			weekDay = 0;
		}
		this.weekDay = weekDay;
	}
	/**
	 * 返回打卡开始时间
	 * @return
	 */
	public String getStart() {
		return start;
	}
	/**
	 * 设置指定开始时间
	 * @param start
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * 返回打卡结束时间
	 * @return
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * 设置指定打卡结束时间
	 * @param end
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	public int getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}
	
	
}
