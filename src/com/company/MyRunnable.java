package com.company;
import java.lang.Thread;
import java.util.ArrayList;

public class MyRunnable implements Runnable{
    private String threadName;
    private ArrayList<String> threadsNamesToWait = new ArrayList<String>();
    private ArrayList<Thread> threadsToWait = new ArrayList<Thread>();
    private int threadSleepTime;

    public MyRunnable(String threadName, int threadSleepTime){
        this.threadName = threadName;
        this.threadSleepTime = threadSleepTime;

        for(MyThread thread : Main.threadsData){//get the name of the threads this thread needs to wait
            if(thread.getThreadName() != null && thread.getThreadName().equals(threadName)){
                threadsNamesToWait = thread.getWaitFor();
            }
        }

        for(String thName : threadsNamesToWait){//get the the threads this thread needs to wait
            for(Thread thread : Main.threadsArray){
                if(thread != null){
                    if(thread.getName() != null && thread.getName().equals(thName)){
                        threadsToWait.add(thread);
                    }
                }
            }
        }

    }

    @Override
    public void run() {

        try {

            for(Thread waitFor : threadsToWait){
                waitFor.join();
            }
            System.out.println(threadName+" started");
            Thread.sleep(threadSleepTime);
            System.out.println(threadName+" finished");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
