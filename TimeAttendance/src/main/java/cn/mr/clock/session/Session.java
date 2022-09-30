package cn.mr.clock.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.arcsoft.face.FaceFeature;
import cn.mr.clock.pojo.Student;
import cn.mr.clock.pojo.User;
import cn.mr.clock.pojo.WorkTime;
import cn.mr.clock.util.JDBCUtil;
import cn.mr.clock.service.CameraServive;
import cn.mr.clock.service.FaceEngineService;
import cn.mr.clock.service.HRService;
import cn.mr.clock.service.ImageService;
import java.awt.image.BufferedImage;

/**
 * @author ������
 *
 */
public class Session {
	
	/**
	 * ��ǰ��¼����Ա
	 */
	public static User user=null;	
	
	
	/**
	 * ��Ϊѧ��,ֵworktimer
	 * ��Ϣʱ��
	 */
	public static HashMap<String, WorkTime>WORK_MAP=new HashMap<>();
	
	/**
	 * ȫ��ѧ��
	 */
	public static final HashSet<Student>Stu_SET=new HashSet<>();
	

	/**
	 * ��Ϊѧ��,ֵΪ����ͼƬ
	 * ȫ������ͼ��
	 */
	public static final HashMap<String, BufferedImage>IMAGE_MAP=new HashMap<>();
	
	
	/**
	 * ��Ϊѧ��,ְλ����ͼƬ
	 * ȫ����������
	 */
	public static final HashMap<String, FaceFeature>FACE_FEATURE_MAP=new HashMap<>();
	
	
	/**
	 * ��Ϊѧ��,ֵΪ�򿨼�¼
	 * ȫ���򿨼�¼
	 */
	public static final HashMap<String, Set<String>> RECORD_MAP=new HashMap<>();
	
	/**
	 * ����ȫ����Դ
	 */
	public static void init() {
		ImageService.loadAllImage();//��������ͼ���ļ�
		HRService.loadWorkTime();//������Ϣʱ��
		HRService.loadAllStu();//��������ѧ��
		HRService.loadAllClockInrecord();//�������д򿨼�¼
		HRService.loadWorkTime();//���������˵���Ϣʱ��
		FaceEngineService.loadAllFaceFeature();//����������������
	}
	
	
	/**
	 * �ͷ�ȫ����Դ
	 */
	public static void dispose() {
		FaceEngineService.dispost();//�ͷ�����ʶ������
		CameraServive.releaseCamera();//�ͷ�����ͷ
		JDBCUtil.close();//�ر����ݿ�����
	}
}
	

