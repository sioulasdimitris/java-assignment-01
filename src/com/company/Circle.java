package com.company;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle {
    private FontMetrics fm;
    private String threadName;

    int x, y, width = 50, height = 50;

    public Circle(int x, int y, String threadName) {
        this.x = x;
        this.y = y;
        this.threadName = threadName;
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

}