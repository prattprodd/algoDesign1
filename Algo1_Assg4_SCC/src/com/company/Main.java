package com.company;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {


    static int[] vertExpl;
    static int[] vertExplFwd;
    static int t = 0;
    static int[] vertOrder;
    static int[] tLeader;
    static int s;
    static int c;
    static int cr;

    //static ArrayList<Integer> sccCount = new ArrayList<Integer>();

    public static void main(String[] args) {

        //ArrayList<ArrayList<Integer>> graphnv = new ArrayList<ArrayList<Integer>>();

        int nVert = 875714;
        //int[] vEA = new int[875714];
        int nEdges = 0;

        //int[][] graphnv;
        //Need to count number of lines to get edges
        int[][] edgeList = new int[5105043][2];
        //int v=0;

        try {
            for (String line : Files.readAllLines(Paths.get("SCC.txt"), StandardCharsets.UTF_8)) {
                String[] sline = line.split("\\s+");
                edgeList[nEdges][0] = Integer.valueOf(sline[0]);
                edgeList[nEdges][1] = Integer.valueOf(sline[1]);
                //graphnv.add(new ArrayList<Integer>);
                //ArrayList<Integer> iline = new ArrayList<>();
                //if (edgeList[nEdges][0]!=edgeList[nEdges][1]) {
                nEdges = nEdges + 1;
                    //if (v == (Integer.valueOf(sline[0]) - 1)) {
                    //    vEA[v]++;
                    //} else {
                    //    v++;
                    //    vEA[v]++;
                    //}
                //}

            }
        }
        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }

        //System.out.println(nEdges);
        //System.out.print(nEdges+"\n");
        //System.out.print(vEA[0]+"\n");
        //System.out.print(vEA[1]+"\n");
        //System.out.print(vEA[875714-1]+"\n");

        //int[][] edgeList = new int[nEdges][2];
        //int nVert = v + 1;
        //int[][] verticeEdgeList = new int[nVert][];
        //int eidx = 0;

        //for (int i = 0; i < nVert ; i++){
            //int[] vertexEdgeList[i] = new int[];
        //    verticeEdgeList[i] = new int[vEA[i]];
        //    for (int j = 0; j < vEA[i];j++){
        //        verticeEdgeList[i][j]= eidx;
        //        eidx++;
        //    }
        //}

        //ArrayList<LinkedList<Integer>> vertRevEdgeList = new ArrayList<LinkedList<Integer>>();
        //TODO Can we use HashMap?
        LinkedList<Integer>[] vertRevEdgeList = new LinkedList[nVert];
        LinkedList<Integer>[] vertEdgeList = new LinkedList[nVert];
        //int[][] verticeRevEdgeList =

        for (int ver = 0; ver < nVert; ver++){
            vertRevEdgeList[ver] = new LinkedList<Integer>();
            vertEdgeList[ver] = new LinkedList<Integer>();
        }

        for (int e = 0; e < nEdges; e++){
            vertRevEdgeList[edgeList[e][1]-1].add(e);
            vertEdgeList[edgeList[e][0]-1].add(e);
        }

        //System.out.print(vertEdgeList[139894]+"\n");
        //System.out.print(vertRevEdgeList[139894]+"\n");

        vertExpl = new int[nVert];
        vertOrder = new int[nVert];
        int countrev = 0;

        for (int i = nVert-1; i >= 0; i--) {
            if (vertExpl[i] == 0) {
                cr = 0;
                DFSRev(vertRevEdgeList, edgeList, i);
                countrev = countrev + cr;
            }
        }

        //System.out.println(countrev);
        //System.out.println(Arrays.toString(vertExpl));

        vertExplFwd = new int[nVert];
        tLeader = new int[nVert];
        int countfwd = 0;
        //Top 5 SCC
        //int[][] scc5Count = new int[5][2];
        int idxscc = 0;
        SCC[] scc = new SCC[5];
        ArrayList<Integer> sccCount = new ArrayList<Integer>();

        //Linked List for all SCCs

        for (int i = nVert-1; i >= 0; i--){
            if (vertExplFwd[vertOrder[i]]==0){
                s = vertOrder[i];
                c = 0;
                DFS(vertEdgeList, edgeList, vertOrder[i]);
                countfwd = countfwd + c;
                //System.out.println(idxscc);
                //System.out.println(s);
                //System.out.println(c);

                if (idxscc < 5){
                    scc[idxscc] = new SCC(s,c);
                }

                if (idxscc + 1 == 5){
                    Arrays.sort(scc);
                }

                if (idxscc > 5){
                    if (c >= scc[0].sccCount){
                        //Add to 0th index, shift right from 0
                        scc[4].setSCC(scc[3].getSCC());
                        scc[3].setSCC(scc[2].getSCC());
                        scc[2].setSCC(scc[1].getSCC());
                        scc[1].setSCC(scc[0].getSCC());
                        scc[0] = new SCC(s, c);

                    }
                    else if (c >= scc[1].sccCount){
                        //Add to 1st index, shift right from 1
                        scc[4].setSCC(scc[3].getSCC());
                        scc[3].setSCC(scc[2].getSCC());
                        scc[2].setSCC(scc[1].getSCC());
                        scc[1] = new SCC(s, c);

                    }
                    else if (c >= scc[2].sccCount){
                        //Add to 2nd index, shift right from 2
                        scc[4].setSCC(scc[3].getSCC());
                        scc[3].setSCC(scc[2].getSCC());
                        scc[2] = new SCC(s, c);

                    }
                    else if (c >=scc[3].sccCount){
                        //Add to 3rd index, shift right from 3
                        scc[4].setSCC(scc[3].getSCC());
                        scc[3] = new SCC(s, c);

                    }
                    else if (c >= scc[4].sccCount){
                        //Add to 4th index, shift right from 4
                        scc[4] = new SCC(s, c);

                    }
                }


                idxscc++;
                //sccCount.add(c);
                //System.out.println("Lead Vertex:" +s);
                //System.out.println(s);
                //System.out.println(i);
                //if (i > nVert-6) {
                //System.out.println(c);
                //}//c++;
            }
        }

        //int sum = 0;
        //Avoid sorting a collection to keep running time O(m+n)
        //Collections.sort(sccCount);
        //Countfwd must match vertex count

        //System.out.println(countfwd);
        for (int i = 0; i <= 4; i++){
            System.out.println(scc[i].leadSCCnode);
            System.out.println(scc[i].sccCount);
            //System.out.println(sccCount.get(sccCount.size()-i));
            //sum = sum + sccCount.get(i);
        }

        //System.out.println(sum);
        //System.out.println(Arrays.toString(vertExplFwd));
        //System.out.println(Arrays.toString(tLeader));

    }

    public static void DFSRev(LinkedList<Integer>[] vertList, int[][]edgeList, int vert){

        vertExpl[vert] = 1;
        cr = cr + 1;
        Iterator<Integer> iterator = vertList[vert].iterator();
        int edge;
        for (int arc = 0; arc < vertList[vert].size(); arc++){
            edge = iterator.next();
            if (vertExpl[edgeList[edge][0]-1]==0){
                DFSRev(vertList, edgeList, edgeList[edge][0]-1);
            }
        }
        if (t < vertList.length) {
            vertOrder[t] = vert;
            t++;
        }
    }

    public static void DFS(LinkedList<Integer>[] vertList, int[][] edgeList, int vert){

        vertExplFwd[vert] = 1;
        tLeader[vert] = s;
        c++;
        Iterator<Integer> iterator = vertList[vert].iterator();
        int edge;
        for (int arc = 0; arc < vertList[vert].size(); arc++){
            edge = iterator.next();
            if (vertExplFwd[edgeList[edge][1]-1]==0){
                DFS(vertList, edgeList, edgeList[edge][1]-1);
            }
        }
    }

}

