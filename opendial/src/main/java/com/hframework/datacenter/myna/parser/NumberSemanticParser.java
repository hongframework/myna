package com.hframework.datacenter.myna.parser;

import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.bn.values.DoubleVal;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.springframework.stereotype.Service;

@Service
public class NumberSemanticParser  implements ISemanticParser{

    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        String valueString = null;
        if(value instanceof DoubleVal) {
            valueString = String.valueOf(((DoubleVal) value).getDouble().intValue());
        }else {
            valueString = value.toString();
        }
        System.out.println("number = > " + valueString);
        if(StringUtils.isBlank(valueString)) return null;
        valueString = SemanticParserUtils.toggleCase(valueString).replaceAll("两","2");
        String[] strings = RegexUtils.find(valueString, "\\d+");
        if(strings.length > 0) {//注意：如果前台传入金额大于1万时，a_u=Inform(资金, 10010000),底层将10010000解析DoubleValue后，
            // 由于DoubleValue的toString()进行了科学计数法的转换，导致二次校验比对不通过：a_u=Inform(资金, 10010000) a_u=Inform(资金, 1.001E7)
            // 见：ComplexCondition.isSatisfiedBy()
            return new StringVal("" + opendial.utils.StringUtils.getShortForm(Double.valueOf(strings[0])));
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(1010101010);
    }
}
