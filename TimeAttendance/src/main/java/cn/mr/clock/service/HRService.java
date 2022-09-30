package cn.mr.clock.service;

import cn.mr.clock.dao.DAO;
import cn.mr.clock.dao.DAOFactory;
import cn.mr.clock.pojo.Student;
import cn.mr.clock.pojo.User;
import cn.mr.clock.pojo.WorkTime;
import cn.mr.clock.session.Session;
import cn.mr.clock.util.DateTimeUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author 敖立鑫
 *人事服务类
 */
public class HRService {

	private static final String CLOCK_IN="I";		//正常上班标记
	private static final String LOCK_OUT="O";		//正常下班标记
	private static final String LATE="L";			//迟到标记
	private static final String LEFT_EARLY="E";		//早退标记
	private static final String ABSENT="A";			//缺席标记
	private static DAO dao=DAOFactory.getDAO();		//DAO接口
	
	/**
	 * 从数据库中加载所有作息时间放在Session中
	 */
	public static void loadWorkTime() {
		Session.WORK_MAP=dao.getWrokTime();
	}

	
	/**
	 * 从数据库中加载所有学生放在session中
	 */
	public static void loadAllStu() {
		Session.Stu_SET.clear();//清空学生表
		Session.Stu_SET.addAll(dao.getALLStu());
	}

	/**
	 * 验证管理员登录
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean userLogin(String username,String password) {
		User user =new User(username,password);
		if(dao.userLogin(user)) {
			//Session.user=user;
			return true;
		}
		else return false;
	}
	
	/**
	 * 添加学生对象
	 * @param name
	 * @param StuId
	 * @param image
	 * @return
	 */
	public static Student addStudent(String name , String StuId,WorkTime time,BufferedImage image) {
		/*for(Student s:Session.Stu_SET) {
			if(s.getStudentId().equals(StuId)) {
				return null;
			}
		}*/
		//创建学生对象
		Student stu=new Student(null,name,StuId,time);
		dao.addStu(stu);//将该学生插入数据库
		ImageService.saveFaceImage(image,StuId);//保存学生照片
		//判断之前是否有该对象
		Session.Stu_SET.add(stu);//将学生插入全局会话
		return stu;
	}

	public static Student addStudent(String name , String StuId,WorkTime time) {
		Student stu=new Student(null,name,StuId,time);
		dao.addStu(stu);//将该学生插入数据库
		return stu;
	}
	
	/**
	 * 通过学号完全删除学生
	 * @param StuId
	 */
	public static void deleteStu(String StuId) {
		Student stu = dao.getStu(StuId);//返回学生对象
		//System.out.println("删除对象: "+StuId);
		if(stu!=null) {
			dao.delStu(stu.getStudentId());//从数据库中删除
			//System.out.println("从数据库中删除");
			dao.deleteClockRecord(stu.getStudentId());//删除数据库中的打卡记录
			//System.out.println("删除打卡记录");
			ImageService.deleteFaceImage(stu.getStudentId());//删除人脸图片
			//System.out.println("删除人脸图片");
		}
	}
	
	/**
	 * 通过学号查找会话中的学生对象
	 * @param StuId
	 * @return
	 */
	public static Student getStu(String StuId){
		for(Student stu:Session.Stu_SET) {
			if(stu.getStudentId().equals(StuId)) {
				return stu;
			}
		}

		return null;
	}
	
	/**
	 * 添加打卡记录
	 * @param stu
	 */
	public static void addClockInRecord(Student stu) {
		String now=DateTimeUtil.dateTimeNow();//返回现在时间
		dao.addClockInRecord(stu.getStudentId(), now);
		if(!Session.RECORD_MAP.containsKey(stu.getStudentId())) {//如果会话中没有打卡记录
			Session.RECORD_MAP.put(stu.getStudentId(), new HashSet<String>());//添加空记录
		}
		//在会话中添加打卡时间
		Session.RECORD_MAP.get(stu.getStudentId()).add(now);
	}
	
	
	/**
	 * 从数据库中加载所有打卡记录
	 */
	public static void loadAllClockInrecord() {
		String record[][]=dao.getAllClockRecord();//从数据库中添加打卡记录
		if(record==null)
		{
			System.err.println("表中无打卡数据");
			return;
		}
		for(int i=0,lenth=record.length;i<lenth;i++){
			String r[]=record[i];//获取第i行记录
			String StuId=r[0];//获取学号

			
			if(!Session.RECORD_MAP.containsKey(StuId)) {//如果会话中没有该学生打卡记录
				Session.RECORD_MAP.put(StuId, new HashSet<String>());//添加打卡记录
			}
			String Date=r[1];
			if(StuId==null)return;
			Session.RECORD_MAP.get(StuId).add(Date);//添加新的打卡时间
		}
	}
	
	/**
	 * 更新作息时间
	 * @param stu
	 * @param time
	 */
	public static void updateWorkTime(Student stu,WorkTime time) {
		dao.updateWorkTime(stu, time);
		Session.WORK_MAP.replace(stu.getStudentId(), time);
	}
	
	/**
	 * 生成某一日的考勤数据
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static Map<Student,String>getOneDayRecordData(int year,int month,int day){
		Map<Student,String>record=new HashMap<>();//键为员工对象,值为考勤标记
		//返回现在星期几
		int weekDay = 0;
		try {
			weekDay = DateTimeUtil.getDateWeek(year, month, day);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String zeroTime=null;
		String lastTime=null;
		//各时间点对象
		try {
			zeroTime=DateTimeUtil.Stringof(DateTimeUtil.dateOf(year,month,day,"00:00:00"));//当天起始时间
			lastTime=DateTimeUtil.Stringof(DateTimeUtil.dateOf(year,month,day,"23:59:59"));//当天最后一秒
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//获取所有记录
		String[][] AllRecord=dao.getAllClockRecord();
		
		//获取一天的记录,键为学号,值为记录数组,数组第一个值为最早的记录,第二个值为最晚的记录
		Map<String,String[]>OneDayRecord=new HashMap<String, String[]>();
		//给OneDayRecord赋值
		if(AllRecord==null)return null;
		for(int i=0;i<AllRecord.length;i++) {
			String key=AllRecord[i][0];
			String val=AllRecord[i][1];
			//判断是否是当天
			try {
				if(DateTimeUtil.after(val, zeroTime)&&DateTimeUtil.before(val, lastTime)) {//如果是当天

					if(OneDayRecord.containsKey(key)) {//如果已有键值,更新最晚的记录
						if(OneDayRecord.get(key)[1]==null) {//如果0有1无
							if (DateTimeUtil.before(val, OneDayRecord.get(key)[0])) {//如果0早,0后移,val填0
								OneDayRecord.get(key)[1]=OneDayRecord.get(key)[0];
								OneDayRecord.get(key)[0] = val;
							}
							else{//否则
								OneDayRecord.get(key)[1]=val;
							}
						}
						else{//如果0,1都满
							if (DateTimeUtil.before(val, OneDayRecord.get(key)[0])){//如果val小于0
								OneDayRecord.get(key)[0]=val;
							}
							if(DateTimeUtil.after(val,OneDayRecord.get(key)[1])){//如果比1晚
								OneDayRecord.get(key)[1]=val;
							}
						}
					}
					else {//如果不在,创建数组,将最早的记录放入数组
						String []t=new String[2];
						t[0]=val;
						OneDayRecord.put(key, t);
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		//获取应该在该天打卡的学生学号
		Set<String> AllIdInWeekday = dao.getWeekDayStu(weekDay);

		//添加当天应该打卡但是没来的人的report,如果不包含这个id,就将这个id的打卡时间设为lastime,这样就能保证他被判断为缺席
		for(String id:AllIdInWeekday) {
			if(!OneDayRecord.containsKey(id)) {
				OneDayRecord.put(id,new String[]{lastTime,lastTime});
			}
		}

		Iterator<Map.Entry<String,String[]>>it = OneDayRecord.entrySet().iterator();
		while(it.hasNext()) {//遍历一天的打卡记录

			String Report="";//学生考勤记录,初始为空

			Map.Entry<String,String[]> entry=it.next();
			String stuId= entry.getKey();//学生id

			if(dao.getStu(stuId).getWorkTime().getWeekDay()!=weekDay){
				it.remove();
				continue;
			}
			String []oneRecord= entry.getValue();//学生打卡记录

			//System.out.println(stuId);

			//返回学生start
			String start=year+"-"+month+"-"+day+" "+dao.getStu(stuId).getWorkTime().getStart();
			//返回学生end
			String end=year+"-"+month+"-"+day+" "+dao.getStu(stuId).getWorkTime().getEnd();
			//填充report考勤记录
			try {

				String come=oneRecord[0];
				String left=oneRecord[1];

				if(come==null)
				{
					Report+=ABSENT;
					record.put(dao.getStu(stuId), Report);
					continue;
				}
				if(left==null)
				{
					Report+=LEFT_EARLY;
					record.put(dao.getStu(stuId), Report);
					continue;
				}

				if(DateTimeUtil.after(come, end)) {//如果来的时间晚于end
					Report+=ABSENT;					//标记为缺席
				}
				if(DateTimeUtil.before(left, end)) {//如果走的时间早于end
					Report+=LEFT_EARLY;				//标记为早退
				}
				if(DateTimeUtil.after(start,come)) {//如果来的时间晚于start
					Report+=LATE;					//标记为迟到
				}
				if(DateTimeUtil.before(come, start)) {//如果来的时间早于start
					Report+=CLOCK_IN;				  //标记为正常上班
				}
				if(DateTimeUtil.after(left,end)) {//如果走的时间晚于end
					Report+=LOCK_OUT;				//标记为正常下班
				}

				//添加考勤记录
				record.put(dao.getStu(stuId), Report);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return record;//返回考勤记录
	}
	
	
	/**
	 * 生成考勤记录字符串
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDayReport(int year,int month,int day){
		Set<String> lateSet=new HashSet<String>();	//迟到
		Set<String> leftSet=new HashSet<String>();	//早退
		Set<String> absentSet=new HashSet<String>();//缺席
		
		 Map<Student,String>record = getOneDayRecordData(year, month, day);//获取考勤数据

		 if(record==null)return null;
		 for(Student stu:record.keySet()) {
			 String OneRecord = record.get(stu);//获取个人的数据
			 //处理数据			 
			 if(OneRecord.contains(ABSENT)) {//如果包含缺席标记
				 absentSet.add(stu.getName());
				 continue;
			 }
			 if(OneRecord.contains(LATE)&&!OneRecord.contains(CLOCK_IN)&&!OneRecord.contains(ABSENT)) {//包含迟到标记且不含正常上班标记且不包含缺席
				 lateSet.add(stu.getName());
			 }
			 if(OneRecord.contains(LEFT_EARLY)&&!OneRecord.contains(LOCK_OUT)) {//包含早退且不包含正常下班
				 leftSet.add(stu.getName());
			 }

		 }
		 StringBuilder report = new StringBuilder();//报表字符串
		 
		 int weekDay = 0;
			try {
				weekDay = DateTimeUtil.getDateWeek(year, month, day);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		 
		 int count = dao.getWeekDayStu(weekDay).size();
		 
		 //拼接报表内容
		 report.append("------ "+year+"年"+month+"月"+day+"日 ----\n");
		 report.append("应到人数: "+count+"\n");
		 
		 report.append("缺席人数: "+absentSet.size()+"\n");
		 if(absentSet.size()!=0){
			 report.append("缺席名单: ");
			 Iterator<String> it =absentSet.iterator();
			 while(it.hasNext()) {
				 report.append(it.next()+" ");
			 }
			 report.append("\n");
		 }
		 
		 report.append("迟到人数: "+lateSet.size()+"\n");
		 
		 if(lateSet.size()!=0){
			 report.append("迟到名单: ");
			 Iterator<String> it =lateSet.iterator();
			 while(it.hasNext()) {
				 report.append(it.next()+" ");
			 }report.append("\n");
		 }
		 
		 report.append("早退人数: "+leftSet.size()+"\n");
		 
		 if(leftSet.size()!=0){
			 report.append("早退名单: ");
			 Iterator<String> it =leftSet.iterator();
			 while(it.hasNext()) {
				 report.append(it.next()+" ");
			 }
			 report.append("\n");
		 }
		 return report.toString();
	}
	
	
}
