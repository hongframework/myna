package com.hframework.datacenter.myna;

import opendial.bn.distribs.CategoricalTable;

public class RobotData {
    String returnVal;
    String returnProb;
    CategoricalTable table;

    public RobotData(String returnVal, String returnProb, CategoricalTable table) {
        this.returnVal = returnVal;
        this.returnProb = returnProb;
        this.table = table;
    }

    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }

    public void setReturnProb(String returnProb) {
        this.returnProb = returnProb;
    }

    public void setTable(CategoricalTable table) {
        this.table = table;
    }

    public String getReturnVal() {
        return returnVal;
    }

    public String getReturnProb() {
        return returnProb;
    }

    public CategoricalTable getTable() {
        return table;
    }


    @Override
    public String toString() {
        return "returnVal : " + returnVal + "; returnProb : " + returnProb + "; table : " + table;
    }
}