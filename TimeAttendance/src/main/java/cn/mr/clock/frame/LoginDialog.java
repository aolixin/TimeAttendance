package cn.mr.clock.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import cn.mr.clock.service.HRService;
/**
 * 管理员登录框
 * @author 龙星洛洛
 *
 */
@SuppressWarnings("serial")
public class LoginDialog extends JDialog {
	private JTextField usernameField = null;
	private JPasswordField passwordField = null;
	private JButton loginBtn = null;
	private JButton cancelBtn = null;
	private int WIDTH = 300,HEIGHT = 150;
	/**
	 * 
	 * @param owner 父窗口
	 * @param model 是否阻塞主窗体
	 */
	public LoginDialog(JFrame owner) {
		super(owner,"管理员登录", true);
		setSize(WIDTH,HEIGHT);
		//在owner中心显示
		setLocation(owner.getX() + (owner.getWidth() -WIDTH)/2,owner.getY() + (owner.getHeight() -HEIGHT)/2);
		init();
		addListener();
	}
	/**
	 * 组件初始化
	 */
	public void init(){
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginBtn = new JButton("登 录");
		cancelBtn = new JButton("取 消");
		JLabel labelUser = new JLabel("管 理 员 名 称: ",JLabel.CENTER);
		JLabel labelPassword = new JLabel("管 理 员 密 码: ",JLabel.CENTER);
		
		Container c = getContentPane();
		c.setLayout(new GridLayout(3,2));
		c.add(labelUser);
		c.add(usernameField);
		c.add(labelPassword);
		c.add(passwordField);
		c.add(loginBtn);
		c.add(cancelBtn);
	}
	/**
	 * 组件添加监听
	 */
	public void addListener() {
		// 注册按钮事件
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText().trim();
				String password = new String(passwordField.getPassword());
				//检测用户名和密码
				boolean result = HRService.userLogin(username,password);
				if(result) {
					LoginDialog.this.dispose();
				}else {
					//提示用户名或密码错误
					JOptionPane.showMessageDialog(LoginDialog.this,"用户名或密码错误");
				}
			}
		});
		//取消按钮事件
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDialog.this.dispose();//销毁登录框
			}
		});
		//密码框回车事件
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();
			}
			
		});
		//用户名框回车事件
		usernameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.grabFocus();
			}

		});
	}
}
