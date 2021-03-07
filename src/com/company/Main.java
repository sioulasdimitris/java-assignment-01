package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Block> blockChain = new ArrayList<>();
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










//        long startTime = System.nanoTime();
//        System.out.println("Started...");
//
//        //Genesis Block
//        Block genesisBlock = new Block("0","data 111",new Date().getTime());
//        genesisBlock.mineBlock(prefix);
//        blockChain.add(genesisBlock);
//        System.out.println("Node:"+(blockChain.size()-1)+" created");
//
//        //Second block
//        Block secondBlock = new Block(blockChain.get(blockChain.size()-1).getHash(),"data 222",new Date().getTime());
//        secondBlock.mineBlock(prefix);
//        blockChain.add(secondBlock);
//        System.out.println("Node:"+(blockChain.size()-1)+" created");
//
//        //Second block
//        Block thirdBlock = new Block(blockChain.get(blockChain.size()-1).getHash(),"data 333",new Date().getTime());
//        thirdBlock.mineBlock(prefix);
//        blockChain.add(thirdBlock);
//        System.out.println("Node:"+(blockChain.size()-1)+" created");
//
//        long endTime = System.nanoTime();
//        long duration = endTime-startTime;
//        System.out.println("Total time for blocks:"+(float)duration/1000000000+ " seconds");
//        System.out.println("BlockChain is valid?:"+ChainValidator.isChainValid(prefix,blockChain));

        System.out.println("MAIN end...");
    }

}
