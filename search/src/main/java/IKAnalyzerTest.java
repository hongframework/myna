
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.*;

/**
 * 1.二者分词效果，相比其他已经不错，都值得肯定；
   2.smartcn为Lucene4.6版本自带（之前版本也有），中文分词不错，英文分词有问题，Lucene分词后变成了Luncn；
   3.IKAnalyzer分词后的碎片太多，可以和人工分析效果做对比；
   4.从自定义词库的角度考虑，因为smartcn在Lucene4.6中的版本，目前不支持自定义词库，成为致命缺陷，只能放弃。
 */
public class IKAnalyzerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Analyzer ik  = new IKAnalyzer();
        try{
            String text = "lucene分析器使用分词器和过滤器构成一个“管道”，文本在流经这个管道后成为可以进入索引的最小单位，因此，一个标准的分析器有两个部分组成，一个是分词器tokenizer,它用于将文本按照规则切分为一个个可以进入索引的最小单位。另外一个是TokenFilter，它主要作用是对切出来的词进行进一步的处理（如去掉敏感词、英文大小写转换、单复数处理）等。lucene中的Tokenstram方法首先创建一个tokenizer对象处理Reader对象中的流式文本，然后利用TokenFilter对输出流进行过滤处理";
            TokenStream ts = ik.tokenStream("field", text);

            CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);

            ts.reset();
            while (ts.incrementToken()) {
                //System.out.println(ch.toString());
                System.out.print(ch.toString() + "\\");
            }
            ts.end();
            ts.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}