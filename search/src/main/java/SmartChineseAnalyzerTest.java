import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.util.Version;

public class SmartChineseAnalyzerTest {

    public static void main(String[] args) {
        try {
            // 要处理的文本  
            String text = "我来自于武汉大学";

            // 自定义停用词  
            String[] self_stop_words = { "的", "了", "呢", "，", "0", "：", ",", "是", "流" };
            CharArraySet cas = new CharArraySet(0, true);
            for (int i = 0; i < self_stop_words.length; i++) {
                cas.add(self_stop_words[i]);
            }

            // 加入系统默认停用词  
            Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
            while (itor.hasNext()) {
                cas.add(itor.next());
            }

            // 中英文混合分词器(其他几个分词器对中文的分析都不行)  
            SmartChineseAnalyzer sca = new SmartChineseAnalyzer(cas);

            TokenStream ts = sca.tokenStream("field", text);
            CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

            ts.reset();
            while (ts.incrementToken()) {
                System.out.println(ch.toString());
            }
            ts.end();
            ts.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}  