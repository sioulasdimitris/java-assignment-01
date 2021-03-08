package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int prefix = 2;
    public static ArrayList<MyThread> threadsData = new ArrayList<MyThread>();
    public static Thread[] threadsArray;
    public static DynamicShapes dynamicShapes;

    public static void main(String[] args) {
        System.out.println("MAIN started...");
        FileUtils fu = new FileUtils();
        fu.readPrecedenceFile();
        //fu.printThreadsHelperMethod();
        fu.readTimingsFileMethod();
        //fu.printThreadsWithTimingsHelperMethod();

        threadsData = fu.getMyThreads();//array for thread data
        threadsArray = new Thread[threadsData.size()];//create an array of threads

        for(int i=0;i<threadsData.size();i++){
            threadsArray[i] = new Thread(new MyRunnable(threadsData.get(i).getThreadName(),threadsData.get(i).getThreadTime()),threadsData.get(i).getThreadName());
        }


        //GUI
        JFrame frame = new JFrame();

        dynamicShapes = new DynamicShapes();
        frame.add(dynamicShapes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for(int i=0;i<threadsData.size();i++){
            threadsArray[i].start();
        }


        System.out.println("MAIN finished...");
    }

}
