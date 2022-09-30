package cn.mr.clock.dao;

/**
 * @author 敖立鑫
 *数据库工厂类
 *封装创建数据库接口方法
 */
public class DAOFactory {
public static DAO getDAO() {
	return new DAOMysql();
}
}
