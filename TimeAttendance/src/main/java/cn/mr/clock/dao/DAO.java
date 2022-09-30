package cn.mr.clock.dao;

import java.util.HashMap;
import java.util.Set;
import cn.mr.clock.pojo.*;

/**
 * @author 敖立鑫
 *抽象DAO接口
 */
public interface DAO {

/**
 * @return 学生集合
 */
public Set<Student> getALLStu();

/**
 * @param id
 * @return 学生对象
 */
public Student getStu(int id);

/**
 * @param stu_id
 * @return 学生对象
 */
public Student getStu(String stu_id);


/**
 * 添加学生对象
 * @param s
 */
public void addStu(Student s);

/**
 * @parami删除指定学生
 */
public void delStu(int i);

public void delStu(String StuId);

/**
 * @return作息时间
 */
public HashMap<String, WorkTime> getWrokTime();

/**
 * 更新时间
 * @param time
 */
public void updateWorkTime(Student stu,WorkTime time);

/**
 * 添加学生打卡记录
 * @param studentId
 * @param now
 */
public void addClockInRecord(String studentId, String now);

/**
 * 删除学生
 * @param Stu_id
 */
public void deleteClockRecord(String Stu_id);

/**
 * 返回所有打卡记录
 * @return
 */
public String[][] getAllClockRecord();

/**
 * 验证管理员登录
 * @param user
 * @return
 */
public boolean userLogin(User user);

/**
 * 添加打卡时间
 * @param stu
 * @param time
 */
public void addWorkTime(Student stu, WorkTime time);


/**
 * 返回应该在星期w打卡的学生
 * @param w
 * @return
 */
public Set<String> getWeekDayStu(int w);
}


