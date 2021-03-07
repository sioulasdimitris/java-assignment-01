package com.company;
import java.lang.Thread;
public class MyRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("!");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
