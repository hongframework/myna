package com.hframework.datacenter.myna.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.synonym.Synonym;
import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.dictionary.common.CommonSynonymDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.utility.Predefine;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class HanLPUtils{
    public static class HanLPWrapper{
        private String text;
        private boolean normalization = false;//是否标准化,繁体->简体，全角->半角，大写->小写
        private boolean removeStopWord = false; //去除停词
        private boolean speechTagging = false; //开启词性标注, 词性分析将会依赖上线文
        private boolean synonymTransfer = false;//转换为同义词
        private Segment segment;
        private String algorithm;


        public HanLPWrapper(String text) {
            this.text = text;
        }

        /**
         * 使用最短路径算法
         * @return
         */
        public HanLPWrapper useShortestAlgorithm(){
            algorithm = "viterbi";
            return this;
        }

        /**
         * 使用n最短路径算法
         * @return
         */
        public HanLPWrapper useNShortAlgorithm(){
            algorithm = "nshort";
            return this;
        }
        /**
         * 使用条件随机场算法
         * @return
         */
        public HanLPWrapper useCRFAlgorithm() throws IOException {
            algorithm = "crf";
            return this;
        }
        /**
         * 使用感知机词法分析
         * @return
         */
        public HanLPWrapper usePerceptronAlgorithm() throws IOException {
            algorithm = "perceptron";
            return this;
        }



        public HanLPWrapper enableNormalization(){
            this.normalization = true;
            HanLP.Config.Normalization = normalization;
            return this;
        }
        public HanLPWrapper enableSpeechTagging(){
            this.speechTagging = true;
            return this;
        }

        public HanLPWrapper enableSynonymTransfer(){
            this.synonymTransfer = true;
            return this;
        }


        public HanLPWrapper removeStopWord(){
            this.removeStopWord = true;
            return this;
        }

        private List<Term> rmStopWord(List<Term> termList){
            ListIterator<Term> listIterator = termList.listIterator();
            while (listIterator.hasNext())
            {
                if (!CoreStopWordDictionary.shouldInclude(listIterator.next()))
                {
                    listIterator.remove();
                }
            }

            return termList;
        }

        private List<List<Term>> rmStopWords(List<List<Term>> sentenceList){
            for (List<Term> sentence : sentenceList){
                rmStopWord(sentence);
            }
            return sentenceList;
        }
        private List<Term> synonymTransform(List<Term> termList){
            String preWord = Predefine.TAG_BIGIN;
            for (Term term : termList) {
                CommonSynonymDictionary.SynonymItem synonymItem = CoreSynonymDictionary.get(term.word);
                if (synonymItem != null){
                    for (Synonym synonym : synonymItem.synonymList) {
                        if (synonym.type == synonymItem.type) {
                            term.word = synonym.realWord;
                            break;
                        }
                    }
                }
            }
            return termList;
        }
        private List<List<Term>> synonymTransforms(List<List<Term>> sentenceList){
            for (List<Term> sentence : sentenceList){
                synonymTransform(sentence);
            }
            return sentenceList;
        }

        public List<Term> segment(){
            if(segment == null) segment = algorithm != null ? HanLP.newSegment(algorithm) : HanLP.newSegment();
            segment.enablePartOfSpeechTagging(speechTagging);
            segment.enableCustomDictionaryForcing(true);
            List<Term> termList = segment.seg(text);
            if(synonymTransfer){
                termList = synonymTransform(termList);
            }
            return removeStopWord ? rmStopWord(termList) : termList;

        }

        public List<List<Term>> seg2sentence() {
            if(segment == null) segment = algorithm != null ? HanLP.newSegment(algorithm) : HanLP.newSegment();
            segment.enablePartOfSpeechTagging(speechTagging);
            segment.enableCustomDictionaryForcing(true);
            List<List<Term>> termList = segment.seg2sentence(text);
            if(synonymTransfer){
                termList = synonymTransforms(termList);
            }
            return removeStopWord ? rmStopWords(termList) : termList;
        }

    }

    public static HanLPWrapper load(String text) {
        return new HanLPWrapper(text);
    }

    public static void addStopWord(String... words) {
        if(words != null) {
            for (String word : words) {
                CoreStopWordDictionary.add(word);
            }
        }
    }
}