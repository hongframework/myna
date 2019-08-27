package com.hframework.datacenter.myna;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.common.util.file.FileUtils;
import com.hframework.web.ControllerHelper;
import com.hframework.web.controller.DefaultController;
import com.hframework.datacenter.myna.nlp.HanLPUtils;
import com.hframework.myna.config.domain.model.SyntaxDef;
import com.hframework.myna.config.domain.model.SyntaxDef_Example;
import com.hframework.myna.config.service.interfaces.ISyntaxDefSV;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping
public class SyntaxController {
    private static final Logger logger = LoggerFactory.getLogger(SyntaxController.class);
    @Resource
    private ISyntaxDefSV syntaxDefSV;


    private static final JSONObject natureObjects = JSONObject.parseObject("" +
            "{\"a\":\"形容词\",\"ad\":\"副形词\",\"ag\":\"形容词性语素\",\"al\":\"形容词性惯用语\"," +
            "\"an\":\"名形词\",\"b\":\"区别词\",\"begin\":\"仅用于始##始\",\"bg\":\"区别语素\"," +
            "\"bl\":\"区别词性惯用语\",\"c\":\"连词\",\"cc\":\"并列连词\",\"d\":\"副词\"," +
            "\"dg\":\"辄,俱,复之类的副词\",\"dl\":\"连语\",\"e\":\"叹词\",\"end\":\"仅用于终##终\"," +
            "\"f\":\"方位词\",\"g\":\"学术词汇\",\"gb\":\"生物相关词汇\",\"gbc\":\"生物类别\",\"gc\":\"化学相关词汇\"," +
            "\"gg\":\"地理地质相关词汇\",\"gi\":\"计算机相关词汇\",\"gm\":\"数学相关词汇\",\"gp\":\"物理相关词汇\"," +
            "\"h\":\"前缀\",\"i\":\"成语\",\"j\":\"简称略语\",\"k\":\"后缀\",\"l\":\"习用语\",\"m\":\"数词\"," +
            "\"mg\":\"数语素\",\"Mg\":\"甲乙丙丁之类的数词\",\"mq\":\"数量词\",\"n\":\"名词\",\"nb\":\"生物名\"," +
            "\"nba\":\"动物名\",\"nbc\":\"动物纲目\",\"nbp\":\"植物名\",\"nf\":\"食品，比如“薯片”\"," +
            "\"ng\":\"名词性语素\",\"nh\":\"医药疾病等健康相关名词\",\"nhd\":\"疾病\",\"nhm\":\"药品\"," +
            "\"ni\":\"机构相关（不是独立机构名）\",\"nic\":\"下属机构\",\"nis\":\"机构后缀\",\"nit\":\"教育相关机构\"," +
            "\"nl\":\"名词性惯用语\",\"nm\":\"物品名\",\"nmc\":\"化学品名\",\"nn\":\"工作相关名词\",\"nnd\":\"职业\"," +
            "\"nnt\":\"职务职称\",\"nr\":\"人名\",\"nr1\":\"复姓\",\"nr2\":\"蒙古姓名\",\"nrf\":\"音译人名\",\"nrj\":\"日语人名\"," +
            "\"ns\":\"地名\",\"nsf\":\"音译地名\",\"nt\":\"机构团体名\",\"ntc\":\"公司名\",\"ntcb\":\"银行\",\"ntcf\":\"工厂\"," +
            "\"ntch\":\"酒店宾馆\",\"nth\":\"医院\",\"nto\":\"政府机构\",\"nts\":\"中小学\",\"ntu\":\"大学\",\"nx\":\"字母专名\"," +
            "\"nz\":\"其他专名\",\"o\":\"拟声词\",\"p\":\"介词\",\"pba\":\"介词“把”\",\"pbei\":\"介词“被”\",\"q\":\"量词\"," +
            "\"qg\":\"量词语素\",\"qt\":\"时量词\",\"qv\":\"动量词\",\"r\":\"代词\",\"rg\":\"代词性语素\",\"Rg\":\"古汉语代词性语素\"," +
            "\"rr\":\"人称代词\",\"ry\":\"疑问代词\",\"rys\":\"处所疑问代词\",\"ryt\":\"时间疑问代词\",\"ryv\":\"谓词性疑问代词\"," +
            "\"rz\":\"指示代词\",\"rzs\":\"处所指示代词\",\"rzt\":\"时间指示代词\",\"rzv\":\"谓词性指示代词\",\"s\":\"处所词\"," +
            "\"t\":\"时间词\",\"tg\":\"时间词性语素\",\"u\":\"助词\",\"ud\":\"助词\",\"ude1\":\"的 底\",\"ude2\":\"地\"," +
            "\"ude3\":\"得\",\"udeng\":\"等 等等 云云\",\"udh\":\"的话\",\"ug\":\"过\",\"uguo\":\"过\",\"uj\":\"助词\"," +
            "\"ul\":\"连词\",\"ule\":\"了 喽\",\"ulian\":\"连 （“连小学生都会”）\",\"uls\":\"来讲 来说 而言 说来\"," +
            "\"usuo\":\"所\",\"uv\":\"连词\",\"uyy\":\"一样 一般 似的 般\",\"uz\":\"着\",\"uzhe\":\"着\",\"uzhi\":\"之\"," +
            "\"v\":\"动词\",\"vd\":\"副动词\",\"vf\":\"趋向动词\",\"vg\":\"动词性语素\",\"vi\":\"不及物动词（内动词）\"," +
            "\"vl\":\"动词性惯用语\",\"vn\":\"名动词\",\"vshi\":\"动词“是”\",\"vx\":\"形式动词\",\"vyou\":\"动词“有”\"," +
            "\"w\":\"标点符号\",\"wb\":\" 半角：%\",\"wd\":\"逗号，全角：， 半角：,\",\"wf\":\"分号，全角：； 半角： ;\"," +
            "\"wh\":\"半角：$\",\"wj\":\"句号，全角：。\",\"wky\":\"】 〗 〉 半角： ) ] { &gt;\",\"wkz\":\" 半角：( [ { &lt;\"," +
            "\"wm\":\"冒号，全角：： 半角： :\",\"wn\":\"顿号，全角：、\",\"wp\":\"—-\",\"ws\":\"…\",\"wt\":\"叹号，全角：！\"," +
            "\"ww\":\"问号，全角：？\",\"wyy\":\"右引号，全角：” ’ 』\",\"wyz\":\"左引号，全角：“ ‘ 『\",\"x\":\"字符串\"," +
            "\"xu\":\"网址URL\",\"xx\":\"非语素字\",\"y\":\"语气词\",\"yg\":\"语气语素\",\"z\":\"状态词\",\"zg\":\"状态词\"," +
            "\"!\":\"叹号\",\"！\":\"叹号\",\"?\":\"问号\",\"？\":\"问号\"}");

    /**
     * 句法分析入口页面
     * @return
     */
    @RequestMapping(value = "/syntax/parse/home.html")
    @ResponseBody
    public ModelAndView syntaxParse(HttpServletRequest request,
                                           HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        try {

            mav = ServiceFactory.getService(DefaultController.class).root(request, response);
            if(!"login".equals(mav.getViewName())) {
                mav.setViewName("syntax/parse");
            }

        } catch (Throwable e) {
            logger.error("error : {}", e);
        }

        return mav;
    }


    /**
     * 句法分析
     * @param sentences
     * @return
     */
    @RequestMapping(value = "/syntax/parse/result.json")
    @ResponseBody
    public ResultData syntaxParse(@RequestParam("sentences") String sentences){
        logger.debug("request : {}, {}", sentences);
        try {
            String filePath = Robot.syntaxParsePath + "/" + "dictionary" + ".txt";
            if(new File(filePath).exists()) {
                List<String> fileContents = FileUtils.readFileToArray(filePath);
                for (String fileContent : fileContents) {
                    if(StringUtils.isNotBlank(fileContent)) {
                        CustomDictionary.insert(fileContent.split("[ ]+")[0], fileContent.split("[ ]+")[1] + " " + 1024);
                    }
                }
            }


            List<List<Term>> lists = parseTerms(sentences);
            List<String> expressions = CollectionUtils.fetch(lists, new Fetcher<List<Term>, String>() {
                @Override
                public String fetch(List<Term> terms) {
                    return Joiner.on("_").join(CollectionUtils.fetch(terms, new Fetcher<Term, String>() {
                        @Override
                        public String fetch(Term term) {
                            return term.nature.equals(Nature.w) ? term.word : term.nature.toString();
                        }
                    }));
                }
            });
            SyntaxDef_Example example = new SyntaxDef_Example();
            example.createCriteria().andExpressionIn(expressions);
            List<SyntaxDef> syntaxDefList = syntaxDefSV.getSyntaxDefListByExample(example);
            Map<String, SyntaxDef> syntaxDefMap = CollectionUtils.convert(syntaxDefList, new Mapper<String, SyntaxDef>() {
                @Override
                public <K> K getKey(SyntaxDef syntaxDef) {
                    return (K) syntaxDef.getExpression();
                }
            });
            JSONArray result = new JSONArray();
            for (List<Term> list : lists) {
                JSONObject jsonObject = new JSONObject();
                StringBuffer words = new StringBuffer();
                StringBuffer express = new StringBuffer();
                StringBuffer expressDesc = new StringBuffer();
                for (Term term : list) {
                    words.append(term.word).append("_");
                    if(term.nature.equals(Nature.w)) {
                        express.append(term.word).append("_");
                        expressDesc.append(natureObjects.getString(term.word)).append("_");
                    }else {
                        express.append(term.nature).append("_");
                        expressDesc.append(natureObjects.getString(term.nature.toString())).append("_");
                    }
                }
                String expression=  express.substring(0, express.length() - 1);
                jsonObject.put("words", words.toString().replaceAll("_",""));
                jsonObject.put("word_split", words.substring(0, words.length() - 1));
                jsonObject.put("express", expression);
                //TODO
                jsonObject.put("express_desc", expressDesc.substring(0, expressDesc.length() - 1));

                if(syntaxDefMap.containsKey(expression)) {
                    SyntaxDef syntaxDef = syntaxDefMap.get(expression);
                    jsonObject.put("hit_express", syntaxDef.getExpression());
                    jsonObject.put("hit_express_desc", syntaxDef.getName());
                    jsonObject.put("hit_type", syntaxDef.getType());
                    jsonObject.put("hit_limits", syntaxDef.getLimits());
                    jsonObject.put("hit_keywords", parseKeywordInstance(syntaxDef.getKeywords(), list));
                    jsonObject.put("example_finish", exampleExists(expression, words.toString().replaceAll("_","")));
                }
                result.add(jsonObject);
            }
            JSONObject[] jsonObjects = result.toArray(new JSONObject[0]);
            Arrays.sort(jsonObjects, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    String o1Express = o1.containsKey("hit_express_desc") ? o1.getString("hit_express_desc") : "";
                    String o2Express = o2.containsKey("hit_express_desc") ? o2.getString("hit_express_desc") : "";
                    String o1Words = o1.getString("words");
                    String o2Words = o2.getString("words");

                    return -(o1Express.compareTo(o2Express) *1000 + o1Words.compareTo(o2Words));
                }
            });
            return ResultData.success(new JSONArray(Lists.<Object>newArrayList(jsonObjects)));
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    private String parseKeywordInstance(String keywordsExpress, List<Term> list) {
        String[] keywords = keywordsExpress.split("_");
        String[] keywordInstance = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            String keyword = keywords[i];
            String nature = keyword;
            int index = 0;
            if(keyword.contains("(")) {
                nature = keyword.substring(0, keyword.indexOf("("));
                index = Integer.parseInt(keyword.substring(keyword.indexOf("(") + 1, keyword.indexOf(")")));
            }

            List<String> values = new ArrayList<>();
            for (Term term : list) {
                if(term.nature.toString().equals(nature)) {
                    values.add(term.word);
                }
            }
            if(index > 0)  index --;
            if(index < 0)  index = values.size() + index;
            keywordInstance[i] = values.get(index);
        }
        return Joiner.on(" ").join(keywordInstance);
    }

    public static boolean exampleExists(String expression, String sentence) throws IOException {
        String filePath = Robot.syntaxParsePath + "/" + expression + ".txt";
        if(new File(filePath).exists()) {
            List<String> fileContents = FileUtils.readFileToArray(filePath);
            if (fileContents.contains(sentence)) {
                return true;
            }
        }
        return false;
    }

    public void appendFileOrNew(String filePath, String rowInfo) throws IOException {
        if(new File(filePath).exists()) {
            List<String> fileContents = FileUtils.readFileToArray(filePath);
            if(!fileContents.contains(rowInfo)) {
                FileUtils.appendMethodB(filePath, rowInfo +"\n");
            }
        }else {
            FileUtils.writeFile(filePath, rowInfo);
        }
    }

    /**
     * 自定义词典添加
     * @param words
     * @return
     */
    @RequestMapping(value = "/syntax/parse/dictionary.json")
    @ResponseBody
    public ResultData dictionaryAdd(@RequestParam("words") String words, @RequestParam("type") String type){
        logger.debug("request : {}, {}", words);
        try {
            appendFileOrNew(Robot.syntaxParsePath + "/" + "dictionary" + ".txt", words + " " + type);
            return ResultData.success();
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 句法确认
     * @param sentences
     * @return
     */
    @RequestMapping(value = "/syntax/parse/confirm.json")
    @ResponseBody
    public ResultData syntaxParseConfirm(@RequestParam("sentences") String sentences){
        logger.debug("request : {}, {}", sentences);
        try {
            List<List<Term>> lists = parseTerms(sentences);
            List<String> expressions = CollectionUtils.fetch(lists, new Fetcher<List<Term>, String>() {
                @Override
                public String fetch(List<Term> terms) {
                    return Joiner.on("_").join(CollectionUtils.fetch(terms, new Fetcher<Term, String>() {
                        @Override
                        public String fetch(Term term) {
                            return term.nature.equals(Nature.w) ? term.word : term.nature.toString();
                        }
                    }));
                }
            });
            for (String expression : expressions) {
                appendFileOrNew(Robot.syntaxParsePath + "/" + expression + ".txt", sentences);
            }

            return ResultData.success();
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    public static final String getExpressDescription(String expression) {
        if(StringUtils.isBlank(expression)) {
            return null;
        }

        String[] parts = expression.split("_");
        String[] desces = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            desces[i] = natureObjects.getString(parts[i]);
        }
        return Joiner.on("_").join(desces);
    }

    public static final List<List<Term>> parseTerms(String sentences) {
        return HanLPUtils.load(sentences).useNShortAlgorithm().enableNormalization()
                .enableSpeechTagging()
                .seg2sentence();
    }

    /**
     * 句法保存
     * @param syntaxDef
     * @return
     */
    @RequestMapping(value = "/syntax/def/save.json")
    @ResponseBody
    public ResultData syntaxDefSave(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef){
        logger.debug("request : {}", syntaxDef);
        try {
            ControllerHelper.setDefaultValue(syntaxDef, "id");
            String expression = syntaxDef.getExpression();
            syntaxDef.setName(getExpressDescription(expression));
            String example = syntaxDef.getExample();
            String keywordDesc = syntaxDef.getKeywords();
            syntaxDef.setKeywords(parseKeyword(example, keywordDesc));
            int result = syntaxDefSV.create(syntaxDef);
            if(result > 0) {
                syntaxParseConfirm(example);
                return ResultData.success(syntaxDef);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    private String parseKeyword(String sentence, String keywordDesc) {
        List<Term> exampleTerms = parseTerms(sentence).get(0);
        String[] keywordDescParts = keywordDesc.split("[_ ]+");
        String[] keywordParts = new String[keywordDescParts.length];
        for (int i = 0; i < keywordDescParts.length; i++) {
            for (int j = 0; j < exampleTerms.size(); j++) {
                if(exampleTerms.get(j).word.equals(keywordDescParts[i])) {
                    keywordParts[i] = exampleTerms.get(j).nature.toString();

                    int prev = 0;
                    int after = 0;
                    for (int k = 0; k < j; k++) {
                        if(exampleTerms.get(k).nature.toString().equals(keywordParts[i])) {
                            prev ++;
                        }
                    }

                    for (int k = exampleTerms.size() - 1; k > j ; k --) {
                        if(exampleTerms.get(k).nature.toString().equals(keywordParts[i])) {
                            after ++;
                        }
                    }
                    if(prev + after != 0) {
                        keywordParts[i] = keywordParts[i] + (prev == 0? "(1)":(after == 0? "(-1)": "(" + (prev + 1)+ ")"));
                    }
                    break;
                }

            }
        }
        return Joiner.on("_").join(keywordParts);
    }


}
