package cn.mr.clock.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.arcsoft.face.FaceFeature;

import cn.mr.clock.pojo.Student;
import cn.mr.clock.pojo.WorkTime;
import cn.mr.clock.service.CameraServive;
import cn.mr.clock.service.FaceEngineService;
import cn.mr.clock.service.HRService;
import cn.mr.clock.session.Session;

/**
 * 添加学生面板类
 * 
 * @author 龙星洛洛
 *
 */
@SuppressWarnings("serial")
public class AddStudentPanel extends JPanel {
	private MainFrame parent;// 主窗体
	private JLabel message;// 提示
	private JTextField nameField;// 姓名输入对话框
	private JTextField stuIdField;// 学号输入对话框
	private JComboBox<String> workTimeComboBox;// 工作开始时间输入对话框
	private JComboBox<String> weekDayComboBox;//星期几时间
	private JButton submit;// 提交按钮
	private JButton back;// 返回按钮
	private JPanel center;// 中部面板
	private WorkTime[] workTimeArr;//打卡时间数组
	private Thread cameraThread;
	/**
	 * 构造函数
	 * @param parent 主窗体
	 */
	public AddStudentPanel(MainFrame parent) {
		this.parent = parent;//指定主窗口
		init();//初始化部件
		addListener();//添加监听事件
	}
	/**
	 * 组件初始化
	 */
	private void init() {
		//五个时间段的打卡时间
		workTimeArr = new WorkTime[5];
		workTimeArr[0] = new WorkTime("9:00:00", "9:35:00", 0);
		workTimeArr[1] = new WorkTime("10:00:00", "10:45:00", 0);
		workTimeArr[2] = new WorkTime("2:00:00", "2:45:00", 0);
		workTimeArr[3] = new WorkTime("3:40:00", "4:30:00", 0);
		workTimeArr[4] = new WorkTime("5:10:00", "6:00:00", 0);
		
		parent.setTitle("录入新学生");
		setLayout(new BorderLayout());
		
		/* 底面版 包括姓名学号输入框，选择打卡时间和星期下滑列表，确认和返回按钮 */
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(5, 2));
		
		//姓名栏
		JLabel nameLabel = new JLabel("姓 名：", JLabel.CENTER);
		nameLabel.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		nameField = new JTextField(15);
		nameField.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		bottom.add(nameLabel);
		bottom.add(nameField);
		
		//学号栏
		JLabel stuIdLabel = new JLabel("学 号：", JLabel.CENTER);
		stuIdLabel.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		stuIdField = new JTextField(15);
		stuIdField.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		bottom.add(stuIdLabel);
		bottom.add(stuIdField);
		
		//打卡时间栏
		JLabel workTimeLabel = new JLabel("选择值班时间：", JLabel.CENTER);
		workTimeLabel.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		workTimeComboBox = new JComboBox<String>();
		workTimeComboBox.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		String[] workTimeStr = {"第一大节","第二大节","第三大节","第四大节","第五大节"}; 
		for(int i = 0;i<5;i++) {
			workTimeComboBox.addItem(workTimeStr[i]);
		}
		workTimeComboBox.setEditable(false);//不可编辑
		bottom.add(workTimeLabel);
		bottom.add(workTimeComboBox);
		
		//值班星期栏
		JLabel weekDayLabel = new JLabel("选择值班星期：", JLabel.CENTER);
		weekDayLabel.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		weekDayComboBox = new JComboBox<String>();
		weekDayComboBox.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		String[] weekDayStr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"}; 
		for(int i = 0;i<7;i++) {
			weekDayComboBox.addItem(weekDayStr[i]);
		}
		weekDayComboBox.setEditable(false);//不可编辑
		bottom.add(weekDayLabel);
		bottom.add(weekDayComboBox);
		
		//确认和返回按钮栏
		submit = new JButton("拍照并录入");
		submit.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		back = new JButton("返 回");
		back.setFont(new Font("黑体", Font.BOLD, 18));// 字体
		bottom.add(submit);
		bottom.add(back);

		add(bottom, BorderLayout.SOUTH);

		center = new JPanel();
		center.setLayout(null);
		message = new JLabel("正在打开摄像头", JLabel.CENTER);
		message.setFont(new Font("黑体", Font.BOLD, 40));// 设置字体
		message.setBounds((640 - 400) / 2, 20, 400, 50);
		center.add(message);

		JPanel blackPanel = new JPanel();// 纯黑面板
		blackPanel.setBounds(150, 75, 320, 240);// 黑色面板的坐标与宽高
		blackPanel.setBackground(Color.BLACK);// 黑色背景
		center.add(blackPanel);
		add(center, BorderLayout.CENTER);

		// 摄像头启动线程
		cameraThread = new Thread() {
			public void run() {
				if (CameraServive.startCamera()) {// 如果摄像头成功开启
					message.setText("请面向摄像头");
					// 获取摄像头画面面板
					JPanel cameraPanel = CameraServive.getCameraPanel();
					// 设置面板的坐标和宽高
					cameraPanel.setBounds(150, 75, 320, 240);
					center.add(cameraPanel);
				} else {
					// 弹出提示
					JOptionPane.showMessageDialog(parent, "未检测到摄像头");
					back.doClick();
				}
			}
		};
		try {
			cameraThread.join();
			cameraThread.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 组件添加监听事件
	 */
	private void addListener() {
		//返回按钮事件，释放摄像头资源
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(cameraThread.isAlive ()) {};//等待摄像头开启,否则会摄像头资源出错
				CameraServive.releaseCamera();
				parent.setPanel(new StudentManagementPanel(parent));
			}
		});
		//确认按钮事件
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText().trim();//获取姓名
				String stuId = stuIdField.getText().trim();//获取学号
				WorkTime workTime = workTimeArr[workTimeComboBox.getSelectedIndex()];//获取打卡时间
				int weekday = weekDayComboBox.getSelectedIndex() +1;//获取值班星期几
				
				if (name == null || "".equals(name)) {
					JOptionPane.showMessageDialog(parent, "名字不能为空");
					nameField.grabFocus();
					return;
				}
				if (stuId == null || "".equals(stuId)) {
					JOptionPane.showMessageDialog(parent, "学号不能为空");
					stuIdField.grabFocus();
					return;
				}
				if(workTime == null){//值班时间错误
					JOptionPane.showMessageDialog(parent, "未选择值班时间");
					workTimeComboBox.grabFocus();
					return;
				}
				if(weekday<1 || weekday >7) {//值班星期几错误
					JOptionPane.showMessageDialog(parent, "未选择星期几");
					weekDayComboBox.grabFocus();
					return;
				}
				if(!CameraServive.cameralsOpen()){//摄像头未开启
					JOptionPane.showMessageDialog(parent, "摄像头未开启，请稍等");
					return;
				}
				
				
				/*   加入新学生  */
				// 获取当前摄像头捕捉的帧
				BufferedImage image = CameraServive.getCameraFrame();
				// 获取此图像中人脸的面部特征
				FaceFeature faceFeature = FaceEngineService.getFacefeature(image);
				if (faceFeature == null) {// 如果不存在面部特征
					JOptionPane.showMessageDialog(parent, "未检测到有效的人脸信息");
					return;
				}
				
				//创建打卡时间对象
				Student obj = HRService.addStudent(name, stuId, new WorkTime(workTime.getStart(),workTime.getEnd(), weekday),image);//添加新学生
				if(obj == null) {
					JOptionPane.showMessageDialog(parent, "添加失败,该学号已被占用");
					return;
				}
				Session.FACE_FEATURE_MAP.put(stuId, faceFeature);// 全局会话记录此学生面部特征
				JOptionPane.showMessageDialog(parent, "学生添加成功");//弹出提示
				back.doClick();
			}
		});
		//姓名输入框添加事件，实现enter后光标跳转到学号输入框
		nameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stuIdField.grabFocus();
			}
		});
		//学号输入框添加事件，实现enter后光标跳转到打卡时间下滑列表
		stuIdField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				workTimeComboBox.grabFocus();
			}
		});
		//打卡时间下滑列表添加事件，实现enter后光标跳转到选择星期几下滑列表
		workTimeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				weekDayComboBox.grabFocus();
			}
		});
	}
}
