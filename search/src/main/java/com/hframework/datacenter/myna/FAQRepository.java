package com.hframework.datacenter.myna;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.lucene.HanLPAnalyzer;
import com.hframework.common.util.file.FileUtils;
import org.apache.commons.lang.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FAQRepository {
    private static final Logger logger = LoggerFactory.getLogger(FAQRepository.class);

    private static FAQRepository repository;
    private static ThreadLocal<FAQRepository> curRepoTL = new ThreadLocal<>();

    private static final String JSON_FILE = "json";

    private static final List<String> DATA_FILE_SUFFIXES;

    private static Analyzer analyzer  = new HanLPAnalyzer();
//    private static Analyzer analyzer  = new SmartChineseAnalyzer();

    private Directory directory = null;

    private String dataDir;
    private String indexDir;

    private static String content="";
    private static String INDEX_DIR = "e:\\lucene\\index";
    private static String DATA_DIR = "e:\\lucene\\data";
//    private static Analyzer analyzer  = new StandardAnalyzer();

    static {
        DATA_FILE_SUFFIXES = new ArrayList<String>(){{
            add(JSON_FILE);
        }};
    }



    private FAQRepository(){}

    /**
     * 创建索引管理器
     * @return 返回索引管理器对象
     */
    public static FAQRepository getNewFAQRepository(){
        return new FAQRepository();
    }

    /**
     * 创建索引管理器
     * @return 返回索引管理器对象
     */
    public static FAQRepository getDefFAQRepository(){
        if(repository == null){
            synchronized (FAQRepository.class) {
                if(repository == null) {
                    repository = getNewFAQRepository();
                }
            }
        }
        return repository;
    }



    public static FAQRepository getCurFAQRepository(){
        if(curRepoTL.get() != null) {
            return curRepoTL.get();
        }
        return getDefFAQRepository();
    }
    public static FAQRepository setCurFAQRepository(FAQRepository repository) {
        curRepoTL.set(repository);
        return repository;
    };

    public static void resetCurFAQRepository(){
        if(curRepoTL.get() != null) {
            curRepoTL.set(null);
        }
    }

    public static void buildIndex(String dataDir, String indexDir) throws IOException {
        logger.info("build index : {}, {}", dataDir, indexDir);
        long startMillis = System.currentTimeMillis();
        cleanDir(indexDir);//清空索引目录
        List<File> dataFiles = getDataFiles(dataDir);
        List<Document> documents = new ArrayList<>();
        for (File dataFile : dataFiles) {
            String fileSuffix = dataFile.getName().substring(dataFile.getName().lastIndexOf(".") + 1);
            if (JSON_FILE.equals(fileSuffix)) {
                buildDocumentsByJsonFile(dataFile, documents);
            }
        }

        Directory directory = FSDirectory.open(Paths.get(indexDir));//本地文件存储
        //内存存储
        //Directory directory = new RAMDirectory();

        getCurFAQRepository().setDataDir(dataDir);
        getCurFAQRepository().setIndexDir(indexDir);
        getCurFAQRepository().setDirectory(directory);

        logger.info("build index load: {}, {}, {}", dataDir, indexDir,
                (System.currentTimeMillis() - startMillis)  + "ms");
        writeIndex(documents, directory);
        logger.info("build index success: {}, {}, {}", dataDir, indexDir,
                (System.currentTimeMillis() - startMillis)  + "ms");
    }

    private static void writeIndex(List<Document> documents, Directory directory) throws IOException {
        //创建IndexWriter，进行索引文件的写入
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        try {
            indexWriter.addDocuments(documents);
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (indexWriter != null) {
                indexWriter.close();
            }
        }
    }

    private static void buildDocumentsByJsonFile(File dataFile, List<Document> documents) throws IOException {
        String content = FileUtils.readFile(dataFile.getAbsolutePath());
        JSONObject json = JSONObject.parseObject(content);
        JSONObject infoJson = json.getJSONObject("info");
        String robotCode = infoJson.getString("code");
        String globalMatching = infoJson.getString("matching");

        JSONArray faqs = json.getJSONArray("qnas");
        for (Object faq : faqs) {
            JSONObject jsonObject = (JSONObject) faq;
            String summary = jsonObject.getString("summary");
            String answer = jsonObject.getString("answer");
            JSONArray questions = jsonObject.getJSONArray("question");
            boolean isComplete = "complete".equals(jsonObject.containsKey("matching")? infoJson.getString("matching") : globalMatching);
            for (Object question : questions) {
                String questionTrim = String.valueOf(question).trim();
                //内容提取，进行索引的存储。
                Document document = new Document();
                document.add(new TextField("filepath", dataFile.getAbsolutePath(), Store.YES));
                document.add(new TextField("summary", summary, Store.YES));
                document.add(new TextField("question", questionTrim, Store.YES));
                if(isComplete) {
                    CustomDictionary.add(questionTrim);
                }
                documents.add(document);
            }
            Document document = new Document();
            document.add(new TextField("filepath", dataFile.getAbsolutePath(), Store.YES));
            document.add(new TextField("summary", summary, Store.YES));
            document.add(new TextField("answer", answer, Store.YES));
            document.add(new TextField("key", summary, Store.YES));
            documents.add(document);
        }
    }

    private static List<File> getDataFiles(String dataDir) {
        File dictionary = new File(dataDir);
        List<File> fileList = new ArrayList<>();
        findDataFile(dictionary, fileList);
        return fileList;
    }

    private static void findDataFile(File dictionary, List<File> fileList) {
        if(dictionary.exists() && dictionary.isDirectory()) {
            File[] files = dictionary.listFiles();

            for (File file : files) {
                if(file.isDirectory()) {
                    findDataFile(file, fileList);
                }else if(isDataFile(file)) {
                    fileList.add(file);
                }
            }
        }
    }

    private static boolean isDataFile(File file) {
        String fileSuffix = file.getName().substring(file.getName().lastIndexOf(".")+1);
        return DATA_FILE_SUFFIXES.contains(fileSuffix);
    }

    /**
     * 删除文件目录下的所有文件
     * @param dir 要删除的文件目录
     * @return 如果成功，返回true.
     */
    public static boolean cleanDir(String dir){
        File dictionary = new File(dir);
        if(!dictionary.exists()) {
            dictionary.mkdir();
        }

        File[] files = dictionary.listFiles();
        for(int i=0; i<files.length; i++){
            deleteDir(files[i]);
        }
        return true;
    }


    /**
     * 查找索引，返回符合条件的文件
     * @param text 查找的字符串
     * @return 符合条件的文件List
     */
    public static String search(String text){
        logger.info("search question : {}", text);
        if(StringUtils.isBlank(text)) return null;
        text = text.trim();
        long startMillis = System.currentTimeMillis();
        String answer = null;
        DirectoryReader ireader = null;
        try{
            ireader = DirectoryReader.open(getCurFAQRepository().getDirectory());
            //创建搜索器
            IndexSearcher isearcher = new IndexSearcher(ireader);

            Query query = new QueryParser("question", analyzer).parse(text);

            ScoreDoc[] hits = isearcher.search(query, 1).scoreDocs;
            if(hits.length == 0) {
                logger.debug("hit doc failed: {}", text);
                return null;
            }
            Document hitDoc = isearcher.doc(hits[0].doc);
            logger.info("hit doc : {}, {}, {}, {}", hitDoc.get("filepath"), hitDoc.get("summary"), hitDoc.get("question"), hits[0].score);

            query = new QueryParser("key", analyzer).parse(hitDoc.get("summary"));
            hits = isearcher.search(query, 1).scoreDocs;
            if(hits.length == 0) {
                logger.debug("hit doc failed: {}", text);
                return null;
            }
            hitDoc = isearcher.doc(hits[0].doc);
            logger.info("hit doc : {}, {}, {}, {}", hitDoc.get("filepath"), hitDoc.get("summary"), hitDoc.get("answer"), hits[0].score);
            answer = hitDoc.get("answer");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(ireader != null) {
                    ireader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("search question success: {}, {}", text, (System.currentTimeMillis() - startMillis)  + "ms");
        return answer;
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

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }

    public void setIndexDir(String indexDir) {
        this.indexDir = indexDir;
    }

    public Directory getDirectory() {
        return directory;
    }




}