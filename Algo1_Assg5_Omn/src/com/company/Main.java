package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        int nVert = 200;
        int[][][] verticeEdgeLengthList = new int[nVert][][];
        int r = 0;
        int sumedgeLen = 0;

        try {

            for (String line : Files.readAllLines(Paths.get("dijkstraData.txt"), StandardCharsets.UTF_8)) {
                String[] sline = line.split("\\s+");
                verticeEdgeLengthList[r] = new int[sline.length - 1][2];

                for (int i = 1; i < sline.length; i++){
                    String[] slinei = sline[i].split(",");
                    verticeEdgeLengthList[r][i-1][0] = Integer.valueOf(slinei[0]);
                    verticeEdgeLengthList[r][i-1][1] = Integer.valueOf(slinei[1]);
                    //System.out.println(Arrays.toString(verticeEdgeLengthList[r][i-1]));
                    sumedgeLen = sumedgeLen + Integer.valueOf(slinei[1]);
                }
                r++;
            }

        }

        catch (IOException ioe){
            System.out.println("Error while reading file line by line:" + ioe.getMessage());
        }

        LinkedList<Integer> verticesAdded = new LinkedList<Integer>();
        int[] verticesAddedtoX = new int[nVert];
        //int verticeAddedCount = 0;
        int[] shortestDistances = new int[nVert];

        for (int d = 0; d < nVert; d++){
            shortestDistances[d] = 1000000;
            verticesAddedtoX[d] = 0;
        }

        shortestDistances[0] = 0;
        verticesAdded.add(0);
        verticesAddedtoX[0] = 1;
        int v = 0;

        //We initialize minNextDist to total sum of all edge lengths, because that is the max possible value of any connected path
        //in the undirected graph

        int minNextDist = sumedgeLen;
        int minNextVert = 1000;
        boolean noPathsToVertices = false;

        while (verticesAdded.size() < nVert && !noPathsToVertices){

            minNextDist = sumedgeLen;
            minNextVert = 1000;

            Iterator<Integer> iterator = verticesAdded.iterator();
            for (int j = 0; j < verticesAdded.size(); j++){
                if (iterator.hasNext()){
                    v = iterator.next();
                }
                for (int k = 0; k < verticeEdgeLengthList[v].length; k++){
                    //Better way of checking for vertices
                    //if (!verticesAdded.contains(verticeEdgeLengthList[v][k][0]-1)){
                    if (verticesAddedtoX[verticeEdgeLengthList[v][k][0]-1]==0){
                        if (shortestDistances[v] + (verticeEdgeLengthList[v][k][1]) < minNextDist){
                            minNextDist = shortestDistances[v] + verticeEdgeLengthList[v][k][1];
                            minNextVert = (verticeEdgeLengthList[v][k][0]-1);
                        }
                    }
                }
            }

            //This check is to verify whether we have paths remaining from start vertex
            if (minNextVert != 1000) {
                verticesAdded.add(minNextVert);
                verticesAddedtoX[minNextVert] = 1;
                shortestDistances[minNextVert] = minNextDist;
            }
            else {
                noPathsToVertices = true;
            }
            //System.out.println("d=" +minNextDist);
            //System.out.println("v=" +minNextVert);

        }

        //You should report the shortest-path distances to the following ten vertices, in order: 7,37,59,82,99,115,133,165,188,197.
        //We just look up the distances corresponding to these vertices

        String answer = String.valueOf(shortestDistances[7-1])+"," +
                        String.valueOf(shortestDistances[37-1])+","+
                        String.valueOf(shortestDistances[59-1])+","+
                        String.valueOf(shortestDistances[82-1])+","+
                        String.valueOf(shortestDistances[99-1])+","+
                        String.valueOf(shortestDistances[115-1])+","+
                        String.valueOf(shortestDistances[133-1])+","+
                        String.valueOf(shortestDistances[165-1])+","+
                        String.valueOf(shortestDistances[188-1])+","+
                        String.valueOf(shortestDistances[197-1]);

        System.out.println(answer);

    }
}
