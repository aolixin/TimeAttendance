package cn.mr.clock.main;

import cn.mr.clock.frame.MainFrame;
import cn.mr.clock.frame.MainPanel;
import cn.mr.clock.service.CameraServive;
import com.github.sarxos.webcam.Webcam;

import java.util.List;

/**
 * 主函数
 * 生成MainFrame 主窗口
 * @author 龙星洛洛
 */
public class Main {

    public static void main(String[] args) {
        MainFrame jf = new MainFrame();//主窗口
        jf.setBounds(300,200,900,600);
        MainPanel mainPanel = new MainPanel(jf);
        jf.setPanel(mainPanel);//添加主面板
        jf.setVisible(true);
    }
}