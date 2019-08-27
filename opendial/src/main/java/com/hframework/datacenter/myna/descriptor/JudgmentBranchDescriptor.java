package com.hframework.datacenter.myna.descriptor;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JudgmentBranchDescriptor {
    private String value;//该分支的答案取值
    private String next;//当问答为该值时下一个节点编码
    private String utterance; //话语
    private String groundUtterance; //落地话术
    private String curPostHandleCode; //当前问答结束后扩展
    private String nextPreHandleCode;//该问题问答结束后下一个节点编码

    public JudgmentBranchDescriptor(String value, String next, String utterance){
        this(value, next, utterance, null, null);
    }

    public JudgmentBranchDescriptor(String value, String next, String utterance,  String curPostHandleCode, String nextPreHandleCode) {
        this.value = value;
        this.next = next;
        this.utterance = utterance;
        this.curPostHandleCode = curPostHandleCode;
        this.nextPreHandleCode = nextPreHandleCode;
    }

    public String getValue() {
        return value;
    }

    public String getNext() {
        return next;
    }

    public String getUtterance() {
        return utterance;
    }

    public String getCurPostHandleCode() {
        return curPostHandleCode;
    }

    public void setCurPostHandleCode(String curPostHandleCode) {
        this.curPostHandleCode = curPostHandleCode;
    }

    public String getNextPreHandleCode() {
        return nextPreHandleCode;
    }

    public void setNextPreHandleCode(String nextPreHandleCode) {
        this.nextPreHandleCode = nextPreHandleCode;
    }

    public String getGroundUtterance() {
        return groundUtterance;
    }

    public void setGroundUtterance(String groundUtterance) {
        this.groundUtterance = groundUtterance;
    }
}