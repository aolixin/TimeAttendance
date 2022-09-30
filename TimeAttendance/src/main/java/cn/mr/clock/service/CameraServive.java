package cn.mr.clock.service;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
/**
 * ����ͷ������
 * ʹ��lib_camera
 * @author ��������
 *
 */
public class CameraServive {
	public static Webcam WEBCAM;//Ĭ������ͷ
	static {
		List<Webcam> webs = Webcam.getWebcams();
		for (int i = 0;i<webs.size();i++){
			System.out.println(webs.get(i).getName());
			if (webs.get(i).getName().equals("USB2.0 HD UVC WebCam 1")){
				WEBCAM = webs.get(i);
			}
		}
	}
	/**
	 * ������ͷ
	 * @return ����ͷ�Ƿ�ɹ���
	 */
	public static boolean startCamera() {
		if(WEBCAM == null) {//��������û����������ͷ
			return false;
		}
		//����640X480���
		WEBCAM.setViewSize(new Dimension(640, 480));
		return WEBCAM.open();//��������ͷ�����ؿ����Ƿ�ɹ�
	}
	/**
	 * ����ͷ�Ƿ��Ѿ�����
	 * @return
	 */
	public static boolean cameralsOpen() {
		if(WEBCAM == null) {
			return false;
		}
		return WEBCAM.isOpen();
	}
	/**
	 * ���һ����ʾ����ͷ��׽������������
	 * @return һ����ʾ����ͷ��׽������������
	 */
	public static JPanel getCameraPanel() {
		if(WEBCAM == null) {
			return new JPanel();
		}
		WebcamPanel panel = new WebcamPanel(WEBCAM);
		panel.setMirrored(true); //��������
		return panel;
	}
	/**
	 * ��ȡ����ͷ����ĵ�ǰ֡����
	 * @return BufferedImage���͵�ͼ��
	 */
	public static BufferedImage getCameraFrame() {
		return WEBCAM.getImage();
	}
	/**
	 * �ͷ�����ͷ
	 */
	public static void releaseCamera() {
		if(WEBCAM != null) {
			WEBCAM.close();
		}
	}
}
