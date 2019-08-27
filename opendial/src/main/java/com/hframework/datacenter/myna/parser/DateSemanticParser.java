package com.hframework.datacenter.myna.parser;

import com.google.common.collect.Lists;
import com.hframework.common.util.DateUtils;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.parser.date.nlp.TimeNormalizer;
import com.hframework.datacenter.myna.parser.date.nlp.TimeUnit;
import opendial.DialogueState;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DateSemanticParser implements ISemanticParser {
    @Override
    public Value parse(Value value, QnaDescriptor qnaDescriptor, DialogueState state) {
        String valueString = value.toString();
        if(StringUtils.isBlank(valueString)) return null;
        TimeNormalizer normalizer = new TimeNormalizer();
        TimeUnit[] units = normalizer.parse(valueString);
        if(units.length > 0 && units[0] != null) {
            return new StringVal(units[0].Time_Norm);
        }
        return null;
//        Integer[] date = parseDate(valueString);
//        if(date == null) {
//            return null;
//        }else {
//            return new StringVal(formatDate(date));
//        }
    }

    public static String formatDate(Integer[] date) {
        if(date == null) return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < date.length; i++) {
            if(date[i] == null) {
                break;
            }
            if(i > 0 && i < 3) {
                sb.append("-");
            }else if (i == 3){
                sb.append(" ");
            }else if(i > 3) {
                sb.append(":");
            }
            sb.append(date[i]);
        }
        return sb.toString();
    }

    /**
     * 支持：年月日(号)时(点)分秒解析
     * 支持： / - 日期格式的解析
     * 支持：本月、上月、昨天、前天、头天
     * 支持：n天后, n天前, n星期后, n星期前
     * 支持：大小写转换，如二零一八年五月十五日
     * @param dateString
     * @return
     */
    public static Integer[] parseDate(String dateString) {

        dateString = SemanticParserUtils.toggleCase(dateString).replaceAll("两","2");
        Calendar baseCalendar = Calendar.getInstance();
        String keywords = "年月日时分秒星";
        ArrayList<Integer> calendarFields = Lists.newArrayList(Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
                Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.WEEK_OF_MONTH);
        String tempString = dateString.replace("小时","时").replace("天","日").replace("星期","星");
        String[] relativeDate = RegexUtils.find(tempString, "\\d+(年|月|日|时|分|秒|星)(前|后)");
        for (String tempDateString : relativeDate) {
            int number = Integer.parseInt(tempDateString.substring(0, tempDateString.length() - 2));
            char unitChar = tempDateString.charAt(tempDateString.length() - 2);
            int unit = keywords.indexOf(unitChar);
            boolean isNegative = '前' == tempDateString.charAt(tempDateString.length() - 1) ? true : false;
            baseCalendar.add(calendarFields.get(unit), (isNegative ? -1 : 1) * number);
            if(unit == 2) {//日
                dateString = dateString.replace(tempDateString, "本" + unitChar)
                        .replace(tempDateString.replace("日","天"), "本" + unitChar);
            }else if(unit == 3) {//时
                dateString = dateString.replace(tempDateString, "本" + unitChar)
                        .replace(tempDateString.replace("时","小时"), "本" + unitChar);
            }else if(unit == 6) {//星期
                dateString = dateString.replace(tempDateString, "本" + unitChar)
                        .replace(tempDateString.replace("星","星期"), "本" + unitChar);
            }else {
                dateString = dateString.replace(tempDateString, "本" + unitChar);
            }
        }

        String[] baseDateString = DateUtils.getDateYYYYMMDDHHMMSS(baseCalendar.getTime()).split("[- :]+");
        int[] baseDate = new int[6];
        for (int i = 0; i < 6; i++) {
            baseDate[i] = Integer.valueOf(baseDateString[i]);
        }

        dateString = dateString.replaceAll("上午","");
        String[] afternoons = RegexUtils.find(dateString, "下午\\d+");
        for (String afternoon : afternoons) {
            if(afternoon.length() > 2) {
                int num = Integer.valueOf(afternoon.substring(2)) + 12;
                dateString = dateString.replaceAll(afternoon, String.valueOf(num));
            }
        }

        dateString = dateString.replaceAll("点","时").replaceAll("号","日").replace("天","日").replaceAll("星期","星");
        String[] result = new String[6];
        String[] numbers = RegexUtils.find(dateString, "\\d+|本|上|昨|头|前|今|明|后");
        String keyChars = dateString.replaceAll("\\d+|本|上|昨|头|前|今|明|后", "");

        int startIndex = keywords.indexOf(keyChars.charAt(0));
        if(startIndex >= 0) {
            for (int i = startIndex; i < startIndex + numbers.length && i < result.length; i++) {
                result[i] = numbers[i - startIndex];
            }
        }else if(numbers.length == 2 || numbers.length == 3) {
            int index = 0;
            if(':' == keyChars.charAt(0)) { //时
                index = 3;
            }else if(Integer.valueOf(numbers[0]) > 12) { //年
                index = 0;
            }else {//月
                index = 1;
            }
            for (int i = 0; i < numbers.length; i++) {
                result[index + i] = numbers[i];
            }
        }else {
            for (int i = 0; i < numbers.length; i++) {
                result[i] = numbers[i];
            }
        }

        Integer[] dateParts = new Integer[6];
        boolean flag = false;
        for (int i = result.length - 1; i > -1 ; i--) {
            if(!flag && result[i] == null) {
                continue;
            }else if((flag && result[i] == null) || "本".equals(result[i]) || "今".equals(result[i])) {
                dateParts[i] = baseDate[i];
            }else if("上".equals(result[i]) || "昨".equals(result[i]) || "头".equals(result[i])) {
                dateParts[i] = baseDate[i] - 1;
            }else if("前".equals(result[i])) {
                dateParts[i] = baseDate[i] - 2;
            }else if("明".equals(result[i])) {
                dateParts[i] = baseDate[i] + 1;
            }else if("后".equals(result[i])) {
                dateParts[i] = baseDate[i] + 2;
            }else {
                dateParts[i] = Integer.valueOf(result[i]);
            }
            flag = true;
        }
        if(!flag) {
            return null;
        }
        if(dateParts[0] < 1000) {
            dateParts[0] = 2000 + dateParts[0];
        }

        return dateParts;
    }

    public static void main(String[] args) {
//        System.out.println("2018年12月23 15点14分59秒" + formatDate(parseDate("2018年12月23 15点14分59秒")));
//        System.out.println("2018年12月23 15点14" + formatDate(parseDate("2018年12月23 15点14")));
//        System.out.println("2018年12月23 15点" + formatDate(parseDate("2018年12月23 15点")));
//        System.out.println("2018年12月23" + formatDate(parseDate("2018年12月23")));
//        System.out.println("2018年12" + formatDate(parseDate("2018年12")));
//        System.out.println("12月23" + formatDate(parseDate("12月23")));
//        System.out.println("12号" + formatDate(parseDate("12号")));
//        System.out.println("15:13" + formatDate(parseDate("15:13")));
//        System.out.println("18-1" + formatDate(parseDate("18-1")));
//        System.out.println("12-1" + formatDate(parseDate("12-1")));
//        System.out.println("本月15号下午两点" + formatDate(parseDate("本月15号下午两点")));
//        System.out.println("2天后" + formatDate(parseDate("2天后")));
//        System.out.println("12月前" + formatDate(parseDate("12月前")));
//        System.out.println("十天前" + formatDate(parseDate("十天前")));
//        System.out.println("dsafdsafds" + formatDate(parseDate("dsafdsafds")));
          System.out.println("下周三" + formatDate(parseDate("下周三")));
          System.out.println("下周三" + formatDate(parseDate("下周三")));

    }
}
