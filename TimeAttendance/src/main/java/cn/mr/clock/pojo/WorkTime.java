package cn.mr.clock.pojo;
/**
 * ��ʱ����
 * @author ��������
 *
 */
public class WorkTime {
	private String start;//�򿨿�ʼʱ�䣬����Ĵ�ʱ��
	private String end;//�򿨽���ʱ�䣬������뿪ʱ��
	private int weekDay;//���ڼ�
	
	/**
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param weekDay ���ڼ�
	 */
	public WorkTime(String start, String end, int weekDay) {
		this.start = start;
		this.end = end;
		if(weekDay < 1 || weekDay >7) {
			//����1 �� 7��Χ��
			weekDay = 0;
		}
		this.weekDay = weekDay;
	}
	/**
	 * ���ش򿨿�ʼʱ��
	 * @return
	 */
	public String getStart() {
		return start;
	}
	/**
	 * ����ָ����ʼʱ��
	 * @param start
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * ���ش򿨽���ʱ��
	 * @return
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * ����ָ���򿨽���ʱ��
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
