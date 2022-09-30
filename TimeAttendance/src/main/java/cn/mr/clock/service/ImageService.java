package cn.mr.clock.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import cn.mr.clock.session.Session;
import cn.mr.clock.util.JDBCUtil;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * @author ������
 *
 */
public class ImageService {

	public static String Path = null;
	static String CLASS_PATH = ImageService.class.getResource("/").getPath();//war包下类路径
	static String CONFIG_FILE = CLASS_PATH + "Faces_path.properties";//配置文件路径
	static {
		Properties prop = new Properties();
		try {
			File config = new File(CONFIG_FILE);
			if (!config.exists()) {
				throw new FileNotFoundException("缺少配置文件" + config.getAbsolutePath());
			}
			prop.load(new FileInputStream(config));
			Path = prop.getProperty("ABSOLUTE_FACE_PATH");//获取文件存储绝对路径

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

private static final File FACE_DIR=new File(Path);//��Ƭλ��
private static final String SUFFIX="png";//�洢��ʽ


/**
 * ��ͼƬ�����ȫ�ֻỰ
 */
public static void loadAllImage(){
	if(!FACE_DIR.exists()) {
		System.err.println(Path);
		return;
	}
	File faces[]=FACE_DIR.listFiles();
	for(File f:faces) {
		try {
			BufferedImage img=ImageIO.read(f);
			String fileName =f.getName();
			String code=fileName.substring(0,fileName.indexOf('.'));
			Session.IMAGE_MAP.put(code,img);//���ֵȫ�ֻỰ
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	return;
}

/**
 * ���ѧ����Ƭ
 * @param img
 * @param StuId
 */
public static void saveFaceImage(BufferedImage img,String StuId) {
	try {
		ImageIO.write(img, SUFFIX, new File(FACE_DIR,StuId+"."+SUFFIX));
		Session.IMAGE_MAP.put(StuId,img);
	}catch(IOException e) {
		e.printStackTrace();
	}
}

public static void deleteFaceImage(String StuId) {
	//Session.IMAGE_MAP.remove(StuId);//��ȫ�ֻỰ���Ƴ�
	File image=new File(FACE_DIR,StuId+"."+SUFFIX);//�����Ա�ͼ��
	if(image.exists()) {//����ļ��Ƿ����
		image.delete();
		System.out.println(image.getAbsolutePath()+"---��ɾ��");
	}
}
}
