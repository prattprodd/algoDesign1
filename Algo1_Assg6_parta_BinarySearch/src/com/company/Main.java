package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

//TODO Verify answer after resolving collisions in custom Hash table

public class Main {

    public static void main(String[] args) {

        long[] numArray = new long[1000000];
        int c = 0;
        long maxValue = 0;
        long minValue = 0;

        try {
            for (String line : Files.readAllLines(Paths.get("algo1-programming_prob-2sum.txt"), StandardCharsets.UTF_8)) {
                Long i = Long.valueOf(line);
                numArray[c] = i;
                c++;
                if (i < minValue) {
                    minValue = i;
                }
                if (i > maxValue) {
                    maxValue = i;
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }
        ;

        Arrays.sort(numArray);

        long x;
        long ymin;
        long ymax;
        int tmin = -10000;
        int tmax = 10000;
        int yminid;
        int ymaxid;
        int[] tHit = new int[tmax - tmin + 1];
        int tCount = 0;

        for (int xi = 0; xi < numArray.length; xi++) {

            x = numArray[xi];
            ymin = tmin - x;
            ymax = tmax - x;

            //TODO
            //yminid, ymaxid = findIdsByBinarySearch(numArray, ymin, ymax);
            yminid = doBinarySearch(numArray, ymin, 0, numArray.length - 1) - 1;
            ymaxid = doBinarySearch(numArray, ymax, 0, numArray.length - 1) + 1;

            if (yminid <= 0) {
                yminid = 0;
            }
            if (ymaxid >= numArray.length - 1) {
                ymaxid = numArray.length - 1;
            }
            //System.out.println(yminid +", " + ymaxid);

            if (yminid <= ymaxid) {
                for (int yi = yminid; yi <= ymaxid; yi++) {
                    if (((int) (x + numArray[yi]) >= 0) && ((int) (x + numArray[yi]) <= (2 * tmax + 1))) {
                        tHit[(int) (x + numArray[yi])] = 1;
                    }
                }
            }

        }

        for (int t = 0; t < tHit.length; t++) {
            if (tHit[t] == 1) {
                tCount++;
            }
        }

        System.out.println(tCount);

    }

        /*
        int stidx = 0;
        int edidx = c-1;
        int tmax = 50000;
        int tmin = -50000;
        int tOffset = 50000;

        int[] tHit = new int[tmax-tmin+1];
        while (stidx!=edidx){
            if ((int)(numArray[stidx] + numArray[edidx]) > tmax){
                edidx--;
            }
            else if ((int)(numArray[stidx] + numArray[edidx]) < tmin){
                stidx++;
            }
            else {
                tHit[(int)(numArray[stidx] + numArray[edidx]) + tOffset] = 1;
                if ((int)(numArray[stidx] + numArray[edidx]) > 0){
                    stidx++;
                } else {
                    edidx--;
                }
            }
        }
        int tCount = 0;
        for (int t = 0; t < tmax-tmin+1; t++){
            if (tHit[t]==1){
                tCount++;
            }
        }

        System.out.println(tCount);

    }
    */

    public static int doBinarySearch(long[]array, long y, int sid, int eid){
        int yid;
        if (eid-sid > 1){
            if (array[(sid+eid)/2] <= y){
                yid = doBinarySearch(array, y, (sid+eid)/2 + 1, eid);
            }
            else {
                yid = doBinarySearch(array, y, sid, (sid+eid)/2);
            }
            return yid;
        }
        else if (eid-sid==1){
            return eid;
        }
        else {
            return sid;
        }

    }

}
