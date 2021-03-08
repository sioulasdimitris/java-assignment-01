package com.company;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Circle {
    private FontMetrics fm;
    private String threadName;
    private ArrayList<String> threadsNamesToWait = new ArrayList<String>();//string arraylist for keeping for each thread which threadNames needs to wait

    int x, y, width = 50, height = 50;

    public Circle(int x, int y, String threadName,ArrayList<String> threadsNamesToWait) {
        this.x = x;
        this.y = y;
        this.threadName = threadName;
        this.threadsNamesToWait = threadsNamesToWait;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, height);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.green);
        g2d.fill(circle);
        g.setColor(Color.black);
        g.drawString(this.threadName, x+15,y+30);
    }

    public String getThreadName(){
        return threadName;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public ArrayList<String> getThreadsNamesToWait(){
        return threadsNamesToWait;
    }

}