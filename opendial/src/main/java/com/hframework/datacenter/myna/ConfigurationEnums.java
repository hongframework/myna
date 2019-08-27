package com.hframework.datacenter.myna;

public interface ConfigurationEnums {

    public enum QnaAnswerType{
        Boolean((byte)1),
        Number((byte)2),
        Date((byte)3),
        Money((byte)4),
        Text((byte)5);
        byte value;

        QnaAnswerType(byte value) {
            this.value = value;
        }

    }

    public enum SemanticParserMethod{
        Local((byte)0),
        Http((byte)1);

        public byte value;
        SemanticParserMethod(byte value) {
            this.value = value;
        }

    }
}
