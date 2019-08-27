package com.hframework.datacenter.myna.parser;

import com.google.common.collect.Lists;
import com.hframework.common.monitor.Node;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.myna.config.domain.model.Thesaurus;
import com.hframework.myna.config.domain.model.Words;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BooleanSemanticParser implements ISemanticParser {
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        String  valueString = value.toString();
        Map<String, Integer> options = qnaDescriptor.getOptions();
        Map<String, String> optionAliases = qnaDescriptor.getOptionAliases();

        if(!options.isEmpty()) {
            boolean isMatched = match(filterMapByValue(options, 1), valueString);
            if(isMatched) {
                return new StringVal("false");
            }
            isMatched = match(filterMapByValue(options, 0), valueString);
            if(isMatched) {
                return new StringVal("true");
            }

            String option = matchAndReturn(optionAliases.keySet(), valueString);
            if(StringUtils.isNotBlank(option)) {
                return new StringVal(String.valueOf(options.get(optionAliases.get(option)) == 1 ? false : true));
            }

        }else {
            Node<Thesaurus>[] tnFThesaurus = RobotManager.getTnFThesaurus();
            boolean isMatched = match(tnFThesaurus[1], valueString);
            if(isMatched) {
                return new StringVal("false");
            }
            isMatched = match(tnFThesaurus[0], valueString);
            if(isMatched) {
                return new StringVal("true");
            }
        }

        return null;
    }

    private Set<String> filterMapByValue(Map<String, Integer> options, int value) {
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, Integer> entry : options.entrySet()) {
            if(value == entry.getValue()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private boolean match(Node<Thesaurus> tnFThesaurus, String valueString) {
        List<Node<Words>> words = tnFThesaurus.getOutputs(Words.class);
        Set<String> options = new HashSet<>();
        for (Node<Words> word : words) {
            String optionStrings = word.getObject().getName();
            options.addAll(Lists.newArrayList(optionStrings.split("[,，、/]+")));
        }
        return match(options, valueString);
    }

    private String matchAndReturn(Set<String> options, String valueString) {
        String matched = SemanticParserUtils.matchOptions(valueString, options);
        if(matched == null) {
            matched = SemanticParserUtils.matchOptionsWithPinYin(valueString, options);
        }
        return matched;
    }

    private boolean match(Set<String> options, String valueString) {
        return matchAndReturn(options, valueString) != null;
    }

}
