package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicShapes extends JPanel {
    public static int posX = 15;
    public static int posY = 15;

    public static List<Object> shapes = new ArrayList<>();
    private Random random = new Random();

    public DynamicShapes() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(400, 400));
    }

    public void addNewCircle(String threadName){
        addCircle(threadName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Object s : shapes) {
            if (s instanceof Circle) {
                ((Circle) s).draw(g);
            }
        }
    }

    public void addCircle(String threadName) {
        DynamicShapes.posX += 60;
        shapes.add(new Circle(DynamicShapes.posX,DynamicShapes.posY,threadName));
        repaint();
    }



}