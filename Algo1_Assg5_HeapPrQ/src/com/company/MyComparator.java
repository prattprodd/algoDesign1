package com.company;

import java.util.Comparator;

public class MyComparator implements Comparator<HeapObject>
{

    public int compare(HeapObject i1, HeapObject i2) {
        if (i1.distScore >= i2.distScore){
            return +1;
        }
        else {
            return -1;
        }
    }


}
