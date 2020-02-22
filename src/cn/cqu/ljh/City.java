package cn.cqu.ljh;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.PrinterMakeAndModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class City {

    private int height; //城市纵向单元格
    private int width;  // 城市横向单元格

    private char map[][]; //

    private char peoplemap[][];

    private int humanNums; //总人数
    private int infectedNums; //感染人数
    private float infectionRate; //感染率

    private Human humans[];

    public City(int humanNums, int infectedNums, float infectionRate) {
        this.humanNums = humanNums;
        this.infectedNums = infectedNums;
        this.infectionRate = infectionRate;
    }

    public void initCity()
    {

        try {
            //读取Map生成最初的地图
            Scanner scan = new Scanner(new File("./map.txt"));
            height = Integer.parseInt(scan.nextLine());
            width = Integer.parseInt(scan.nextLine());
            map = new char[height][width];
            peoplemap = new char[height][width];
            for(int i=0;i<height;i++)
            {
                String row = scan.nextLine();
                for(int j=0;j<width;j++)
                {
                    map[i][j] = row.charAt(j); //貌似不能直接row[j]
                }
            }

            //往城市添加人类
            humans = new Human[humanNums];
            Random random = new Random();
            for(int i=0;i<humanNums;i++)
            {

                int x = random.nextInt(50);
                int y = random.nextInt(50);

                //人不能刷到墙里了
                while(map[x][y] == '1')
                {
                    x = random.nextInt(50);
                    y = random.nextInt(50);
                }

                if(i<infectedNums)
                {
                    humans[i] = new Human(x,y,true);

                }
                else {
                    humans[i] = new Human(x, y, false);
                }

            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void move()
    {
        //用map来初始化peoplemap
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
                peoplemap[i][j] = map[i][j];
        }

        //全部人动一次
        for(int i=0;i<humanNums;i++)
        {
            int prevx = humans[i].x;
            int prevy = humans[i].y;

            humans[i].move();

            if(humans[i].x >=50 || humans[i].x <0 || humans[i].y >=50 || humans[i].y <0 || map[humans[i].x][humans[i].y] == '1') //如果撞墙了 就不动
            {
                humans[i].x = prevx;
                humans[i].y = prevy;
            }

            if(!humans[i].infected)
            {
                for(int j=0;j<humanNums;j++)
                {
                    if(humans[j].infected && humans[j].x == humans[i].x && humans[j].y == humans[i].y)
                    {
                        Random random = new Random();
                        if(random.nextFloat() < infectionRate)
                        {
                            //System.out.println(random.nextFloat());
                            humans[i].infected = true;
                        }
                    }
                }
            }

            if(humans[i].infected)
            {
                peoplemap[humans[i].x][humans[i].y] = 'r';
            }
            else
            {
                peoplemap[humans[i].x][humans[i].y] = 'g';
            }

        }



        int infectant  = 0;
        for(int i=0; i < humanNums;i++)
        {
            if(humans[i].infected){
                infectant++;
            }
        }
        System.out.println("当前感染人数为"+ infectant);
    }



    public void printMap() {
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                System.out.print(peoplemap[i][j]);
            }
            System.out.println();
        }
    }

    public BufferedImage generateMapImg()
    {
        int cell = 15;
        BufferedImage image;
        image = new BufferedImage(cell*width,cell*height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,image.getWidth(),image.getHeight());

        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                switch (peoplemap[i][j]){
                    case '1':
                        g.setColor(Color.BLACK);
                        g.fillRect(j*cell,i*cell,cell,cell);
                        break;
                    case 'r':
                        g.setColor(Color.RED);
                        g.fillRect(j*cell,i*cell,cell,cell);
                        break;
                    case 'g':
                        g.setColor(Color.GREEN);
                        g.fillRect(j*cell,i*cell,cell,cell);
                        break;

                }
            }
        }

        return image;
    }


    public char[][] getMap()
    {
        return peoplemap;
    }

    public static void main(String[] args) {
        City c = new City(100,10,0.2f);
        c.initCity();
        c.move();
        try {
            ImageIO.write(c.generateMapImg(),"png",new File("a.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.move();
        try {
            ImageIO.write(c.generateMapImg(),"png",new File("b.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
