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
    private int cell; //生成图片每个单元格的像素大小
    private BufferedImage cityMapImg;


    public City(int humanNums, int infectedNums, float infectionRate) {
        this.humanNums = humanNums;
        this.infectedNums = infectedNums;
        this.infectionRate = infectionRate;
        this.cell = 15; //默认为15

    }

    public City(int humanNums, int infectedNums, float infectionRate, int cell) {
        this.humanNums = humanNums;
        this.infectedNums = infectedNums;
        this.infectionRate = infectionRate;
        this.cell = cell;

    }

    public void initCity()
    {

        //加载地图图片
        try {
            cityMapImg = ImageIO.read(new File("./cityMap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                int x = random.nextInt(height);
                int y = random.nextInt(width);

                //人不能刷到墙里了
                while(map[x][y] == '1')
                {
                    x = random.nextInt(height);
                    y = random.nextInt(width);
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

            if(humans[i].x >=height || humans[i].x <0 || humans[i].y >=width || humans[i].y <0 || map[humans[i].x][humans[i].y] == '1') //如果撞墙了 就不动
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
        BufferedImage image;
//        System.out.println(width);
//        System.out.println(cell*width);
        image = new BufferedImage(cell*width,cell*height,BufferedImage.TYPE_INT_BGR);
        //image = new BufferedImage(1600,800,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,image.getWidth(),image.getHeight());

        g.drawImage(cityMapImg,0,0,null); // ?

        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                switch (peoplemap[i][j]){
                    case '1':
//                        g.setColor(Color.BLACK);
//                        g.fillRect(j*cell,i*cell,cell,cell);
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
