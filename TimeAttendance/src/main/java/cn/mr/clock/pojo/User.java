package cn.mr.clock.pojo;
/**
 * ����Ա��
 * @author ��������
 *
 */
public class User {
	private String username;//�û���
	private String password;//����
	private String User_id;//����ԱId
	/**
	 * @param username �û���
	 * @param password ����
	 * @param user_id ����ԱId
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
