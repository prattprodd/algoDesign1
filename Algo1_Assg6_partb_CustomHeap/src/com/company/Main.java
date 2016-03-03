package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] strIntArr = new int[10000];
        int idx = 0;
        try {
            for (String line : Files.readAllLines(Paths.get("Median.txt"), StandardCharsets.UTF_8)) {
                Integer i = Integer.valueOf(line);
                strIntArr[idx] = i;
                idx++;
            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }

        Heap heapLow = new Heap((strIntArr.length/2) + 1, "max");
        Heap heapHigh = new Heap((strIntArr.length/2) + 1, "min");
        long sumOfMedians = 0;

        //Init heaps
        //HeapHigh - Extract MIN
        //HeapLow - Extract MAX

        heapHigh.addToHeap(strIntArr[0]);
        System.out.println(heapHigh.peek());
        sumOfMedians = sumOfMedians + heapHigh.peek();

        if (strIntArr[1] <= heapHigh.peek()){
            heapLow.addToHeap(strIntArr[1]);
        } else {
            heapLow.addToHeap(heapHigh.pop());
            heapHigh.addToHeap(strIntArr[1]);
        }
        System.out.println(heapLow.peek());
        sumOfMedians = sumOfMedians + heapLow.peek();


        for (idx = 2; idx < 10000; idx++){
            if (strIntArr[idx] <= heapLow.peek()){
                heapLow.addToHeap(strIntArr[idx]);
            } else if (strIntArr[idx] >= heapHigh.peek()) {
                heapHigh.addToHeap(strIntArr[idx]);
            } else {
                if (heapLow.currSize<= heapHigh.currSize){
                    heapLow.addToHeap(strIntArr[idx]);
                } else {
                    heapHigh.addToHeap(strIntArr[idx]);
                }
            }
            //System.out.println(Arrays.toString(heapHigh.getHeap()));
            reBalanceHeaps(heapLow, heapHigh);
            sumOfMedians = sumOfMedians + heapLow.peek();
            System.out.println("Low:" + heapLow.peek());
            //System.out.println("LowSize: " + heapLow.currSize);
            //System.out.println(Arrays.toString(heapLow.getHeap()));
            //System.out.println("High:" + heapHigh.peek());
            //System.out.println("HighSize: " + heapHigh.currSize);
            //System.out.println(Arrays.toString(heapHigh.getHeap()));

        }


        //System.out.println("High:" + heapHigh.peek());
        //System.out.println(Arrays.toString(heapLow.getHeap()));
        //System.out.println(Arrays.toString(heapHigh.getHeap()));

        System.out.println(sumOfMedians);
        System.out.println(sumOfMedians/10000);

    }

    public static void reBalanceHeaps(Heap heap1, Heap heap2){
        int eval;
        if (heap1.currSize - heap2.currSize > 1){
            eval = heap1.pop();
            heap2.addToHeap(eval);
        }else if (heap1.currSize - heap2.currSize < 0){
            eval = heap2.pop();
            heap1.addToHeap(eval);
        }
    }

}
