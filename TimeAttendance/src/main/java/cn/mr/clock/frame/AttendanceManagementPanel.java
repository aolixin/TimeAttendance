package cn.mr.clock.frame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;

import cn.mr.clock.service.HRService;
import cn.mr.clock.util.DateTimeUtil;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

/**
 * @author ������
 * �����ձ����
 */
public class AttendanceManagementPanel extends JPanel {
	
	/**
	 * �����ձ����
	 * ������û��ʵ�ֵĶ����벻Ҫ��,�Ժ���ܻ�ʵ��>_<
	 */
	private static final long serialVersionUID = 5581384161553262968L;

	private MainFrame parent;

	private JToggleButton dayRecordBth;//�ձ���ť,δ��
	private JToggleButton monthRecordBth;//�±���ť,δ��
	
	private JButton Back;//����
	private JPanel center;//�������
	private CardLayout card;//��Ƭ����
	
	private JPanel dayReportPanel;//�ձ����,δ��
	private JTextArea area;//�ձ�����ı���
	
	//�ձ�����б�
	private JComboBox<Integer>yearComboxD;//�������б�
	private JComboBox<Integer>monthComboxD;//�������б�
	private JComboBox<Integer>dayComboxD;//�������б�
	private DefaultComboBoxModel<Integer>yearModelD,monthModeLD,dayModelD;//���������б�ʹ�õ�����ģ��
	
	
	//�±�����б�
	private JComboBox<Integer>yearComboBoxM;//�������б�,δ��
	private JComboBox<Integer>monthComboxM;//�������б�,δ��
	private DefaultComboBoxModel<Integer>yearModelM,monthModeLM;//���������б�ʹ�õ�����ģ��,δ��
	
	
	
	public AttendanceManagementPanel(MainFrame parent) {
		this.parent=parent;
		init();
		addListener();
		updateDayRecord();
	}
	
	/**
	 * ��ʼ�����
	 */
	public void init() {
		parent.setTitle("ѧ�������ձ�");
		//��ʼ��
		setLayout(new BorderLayout());//�߿򲼾�
		
		center =new JPanel();
		card=new CardLayout();
		center.setLayout(card);//���ÿ�Ƭʽ����
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new BorderLayout());
		JPanel monthPanel = new JPanel();
		monthPanel.setLayout(new BorderLayout());
		
		//�����б�ģ��
		yearModelD = new DefaultComboBoxModel<Integer>();
		for(int i=2022;i<2050;i++) {
			yearModelD.addElement(i);
		}
		monthModeLD = new DefaultComboBoxModel<Integer>();
		for(int i=1;i<13;i++) {
			monthModeLD.addElement(i);
		}
		dayModelD = new DefaultComboBoxModel<Integer>();
		for(int i=1;i<DateTimeUtil.getLastDay(2022, 1);i++) {
			dayModelD.addElement(i);
		}
		
		
		//�����б�
		yearComboxD =new JComboBox<>(yearModelD);
		monthComboxD =new JComboBox<>(monthModeLD);
		dayComboxD =new JComboBox<>(dayModelD);
		Integer[] yesterday = DateTimeUtil.yesterday();
		yearComboxD.setSelectedItem(yesterday[0]);
		monthComboxD.setSelectedItem(yesterday[1]);
		dayComboxD.setSelectedItem(yesterday[2]);
		
		//�������������б�
		JPanel dayNorthPanel = new JPanel();
		
		dayNorthPanel.add(yearComboxD);
		dayNorthPanel.add(monthComboxD);
		dayNorthPanel.add(dayComboxD);
		
		//�ı���
		area = new JTextArea();
		area.setEditable(false);
		
		dayPanel.add(dayNorthPanel,BorderLayout.NORTH);
		dayPanel.add(area,BorderLayout.CENTER);
		
		//center����dayPanel
		center.add(dayPanel,"DayRecord");
		card.show(center,"DayRecord");//��ʾdayPanel
				
		JPanel bottom = new JPanel();
		Back=new JButton("����");
		Back.setSize(100,100);
		dayRecordBth=new JToggleButton("�ձ�");
		monthRecordBth=new JToggleButton("�±�");
		bottom.add(Back);
		
		this.add(center,BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);	
	}
	
	
	/**
	 * ��Ӽ����¼�
	 */
	public void addListener() {
		//����������
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setPanel(new MainPanel(parent));
			}
		});
		
		//�����ձ�ʱ��
		final ActionListener dayD_Listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("");
				updateDayRecord();//�����ձ�
			}
		};
		
		dayComboxD.addActionListener(dayD_Listener);//������Ӽ���
		
		ActionListener yearD_monthD_Listener=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ɾ�����������б�Ķ���
				dayComboxD.removeActionListener(dayD_Listener);
				updateDayModel();//�������������б��е�����
				updateDayRecord();//�����ձ�
				//����Ϊ���������б���Ӽ�������
				dayComboxD.setModel(dayModelD);
				dayComboxD.addActionListener(dayD_Listener);
			}	
		};
		yearComboxD.addActionListener(yearD_monthD_Listener);
		monthComboxD.addActionListener(yearD_monthD_Listener);
	}
	
	

	/**
	 * ������ģ��
	 */
	private void updateDayModel() {
		int year = (int)yearComboxD.getSelectedItem();//��ȡ�����б��е�ֵ
		int month = (int)monthComboxD.getSelectedItem();
		int lastDay = DateTimeUtil.getLastDay(year, month);
		dayModelD.removeAllElements();
		for(int i=1;i<=lastDay;i++) {
			dayModelD.addElement(i);//��ÿһ�춼��������������б�����ģ����
		}
	}
	
	
	/**
	 * �����ձ�
	 */
	private void updateDayRecord(){
		
		int year=(int)yearComboxD.getSelectedItem();
		int month=(int)monthComboxD.getSelectedItem();
		int day=(int)dayComboxD.getSelectedItem();
		String dateNow = DateTimeUtil.dateNow() + " 0:0:0";
		String date = year + "-" + month +"-" +day +" 23:59:59";
		try {
			if(DateTimeUtil.before(dateNow, date)) {
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		String report = HRService.getDayReport(year, month, day);
		if(report==null)return;
		area.setText(report);
	}
}
