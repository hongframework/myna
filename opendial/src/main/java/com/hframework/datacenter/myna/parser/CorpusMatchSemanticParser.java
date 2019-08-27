package com.hframework.datacenter.myna.parser;

import com.google.common.base.Joiner;
import com.hframework.common.util.RegexUtils;
import com.hframework.datacenter.myna.CorpusDescriptor;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.modules.DialogSystemHelper;
import com.hframework.datacenter.myna.parser.date.MapVal;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CorpusMatchSemanticParser implements ISemanticParser {
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        String valueString = value.toString();
        String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
        ConfigurationDescriptor configurationDescriptor = RobotManager.getConfigurationDescriptor(taskCode);
        List<CorpusDescriptor> corpusList = configurationDescriptor.getCorpusList();
        for (CorpusDescriptor corpusDescriptor : corpusList) {
            String simpleFormat = corpusDescriptor.getSimpleFormat();
            if(valueString.matches(".*"+simpleFormat+".*")) {
                String[] item = RegexUtils.findItem(valueString, simpleFormat);

                if(item.length == 1 && corpusDescriptor.getSlots()[0].equals(qnaDescriptor.getCode())) {//表明一问一答，答应所答
                    return null;
                }else {
                    String lastValue = null;
                    MapVal mapVal = new MapVal(new HashMap<String, String>());
                    for (int i = 0; i < corpusDescriptor.getSlots().length; i++) {
                        if(StringUtils.isBlank(item[i])) {
                            item[i] = lastValue;
                        }
                        System.out.println("format=" + simpleFormat + ";slot=" + corpusDescriptor.getSlots()[i] + ";value=" + item[i]);
                        mapVal.add(corpusDescriptor.getSlots()[i], item[i]);
                        lastValue = item[i];
                    }
                    return mapVal;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(RegexUtils.findItem("21号,从北京到上海啊1", "{启程日},从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.findItem("从北京到上海啊", "从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.findItem("从北京到上海啊", "从{出发地}到{目的地}啊".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.findItem("北京到上海", "从{出发地}到{目的地}".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.findItem("北京去上海111", "{出发地}去{目的地}".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.findItem("到上海", "到{目的地}".replaceAll("\\{[^}]+\\}","(.+)"))));
        System.out.println(Arrays.toString(RegexUtils.find("{启程日},从{出发地}到{目的地}啊","(?<=\\{)[^}]+(?=\\})")));
        System.out.println(Arrays.toString(RegexUtils.findItem("给我来5张星期三早上北京到上海的灰机票", "{人数}张{启程日}{出发地}到{目的地}".replaceAll("(?<=})\\{[^}]+\\}","(.*)").replaceAll("\\{[^}]+\\}","(.+)"))));

    }
}
