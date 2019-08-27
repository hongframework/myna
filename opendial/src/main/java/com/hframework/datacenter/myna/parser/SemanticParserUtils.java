package com.hframework.datacenter.myna.parser;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.base.Joiner;
import com.hframework.common.util.RegexUtils;
import opendial.bn.values.StringVal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SemanticParserUtils {
    /**
     * 大写转小写
     * @param string
     * @return
     */
    public static String toggleCase(String string) {
        String[] upperCases = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] chineseUpperCases = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        for (int i = 0; i < upperCases.length; i++) {
            string = string.replaceAll(chineseUpperCases[i], String.valueOf(i));
            string = string.replaceAll(upperCases[i], String.valueOf(i));
        }
        string = string.replaceAll("拾","十").replaceAll("佰","百").replaceAll("仟","千");
        String[] tempStrings = RegexUtils.find(string, "\\d?(十|百|千|万|亿)\\d?");
        for (String tempString : tempStrings) {
            String firstChar = String.valueOf(tempString.charAt(0));
            String endChar = String.valueOf(tempString.charAt(tempString.length() - 1));
            String unit = RegexUtils.find(tempString, "十|百|千|万|亿")[0];

            int firstNo = firstChar.matches("\\d+")? Integer.valueOf(firstChar) : 1;
            int endNo = endChar.matches("\\d+")? Integer.valueOf(endChar) : 0;


            double result = firstNo * Math.pow(10, "个十百千万000亿".indexOf(unit)) + endNo;
            string = string.replaceAll(tempString, String.valueOf(new BigDecimal(result).intValue()));
        }
        return string;
    }

    public static String matchOptions(String str, Set<String> options){
        if(options != null && options.size() > 0) {
            String[] matches = RegexUtils.find(str, "(" + Joiner.on(")|(").join(options) + ")");
            if(matches.length > 0) {
                return matches[0];
            }
        }
        return null;
    }

    public static String matchOptionsWithPinYin(String inputString, Set<String> optionSet){
        List<String> options = new ArrayList<String>(optionSet);
        if(options == null || options.size() == 0) {
            return null;
        }
        String optionsString = "(" + Joiner.on(")|(").join(options) + ")";
        optionsString = ChineseHelper.convertToSimplifiedChinese(optionsString);//繁体转简体
        inputString = ChineseHelper.convertToSimplifiedChinese(inputString);//繁体转简体

        try {
            String inputPinYin = PinyinHelper.convertToPinyinString(inputString, "", PinyinFormat.WITHOUT_TONE).replaceAll("[ ]+", "");
            String optionsPinYin = PinyinHelper.convertToPinyinString(optionsString, "", PinyinFormat.WITHOUT_TONE).replaceAll("[ ]+", "");

            String[] matches = RegexUtils.find(inputPinYin, optionsPinYin);
            if(matches.length > 0) {
                String matchPinYin = matches[0];
                String tempString = optionsPinYin.substring(0, optionsPinYin.indexOf(matchPinYin));
                int startIndex = RegexUtils.find(tempString, "\\|").length;
                return options.get(startIndex);
            }
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return null;
    }
}
