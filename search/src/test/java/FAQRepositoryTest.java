import com.hframework.datacenter.myna.FAQRepository;

import java.io.IOException;

public class FAQRepositoryTest {


    public static void main(String[] arg) throws IOException {
        FAQRepository.buildIndex( "e:\\lucene\\data",  "e:\\lucene\\index");
//        System.out.println(com.hframework.datacenter.myna.nlp.FAQRepository.search("随鑫约收益"));
//        System.out.println(com.hframework.datacenter.myna.nlp.FAQRepository.search("专享"));
//        System.out.println(com.hframework.datacenter.myna.nlp.FAQRepository.search("P2P"));
        System.out.println("先生，你怎么称呼" + " => " + FAQRepository.search("先生，你怎么称呼"));
        System.out.println("你们公司目前在哪里" + " => " + FAQRepository.search("你们目前在哪里"));
        System.out.println("你们是做啥子的" + " => " + FAQRepository.search("你们是做啥子的"));
        System.out.println("我年龄大" + " => " + FAQRepository.search("我年龄大"));
        System.out.println("啥子公司" + " => " + FAQRepository.search("啥子公司"));


    }
}
