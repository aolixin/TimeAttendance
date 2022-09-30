package cn.mr.clock.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.mr.clock.session.Session;

/**
 * ������
 * @author ��������
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	/**
	 * ��ʼ������
	 * �������ô��ڽ����С
	 */
	public  MainFrame() {
		Session.init();
		init();
		addListener(); 
	}
	/**
	 * ��Ӽ����¼�
	 * �����˳�ʱ��ѯ���Ƿ��˳�����
	 * �˳�����ʱ���ͷ�ȫ����Դ
	 */
	public void addListener() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int closeCode = JOptionPane.showConfirmDialog(MainFrame.this, "�Ƿ��˳�����"
						,"��ʾ!",JOptionPane.YES_NO_OPTION);
				if(closeCode == JOptionPane.YES_OPTION) {
					//�ͷ�ȫ����Դ
					Session.dispose();
					System.exit(closeCode);
				}
			}
		});
	}
	/**
	 * ��ʼ������
	 */
	private void init() {
		setSize(640,480);//���ô��ڿ��
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//������ڹرհ���������Ӧ
		Toolkit tool = Toolkit.getDefaultToolkit();//���ϵͳĬ��������߰�
		Dimension d = tool.getScreenSize();//���ϵͳ�����С
		setLocation((d.width - getWidth())/2,(d.height - getHeight())/2);//����������Ϊ����Ļ�м���ʾ
	}
	/**
	 * �����������е����
	 * @param panel
	 */
	public void setPanel(JPanel panel) {
		Container c = getContentPane();
		c.removeAll();
		c.add(panel);
		c.validate();
	}
}
