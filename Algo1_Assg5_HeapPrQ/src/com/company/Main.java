package com.company;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//TODO map vertex with position in heap for faster access O(1)
//TODO Access Heap object and Implement delete in O(log(n)). Below is worse than O(n)


public class Main {

    static int nVert = 200;
    static PriorityQueue<HeapObject> heapVmX = new PriorityQueue<HeapObject>(nVert-1, new MyComparator());

    public static void main(String[] args) {

        //int nVert = 200;
        int[][][] verticeEdgeLengthList = new int[nVert][][];
        int r = 0;
        int sumedgeLen = 0;
        int distanceInf = 1000000;

        int[] shortestDistances = new int[nVert];
        int[] verticesAddedtoX = new int[nVert];
        int[] verticesAddedtoHeap = new int[nVert];


        for (int d = 0; d < nVert; d++) {
            shortestDistances[d] = distanceInf;
            verticesAddedtoX[d] = 0;
            verticesAddedtoHeap[d] = 0;
        }

        //PriorityQueue<HeapObject> heapVmX = new PriorityQueue<HeapObject>(nVert - 1, new MyComparator());
        /*
        {
            @Override
            public int compare(HeapObject o1, HeapObject o2) {
                if (o1.distScore <= o2.distScore){
                    return +1;
                }
                else {
                    return -1;
                }
            }
        });
        */

        try {

            //O(mLogn)
            for (String line : Files.readAllLines(Paths.get("dijkstraData.txt"), StandardCharsets.UTF_8)) {
                String[] sline = line.split("\\s+");
                verticeEdgeLengthList[r] = new int[sline.length - 1][2];

                for (int i = 1; i < sline.length; i++) {
                    String[] slinei = sline[i].split(",");
                    verticeEdgeLengthList[r][i - 1][0] = Integer.valueOf(slinei[0]);
                    verticeEdgeLengthList[r][i - 1][1] = Integer.valueOf(slinei[1]);
                    sumedgeLen = sumedgeLen + Integer.valueOf(slinei[1]);
                    if (r == 0) {
                        heapVmX.add(new HeapObject(verticeEdgeLengthList[r][i - 1][1], verticeEdgeLengthList[r][i - 1][0] - 1));
                        verticesAddedtoHeap[verticeEdgeLengthList[r][i - 1][0] - 1] = 1;
                        //System.out.println(verticeEdgeLengthList[r][i - 1][0] - 1);
                    }
                }
                r++;
            }

        } catch (IOException ioe) {
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }

        //O(nlogn)
        for (int j = 1; j < nVert; j++) {
            if (verticesAddedtoHeap[j] == 0) {
                heapVmX.offer(new HeapObject(distanceInf, j));
                verticesAddedtoHeap[j] = 1;
            }
        }

        //HeapObject nve;
        //for (int i = 0; i < heapVmX.size();i++) {
        //Iterator<HeapObject> iterator = heapVmX.iterator();
        //while (iterator.hasNext()) {
        //        nve = iterator.next();
        //        System.out.println(nve.vertex);
        //        System.out.println(nve.distScore);
        //    }
        //}

        //System.out.println(Arrays.toString(heapVmX.toArray()));

        //System.out.println(heapVmX.size());

        shortestDistances[0] = 0;
        verticesAddedtoX[0] = 1;

        //O(n)
        while (heapVmX.size() != 0) {

            //O(logn)
            HeapObject nextObject = heapVmX.poll();

            int nextVert = nextObject.vertex;
            //System.out.println(nextVert);

            int minNextDist = nextObject.distScore;
            //System.out.println(minNextDist);

            //int temp;

            shortestDistances[nextVert] = minNextDist;
            verticesAddedtoX[nextVert] = 1;

            //Each vertex can have atmost n edges, but overall, total edges looked at is O(m)
            for (int k = 0; k < verticeEdgeLengthList[nextVert].length; k++) {

                if (verticesAddedtoX[verticeEdgeLengthList[nextVert][k][0] - 1] == 0) {

                    //System.out.println(verticeEdgeLengthList[nextVert][k][0] - 1);

                    //System.out.println(getHeapObject(79).distScore);

                    //Get Heap Object is O(n)
                    //TODO map vertex with position in heap for faster access O(1)
                    if (shortestDistances[nextVert] + (verticeEdgeLengthList[nextVert][k][1]) < getHeapObject(verticeEdgeLengthList[nextVert][k][0] - 1).distScore) {
                        //TODO This is not implemented exactly as specified in class, working correctly. Any corner cases missed out?
                        //temp = minNextDist;
                        minNextDist = shortestDistances[nextVert] + verticeEdgeLengthList[nextVert][k][1];
                        //TODO Access Heap object and Implement delete in O(log(n)). Below is worse than O(n)
                        //With mapping of vertex and position in heap, remove can be O(logn)
                        heapVmX.remove(getHeapObject(verticeEdgeLengthList[nextVert][k][0] - 1));
                        //HeapObject nv1 = null;
                        //for (int i = 0; i < heapVmX.size(); i++) {
                        //Iterator<HeapObject> iterator1 = heapVmX.iterator();
                        //while (iterator1.hasNext()) {
                        //        nv1 = iterator1.next();
                            //}
                        //    if (nv1.vertex == verticeEdgeLengthList[nextVert][k][0] - 1) {
                        //        heapVmX.remove(nv1);
                        //    }

                        //}
                        //minNextVert = (verticeEdgeLengthList[nextVert][k][0]-1);
                        //TODO heap.add with Exception Handling if needed
                        //O(Log(n)
                        heapVmX.offer(new HeapObject(minNextDist, verticeEdgeLengthList[nextVert][k][0] - 1));
                    }

                }

            }
        }

        String answer = String.valueOf(shortestDistances[7-1])+", " +
                String.valueOf(shortestDistances[37-1])+", "+
                String.valueOf(shortestDistances[59-1])+", "+
                String.valueOf(shortestDistances[82-1])+", "+
                String.valueOf(shortestDistances[99-1])+", "+
                String.valueOf(shortestDistances[115-1])+", "+
                String.valueOf(shortestDistances[133-1])+", "+
                String.valueOf(shortestDistances[165-1])+", "+
                String.valueOf(shortestDistances[188-1])+", "+
                String.valueOf(shortestDistances[197-1]);

        System.out.println(answer);

    }

        //Heap based implementation
        //1. Using prioirty queue

        //2. Using Array


    public static HeapObject getHeapObject(int vert){

        HeapObject retobject = null;
        HeapObject nv = null;
        //for (int i = 0; i < heapVmX.size();i++){
        Iterator<HeapObject> iterator = heapVmX.iterator();
        while(iterator.hasNext()){
                nv = iterator.next();
                //System.out.println(nv.vertex);
                //System.out.println(nv.distScore);
            //}
            if (nv.vertex==vert){
                retobject = nv;
            }

        }

        return retobject;

    }


}

