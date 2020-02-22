package cn.cqu.ljh;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import java.awt.*;

public class simulationFrame extends JFrame {

    private  simulationCanvas canvas = new simulationCanvas();
    public simulationFrame(){
        setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //位于屏幕中央
        Thread t = new Thread(canvas);
        t.start();
    }

    public static void main(String[] args) {
        simulationFrame frame = new simulationFrame();
        frame.setVisible(true);
    }
}
