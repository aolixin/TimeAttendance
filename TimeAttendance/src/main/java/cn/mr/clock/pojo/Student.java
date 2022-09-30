package cn.mr.clock.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ѧ����
 * @author ��������
 *
 */@Getter@Setter
public class Student {

	private Integer id;  //ѧ�����
	private String name; //ѧ������
	private String studentId; //ѧ����
	private WorkTime workTime;//��ʱ��
	/**
	 * @param id ���
	 * @param name ����
	 * @param studentId ѧ�� 
	 * @paramwork_time ����ʱ��
	 */
	public Student(Integer id, String name, String studentId, WorkTime workTime) {
		this.id = id;
		this.name = name;
		this.studentId = studentId;
		this.workTime = workTime;
	}

	/**
	 * ��дhashCode()������ֻͨ��id���ɹ�ϣ��
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime* result + ((id==null)? 0:id.hashCode());
		return result;
	}
	/**
	 * ��дequals()������ֻͨ��id�ж��Ƿ�ΪͬһλԱ��
	 */
	public boolean equals(Object obj) {
		if(this == obj){return true;}
		if(obj == null) {return false;}
		if(getClass() != obj.getClass()) {return false;}
		Student stu = (Student) obj;
		if(id == null) {
			if(stu.studentId!=null) {
				return false;
			}
		}else if(!studentId.equals(stu.studentId)) {
			return false;
		}
		return true;
	}
}
