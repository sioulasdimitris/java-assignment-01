package com.company;
import java.lang.Thread;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class MyRunnable implements Runnable{
    private static List<Block> blockChain = Collections.synchronizedList(new ArrayList());
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
            Main.dynamicShapes.addNewCircle(threadName);//add new circle shape
            startTime = System.currentTimeMillis();
            Thread.sleep(threadSleepTime);

            endTime = System.currentTimeMillis();
            totalDuration = endTime-startTime;
            waitedIdleTime = endTime-callTime;

            String threadNameDependencies = "";
            for(String s :threadsNamesToWait){
                threadNameDependencies += s+" ";
            }
            data = threadName+" "+totalDuration+" "+threadNameDependencies+""+endTime;
            System.out.println(threadName+" finished, "+"waited idle for "+waitedIdleTime+", process time: "+totalDuration);

            synchronized(blockChain) {
                //blockchain
                if(blockChain.size()==0){
                    addToBlockChain(new Block("0",data,new Date().getTime()));
                } else {
                    addToBlockChain(new Block(blockChain.get(blockChain.size()-1).getHash(),data,new Date().getTime()));
                }
                //sqlite
                addToDatabase();
            }

        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addToBlockChain(Block block) {
            block.mineBlock(Main.prefix);
            blockChain.add(block);
            //System.out.println("BlockChain is valid?:"+ChainValidator.isChainValid(Main.prefix,blockChain));
    }

    synchronized void addToDatabase() throws SQLException, ClassNotFoundException {
        SQLiteHelper.getSQLiteHelper().setConnection();
        SQLiteHelper.getSQLiteHelper().insertToDb(blockChain.get(blockChain.size()-1));
        for(Thread thread : Main.threadsArray){
            if(thread.isAlive()) break;
            SQLiteHelper.getSQLiteHelper().closeConnection();
        }

    }
}
