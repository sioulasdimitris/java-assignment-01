package com.company;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicShapes extends JPanel {
    private static DynamicShapes dynamicShapes = new DynamicShapes();//singleton design pattern - instance will be created at load time

    public static int posX = 15;
    public static int posY = 15;
    public static LocalTime executionTime;
    public static Graphics gr;

    public static List<Circle> shapes = new ArrayList<>();

    public DynamicShapes() {
        executionTime = LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(new Date().getTime()));
        setBackground(Color.white);
        setPreferredSize(new Dimension(400, 400));
    }

    public void addNewCircle(String threadName,long timestamp, ArrayList<String> threadNamesToWait){
        LocalTime newExecutionTime = LocalTime.parse(new SimpleDateFormat("HH:mm:ss").format(timestamp));

        if(newExecutionTime.isAfter(executionTime)) {
            executionTime = newExecutionTime;
            addCircle(threadName,1,threadNamesToWait);
        } else {
            addCircle(threadName,0,threadNamesToWait);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gr = g;
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
                if(((Circle) s).getThreadsNamesToWait().size()>0){//if there are threads to wait
                    for (Circle s1 : shapes) {
                        for(String threadNameToWait : ((Circle) s).getThreadsNamesToWait()){
                            if(s1.getThreadName().equals(threadNameToWait)){
                                drawArrowLine(g,((Circle) s).getX(),((Circle) s).getY(),s1.getX(),s1.getY(),10,10);
                            }
                        }
                    }
                }
            }
        }

    }

    public void addCircle(String threadName, int posY, ArrayList<String> threadNamesToWait) {
        if(posY>0){
            DynamicShapes.posY+=60;
        }
        DynamicShapes.posX += 60;
        if(threadNamesToWait.size()>0){
            DynamicShapes.posY += 40;
            DynamicShapes.posX += 10;
        }
        shapes.add(new Circle(DynamicShapes.posX,DynamicShapes.posY,threadName,threadNamesToWait));
        repaint();
    }

    public List<Circle> getCircles(){
        return shapes;
    }

    public static DynamicShapes getDynamicShapes(){
        return dynamicShapes;
    }

    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

}