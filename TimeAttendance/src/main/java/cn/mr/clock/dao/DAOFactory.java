package cn.mr.clock.dao;

/**
 * @author ������
 *���ݿ⹤����
 *��װ�������ݿ�ӿڷ���
 */
public class DAOFactory {
public static DAO getDAO() {
	return new DAOMysql();
}
}
