package com.company;

import java.util.Arrays;
import java.util.Comparator;

public class Heap implements Comparator<Integer> {

    public int capacity;
    public String heapType;
    //public int[] heap = new int[capacity];

    public int[] heap;

    public Heap(int capacity, String heapType) {
        this.capacity = capacity;
        this.heapType = heapType;
        this.heap = new int[this.capacity];
    }

    public int currSize = 0;
    public int startidx = 1;
    public int endidx;
    public int curridx;

    public void addToHeap(int newval){
        //heap[0] = 0;
        if (currSize==0){
            heap[startidx] = newval;
            currSize++;
            endidx = startidx;
        } else {
            curridx = endidx+1;
            heap[curridx] = newval;
            //while (curridx!=startidx){
            //low=max, high=min
            //int pid;
            while (curridx!=startidx && compare(heap[curridx], heap[getParentId(curridx)]) >= 0){
                    swap(curridx, getParentId(curridx));
                    curridx = getParentId(curridx);
            }
            currSize++;
            endidx++;
        }
    }

    public int pop(){

        int retval;
        if (currSize==0){
            retval = -1;
        } else if (currSize==1) {
            retval = heap[startidx];
            currSize--;
        } else {
            retval = heap[startidx];
            heap[startidx] = heap[endidx];
            endidx--;
            currSize--;
            curridx = startidx;
            int cid;
            //cid = getChildId(curridx);
            //while ((endidx >= 2*curridx)&&(compare(heap[getChildId(curridx)[0]], heap[curridx]) >= 0||compare(heap[getChildId(curridx)[1]], heap[curridx]) >= 0)

            //cid = getChildId(curridx);

            while ((endidx >= 2*curridx)&&(curridx!=-1)&&(compare(heap[getChildId(curridx)], heap[curridx]) >= 0)){
                cid = getChildId(curridx);
                swap(curridx, cid);
                curridx = cid;
            }


            /*
            cid = getChildId(curridx);
            if(cid[0]!=-1 && cid[1]!=-1){
                while(compare(heap[cid[0]], heap[curridx]) >= 0||compare(heap[cid[1]], heap[curridx]) >= 0){
                    if (compare(heap[cid[0]], heap[cid[1]]) >= 0){
                        swap(curridx, cid[0]);
                            curridx = cid[0];
                    } else {
                        swap(curridx, cid[1]);
                        curridx = cid[1];
                    }
                }
            }
            */
        }

        return retval;

    }

    public int peek(){
        if (this.currSize>0) {
            return heap[startidx];
        } else {
            return -1;
        }
    }

    public int[] getHeap(){
        return Arrays.copyOfRange(this.heap, startidx, endidx+1);
    }

    public int getChildId(int pid){

        int[] cid = new int[2];
        int retcid;
        if (endidx > 2*pid){
            cid[0] = 2*pid;
            cid[1] = 2*pid + 1;
            if (compare(heap[cid[0]], heap[cid[1]]) >= 0){
                retcid = cid[0];
            } else {
                retcid = cid[1];
            }
        } else if (endidx==2*pid){
            cid[0] = 2*pid;
            cid[1] = -1;
            retcid = cid[0];
        } else {
            cid[0] = -1;
            cid[1] = -1;
            retcid = -1;
        }
        return retcid;
    }


    public int getParentId(int cid){
        if (cid!=startidx){
            return cid/2;
        } else {
            return startidx;
        }
    }

    public void swap(int id1, int id2){
        int temp = 0;
        temp = heap[id1];
        heap[id1] = heap[id2];
        heap[id2] = temp;
    }


    @Override
    public int compare(Integer i1, Integer i2){
        if (this.heapType.equals("max")) {
            return i1 - i2;
        }
        else //if (this.heapType.equals("min")){
        {    return i2 - i1;
        }
    }



}
