
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.file.FileUtils;
import com.hframework.datacenter.myna.nlp.HanLPUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class HanLPSyntaxUtils {

    public static Map<String, String> normalizeString = new HashMap(){{
        put("芝麻信用", "信用");
        put("芝麻分", "信用");
    }};

    public static Map<String, String> normalizeEndString = new HashMap(){{
        put("ma", "吗");
        put("口马", "吗");
    }};

    public static Set<String> dKeywords = Sets.newHashSet("分期","临时","自动");
    public static List<String> customDictionary4n = Lists.newArrayList("淘宝", "花呗", "借呗", "收钱码", "收款码", "二维码", "外卖","美团", "余额宝", "网商贷","网商银行","网商", "双十一", "携程", "新号", "支付宝");
    public static List<String> customDictionary4v = Lists.newArrayList("清空", "扫码", "充值", "免息", "扣款", "绑定"/*, "到账"*/);


    public static List<String> removeWords = Lists.newArrayList("帮我看一下", "我想咨询下", "帮忙看一下","蚂蚁", "我说的是","帮我", "想咨询");
    public static List<String> whatQuestions = Lists.newArrayList("是什么","什么叫", "什么是", "有什么", "什么信息", "什么条件");
    public static List<String> whetherQuestions = Lists.newArrayList("是不是","有没有","有木有","开不开","会不会","能不能","可不可以","可以不可以","可不可","是否也可以","是否可以","是否能", "可以吧","对吧", "是否");
    public static List<String> whyQuestions = Lists.newArrayList("什么意思", "啥意思", "什么原因", "为啥","凭什么", "怎没", "为何","为什么","干嘛", "没有办法");
    public static List<String> whenQuestions = Lists.newArrayList("什么情况", "什么时候", "什么时候", "啥时候", "多久", "几时", "几号", "几点", "时间", "何时");
    public static List<String> howQuestions = Lists.newArrayList("如何", "怎么办", "怎么","怎样","咋样","咋", "怎样办","咋样办","咋办");
    public static List<String> manyQuestions = Lists.newArrayList("多少","好多","几次","几个","几期");
    public static List<String> whereQuestions = Lists.newArrayList("什么地方", "在哪儿","哪里","哪");
    public static List<String> relateQuestions = Lists.newArrayList("区别","互斥","一样","一回事儿","同一个", "公用","不能同时", "有什么不同", "有何不同", "不一致");

    private static Comparator comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if(o1.length() == o2.length()) {
                return -o1.compareTo(o2);
            }else {
                return o2.length() - o1.length();
            }
        }
    };

    public static Map<String, ParseInfo.Type> typeKeyWords = new TreeMap(comparator);
    public static Map<String, ParseInfo.Type> typeEndKeyWords = new TreeMap(comparator){{
        put("日", ParseInfo.Type.When);
        put("不了", ParseInfo.Type.Why);
        put("不通过", ParseInfo.Type.Why);
        put("不行", ParseInfo.Type.Why);
        put("不对", ParseInfo.Type.Why);
        put("失败", ParseInfo.Type.Why);
        put("啥原因", ParseInfo.Type.Why);
        put("未通过", ParseInfo.Type.Why);
        put("没通过", ParseInfo.Type.Why);
        put("没用", ParseInfo.Type.Why);
        put("没有了", ParseInfo.Type.Why);
        put("不见了", ParseInfo.Type.Why);
        put("没有", ParseInfo.Type.Whether);
        put("是吧", ParseInfo.Type.Whether);
        put("不", ParseInfo.Type.Whether);
        put("吗", ParseInfo.Type.Whether);
        put("嘛", ParseInfo.Type.Whether);
        put("么", ParseInfo.Type.Whether);
        put("吧", ParseInfo.Type.Whether);
        put("什么条件", ParseInfo.Type.What);
        put("是啥东西", ParseInfo.Type.What);
        put("有何作用", ParseInfo.Type.What);
        put("什么作用", ParseInfo.Type.What);
        put("什么要求", ParseInfo.Type.What);
        put("什么的", ParseInfo.Type.What);
        put("是啥", ParseInfo.Type.What);
        put("几天呀", ParseInfo.Type.Many);



    }};
    public static Map<String, ParseInfo.Type> typeStartKeyWords = new TreeMap(comparator){{
        put("我想", ParseInfo.Type.How);
        put("想", ParseInfo.Type.How);
        put("请给我", ParseInfo.Type.Imperative);
        put("我要", ParseInfo.Type.Imperative);
        put("帮我", ParseInfo.Type.Imperative);
        put("赶紧", ParseInfo.Type.Imperative);
        put("希望", ParseInfo.Type.Imperative);
        put("几时", ParseInfo.Type.When);
        put("为什", ParseInfo.Type.Why);
    }};

    public static Map<String, Set<ParseInfo.Type>> typeLikeKeyWords = new TreeMap(comparator){{
        put("回事", Sets.newHashSet(ParseInfo.Type.Why));
        put("不可以", Sets.newHashSet(ParseInfo.Type.Why));
        put("那里", Sets.newHashSet(ParseInfo.Type.Where));//在QA中多半会打错为哪里
        put("什么", Sets.newHashSet(ParseInfo.Type.What, ParseInfo.Type.How));//将怎么打成什么
    }};

    public static Map<String, ParseInfo.Type> typePhrases = new TreeMap(comparator){{
        put(".*(有|是|要|(需要)).*(吗|么)", ParseInfo.Type.Whether);
        put(".*有.*没", ParseInfo.Type.Whether);
        put(".*(能|可)否.*", ParseInfo.Type.Whether);
        put(".*好.*还是.*好.*", ParseInfo.Type.Whether);
        put(".*不了.*", ParseInfo.Type.Why);
        put(".*找不到.*", ParseInfo.Type.Why);
        put(".*无法(开通)?.*", ParseInfo.Type.Why);
        put(".*怎么.*(没|不).*", ParseInfo.Type.Why);

    }};


    static {
        for (String whereQuestion : whereQuestions) {
            typeKeyWords.put(whereQuestion, ParseInfo.Type.Where);
        }
        for (String whatQuestion : whatQuestions) {
            typeKeyWords.put(whatQuestion, ParseInfo.Type.What);
        }
        for (String booleanQuestion : whetherQuestions) {
            typeKeyWords.put(booleanQuestion, ParseInfo.Type.Whether);
        }

        for (String whyQuestion : whyQuestions) {
            typeKeyWords.put(whyQuestion, ParseInfo.Type.Why);
        }

        for (String howQuestion : howQuestions) {
            typeKeyWords.put(howQuestion, ParseInfo.Type.How);
        }

        for (String whenQuestion : whenQuestions) {
            typeKeyWords.put(whenQuestion, ParseInfo.Type.When);
        }

        for (String manyQuestion : manyQuestions) {
            typeKeyWords.put(manyQuestion, ParseInfo.Type.Many);
        }
        for (String relateQuestion : relateQuestions) {
            typeKeyWords.put(relateQuestion, ParseInfo.Type.Relate);
        }

        for (String n1 : customDictionary4n) {
            CustomDictionary.add(n1, "n 1024");
        }

        for (String v1 : customDictionary4v) {
            CustomDictionary.add(v1, "v 1024");
        }
        CustomDictionary.add("分期", "n 1024");
        CustomDictionary.add("闲鱼", "ns 1024");
        CustomDictionary.add("怎么回事", "r 1024");
        CustomDictionary.add("什么意思", "r 1024");
    }


    public static void main(String[] args) throws IOException {
        List<String> rows = FileUtils.readFileToArray("E:\\my_workspace\\myna\\search\\src\\test\\java\\atec_nlp_sim_train_add.csv");
        Statics statics = new Statics();
        List<String> result = new ArrayList<>();
        for (String row : rows) {
            String[] spans = row.split("\t");
            long rowNo = Long.parseLong(spans[0]);
            boolean actual = "1".equals(spans[3]);//相同和不相同
            String s1 = spans[1];
            String s2 = spans[2];

//            if((s1 + s2).contains("，")) continue;

            ParseInfo p1 = parse(s1);
            ParseInfo p2 = parse(s2);

            int score = p1.similarWith(p2);
            boolean isTypeSame = p1.typeSameTo(p2);
            boolean predict = isTypeSame /*&& p1.tf == p2.tf*/ && score >= 60;

            String r1 = Joiner.on("\t").join(new Object[]{rowNo, s1, p1, score});
            result.add(r1);
            String r2 = Joiner.on("\t").join(new Object[]{rowNo, s2, p2, score});
            result.add(r2);

            Statics.Type type = statics.add(predict, predict == actual);
            for (List<String> strings : p1.keyTermsGroupByNature.keySet()) statics.addWords(strings);
            for (List<String> strings : p2.keyTermsGroupByNature.keySet()) statics.addWords(strings);


            if(rowNo > 0
                    && type == Statics.Type.fp) {
//                if(p1.types == null || p1.types.size() == 0)
                if("n".equals(Joiner.on("_").join(p1.getKeyWordNatureString().values()))
                        && "n".equals(Joiner.on("_").join(p2.getKeyWordNatureString().values()))) {
                    System.out.println(r1);
                    System.out.println(r2);
                }
//                    System.out.println(r1);
////                if(p2.types == null || p2.types.size() == 0)
//                    System.out.println(r2);
            }
        }

        System.out.println(statics);
        FileUtils.deleteFile("E:\\my_workspace\\myna\\search\\src\\test\\java\\result.csv");
        FileUtils.writeFile("E:\\my_workspace\\myna\\search\\src\\test\\java\\result.csv", Joiner.on("\n").join(result));

        FileUtils.deleteFile("E:\\my_workspace\\myna\\search\\src\\test\\java\\words.csv");
        final ArrayList<Map.Entry<String, Integer>> entries = Lists.newArrayList(statics.wordCount.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        FileUtils.writeFile("E:\\my_workspace\\myna\\search\\src\\test\\java\\words.csv", Joiner.on("\n").join(
                CollectionUtils.fetch(entries, new Fetcher<Map.Entry<String,Integer>, String>() {
                    @Override
                    public String fetch(Map.Entry<String, Integer> stringIntegerEntry) {
                        return stringIntegerEntry.getKey() + "\t" + stringIntegerEntry.getValue();
                    }
                })));
    }

    private static ParseInfo parse(String sentence) {

        for (String str : normalizeString.keySet()) {
            if(sentence.contains(str)) {
                sentence = sentence.replaceAll(str, normalizeString.get(str));
            }
        }

        for (String str : normalizeEndString.keySet()) {
            if(sentence.endsWith(str)) {
                sentence = sentence.substring(0, sentence.length() - str.length()) + normalizeEndString.get(str);
            }
        }

        ParseInfo parseInfo = new ParseInfo(sentence);
//        sentence = sentence.replaceAll("\\*+", "1");
        for (String removeWord : removeWords) {
            if(sentence.contains(removeWord)) {
                sentence = sentence.replaceAll(removeWord, "");
            }
        }
        String target = null;
        boolean isDeny = false;
        List<ParseInfo.Type> types = new ArrayList<>();
        if(sentence.matches("(不)?(能|让|会|(可以)).*(是)?(吗|么|了|吧)")) {
            String[] item = RegexUtils.find(sentence, "(不)?(能|让|会|(可以)).*(是)?(吗|么|了|吧)");
            if(item.length > 0) {
                target = item[0];
                isDeny = !target.startsWith("不");
                types.add(ParseInfo.Type.Whether);
                if(target.length() >= 3 && target.substring(0,3).matches("(不)?(能|让|会|(可以))")) {
                    target = target.substring(3);
                }else if(target.length() >= 2 && target.substring(0,2).matches("(不)?(能|让|会|(可以))")) {
                    target = target.substring(2);
                } if(target.length() >= 1 && target.substring(0,1).matches("(不)?(能|让|会|(可以))")) {
                    target = target.substring(1);
                }

                if(target.length() >= 2 && target.substring(target.length() - 2).matches("(是)?(吗|么|了|吧)")) {
                    target = target.substring(0, target.length() - 2);
                }else if(target.length() >= 1 && target.substring(target.length() - 1).matches("(是)?(吗|么|了|吧)")) {
                    target = target.substring(0, target.length() - 1);
                }

                sentence = sentence.replace(item[0], target);
            }
        }


        for (String phrases : typePhrases.keySet()) {
            if(sentence.matches(phrases)) {
                types.add(typePhrases.get(phrases));
            }
        }

        for (String keywords : typeKeyWords.keySet()) {
            if(sentence.contains(keywords)) {
                sentence = sentence.replaceAll(keywords, "");
                types.add(typeKeyWords.get(keywords));
            }
        }

        for (String keywords : typeEndKeyWords.keySet()) {
            if(sentence.endsWith(keywords)) {
                sentence = sentence.substring(0, sentence.length() - keywords.length());
                types.add(typeEndKeyWords.get(keywords));
            }
        }

        for (String keywords : typeStartKeyWords.keySet()) {
            if(sentence.startsWith(keywords)) {
                sentence = sentence.substring(keywords.length());
                types.add(typeStartKeyWords.get(keywords));
            }
        }


        for (String keywords : typeLikeKeyWords.keySet()) {
            if(sentence.contains(keywords)) {
                types.addAll(typeLikeKeyWords.get(keywords));
            }
        }



        String[] matches = RegexUtils.find(sentence, "[没不]+");
        if(matches.length > 0) {
            isDeny = (matches.length) % 2 == 1;
        }

        sentence = sentence.replaceAll("[没有不能]+", "");

        List<Term> terms = parseTerms(sentence);
        modifyTermsByTarget(terms, target);

        List<Term> keyTerms =  parseKeyTerms(terms);
        Map<List<String>, Nature> listNatureMap = parseInfo.groupKeywordsByNature(keyTerms);
        if(listNatureMap.size() == 1 && !listNatureMap.values().contains(Nature.v)) {
            sentence = parseInfo.sentence;
            for (String removeWord : removeWords) {
                if(sentence.contains(removeWord)) {
                    sentence = sentence.replaceAll(removeWord, "");
                }
            }
            terms = parseTerms(sentence);
            modifyTermsByTarget(terms, target);
            keyTerms =  parseKeyTerms(terms);
            listNatureMap = parseInfo.groupKeywordsByNature(keyTerms);
        }

        if(isDeny && !types.contains(ParseInfo.Type.Whether) && !parseInfo.sentence.startsWith("为什么")) types.add(ParseInfo.Type.Whether);

        parseInfo.setTerms(terms);
        parseInfo.setKeyTerms(keyTerms);
        parseInfo.setKeyTermsGroupByNature(listNatureMap);
        parseInfo.setTf(!isDeny);
        parseInfo.setTypes(types);
        return parseInfo;
    }

    private static List<Term> parseKeyTerms(List<Term> terms) {
        List<Term> keyTerms = new ArrayList<>();
        for (Term term : terms) {
            switch (term.nature){
                case v:
                    if("可以".equals(term.word) || "需要".equals(term.word) || "还要".equals(term.word)){
                        break;
                    }
                case vn:
                case n:
                case ns:
                case ul:
                case a:
                case an:
//                case d: //花呗如何临时提额	[花呗/n, 临时/d, 提/v, 额/n] 花呗如何提额	[花呗/n, 提/v, 额/n]
                    keyTerms.add(term);
                    break;
            }
        }
        return keyTerms;
    }

    private static void modifyTermsByTarget(List<Term> terms, String target) {
        if(StringUtils.isNotBlank(target)) {
            boolean isMatch = false;
            Term gusTerm = terms.get(terms.size() - 1);
            for (int i = terms.size() -1 ; i >= 0 ; i--) {
                Term term = terms.get(i);
                if(target.endsWith(term.word) && term.nature != Nature.v) {
                    target = target.substring(0, term.word.length());
                    gusTerm = mergeGusTerm(gusTerm, term);
                }else if(target.endsWith(term.word) && term.nature == Nature.v) {
                        isMatch = true;
                        break;
                }
                if(StringUtils.isBlank(target)) {
                    break;
                }
            }

            if(!isMatch) {
                gusTerm.nature = Nature.v;
            }
        }
    }

    private static Term mergeGusTerm(Term gusTerm, Term curTerm) {
        if(gusTerm.equals(curTerm)) return gusTerm;

        //TODO 根据已知词性，判断谁最有可能
        return gusTerm;
    }


    public static final List<Term> parseTerms(String sentences) {
        return HanLPUtils.load(sentences).useNShortAlgorithm().enableNormalization()
                .enableSpeechTagging()
                .segment();
    }

    public static class ParseInfo {

        private String sentence;
        private List<Term> terms;
        private List<Term> keyTerms;
        private boolean tf = true;
        private List<Type> types = null;

        private Map<List<String>, Nature> keyTermsGroupByNature = null;

        public boolean typeSameTo(ParseInfo p2) {
            List<Type> otherTypes = p2.types;
            if(isListEmpty(types) && isListEmpty(otherTypes)) {
                return true;
            }else if(isListEmpty(types) != isListEmpty(otherTypes)) {
                return false;
            }else {
                return Lists.newArrayList(types).removeAll(otherTypes); //是否有相同的类型
//                return types.containsAll(otherTypes) || otherTypes.containsAll(types);
            }
        }

        public boolean isListEmpty(List list){
            return list == null || list.isEmpty();
        }

        public enum Type{
            What, Whether,Why, Where, When, How, Many, Relate, Imperative, Unknown
        }

        public ParseInfo(String sentence) {
            this.sentence = sentence;
        }

        public List<Term> getTerms() {
            return terms;
        }

        public List<Term> getKeyTerms() {
            return keyTerms;
        }

        public void setTypes(List<Type> types) {
            this.types = types;
        }

        public static String getSortedWordsString(List<String> words){
            Collections.sort(words);
            return Joiner.on("").join(words);
        }

        public static char[] getSortedChars(List<String> words){
            String chars = Joiner.on("").join(words);
            char[] chars1 = chars.toCharArray();
            Arrays.sort(chars1);
            return chars1;
        }

        public List<String> getKeyWordsList(){
            List<String> words = CollectionUtils.fetch(keyTerms, new Fetcher<Term, String>() {
                @Override
                public String fetch(Term term) {
                    return term.word;
                }
            });
            return words;
        }

        public Set<String> getKeyWordByNatureStart(String startBy, int lengthGreaterThan, int lengthLessThan, Set<String> scope) {
            Set<String> result = new HashSet<>();
            for (Term term : terms) {
                if(term.nature.startsWith(startBy)) {
                    if(term.word.length() > lengthGreaterThan && term.word.length() < lengthLessThan && scope.contains(term.word)) {
                        result.add(term.word);
                    }

                }
            }

            return result;
        }

        public void setKeyTermsGroupByNature(Map<List<String>, Nature> keyTermsGroupByNature) {
            this.keyTermsGroupByNature = keyTermsGroupByNature;
        }

        public static Map<List<String>, Nature> groupKeywordsByNature(List<Term> terms) {
            Nature lastNature = null;
            Map<List<String>, Nature> info = new LinkedHashMap<>();
            List<String> words = null;
            for (Term keyTerm : terms) {
                Nature nature = keyTerm.nature;
                if (nature.firstChar() == 'n' || nature.firstChar() == 'v') {
                    if(lastNature != nature) {
                        words = new ArrayList<String>();
                        info.put(words, nature);
                    }
                    words.add(keyTerm.word);
                }
                lastNature = nature;
            }
            return info;
        }

        public Map<List<String>, Nature> getKeyWordNatureString(){
            return keyTermsGroupByNature;
        }

        @Override
        public String toString() {
            return Joiner.on("\t").join(new Object[]{
                    Arrays.toString(terms.toArray(new Term[0])),
                    Arrays.toString(keyTerms.toArray(new Term[0])),
                    tf,
                    Arrays.toString(types.toArray(new Type[0])),
                    getKeyWordNatureString()});
        }

        public int similarWith(ParseInfo b) {

            Map<List<String>, Nature> thisKeyWorkNature = getKeyWordNatureString();
            Map<List<String>, Nature> otherKeyWorkNature = b.getKeyWordNatureString();
            String thisKeyString = Joiner.on("_").join(thisKeyWorkNature.values());
            String otherKeyString = Joiner.on("_").join(otherKeyWorkNature.values());


            if(isSameValue(thisKeyString, otherKeyString, "n_v_n")
                    || isSameValue(thisKeyString, otherKeyString, "n")
                    ) {
                Iterator<Map.Entry<List<String>, Nature>> thisIterator = thisKeyWorkNature.entrySet().iterator();
                Iterator<Map.Entry<List<String>, Nature>> otherIterator = otherKeyWorkNature.entrySet().iterator();
                Set<Nature> dealNature = new HashSet<>();
                while (thisIterator.hasNext()) {
                    Map.Entry<List<String>, Nature> thisEntry = thisIterator.next();
                    Nature nature = thisEntry.getValue();
                    List<String> thisKeywords = thisEntry.getKey();
                    if(otherIterator.hasNext()) {
                        Map.Entry<List<String>, Nature> otherEntry = otherIterator.next();
                        List<String> otherKeywords = otherEntry.getKey();
                        boolean success = compareKeywords(thisKeywords, otherKeywords, nature, dealNature);
                        if(!success) {
                            return -1;
                        }
                        dealNature.add(nature);
                    }
                }

                Set<String> thisD = getKeyWordByNatureStart("d", 1, 100, dKeywords);
                Set<String> otherD = b.getKeyWordByNatureStart("d", 1, 100, dKeywords);
                if(!thisD.isEmpty() && !otherD.isEmpty()) {
                    if(!hasSameCharacter(thisD, otherD)) {
                        return -2;
                    }
                }else if(thisD.isEmpty() != otherD.isEmpty()) {
//                    System.out.println(sentence + "<==>" + b.sentence);
//                    System.out.println(thisD + "<==>" + otherD);
                    return -2;
                }
            }





            List<String> thisKeyWordsList = this.getKeyWordsList();
            List<String> otherKeyWordsList = b.getKeyWordsList();
            if(thisKeyWordsList.containsAll(otherKeyWordsList) || otherKeyWordsList.containsAll(thisKeyWordsList)) {
                return 100;
            }

            char[] thisSortedChars = getSortedChars(thisKeyWordsList);
            char[] otherSortedChars = getSortedChars(otherKeyWordsList);

            int total = thisSortedChars.length + otherSortedChars.length;

            int i = 0; int j = 0;
            int same = 0;
            while (thisSortedChars.length > i && otherSortedChars.length > j) {//比对都没有结束
                if(thisSortedChars[i] == otherSortedChars[j]) {
                    same +=2;
                    i++;j++;
                }else if(thisSortedChars[i] < otherSortedChars[j]) {
                    i++;
                }else {
                    j++;
                }
            }
//            System.out.println(total + ":" + same);


//            if(this.getKeyTermSortedWords().contains(b.getKeyTermSortedWords())
//                    || b.getKeyTermSortedWords().contains(this.getKeyTermSortedWords())) {
//                return 100;
//            }


            return same * 100 / total;
        }

        private boolean isSameValue(String thisKeyString, String otherKeyString, String value) {
            return thisKeyString.equals(otherKeyString)
                    && value.equals(thisKeyString) && value.equals(otherKeyString);
        }

        private boolean hasSameCharacter(Collection<String> thisKeywords, Collection<String> otherKeywords){
            boolean sameKeyword = Lists.newArrayList(thisKeywords).removeAll(otherKeywords);//是否有相同的类型
            if(sameKeyword) {
                return true;
            }
            for (String thisKeyword : thisKeywords) {
                for (String otherKeyword : otherKeywords) {
                    if((otherKeyword.length() == 1 && thisKeyword.contains(otherKeyword))
                            || (thisKeyword.length() == 1 && otherKeyword.contains(thisKeyword))) {//因为缩写
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean compareKeywords(List<String> thisKeywords, List<String> otherKeywords, Nature nature, Set<Nature> dealNature) {
            if(nature.startsWith("n")) {
                if(!dealNature.contains(nature)) {
                    return thisKeywords.containsAll(otherKeywords) && otherKeywords.containsAll(thisKeywords);
                }else {
                    return hasSameCharacter(thisKeywords, otherKeywords);
                }

            }else if(nature.startsWith("v")) {
                return hasSameCharacter(thisKeywords, otherKeywords);
//                String thisKeywordString = Joiner.on("").join(thisKeywords);
//                String otherKeywordString = Joiner.on("").join(otherKeywords);
//                if(Sets.newHashSet(thisKeywordString.toCharArray())
//                        .removeAll(Sets.newHashSet(otherKeywordString.toCharArray()))) {
//                    return true;
//                }else {
//                    return false;
//                }

//                return thisKeywords.containsAll(otherKeywords) || otherKeywords.containsAll(thisKeywords);
            }
             return true;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public void setTerms(List<Term> terms) {
            this.terms = terms;
        }

        public void setKeyTerms(List<Term> keyTerms) {
            this.keyTerms = keyTerms;
        }

        public void setTf(boolean tf) {
            this.tf = tf;
        }
    }

    public static class Statics{
        private int tp = 0; // True Positive，表示对同义的判定的个数
        private int fp = 0; // False Positive，表示错误的同义判断个数
        private int tn = 0; // True Negative, 标识正确的不同义判断个数
        private int fn = 0; // False Negative，错误的不同义判断个数

        private LinkedHashMap<String, Integer> wordCount = new LinkedHashMap<>();


        public enum Type{
            tp,fp,tn,fn
        }

        public void tpAdd(){
            tp ++;
        }

        public void fpAdd(){
            fp ++;
        }

        public void tnAdd(){
            tn ++;
        }

        public void fnAdd(){
            fn ++;
        }

        public Type add(boolean predict, boolean success) {
            if(predict && success){
                tp++;
                return Type.tp;
            }else if(predict && !success){
                fp++;
                return Type.fp;
            }else if(!predict && success){
                tn++;
                return Type.tn;
            }else if(!predict && !success){
                fn++;
                return Type.fn;
            }

            return null;
        }

        public void addWords(Collection<String> words) {
            for (String word : words) {
                if(!wordCount.containsKey(word)) {
                    wordCount.put(word, 1);
                }else {
                    wordCount.put(word, wordCount.get(word) + 1);
                }
            }
        }

        public double accuracy(){
            return BigDecimal.valueOf(tp + tn).divide(BigDecimal.valueOf(tp + tn + fp + fn), 4, RoundingMode.CEILING).doubleValue();
        }

        public double precision(){
            return BigDecimal.valueOf(tp)
                    .divide(BigDecimal.valueOf(tp + fp), 4, RoundingMode.CEILING)
                    .doubleValue();
        }

        public double recall(){
            return BigDecimal.valueOf(tp)
                    .divide(BigDecimal.valueOf(tp + fn), 4, RoundingMode.CEILING)
                    .doubleValue();
        }

        public double f1score(){
            return BigDecimal.valueOf(2 * precision() * recall())
                    .divide(BigDecimal.valueOf(precision() + recall()), 4, RoundingMode.CEILING)
                    .doubleValue();
        }

        @Override
        public String toString() {
            return "tp : " + tp + "\n"
                    + "fp : " + fp + "\n"
                    + "tn : " + tn + "\n"
                    + "fn : " + fn + "\n"
                    + "accuracy : " + accuracy() + "\n"
                    + "precision : " + precision() + "\n"
                    + "recall : " + recall() + "\n"
                    + "f1score : " + f1score() + "\n" ;
        }
    }
}