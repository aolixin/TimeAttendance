package cn.mr.clock.pojo;
/**
 * 管理员类
 * @author 龙星洛洛
 *
 */
public class User {
	private String username;//用户名
	private String password;//密码
	private String User_id;//管理员Id
	/**
	 * @param username 用户名
	 * @param password 密码
	 * @param user_id 管理员Id
	 */
	public User(String username, String password, String user_id) {
		this.username = username;
		this.password = password;
		this.User_id = user_id;
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.User_id = "U202100000";
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_id() {
		return User_id;
	}
	public void setUser_id(String user_id) {
		User_id = user_id;
	}
	
	
}
