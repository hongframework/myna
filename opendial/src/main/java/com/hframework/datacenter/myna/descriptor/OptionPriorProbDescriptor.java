package com.hframework.datacenter.myna.descriptor;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionPriorProbDescriptor {
    private String item;//选项值
    private String prob;//先验概率

    public OptionPriorProbDescriptor(String item, String prob) {
        this.item = item;
        this.prob = prob;
    }

    public String getItem() {
        return item;
    }

    public String getProb() {
        return prob;
    }
}