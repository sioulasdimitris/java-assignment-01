package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Block> blockChain = new ArrayList<>();
    public static int prefix = 2;
    private static ArrayList<MyThread> threadsData = new ArrayList<MyThread>();
    private static Thread[] threadsArray;
    private String message;


    public static void main(String[] args) {
        FileUtils fu = new FileUtils();
        fu.readPrecedenceFile();
        //fu.printThreadsHelperMethod();
        fu.readTimingsFileMethod();
        //fu.printThreadsWithTimingsHelperMethod();

        threadsData = fu.getMyThreads();
        threadsArray = new Thread[threadsData.size()];


        for(int i=0;i<threadsData.size();i++){
            MyRunnable runnableInstance = new MyRunnable();
            threadsArray[i] = new Thread(runnableInstance);

            threadsArray[i].start();

            try {
                threadsArray[i].join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
