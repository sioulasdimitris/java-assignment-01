package com.company;

import java.util.ArrayList;

public class MyThread {
    private String threadName;
    private int threadTime;
    private boolean isFinished;
    private ArrayList<String> waitFor = new ArrayList<String>();

    public MyThread(String threadName){
        this.threadName = threadName;
        this.isFinished = false;
    }

    public void setIsFinished(){
        this.isFinished = true;
    }

    public boolean getIsFinished(){
        return isFinished;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getThreadTime() {
        return threadTime;
    }

    public void setThreadTime(int threadTime) {
        this.threadTime = threadTime;
    }

    public void addThreadNameToWaitFor(String threadName){
        waitFor.add(threadName);
    }

    public ArrayList<String> getWaitFor() {
        return waitFor;
    }
}
