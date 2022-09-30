package cn.mr.clock.util;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.io.InputStreamReader;
import javax.naming.ConfigurationException;

/**
 * @author 敖立鑫
 * 连接数据库
 */
public class JDBCUtil {
	private static String con_name;
	private static String url;
	private static String con_password;
	private static String DriverName;
	private static Connection con = null;
	private static String CLASS_PATH = JDBCUtil.class.getResource("/").getPath();
	private static final String CONFIG_FILE = CLASS_PATH + "jdbc.properties";

	static {
		Properties prop = new Properties();
		try {
			//System.out.println(CONFIG_FILE);
			File config = new File(CONFIG_FILE);
			if (!config.exists()) {
				throw new FileNotFoundException("缺少配置文件" + config.getAbsolutePath());
			}

			prop.load(new FileInputStream(config));

			con_name = prop.getProperty("user");
			url = prop.getProperty("url");
			con_password = prop.getProperty("password");
			DriverName = prop.getProperty("driverName");

			if (con_name.isEmpty() || url.isEmpty() || con_password.isEmpty() || DriverName.isEmpty()) {
				throw new ConfigurationException("jdbc.properties文件缺少配置信息");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			System.err.print("从配置文件获取的内容 [driver_name:" + DriverName + "] [username:" + con_name + "] " + "[password:"
					+ con_password + "] " + "[url:" + url + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection()
	{
		try {
			if(con==null||con.isClosed())
			{
				Class.forName(DriverName);
				con=DriverManager.getConnection(url,con_name,con_password);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	public static void close()
	{
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
