package com.hframework.datacenter.myna.parser;

import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RegexSemanticParser implements ISemanticParser{
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        Set<String> options = qnaDescriptor.getOptions().keySet();
        String matchStr = SemanticParserUtils.matchOptions(value.toString(), options);
        if(matchStr != null) {
            return new StringVal(matchStr);
        }

        Map<String, String> optionAliases = qnaDescriptor.getOptionAliases();
        matchStr = SemanticParserUtils.matchOptions(value.toString(), optionAliases.keySet());
        if(matchStr != null) {
            return new StringVal(optionAliases.get(matchStr));
        }

        return null;
    }
}
