package com.hframework.datacenter.myna.parser;

import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NullSemanticParser implements ISemanticParser{
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        return value;
    }
}
