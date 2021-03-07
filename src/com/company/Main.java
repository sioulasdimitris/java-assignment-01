package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int prefix = 2;
    public static ArrayList<MyThread> threadsData = new ArrayList<MyThread>();
    public static Thread[] threadsArray;


    public static void main(String[] args) {
        FileUtils fu = new FileUtils();
        fu.readPrecedenceFile();
        //fu.printThreadsHelperMethod();
        fu.readTimingsFileMethod();
        //fu.printThreadsWithTimingsHelperMethod();

        threadsData = fu.getMyThreads();//array for thread data
        threadsArray = new Thread[threadsData.size()];//create an array of threads


        for(int i=0;i<threadsData.size();i++){
            threadsArray[i] = new Thread(new MyRunnable(threadsData.get(i).getThreadName(),threadsData.get(i).getThreadTime()),threadsData.get(i).getThreadName());
            threadsArray[i].start();
        }









        System.out.println("MAIN end...");
    }

}
