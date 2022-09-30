package cn.mr.clock.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import cn.mr.clock.pojo.Student;
import cn.mr.clock.pojo.User;
import cn.mr.clock.pojo.WorkTime;
import cn.mr.clock.util.JDBCUtil;

/**
 * @author 敖立鑫
 * 实现DAO接口
 */
public class DAOMysql implements DAO {

	private Connection con=null;
	private PreparedStatement prep=null;
	private ResultSet res=null;
	
	//构造函数
	public DAOMysql() {
	}
	
	
	/**
	 *获取所有学生
	 */
	public Set<Student> getALLStu() {
		String sql ="select *from t_stu";
		con = JDBCUtil.getConnection();
		try {
			Statement stmt=con.createStatement();
			res=stmt.executeQuery(sql);
			Set<Student> set=new HashSet<Student>();
			while(res.next())
			{
				int id=res.getInt("id");
				String name =res.getString("name");
				String stu_id=res.getString("stu_id");
				String start=res.getString("start_time");
				String end=res.getString("end_time");
				int weekDay=res.getInt("week_day");
				WorkTime time=new WorkTime(start,end,weekDay);
				set.add(new Student((Integer)id, name, stu_id,time));
			}
			return (Set<Student>) set;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		return null;
	}

	
	/**
	 *通过id返回学生对象
	 */
	public Student getStu(int id) {
		String sql ="select *from t_stu where id= ?";
		con = JDBCUtil.getConnection();
		try {
			prep=con.prepareStatement(sql);
			prep.setInt(1, id);
			res=prep.executeQuery();
			
			if(res.next())
			{
				String name =res.getString("name");
				String stu_id=res.getString("stu_id");
				String start=res.getString("start_time");
				String end=res.getString("end_time");
				int weekDay=res.getInt("week_day");
				WorkTime time=new WorkTime(start,end,weekDay);
				//返回学生对象
				return new Student((Integer)id,name,stu_id,time);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		return null;
	}

	
	/**
	 *通过学号返回学生对象
	 */
	public Student getStu(String stu_id) { 
		String sql ="select *from t_stu where stu_id= ?";
		con = JDBCUtil.getConnection();
		try {
			prep=con.prepareStatement(sql);
			prep.setString(1, stu_id);
			res=prep.executeQuery();
			if(res.next())
			{
				String name =res.getString("name");
				String start=res.getString("start_time");
				String end=res.getString("end_time");
				int weekDay=res.getInt("week_day");
				WorkTime time=new WorkTime(start,end,weekDay);
				//返回学生对象
				return new Student(null,name,stu_id,time);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		return null;
	}

	
	/**
	 *添加学生对象
	 */
	public void addStu(Student s) {
		String sql ="replace into t_stu (name,stu_id,start_time,end_time,week_day) values(?,?,?,?,?)";
		con = JDBCUtil.getConnection();
		try {
			prep = con.prepareStatement(sql);
			String name=s.getName();
			String stu_id=s.getStudentId();
			prep.setString(1,name);
			prep.setString(2,stu_id);
			prep.setString(3,s.getWorkTime().getStart());
			prep.setString(4,s.getWorkTime().getEnd());
			prep.setInt(5,s.getWorkTime().getWeekDay());
			prep.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
			System.out.println("添加成功");
		JDBCUtil.close();
	}
	}
	
	
	/**
	 * 添加学生打卡时间
	 * 时间有点紧,还没什么用,没来得及做>_<
	 * @param stu
	 * @param time
	 */

	
	
	/**
	 *删除学生对象
	 */
	public void delStu(int id) {
		String sql = "delete from t_stu where id = ?";
		con = JDBCUtil.getConnection();
		try {
			prep = con.prepareStatement(sql);
			prep.setInt(1,id);
			prep.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
	}

	@Override
	public void delStu(String StuId) {
		String sql = "delete from t_stu where stu_id = ?";
		con = JDBCUtil.getConnection();
		try {
			prep = con.prepareStatement(sql);
			prep.setString(1,StuId);
			prep.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
			JDBCUtil.close();
		}
	}


	/**
	 * 添加学生打卡时间
	 * @param stu
	 * @param time
	 */
	public void addWorkTime(Student stu,WorkTime time) {

}

		/**
		 * 添加管理员对象
		 * @param u
		 */
		public void addUser(User u) {
			String sql ="insert into t_user (username,password,user_id) values(?,?,?)";
			con = JDBCUtil.getConnection();
			try {
				prep = con.prepareStatement(sql);
				String username=u.getUsername();
				String password=u.getPassword();
				String user_id=u.getUser_id();
				prep.setString(1,username);
				prep.setString(2,password);
				prep.setString(3,user_id);
				prep.executeUpdate();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}finally {
				System.out.println("添加成功");
			JDBCUtil.close();
		}
		}

		public User getUser(String User_id) { 
			String sql ="select *from t_stu where user_id= ?";
			con = JDBCUtil.getConnection();
			try {
				prep=con.prepareStatement(sql);
				prep.setString(1, User_id);
				res=prep.executeQuery();
				if(res.next())
				{
					String name =res.getString("username");
					String password=res.getString("password");
					//返回学生对象
					return new User(name,password,User_id);
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}finally {
			JDBCUtil.close();
		}
			return null;
		}

		
	/**
	 * 返回所有人的作息时间
	 */
	public HashMap<String, WorkTime> getWrokTime() {
		 HashMap<String, WorkTime>result=new HashMap<>();
		 
		String sql="select  * from t_stu";
		con = JDBCUtil.getConnection();
		try {
			Statement stmt=con.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next()) {
				String stu_id=res.getString("stu_id");
				String start=res.getString("start_time");
				String end=res.getString("end_time");
				int weekDay=res.getInt("week_day");
				//创建时间对象
				WorkTime time=new WorkTime(start, end,weekDay);
				result.put(stu_id, time);
			}return result;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		return null;
	}

	
	/**
	 *更新打卡时间
	 */
	public void updateWorkTime(Student stu,WorkTime time) {
		String sql="update t_stu set start = ?,end = ? where stu_id=?";
		con = JDBCUtil.getConnection();
		try {
			prep = con.prepareStatement(sql);
			prep.setString(1,time.getStart());
			prep.setString(2,time.getEnd());
			prep.setString(3,stu.getStudentId());
			prep.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		
	}

	
	/**
	 *添加打卡记录
	 */
	public void addClockInRecord(String Stu_id, String now) {
		String sql="INSERT into t_lock_in_record  values(?,?)";
		con = JDBCUtil.getConnection();
		try {
			prep = con.prepareStatement(sql);
			prep.setString(1,Stu_id);
			prep.setString(2,now);
			prep.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		
	}

	
	/**
	 *删除打卡记录
	 */
	public void deleteClockRecord(String Stu_id) {
		String sql="delete from t_lock_in_record  where stu_id = ?";
		con = JDBCUtil.getConnection();
		try {
			prep=con.prepareStatement(sql);
			prep.setString(1,Stu_id);
			prep.execute();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		
	}

	
	/**
	 *返回所有打卡记录
	 */
	public String[][] getAllClockRecord() {
		HashSet<String[]> set=new HashSet<String[]>(); 
		String sql="select *from t_lock_in_record";
		con = JDBCUtil.getConnection();
		try {
			Statement stmt=con.createStatement();
			res=stmt.executeQuery(sql);
			while(res.next())
			{
				String stu_id=res.getString("stu_id");
				String lock_in_time=res.getString("lock_in_time");
				set.add(new String[] {stu_id,lock_in_time});
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		if(set.isEmpty()){
			return null;
		}else {
			String result[][]=new String[set.size()][2];
			Iterator<String[]>it=set.iterator();
			for(int i=0;it.hasNext();i++)
			{
				result[i]=it.next();
			}
			return result;
		}
	}

	
	/**
	 *判断是否是管理员
	*/
	public boolean userLogin(User user) {
		con = JDBCUtil.getConnection();
		String sql ="select *from t_user where username=? and password=?";
		try {
			prep=con.prepareStatement(sql);
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			res=prep.executeQuery();
			if(res.next())return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
	/**
	 * 返回应该在星期w的所有人的id
	 * @param w
	 * @return
	 */
	public Set<String> getWeekDayStu(int w) {
		//
		Set<String>WeekDayId=new HashSet<String>();
		String sql ="select stu_id from t_stu where week_day= ?";
		con = JDBCUtil.getConnection();
		try {
			prep=con.prepareStatement(sql);
			prep.setInt(1, w);
			res=prep.executeQuery();
			while(res.next())
			{
				WeekDayId.add(res.getString("stu_id"));
			}
			return WeekDayId;
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally {
		JDBCUtil.close();
	}
		return null;
	}

}
