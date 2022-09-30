package cn.mr.clock.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import cn.mr.clock.pojo.Student;
import cn.mr.clock.service.HRService;
import cn.mr.clock.session.Session;
/**
 * 学生管理面板类
 * @author 龙星洛洛
 *
 */
@SuppressWarnings("serial")
public class StudentManagementPanel extends JPanel{
	private MainFrame parent;//主窗体
	private JTable table;//学生信息表格
	private DefaultTableModel model;//数据模型
	private JButton back;//返回按钮
	private JButton add;//添加学生按钮
	private JButton delete;//删除按钮
	/**
	 * 学生管理面板构造
	 * @param parent 主窗体
	 */
	public StudentManagementPanel(MainFrame parent) {
		this.parent = parent;
		init();
		addListener();
	}
	/**
	 * 组件初始化
	 */
	private void init() {
		this.parent.setTitle("学生管理面板");
		back = new JButton("返 回");
		add = new JButton("添加新学生");
		delete = new JButton("删除学生");
		model = new DefaultTableModel();
		
		/*  model初始化 */
		String columnName[] = {"学生名称","学生学号","打卡开始时间","打卡结束时间","工作日"};//表头
		int count = Session.Stu_SET.size();//员工人数
		String value[][] = new String[count][5];//表格展示数据
		String[] weekDayStr = {"0","星期一","星期二","星期三","星期四","星期五","星期六","星期日"}; 
		//创建员工结合的迭代器
		Iterator<Student> stuIterable = Session.Stu_SET.iterator();
		for (int i = 0; stuIterable.hasNext(); i++) {
			Student stu = stuIterable.next();
			value[i][0] = stu.getName();//获取名字
			value[i][1] = stu.getStudentId();//获取学生号
			value[i][2] = stu.getWorkTime().getStart();//获取打卡开始时间
			value[i][3] = stu.getWorkTime().getEnd();//获取打卡结束时间
			value[i][4] = weekDayStr[stu.getWorkTime().getWeekDay()];//获取星期几
		}
		model.setDataVector(value, columnName);//放入数据和表头
		
		table = new StuTable(model);
		JScrollPane scroll = new JScrollPane(table);//表格放入滚动面板
		
		setLayout(new BorderLayout());// 采用边界布局
		add(scroll,BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		bottom.add(add);
		bottom.add(delete);
		bottom.add(back);
		add(bottom,BorderLayout.SOUTH);
	}
	/**
	 * 为组件添加监听
	 */
	private void addListener() {
		//添加按钮事件
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setPanel(new AddStudentPanel(parent));
			}
		});
		//返回按钮事件
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setPanel(new MainPanel(parent));
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selecRow = table.getSelectedRow();//获得选中行索引
				if(selecRow != -1) {//如果有行被选中
					//弹出选择对话框
					int isDelete = JOptionPane.showConfirmDialog(parent, "确定删除该学生?","提示！",JOptionPane.YES_NO_OPTION);
					if(isDelete == JOptionPane.YES_OPTION){//确认删除
						//获取选中的员工编号
						/*  通过学号删除学生 */
						String stuId = (String)model.getValueAt(selecRow, 1);//下标为1的为学号
						HRService.deleteStu(stuId);
						model.removeRow(selecRow);//表格删除此行
						
					}
				}
			}
		});
		
	}
	
	
	/**
	 * 学生信息表格类
	 * @author 龙星洛洛
	 *
	 */
	private class StuTable extends JTable{
		public StuTable(TableModel tablemodel) {
			super(tablemodel);
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		@Override
		public boolean isCellEditable(int row,int column) {
			return false;//表格不可编辑	
		}
		@Override
		public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
			//获取单元格渲染对象
			DefaultTableCellRenderer cr = (DefaultTableCellRenderer)super.getDefaultRenderer(columnClass);
			//表格文字居中显示
			cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return cr;
		}
	}
	
}
