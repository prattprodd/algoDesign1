package com.company;

import java.util.Comparator;

public class SCC implements Comparable<SCC> {

    public int leadSCCnode, sccCount;
    public SCC(int leadSCCnode, int sccCount) {
        this.leadSCCnode = leadSCCnode;
        this.sccCount = sccCount;
    }

    public void setSCC(SCC o){
        this.leadSCCnode = o.leadSCCnode;
        this.sccCount = o.sccCount;
    }

    public SCC getSCC() {
       return this;
    }


    @Override
    public int compareTo(SCC o) {
        if (this.sccCount >= o.sccCount) {
            return +1;
        } else {
            return -1;
        }
    }
}
