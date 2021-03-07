package com.company;
import java.lang.Thread;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyRunnable implements Runnable{
    public static List<Block> blockChain = new ArrayList<>();
    private String threadName;
    private ArrayList<String> threadsNamesToWait = new ArrayList<String>();
    private ArrayList<Thread> threadsToWait = new ArrayList<Thread>();
    private int threadSleepTime;
    private long startTime;
    private long callTime;
    private long endTime;
    private long waitedIdleTime;
    private long totalDuration;
    private String data;

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
        callTime = System.currentTimeMillis();
        try {
            for(Thread waitFor : threadsToWait){
                waitFor.join();
            }
            System.out.println(threadName+" started");
            startTime = System.currentTimeMillis();
            Thread.sleep(threadSleepTime);

            endTime = System.currentTimeMillis();
            totalDuration = endTime-startTime;
            waitedIdleTime = endTime-callTime;

            String threadNameDependencies = "";
            for(String s :threadsNamesToWait){
                threadNameDependencies += s+" ";
            }
            data = threadName+" "+totalDuration+" "+threadNameDependencies+" "+endTime;
            System.out.println(threadName+" finished, "+"waited idle for "+waitedIdleTime+", process time: "+totalDuration);

            //blockchain
            Block currentBlock;
            if(blockChain.size()==0){
                currentBlock = new Block("0",data,new Date().getTime());
            } else {
                currentBlock = new Block(blockChain.get(blockChain.size()-1).getHash(),data,new Date().getTime());
            }
            currentBlock.mineBlock(Main.prefix);
            blockChain.add(currentBlock);

            //sqlite
            String jdbcUrl = "jdbc:sqlite:C:/my_workspaces/intellij_workspace/Ergasia01/res/blocksdb.db";
            try {
                Class.forName("org.sqlite.JDBC");
                Connection connection = DriverManager.getConnection(jdbcUrl);
                String sql = "INSERT INTO blocks(hash, previoushash, data, timeStamp) values ('"+currentBlock.getHash()+"','"+currentBlock.getPreviousHash()+"','"+currentBlock.getData()+"','"+currentBlock.getTimeStamp()+"')";

                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);



            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
