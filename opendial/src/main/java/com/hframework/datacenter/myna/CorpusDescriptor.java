package com.hframework.datacenter.myna;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hframework.common.util.RegexUtils;
import com.hframework.myna.config.domain.model.Corpus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorpusDescriptor {
    private String corpusDesc;//语料描述
    private String simpleFormat;//简单格式
    private String[] slots;//简单格式

    public CorpusDescriptor(){}

    public CorpusDescriptor(Corpus corpus){
        corpusDesc = corpus.getContent();
        //{人数}张{启程日}{出发地}到{目的地}
        simpleFormat = corpus.getContent().replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)");
        slots = RegexUtils.find(corpus.getContent(),"(?<=\\{)[^}]+(?=\\})");
    }

    public String getCorpusDesc() {
        return corpusDesc;
    }

    public void setCorpusDesc(String corpusDesc) {
        this.corpusDesc = corpusDesc;
    }

    public String getSimpleFormat() {
        return simpleFormat;
    }

    public void setSimpleFormat(String simpleFormat) {
        this.simpleFormat = simpleFormat;
    }

    public String[] getSlots() {
        return slots;
    }

    public void setSlots(String[] slots) {
        this.slots = slots;
    }
}
