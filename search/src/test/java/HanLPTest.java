import com.hframework.datacenter.myna.nlp.HanLPUtils;

import java.io.IOException;

public class HanLPTest {

    public static void main(String[] args) throws IOException {
        print("我来自于武汉大学,我最喜欢校园里的樱花");
        HanLPUtils.addStopWord("请问");
        print("请问随鑫约收益计算方式, 请问随鑫约收益怎么计算， 您好随鑫约的收益应该如何计算，");
        print("21号，去北京");
        print("下周三从北京到上海");
        print("你帮我订一张周五南充到泰安的票");
        print("吉林延边");
        print("我要去天上");
        print("平谷");
        print("你要吃桃子么？");
        print("你想要吃桃子么？");
        print("你吃桃子么？");
        print("你要桃子么？");
        print("你想桃子么？");
        print("你要去哪里？");
        print("你想去哪里？");
        print("你想要去哪里？");
        print("你到底想要去哪里？");
        print("你去哪里？");
        print("你想看电影吗？");
        print("你想要去爬山不？");
        print("你想去爬山不？");
        print("你去爬山不？");
        print("你姓张吗？");


        print("找个凳子坐下吧！");
        print("让他走吧！");
        print("可得抓紧时间啊！");
        print("大家快过来呀！");
        print("你可得好好干哪！");

        print("各族人民大团结万岁！");
        print("那该有多好啊！");

        print("我要回家");
        print("我要去北京");
        print("我想想想想想想想想吃桃子");
        print("我来自上海");
        print("我不去看电影");

        print("来一首歌");
        print("歪了");
        print("你要吃道口烧鸡么？");
        print("四川省南部县大南街谢家巷子18号");
        print("香港理工大学");
        print("如何绑定手机？");


    }
    public static void print(String text) throws IOException {
        System.out.println("HanLPUtils.load(text).enableNormalization().enableSpeechTagging().removeStopWord().segment()\n==> " +
                HanLPUtils.load(text).enableNormalization().enableSpeechTagging().removeStopWord().segment());
        System.out.println("[[[[[" + text + "]]]]]]");
        System.out.println("最短路径:\n==> " +
                HanLPUtils.load(text).enableNormalization()
                        .enableSpeechTagging()
//                        .enableSynonymTransfer()
//                        .removeStopWord()
                        .seg2sentence()
        );
        System.out.println("n路最短路径:\n==> " +
                HanLPUtils.load(text).useNShortAlgorithm().enableNormalization()
                        .enableSpeechTagging()
//                        .enableSynonymTransfer()
//                        .removeStopWord()
                        .seg2sentence()
        );
        System.out.println("CRF:\n==> " +
                HanLPUtils.load(text).useCRFAlgorithm().enableNormalization()
                        .enableSpeechTagging()
//                        .enableSynonymTransfer()
//                        .removeStopWord()
                        .seg2sentence()
        );
        System.out.println("感知机:\n==> " +
                HanLPUtils.load(text).usePerceptronAlgorithm().enableNormalization()
                        .enableSpeechTagging()
//                        .enableSynonymTransfer()
//                        .removeStopWord()
                        .seg2sentence()
        );
    }


}
