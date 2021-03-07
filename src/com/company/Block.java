package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
    //basic attributes of block
    private String hash; //we produce a hash
    private String previousHash; //hash of previous node
    private String data; //data for each block
    private long timeStamp;
    private int nonce; //variable for mining process

    public Block(String previousHash, String data, long timeStamp) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash(); //calculate current block hash
    }

    public String mineBlock(int prefix){
        System.out.println(new char[prefix]);
        String prefixString = new String(new char[prefix]).replace('\0','0');
        System.out.println(prefixString);
        while (!hash.substring(0,prefix).equals(prefixString)){
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String calculateBlockHash(){
        String dataToHash = previousHash + String.valueOf(timeStamp)+
                String.valueOf(nonce) + data;
        MessageDigest digest = null;
        byte[] bytes = null;
        try
        {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b: bytes)
            buffer.append(String.format("%02x",b));

        return buffer.toString();
    }

}
