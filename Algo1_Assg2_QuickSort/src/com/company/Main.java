package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

//TODO Implement randomized Quicksort Algorithm

    public static long qsCount;

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("QuickSort.txt"), StandardCharsets.UTF_8)) {
                Integer i = Integer.valueOf(line);
                numbers.add(i);
            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        };

        int[] myArray = new int[numbers.size()];
        Iterator<Integer> iterator = numbers.iterator();
        //int[] myArray = numbers.toArray(new int[numbers.size()]);
        for(int i = 0;i < myArray.length;i++)
        {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others
            myArray[i] = iterator.next().intValue();
            //System.out.println(myArray[i]);
            //System.out.println(myArray[i]);
        }

        //long qsCount = 0;
        //qsCount = quickSortCount(myArray, 0, myArray.length-1);
        quickSortCount(myArray, 0, myArray.length-1);
        System.out.println(Arrays.toString(myArray));
        System.out.println(qsCount);

    }

    public static void quickSortCount(int[] array, int startidx, int endidx) {

        //long retCompCount = 0;
        int arrayLength = endidx - startidx + 1;
        //int[] retArray = new int[arrayLength];
        if (arrayLength > 1) {
            //System.out.println(arrayLength);
            //retCompCount = 0;
            //retArray = array;
            //return true;
            //return;
        //} else {

            qsCount = qsCount + arrayLength-1 ;

            int pivotIndex = startidx;
            pivotIndex = choosePivot(array, startidx, endidx);
            int temp0 = 0;
            temp0 = array[startidx];
            array[startidx] = array[pivotIndex];
            array[pivotIndex] = temp0;

            //int l, r;
            //l = 0;
            //r = arrayLength - 1;
            //int[] partitionedArray;
            //aQSCompCount = partitionQS(array, l, r);
            int p;
            p = array[startidx];
            int i, j;
            i = startidx + 1;
            int temp1;
            //int partCount = 0;

            for (j = startidx + 1; j <= endidx; j++) {
                if (array[j] < p) {
                    temp1 = array[i];
                    array[i] = array[j];
                    array[j] = temp1;
                    i = i + 1;
                }
            }
            array[startidx] = array[i - 1];
            array[i - 1] = p;

            //long qsCompCount1, qsCompCount2;
            //qsCompCount1 = quickSortCount(array, startidx, (i - 2));
            //qsCompCount2 = quickSortCount(array, i, endidx);
            //retCompCount = retCompCount + ;
            quickSortCount(array, startidx, (i - 2));
            quickSortCount(array, i, endidx);
            //return;
        }
        //return true;
    }

    public static int choosePivot(int[] array, int sidx, int eidx){

        int retindex;
        
        //Problem1
        //retindex = sidx;

        //Problem2
        //retindex = eidx;

        //Problem 3
        //retindex = medianidx(array[sidx], array[eidx], array[(sidx + eidx)/2])

        int mididx = (sidx + eidx)/2 ;
        
        if (array[sidx] <= array[eidx]){

            if (array[mididx] <= array[sidx]){
                retindex = sidx;
            } 

            else if (array[mididx] > array[eidx]){
                retindex = eidx;
            }

            else {
                retindex = mididx;
            }

        } 

        else {

            if (array[mididx] <= array[eidx]){
                retindex = eidx;
            } 

            else if (array[mididx] > array[sidx]){
                retindex = sidx;
            }

            else {
                retindex = mididx;
            }

        }


        return retindex;

    }


}




//5234
