package cn.mr.clock.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import cn.mr.clock.pojo.Student;
import cn.mr.clock.service.CameraServive;
import cn.mr.clock.service.FaceEngineService;
import cn.mr.clock.service.HRService;
import cn.mr.clock.session.Session;
import cn.mr.clock.util.DateTimeUtil;
/**
 * 主面板类，包含了登录按钮，打卡按钮，查看考勤表按钮，查看学生表按钮
 * @author 龙星洛洛
 *
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel{
    private MainFrame parent;//主窗口
    private JToggleButton dakaTogBut;//打卡按钮
    private JButton kaoqinBut;//考勤表按钮
    private JButton stuButton;//学生管理表按钮
    private JTextArea inforTextArea;//提示信息文本域
    private DetectFaceThread detectFaceThread;//人脸识别线程
    private JPanel center;//中心面板
    /**
     * MainPanel构造函数
     * @param parent 父窗体
     */
    public MainPanel(MainFrame parent) {
        this.parent = parent;
        init();
        addListener();
    }
    /**
     * 初始化组件
     */
    public void init() {
        parent.setTitle("人脸识别打卡系统");

        detectFaceThread = null;

        center = new JPanel();
        center.setLayout(null);

        inforTextArea = new JTextArea();
        inforTextArea.setEditable(false);//文本域不可编辑
        inforTextArea.setFont(new Font("黑体",Font.BOLD,18));//字体
        JScrollPane scoll = new JScrollPane(inforTextArea);//文本域放入滚动面板
        scoll.setBounds(0,0,400,380);
        center.add(scoll);

        dakaTogBut = new JToggleButton("打 卡");
        dakaTogBut.setFont(new Font("黑体",Font.BOLD,40));//字体
        dakaTogBut.setBounds(430,300,300,70);
        center.add(dakaTogBut);

        JPanel blackPanel = new JPanel();//纯黑面板
        blackPanel.setBounds(420, 16, 320, 240);// 黑色面板的坐标与宽高
        blackPanel.setBackground(Color.BLACK);//黑色背景
        center.add(blackPanel);

        setLayout(new BorderLayout());// 主面板采用边界布局
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();//底部面板
        kaoqinBut = new JButton("考 勤");
        kaoqinBut.setFont(new Font("黑体",Font.BOLD,40));//字体
        stuButton = new JButton("学生管理");
        stuButton.setFont(new Font("黑体",Font.BOLD,40));//字体
        bottom.add(kaoqinBut);
        bottom.add(stuButton);
        add(bottom,BorderLayout.SOUTH);
    }
    /**
     * 人脸识别线程
     * @author 龙星洛洛
     *
     */
    private class DetectFaceThread extends Thread{
        boolean work = true;
        public void run() {
            while(work) {
                if(CameraServive.cameralsOpen()) {
                    //获取摄像头当前帧
                    BufferedImage frame = CameraServive.getCameraFrame();
                    if(frame != null) {
                        //获取当前帧出现的人脸对应的特征码
                        String stuId = FaceEngineService.detecFace(FaceEngineService.getFacefeature(frame));
                        if(stuId != null) {
                            Student stu = HRService.getStu(stuId);
                            if(stu != null) {
                                HRService.addClockInRecord(stu);
                                //文本域添加提示信息
                                inforTextArea.append("\n" + DateTimeUtil.dateTimeNow() + "\n");
                                inforTextArea.append(stu.getName() + "打卡成功。\n\n");
                                try {
                                    sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            }
        }
        public synchronized void stopThread() {
            work  = false;
        }
    }
    /**
     * 释放摄像头以及面板中的一些资源
     */
    private void releaseCamera() {
        CameraServive.releaseCamera();
        inforTextArea.append("摄像头已关闭。\n");
        if(detectFaceThread != null ) {
            detectFaceThread.stopThread();
        }
        dakaTogBut.setText("打 卡");
        dakaTogBut.setSelected(false);
        dakaTogBut.setEnabled(true);
    }
    /**
     * 添加监听
     */
    public void addListener() {
        //打卡按钮事件
        dakaTogBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(dakaTogBut.isSelected()) {
                    inforTextArea.append("正在开启摄像头，请稍后······\n");//文本域添加提示信息
                    dakaTogBut.setSelected(true);
                    dakaTogBut.setText("关闭摄像头");

                    //创建启动摄像头的临时线程
                    Thread camThread = new Thread() {
                        public void run() {
                            //摄像头正常工作
                            if(CameraServive.startCamera()) {
                                inforTextArea.append("请面向摄像头打卡。\n");
                                dakaTogBut.setEnabled(true);
                                JPanel cameraPanel = CameraServive.getCameraPanel();
                                //设置面板坐标和宽高
                                cameraPanel.setBounds(420, 16, 320, 240);
                                center.add(cameraPanel);
                            }else {
                                //弹出提示
                                JOptionPane.showMessageDialog(parent, "未检测到摄像头");
                                releaseCamera();
                                return;
                            }
                        }
                    };
                    camThread.start();
                    detectFaceThread = new DetectFaceThread();
                    detectFaceThread.start();
                }else {
                    //打卡按钮未被选中，释放摄像头资源
                    inforTextArea.append("正在开启关闭······\n");//文本域添加提示信息
                    releaseCamera();
                    dakaTogBut.setText("打 卡");
                    dakaTogBut.setSelected(false);
                }
            }
        });
        //考勤按钮事件
        kaoqinBut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(Session.user == null) {
                    //创建登录对话框
                    LoginDialog ld = new LoginDialog(parent);
                    ld.setVisible(true);
                }
                if(Session.user != null) {
                    //创建考勤报表
                    //创建学生管理面板
                    AttendanceManagementPanel att = new AttendanceManagementPanel(parent);
                    parent.setPanel(att);
                    releaseCamera();
                }
            }

        });
        //学生按钮事件
        stuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Session.user == null) {
                    //创建登录对话框
                    LoginDialog ld = new LoginDialog(parent);
                    ld.setVisible(true);
                }
                if(Session.user != null) {
                    //创建学生管理面板
                    StudentManagementPanel stuManPanel = new StudentManagementPanel(parent);
                    parent.setPanel(stuManPanel);
                    releaseCamera();
                }

            }
        });
    }

}
