package cn.cqu.ljh;

import java.util.Random;

public class Human {
    public int x;
    public int y;
    public boolean infected;

    public Human(int x, int y, boolean infected) {
        this.x = x;
        this.y = y;
        this.infected = infected;
    }

    public void move(){
        Random random = new Random();
        int direction = random.nextInt(5);
        switch (direction){
            case 0:
                x = x +1;
                break;
            case 1:
                x =x -1;
                break;
            case 2:
                y = y+1;
                break;
            case 3:
                y = y-1;
                break;
            default:
                x= x;
        }
    }


    public static void main(String args[])
    {
        Human human = new Human(1,1,true);
        human.move();
    }


}
