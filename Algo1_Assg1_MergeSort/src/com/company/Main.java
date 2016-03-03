package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.ArrayUtils;

public class Main {

    public static void main(String[] args) {

        //Get input array
        //int[] myArray;

        //String contents = FileUtils.readFileToString(new File("path/to/your/file.txt"));
        //String[] array = ArrayUtils.toArray(contents.split("\n"));

        //int[] testArray = {6,5,4,3,2,2};

        List<Integer> numbers = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("IntegerArray.txt"), StandardCharsets.UTF_8)) {
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

        ArrayInvCount arrayInvCount;
        long inversionCount;
        int[] sortedArray;
        //System.out.println((myArray.length));
        arrayInvCount = invCount(myArray, myArray.length);
        //arrayInvCount = invCount(testArray, testArray.length);
        inversionCount = arrayInvCount.getArrayCount();
        sortedArray = arrayInvCount.getArray();
        System.out.println(Arrays.toString(sortedArray));
        System.out.println(inversionCount);

    }


    public static ArrayInvCount invCount(int[] array, int arrayLength) {

        long retInvCount = 0;
        int[] retArray = new int[arrayLength];
        if (arrayLength == 1) {
            //System.out.println(arrayLength);
            retInvCount = 0;
            retArray = array;
        }

        else {
            long x, y, z;
            int[] xArray, yArray, zArray;
            ArrayInvCount xArrayInvCount, yArrayInvCount, zArrayInvCount;
            //System.out.println((arrayLength/2)-1);
            //System.out.println(arrayLength-1);
            //System.out.println("beforex");
            xArrayInvCount = invCount(Arrays.copyOfRange(array, 0, (arrayLength/2)), (arrayLength/2));
            //System.out.println("beforey");
            yArrayInvCount = invCount(Arrays.copyOfRange(array, (arrayLength/2), arrayLength), arrayLength - (arrayLength/2));
            x = xArrayInvCount.getArrayCount();
            xArray = xArrayInvCount.getArray();
            y = yArrayInvCount.getArrayCount();
            yArray = yArrayInvCount.getArray();
            //System.out.println("beforez");
            zArrayInvCount = countSplitInv(xArray, yArray, arrayLength);
            z = zArrayInvCount.getArrayCount();
            zArray = zArrayInvCount.getArray();
            retInvCount = x + y + z;
            retArray = zArray;
        }
        ArrayInvCount arrayinvcount = new ArrayInvCount();
        arrayinvcount.setArray(retArray);
        arrayinvcount.setArrayCount(retInvCount);
        //System.out.println(retInvCount);
        return arrayinvcount;
    }

    public static ArrayInvCount countSplitInv(int[] array1, int[] array2, int length){

        int[] retSplitArray = new int[length];
        long retSplitCount = 0;
        int i = 0;
        int j = 0;

        for (int k=0;k < length;k++){

            if (i > (array1.length-1) && j <= (array2.length-1)){
                retSplitArray[k]=array2[j];
                j++;
            }

            else if (j > (array2.length-1) && i <= (array1.length-1)){
                retSplitArray[k]=array1[i];
                i++;
            }

            else if (j <= (array2.length-1) && i <= (array1.length-1)) {
                if (array1[i]<=array2[j]){
                    retSplitArray[k]=array1[i];
                    i++;
                }
                else {
                    retSplitArray[k] = array2[j];
                    j++;
                    retSplitCount = retSplitCount + (array1.length - i);
                    //System.out.println(retSplitCount);
                }
            }
            else {
                //Print both arrays out of bounds
                //System.err.println("both arrays out of bounds");
            }
        }
        ArrayInvCount arraysplitcount = new ArrayInvCount();
        arraysplitcount.setArray(retSplitArray);
        arraysplitcount.setArrayCount(retSplitCount);
        //System.out.println(retSplitCount);
        return arraysplitcount;
    }
}
