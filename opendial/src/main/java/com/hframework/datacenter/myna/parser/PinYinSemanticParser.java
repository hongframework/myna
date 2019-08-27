package com.hframework.datacenter.myna.parser;

import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PinYinSemanticParser implements ISemanticParser {
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        String inputString = value.toString();
        String matchOption = SemanticParserUtils.matchOptionsWithPinYin(inputString, qnaDescriptor.getOptions().keySet());
        if(matchOption != null) {
            return new StringVal(matchOption);
        }
        Map<String, String> optionAliases = qnaDescriptor.getOptionAliases();
        matchOption = SemanticParserUtils.matchOptionsWithPinYin(inputString, optionAliases.keySet());
        if(matchOption != null) {
            return new StringVal(optionAliases.get(matchOption));
        }
        return null;
    }
}
