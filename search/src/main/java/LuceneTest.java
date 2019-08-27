import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class LuceneTest {
    private static LuceneTest indexManager;
    private static String content="";
    private static String INDEX_DIR = "e:\\lucene\\index";
    private static String DATA_DIR = "e:\\lucene\\data";
//    private static Analyzer analyzer  = new StandardAnalyzer();
    private static Analyzer analyzer  = new SmartChineseAnalyzer();
    private static Directory directory = null;
    private static IndexWriter indexWriter = null;

    public static void main(String[] arg){
        File fileIndex = new File(INDEX_DIR);
        if(deleteDir(fileIndex)){
            fileIndex.mkdir();
        }else{
            fileIndex.mkdir();
        }
        createIndex(DATA_DIR);
//        searchIndex("jerry");
//        searchIndex("allowed friends drink Jerry");
        searchIndex("智多星收益");
    }

    /**
     * 创建当前文件目录的索引
     * @param path 当前文件目录
     * @return 是否成功
     */
    public static boolean createIndex(String path){

        Date date1 = new Date();
        List<File> fileList = getFileList(path);
        for (File file : fileList) {
            content = "";
            //获取文件后缀
            String type = file.getName().substring(file.getName().lastIndexOf(".")+1);
            if("txt".equalsIgnoreCase(type)){
                content += txt2String(file);
            }else if("doc".equalsIgnoreCase(type)){
                content += doc2String(file);
            }else if("xls".equalsIgnoreCase(type)){
                content += xls2String(file);
            }
            System.out.println("name :"+file.getName());
            System.out.println("path :"+file.getPath());
            System.out.println("content :"+content);
            System.out.println();

            try{
                //词法分析器Analyzer
//                analyzer = new StandardAnalyzer();
                //本地文件存储
                directory = FSDirectory.open(Paths.get(INDEX_DIR));
                //内存存储
                //Directory directory = new RAMDirectory();

                File indexFile = new File(INDEX_DIR);
                if (!indexFile.exists()) {
                    indexFile.mkdirs();
                }
                //创建IndexWriter，进行索引文件的写入
                IndexWriterConfig config = new IndexWriterConfig(analyzer);
                indexWriter = new IndexWriter(directory, config);

                //内容提取，进行索引的存储。
                Document document = new Document();
                document.add(new TextField("filename", file.getName(), Store.YES));
                document.add(new TextField("content", content, Store.YES));
                document.add(new TextField("path", file.getPath(), Store.YES));
                indexWriter.addDocument(document);
                indexWriter.commit();
                closeWriter();
            }catch(Exception e){
                e.getStackTrace();
            }

        }
        Date date2 = new Date();
        System.out.println("创建索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
        return true;
    }
    /**
     * 查找索引，返回符合条件的文件
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public static void searchIndex(String text){
        Date date1 = new Date();
        try{
            //打开存储位置
            directory = FSDirectory.open(Paths.get(INDEX_DIR));
//            analyzer = new StandardAnalyzer();
            DirectoryReader ireader = DirectoryReader.open(directory);
            //创建搜索器
            IndexSearcher isearcher = new IndexSearcher(ireader);

            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse(text);

            ScoreDoc[] hits = isearcher.search(query, 10000).scoreDocs;

            for (int i = 0; i < hits.length; i++) {

                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("____________________________");
                System.out.println("   file : " + hitDoc.get("filename"));
                System.out.println("content : " + hitDoc.get("content"));
                System.out.println("   path : " + hitDoc.get("path"));
                System.out.println("   score : " + hits[i].score);
                System.out.println("____________________________");
            }
            ireader.close();
            directory.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        Date date2 = new Date();
        System.out.println("查看索引-----耗时：" + (date2.getTime() - date1.getTime()) + "ms\n");
    }
    /**
     * 删除文件目录下的所有文件
     * @param file 要删除的文件目录
     * @return 如果成功，返回true.
     */
    public static boolean deleteDir(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                deleteDir(files[i]);
            }
        }
        file.delete();
        return true;
    }
    /**
     * 过滤目录下的文件
     * @param dirPath 想要获取文件的目录
     * @return 返回文件list
     */
    public static List<File> getFileList(String dirPath) {
        File[] files = new File(dirPath).listFiles();
        List<File> fileList = new ArrayList<File>();
        for (File file : files) {
            if (isTxtFile(file.getName())) {
                fileList.add(file);
            }
        }
        return fileList;
    }
    /**
     * 判断是否为目标文件，目前支持txt xls doc格式
     * @param fileName 文件名称
     * @return 如果是文件类型满足过滤条件，返回true；否则返回false
     */
    public static boolean isTxtFile(String fileName) {
        if (fileName.lastIndexOf(".txt") > 0) {
            return true;
        }else if (fileName.lastIndexOf(".xls") > 0) {
            return true;
        }else if (fileName.lastIndexOf(".doc") > 0) {
            return true;
        }
        return false;
    }
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result = result + "\n" +s;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 读取doc文件内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String doc2String(File file){
        String result = "";
        try{
            FileInputStream fis = new FileInputStream(file);
            //适合office2007以上
            OPCPackage pack = POIXMLDocument.openPackage(file.getPath());
            XWPFDocument  docx = new XWPFDocument(pack) ;
            List<XWPFParagraph> paras = docx.getParagraphs();
            for (XWPFParagraph para : paras) {
                result += para.getText().trim();
            }
            //只适合office2003
//            HWPFDocument doc = new HWPFDocument(fis);
//            Range rang = doc.getRange();
//            result += rang.text();
//            fis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 读取xls文件内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String xls2String(File file){
        String result = "";
        try{
            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            jxl.Workbook rwb = Workbook.getWorkbook(fis);
            Sheet[] sheet = rwb.getSheets();
            for (int i = 0; i < sheet.length; i++) {
                Sheet rs = rwb.getSheet(i);
                for (int j = 0; j < rs.getRows(); j++) {
                    Cell[] cells = rs.getRow(j);
                    for(int k=0;k<cells.length;k++)
                        sb.append(cells[k].getContents());
                }
            }
            fis.close();
            result += sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static void closeWriter() throws Exception {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }

    /**
     * 创建索引管理器
     * @return 返回索引管理器对象
     */
    public LuceneTest getManager(){
        if(indexManager == null){
            this.indexManager = new LuceneTest();
        }
        return indexManager;
    }

}