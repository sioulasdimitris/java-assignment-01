package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileUtils {
    private String precedenceFile = "p_precedence.txt"; //C:\my_workspaces\intellij_workspace\Ergasia01\out\production\Ergasia01\p_precedence.txt
    private String timingsFile = "p_timings.txt"; //C:\my_workspaces\intellij_workspace\Ergasia01\out\production\Ergasia01\p_timings.txt
    private ArrayList<MyThread> myThreads = new ArrayList<MyThread>();
    private MyThread myThread;

    public FileUtils(){
    }

    //this method reads the precendence file, creates the thread objects(name,waitfor), adds each thread in the arraylist
    public void readPrecedenceFile(){
        try {
            ;
            File myObj = new File(getResourcesPath(precedenceFile));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] waitForSplit = data.split(" waitfor ");
                myThread = new MyThread(waitForSplit[0]);//create new Thread object
                if(waitForSplit.length>1){//if there are threads to waitFor
                    String[] commaSplit = waitForSplit[1].split(",");
                    for(int i=0;i < commaSplit.length;i++){
                        myThread.addThreadNameToWaitFor(commaSplit[i]);//add the threads to wait for to the thread
                    }
                }
                myThreads.add(myThread);//add it to the arraylist
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //this method reads the timings file and sets the time to each thread
    public void readTimingsFileMethod(){
        try {
            File myObj = new File(getResourcesPath(timingsFile));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] spaceSplit = data.split(" ");
                if(spaceSplit.length>1){//if there is timing value in the file
                    for(MyThread thread : myThreads){
                        if(thread.getThreadName() != null && thread.getThreadName().equals(spaceSplit[0])){
                            thread.setThreadTime(Integer.parseInt(spaceSplit[1]));
                        }
                    }
                } else {//if there is no timing value in the file set it to 0
                    for(MyThread thread : myThreads){
                        if(thread.getThreadName() != null && thread.getThreadName().equals(spaceSplit[0])){
                            thread.setThreadTime(0);
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void printThreadsHelperMethod(){
        int count = 0;
        while (myThreads.size() > count) {
            System.out.print(myThreads.get(count).getThreadName()+" waitFor ");
            for(int i=0;i<myThreads.get(count).getWaitFor().size();i++){
                if(i>0)
                    System.out.print(",");
                System.out.print(myThreads.get(count).getWaitFor().get(i));
            }
            System.out.println();
            count++;
        }
    }

    public void printThreadsWithTimingsHelperMethod(){
        int count = 0;
        while (myThreads.size() > count) {
            System.out.print(myThreads.get(count).getThreadName()+" ");
            System.out.print(myThreads.get(count).getThreadTime());
            System.out.println();
            count++;
        }
    }

    public ArrayList<MyThread> getMyThreads(){
        return myThreads;
    }

    public String getDesktopPath(){
        return new File(System.getProperty("user.home"), "Desktop").getPath();
    }

    public String getResourcesPath(String filename){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        return file.getAbsolutePath();
    }
}
