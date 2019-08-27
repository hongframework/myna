package com.hframework.datacenter.myna.parser;

import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.bn.values.Value;

public interface ISemanticParser {

    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state);
}
