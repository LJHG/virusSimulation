package cn.cqu.ljh;

import javax.swing.*;
import java.awt.*;

public class simulationCanvas extends JPanel implements Runnable {
    private City city;
    public simulationCanvas(){
        city = new City(100,10,0.2f);
        city.initCity();
    }
    @Override
    public void paint(Graphics g) { //重写
        super.paint(g);
        g.drawImage(city.generateMapImg(),0,0,null);
    }

    @Override
    public void run() {

        while(true){
            try {
                Thread.sleep(100);
                city.move();
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
