package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//TODO Improve performance of edge contraction implementation

//TODO Adjaceny List Representation of Undirected graph. Can we do better with Adjacency Matrix?

public class Main {

    public static void main(String[] args) {

        int[][][] verticeEdgeList = new int[200][][];
        int eidx = 0;
        int r=0;

        //Reading the graph into vertice list ~
        // TODO - O(mn). Can we do better with directed representation of the given undirected graph
        // i.e, 2 versions of edge representations for each (edge) pair of connected vertices?
        try {
            for (String line : Files.readAllLines(Paths.get("kargerMinCut.txt"), StandardCharsets.UTF_8)) {
                String[] sline = line.split("\\s+");
                verticeEdgeList[r] = new int[sline.length-1][2];
                for (int j=1;j<sline.length;j++){
                    if (r+1 < Integer.valueOf(sline[j])){
                        verticeEdgeList[r][j-1][0] = eidx;
                        verticeEdgeList[r][j-1][1] = Integer.valueOf(sline[j]);

                        eidx++;

                    }
                    else {
                        int k = 0;
                        while (verticeEdgeList[Integer.valueOf(sline[j])-1][k][1] != r+1){
                            k++;
                        }
                        if (verticeEdgeList[Integer.valueOf(sline[j])-1][k][1] == r+1){
                            verticeEdgeList[r][j-1][0] = verticeEdgeList[Integer.valueOf(sline[j])-1][k][0];
                            verticeEdgeList[r][j-1][1] = Integer.valueOf(sline[j]);
                        }
                    }

                }

                r++;
            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }

        int nEdges = eidx;
        int nVert = r;

        //nEdges = nEdges/2;

        //Creating edge list O(m)

        //eidx = 0;
        int[][] edgeList = new int[nEdges][2];
        for (int i = 0; i < nVert; i++){
            for (int j = 0; j < verticeEdgeList[i].length; j++){
                if (i+1 < verticeEdgeList[i][j][1]) {
                    edgeList[verticeEdgeList[i][j][0]][0] = i + 1;
                    edgeList[verticeEdgeList[i][j][0]][1] = verticeEdgeList[i][j][1];
                    //edgeList[eidx][0] = i + 1;
                    //edgeList[eidx][1] = verticeEdgeList[i][j][1];
                    //eidx++;
                }
            }
        }
        //for (int e = 0; e < nEdges; e++){
        //    System.out.println(+edgeList[e][0]+","+edgeList[e][1]);
        //}

        double trials = (nVert*nVert)*(Math.log(nVert));
        int minCount = nEdges;
        long t1 = System.currentTimeMillis();

        for (double tr = 0; tr < trials; tr++ ) {

            int[] edgeIndexArray = new int[nEdges];
            ArrayList<LinkedList<Integer>> mergedVertices = new ArrayList<LinkedList<Integer>>();

            for (int i = 0; i < nEdges; i++) {
                edgeIndexArray[i] = 1;
            }

            int remainingVertices = nVert;
            int remainingEdges = nEdges;

            //TODO O(n)
            while (remainingVertices > 2) {
                Random randno = new Random();
                int[] edgesLeft = new int[remainingEdges];
                int ecount = 0;
                //TODO Picking a remaining edge at random - O(m). O(1) possible?
                for (int e = 0; e < nEdges; e++) {
                    if (edgeIndexArray[e] == 1) {
                        edgesLeft[ecount] = e;
                        ecount++;
                    }
                }
                int cedgeidx = edgesLeft[randno.nextInt(remainingEdges)];
                //System.out.println(cedgeidx);
                edgeIndexArray[cedgeidx] = 0;
                remainingEdges = remainingEdges - 1;

                if (remainingVertices == nVert) {
                    //System.out.println(cedgeidx);
                    //System.out.println(edgeList[cedgeidx][0]);
                    //System.out.println(edgeList[cedgeidx][1]);
                    //HashSet<Integer> initialVertices = new HashSet<Integer>();
                    LinkedList<Integer> initialVertices = new LinkedList<Integer>();
                    initialVertices.add(edgeList[cedgeidx][0]);
                    initialVertices.add(edgeList[cedgeidx][1]);
                    mergedVertices.add(0, initialVertices);
                    //System.out.println(mergedVertices.size());
                    //Iterator iterator = mergedVertices.get(0).iterator();
                    //while (iterator.hasNext()) {
                    //    System.out.println(iterator.next() + ", ");
                    //}
                    //System.out.println();
                }
                remainingVertices = remainingVertices - 1;
                //TODO Removing Self Loops
                if (remainingVertices < nVert - 1) {
                    int s1 = 0;
                    int s2 = 0;
                    //do {
                        //if (!mergedVertices.get(s1).contains(edgeList[cedgeidx][0])) {
                    //TODO O(n)
                    while (s1 < mergedVertices.size() && !mergedVertices.get(s1).contains(edgeList[cedgeidx][0])){
                            s1++;
                            //System.out.println("s1," +s1);
                            //System.out.println(mergedVertices.size());
                        //}
                    } //while (!mergedVertices.get(s1).contains(edgeList[cedgeidx][0]) && s1 < mergedVertices.size());
                    //do {
                        //if (!mergedVertices.get(s2).contains(edgeList[cedgeidx][1])) {
                    //TODO O(n)
                    while (s2 < mergedVertices.size() && !mergedVertices.get(s2).contains(edgeList[cedgeidx][1])){
                            s2++;
                            //System.out.println("s2," +s2);
                            //System.out.println(mergedVertices.size());
                    }
                    //} while (s2 < mergedVertices.size());

                    //System.out.println("s1aw," +s1);
                    //System.out.println("s2aw," +s2);
                    //System.out.println(mergedVertices.size());

                    if (s1 < mergedVertices.size() && s2 >= mergedVertices.size()){
                        //System.out.println("Enter Case1");
                        //TODO O(n)
                        for (int p = 0; p < verticeEdgeList[edgeList[cedgeidx][1]-1].length; p++) {
                            //System.out.println("Enter Case1" +p);
                            //TODO O(n)
                            if (mergedVertices.get(s1).contains(verticeEdgeList[edgeList[cedgeidx][1]-1][p][1])) {
                                if (edgeIndexArray[verticeEdgeList[edgeList[cedgeidx][1]-1][p][0]] == 1) {
                                    edgeIndexArray[verticeEdgeList[edgeList[cedgeidx][1]-1][p][0]] = 0;
                                    remainingEdges = remainingEdges - 1;
                                    //System.out.println("Case1re" +remainingEdges);
                                }
                            }
                        }
                        mergedVertices.get(s1).add(edgeList[cedgeidx][1]);
                        //System.out.println("Case1 Done");

                        //TODO O(n)
                    } else if (s1 >= mergedVertices.size() && s2 < mergedVertices.size()){
                        //System.out.println("Enter Case2");
                        for (int p = 0; p < verticeEdgeList[edgeList[cedgeidx][0]-1].length; p++) {
                            //System.out.println("Enter Case2" +p);
                            //TODO O(n)
                            if (mergedVertices.get(s2).contains(verticeEdgeList[edgeList[cedgeidx][0]-1][p][1])) {
                                if (edgeIndexArray[verticeEdgeList[edgeList[cedgeidx][0]-1][p][0]] == 1) {
                                    edgeIndexArray[verticeEdgeList[edgeList[cedgeidx][0]-1][p][0]] = 0;
                                    remainingEdges = remainingEdges - 1;
                                    //System.out.println("Case2re" +remainingEdges);
                                }
                            }
                        }
                        mergedVertices.get(s2).add(edgeList[cedgeidx][0]);
                        //System.out.println("Case2 Done");

                    } else if (s1 < mergedVertices.size() && s2 < mergedVertices.size()){
                        //System.out.println("Case3 Start");
                        //int v = s2;
                        //if (mergedVertices.get(s1).size() < mergedVertices.get(s2).size()) {
                        //    v = s1;
                        //}
                        //for (int s = 0; s < mergedVertices.get(v).size();s++){
                        //TODO O(n)
                        Iterator<Integer> iterator = mergedVertices.get(s1).iterator();
                        while (iterator.hasNext()) {
                            //System.out.println("Case3 Iterator");
                            int nextVertice = iterator.next();
                            //TODO O(n)
                            for (int p = 0; p < verticeEdgeList[nextVertice-1].length; p++) {
                                //TODO O(n)
                                if (mergedVertices.get(s2).contains(verticeEdgeList[nextVertice-1][p][1])) {
                                    if (edgeIndexArray[verticeEdgeList[nextVertice-1][p][0]] == 1) {
                                        edgeIndexArray[verticeEdgeList[nextVertice-1][p][0]] = 0;
                                        remainingEdges = remainingEdges - 1;
                                        //System.out.println("Case3re" +remainingEdges);
                                    }
                                }
                            }
                        }
                        //TODO Can make this O(1) concatenation of two linked lists
                        mergedVertices.get(s1).addAll(mergedVertices.get(s2));
                        //mergedVertices.get(s2).remove();
                        mergedVertices.remove(s2);
                        //System.out.println("Case3 Done");

                    } else {
                        LinkedList<Integer> nextVertices = new LinkedList<Integer>();
                        nextVertices.add(edgeList[cedgeidx][0]);
                        nextVertices.add(edgeList[cedgeidx][1]);
                        mergedVertices.add(nextVertices);
                        //System.out.println(mergedVertices.size());
                        //System.out.println("Case4 Done");
                    }

                }

                //System.out.println(remainingEdges);
                //System.out.println(remainingVertices);
                //System.out.println(Arrays.toString(edgeIndexArray));


            }

            if (remainingEdges < minCount){
                minCount = remainingEdges;
            }

            //remEdgesCount[tr] = remainingEdges;
            //System.out.println(minCount);
            //System.out.println(remainingEdges);
            //System.out.println(Arrays.toString(edgeIndexArray));

        }

        //Arrays.sort(remEdgesCount);
        System.out.println("minCount:"+minCount);
        long t2 = System.currentTimeMillis();
        System.out.println((t2-t1)/(1000*60));





        //Set<ArrayList<Integer>> edgeSet = new Set<ArrayList<Integer>>();

        //public void doEdgeContraction(int firstvertexidx){


        /*
        int firstvertexidx = 0;
        int verticesRemaining = verticeList.size();

        while (verticesRemaining > 2){
            int nextvertexidx = 0;
            //Pick a random number from 0 to getSize
            Random randno = new Random();
            //TODO This needs to be improved
            int listidx = randno.nextInt(verticeList.get(firstvertexidx).getSize());
            int edgeidx = verticeList.get(firstvertexidx).getNode(listidx).getEdgeIndex;
            verticeList.get(firstvertexidx).removeNode(listidx);
            //
            for (int x=0; x<2; x++){
                if (edgeList.get(edgeidx)[x]!= firstvertexidx){
                    nextvertexidx = edgeList.get(edgeidx)[x];
                }
            }
            verticeList.get(nextvertexidx).findAndRemoveNode(edgeidx);
            verticeList.get(nextvertexidx).getLastNode().setNext(verticeList.get(firstvertexidx).getFirstNode());
            firstvertexidx = randno.nextInt(verticeList.)

        }
        */

        //Answers: 169, 166, 167, 143
        //Running time~ 20min

    }

}

