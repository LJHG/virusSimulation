package cn.cqu.ljh;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import java.awt.*;

public class simulationFrame extends JFrame {

    private  simulationCanvas canvas = new simulationCanvas();


    public simulationFrame(){



        setLayout(new BorderLayout());

        add(canvas, BorderLayout.CENTER); //使用边界布局需要给两个参数，第一个是部件，第二个是位置（东西南北中 ）
        setSize(1800,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //位于屏幕中央

        Thread t = new Thread(canvas);
        t.start();
    }




    public static void main(String[] args) {
        simulationFrame frame = new simulationFrame();
        frame.setVisible(true); //设置窗体可见
    }
}
