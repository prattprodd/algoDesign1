package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    //TODO Resolve Collisions in Open Addressing Custom Hash Table

    public static void main(String[] args) {

        long[] numArray = new long[1000000];
        int c = 0;
        long maxValue = 0;
        long minValue = 0;
        int zCount = 0;
        int maxLengthp = 0;
        int minLengthp = 11;
        int maxLengthn = 0;
        int minLengthn = 12;

        try {
            for (String line : Files.readAllLines(Paths.get("algo1-programming_prob-2sum.txt"), StandardCharsets.UTF_8)) {
                Long i = Long.valueOf(line);
                numArray[c] = i;
                c++;
                if (i < minValue){
                    minValue = i;
                }
                if (i > maxValue){
                    maxValue = i;
                }
                if (i==0){
                    zCount++;
                }

                if (i > 0){
                    if (line.length() >= maxLengthp){
                        maxLengthp = line.length();
                    }
                    if (line.length() <= minLengthp){
                        minLengthp = line.length();
                    }
                } else {
                    if (line.length() >= maxLengthn){
                        maxLengthn = line.length();
                    }
                    if (line.length() <= minLengthn) {
                        minLengthn = line.length();
                    }
                }


            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        };

        //System.out.println(minValue);
        //System.out.println(maxValue);
        //System.out.println(maxLengthp);
        //System.out.println(minLengthp);
        //System.out.println(maxLengthn);
        //System.out.println(minLengthn);


        long[] hTableOpenAddposL = new long[1000000];
        long[] hTableOpenAddnegL = new long[1000000];

        long[] hTableOpenAddposM = new long[1000000];
        long[] hTableOpenAddnegM = new long[1000000];

        int collisionCount = 0;

        //6LSB

        for (int i = 0; i < numArray.length;  i++){
            /*
            long[] num6LSB = new long[6];
            num6LSB[5] = numArray[i] - (numArray[i]/10)*10;
            num6LSB[4] = ((numArray[i]/10)*10 - (numArray[i]/100)*100)/10;
            num6LSB[3] = ((numArray[i]/100)*100 - (numArray[i]/1000)*1000)/100;
            num6LSB[2] = ((numArray[i]/1000)*1000 - (numArray[i]/10000)*10000)/1000;
            num6LSB[1] = ((numArray[i]/10000)*10000 - (numArray[i]/100000)*100000)/10000;
            num6LSB[0] = ((numArray[i]/100000)*100000 - (numArray[i]/1000000)*1000000)/100000;
            //System.out.println(Arrays.toString(num6LSB));
            */
            String s = String.valueOf(numArray[i]);
            char[] cDigits = s.toCharArray();
            String addressLSB = "";
            String addressMSB = "";

            //6LSB

            for (int j = 0; j < cDigits.length; j++){

                if (numArray[i] >= 0) {
                    if (addressLSB.length() < 6) {
                        addressLSB = cDigits[cDigits.length - j - 1] + addressLSB;
                    }
                    if (addressMSB.length() < 6) {
                        addressMSB = addressMSB + cDigits[j];
                    }
                } else {
                    if (addressLSB.length() < 6 && cDigits[cDigits.length - j - 1]!='-') {
                        addressLSB = cDigits[cDigits.length - j - 1] + addressLSB;
                    }
                    if (addressMSB.length() < 6 && cDigits[j]!='-') {
                        addressMSB = addressMSB + cDigits[j];
                    }

                }

            }
            //6MSB

            /*
            if (numArray[i] >= 0) {
                for (int j = 0; j < cDigits.length - 1; j++) {
                    if (j < 6) {
                        address = address + cDigits[j];
                    }
                }
            }

            if (numArray[i] < 0) {
                for (int j = 1; j < cDigits.length - 1; j++) {
                    if (j < 7) {
                        address = address + cDigits[j];
                    }
                }
            }
            */

            //TODO Resolving Collisions

            if (numArray[i] >= 0) {
                if (hTableOpenAddposL[Integer.valueOf(addressLSB)] == 0) {
                    //collisionCount++;
                    hTableOpenAddposL[Integer.valueOf(addressLSB)] = numArray[i];
                } else {
                    if (hTableOpenAddposM[Integer.valueOf(addressMSB)] == 0) {
                        hTableOpenAddposM[Integer.valueOf(addressMSB)] = numArray[i];
                    } else {
                        if (hTableOpenAddposM[Integer.valueOf(addressLSB)] == 0) {
                            hTableOpenAddposM[Integer.valueOf(addressLSB)] = numArray[i];
                        } else {
                            if (hTableOpenAddposL[Integer.valueOf(addressMSB)] == 0) {
                                hTableOpenAddposL[Integer.valueOf(addressMSB)] = numArray[i];
                            } else {
                                collisionCount++;
                            }
                        }
                    }
                }
            }
            else {
                if (hTableOpenAddnegL[Integer.valueOf(addressLSB)] == 0) {
                    //collisionCount++;
                    hTableOpenAddnegL[Integer.valueOf(addressLSB)] = numArray[i];
                } else {
                    if (hTableOpenAddnegM[Integer.valueOf(addressMSB)] == 0) {
                        hTableOpenAddnegM[Integer.valueOf(addressMSB)] = numArray[i];
                    } else {
                        if (hTableOpenAddnegM[Integer.valueOf(addressLSB)] == 0) {
                            hTableOpenAddnegM[Integer.valueOf(addressLSB)] = numArray[i];
                        } else {
                            if (hTableOpenAddnegL[Integer.valueOf(addressMSB)] == 0) {
                                hTableOpenAddnegL[Integer.valueOf(addressMSB)] = numArray[i];
                            } else {
                                collisionCount++;
                            }
                        }
                    }
                }
            }

        }

        System.out.println(collisionCount);


        long count = 0;

        for (int t = -10000; t <= 10000; t++) {
            boolean tSumFound = false;
            int i = 0;

            while (!tSumFound && i < numArray.length) {

                //for (int i = 0; i < numArray.length; i++) {

                String s = String.valueOf(t - numArray[i]);
                char[] cDigits = s.toCharArray();
                String addressLSB = "";
                String addressMSB = "";

                for (int j = 0; j < cDigits.length; j++){

                    if (t - numArray[i] >= 0) {
                        if (addressLSB.length() < 6) {
                            addressLSB = cDigits[cDigits.length - j - 1] + addressLSB;
                        }
                        if (addressMSB.length() < 6) {
                            addressMSB = addressMSB + cDigits[j];
                        }
                    } else {
                        if (addressLSB.length() < 6 && cDigits[cDigits.length - j - 1]!='-') {
                            addressLSB = cDigits[cDigits.length - j - 1] + addressLSB;
                        }
                        if (addressMSB.length() < 6 && cDigits[j]!='-') {
                            addressMSB = addressMSB + cDigits[j];
                        }

                    }

                }

                if (t - numArray[i] >= 0) {
                    if (hTableOpenAddposL[Integer.valueOf(addressLSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i]!=hTableOpenAddposL[Integer.valueOf(addressLSB)]){
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddposM[Integer.valueOf(addressMSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddposM[Integer.valueOf(addressMSB)]) {
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddposM[Integer.valueOf(addressLSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddposM[Integer.valueOf(addressLSB)]) {
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddposL[Integer.valueOf(addressMSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddposL[Integer.valueOf(addressMSB)]) {
                            tSumFound = true;
                        }
                    }
                } else {
                    if (hTableOpenAddnegL[Integer.valueOf(addressLSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i]!=hTableOpenAddnegL[Integer.valueOf(addressLSB)]){
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddnegM[Integer.valueOf(addressMSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddnegM[Integer.valueOf(addressMSB)]) {
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddnegM[Integer.valueOf(addressLSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddnegM[Integer.valueOf(addressLSB)]) {
                            tSumFound = true;
                        }
                    } else if (hTableOpenAddnegL[Integer.valueOf(addressMSB)] == (t - numArray[i])) {
                        //Looking for distinct x, y that sum to t
                        if (numArray[i] != hTableOpenAddnegL[Integer.valueOf(addressMSB)]) {
                            tSumFound = true;
                        }
                    }
                }
                i++;

                //}
            }

            if (tSumFound) { count++;}

        }
        //}
        System.out.println(count);
        //System.out.println(hTableOpenAddpos[543430]);
        //System.out.println(hTableOpenAddneg[473082]);
        //6MSB

        //Collisions - 326
        //-100 to 100 - t hit 5

    }
}
