package com.hframework.datacenter.myna.nlp;

import com.hframework.common.util.file.FileUtils;

public class FileWriter {
    private Mode mode;
    private boolean empty = true;
    private String filePath = null;
    public enum Mode{
        append, override,
    }
    private FileWriter(Mode mode, String filePath){
        this.mode = mode;
        this.filePath = filePath;
    }

    public static FileWriter getInstance(String filePath){
        return new FileWriter(Mode.override, filePath);
    }

    public void append(String row){
        if(empty) {
            FileUtils.deleteFile(filePath);
            FileUtils.newFile(filePath);
        }
        FileUtils.appendMethodA(filePath, row);
    }
}
