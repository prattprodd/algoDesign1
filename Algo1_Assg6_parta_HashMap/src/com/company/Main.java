package com.company;

import sun.security.timestamp.TimestampToken;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    //TODO Custom HashMap

    public static void main(String[] args) {

        long[] numArray = new long[1000000];
        int b = 0;
        HashMap hashMap = new HashMap<Long, Long>(1000000);

        try {
            for (String line : Files.readAllLines(Paths.get("algo1-programming_prob-2sum.txt"), StandardCharsets.UTF_8)) {
                Long val = Long.valueOf(line);
                numArray[b] = val;
                if (!hashMap.containsKey(val)) {
                    hashMap.put(val, val);
                }
                b++;
            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        };


        //System.out.println(hashMap.size());
        //System.out.println(numArray.length);
        //248 repetitions

        //System.out.println(System.currentTimeMillis());

        long count = 0;

        for (int t = -10000; t <= 10000; t++) {

            boolean tSumFound = false;
            int i = 0;
            while (!tSumFound && i < numArray.length) {
                if (hashMap.containsKey(t - numArray[i])){
                    if (hashMap.get(t - numArray[i])!= numArray[i]){
                        tSumFound = true;
                    }
                }
                i++;
            }
            if (tSumFound) {count++;}
        }

        System.out.println(count);
        //System.out.println(System.currentTimeMillis());

    }
}
