package com.hframework.datacenter.myna;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.monitor.ConfigMapMonitor;
import com.hframework.common.monitor.ConfigMultiMonitor;
import com.hframework.common.monitor.Node;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.file.FileUtils;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.myna.config.domain.model.*;
import com.hframework.myna.config.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class ConfigurationWatcher {
    private static Logger logger = LoggerFactory.getLogger(ConfigurationWatcher.class);

    private static ConfigurationWatcher watcher;

    private ConfigMultiMonitor monitor;

    private ITaskSV taskSV = ServiceFactory.getService(ITaskSV.class);
    private IQnaSV qnaSV = ServiceFactory.getService(IQnaSV.class);
    private IQnaActionsSV qnaActionsSV = ServiceFactory.getService(IQnaActionsSV.class);

    private IModelSV modelSV = ServiceFactory.getService(IModelSV.class);
    private IActionSV actionSV = ServiceFactory.getService(IActionSV.class);
    private IModelActionsSV modelActionsSV = ServiceFactory.getService(IModelActionsSV.class);

    private IUtteranceSV utteranceSV = ServiceFactory.getService(IUtteranceSV.class);

    private IOptionsSV optionsSV = ServiceFactory.getService(IOptionsSV.class);
    private IOptionSV optionSV = ServiceFactory.getService(IOptionSV.class);
    private IThesaurusSV thesaurusSV = ServiceFactory.getService(IThesaurusSV.class);
    private IWordsSV wordsSV = ServiceFactory.getService(IWordsSV.class);
    private ICorpusSV corpusSV = ServiceFactory.getService(ICorpusSV.class);
    private ITaskActionsSV taskActionsSV = ServiceFactory.getService(ITaskActionsSV.class);

    private IP2pReplySV ip2pReplySV = ServiceFactory.getService(IP2pReplySV.class);
    private IP2pCorpusSV p2pCorpusSV = ServiceFactory.getService(IP2pCorpusSV.class);

    private ISemanticParserSV semanticParserSV = ServiceFactory.getService(ISemanticParserSV.class);

    private Map<String, ConfigurationDescriptor> configurationDescriptors = new HashMap();

    private ConfigurationWatcher(){
        monitor = new ConfigMultiMonitor(5) {
            @Override
            public void onEvent(List<Node> nodeLists) {
                Set<Node> taskNodes = new HashSet<>();
                Set<Node> p2pCorpusTaskNodes = new HashSet<>();
                for (Node node : nodeLists) {
                    if(node.getObject() instanceof P2pReply) {
                        p2pCorpusTaskNodes.add(node.getInput(Task.class));
                        continue;
                    }else if(node.getObject() instanceof P2pCorpus) {
                        p2pCorpusTaskNodes.add(node.getInput(P2pReply.class).getInput(Task.class));
                        continue;
                    }
                    Map<Class, Map<String, Node>> descendants = this.getDescendants(node);
                    if(descendants.containsKey(Task.class)) {
                        taskNodes.addAll(descendants.get(Task.class).values());
                    }

                    Map<Class, Map<String, Node>> ancestors = this.getAncestors(node);
                    if(ancestors.containsKey(Task.class)) {
                        taskNodes.addAll(ancestors.get(Task.class).values());
                    }
                }

                for (Node taskNode : p2pCorpusTaskNodes) {
//                    String basePath = "e:\\lucene\\data\\";
                    JSONObject jsonObject = ConfigurationXmlBuilder.buildFAQJson(taskNode);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                        writeFile(Robot.faqDataPath, ((Task)taskNode.getObject()).getCode() + ".json", content);
                        logger.info("{}/{} => {}, {}", Robot.faqDataPath,((Task)taskNode.getObject()).getCode() + ".json", content);
                        FAQRepository.buildIndex( Robot.faqDataPath,  Robot.faqIndexPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                for (Node taskNode : taskNodes) {
                    ConfigurationDescriptor descriptor = ConfigurationXmlBuilder.buildDescriptor(taskNode);
                    configurationDescriptors.put(((Task)taskNode.getObject()).getCode(), descriptor);
                    String[] xmls = ConfigurationXmlBuilder.buildXml(descriptor);
//                    String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/opendial/domains/";
                    String basePath = Robot.domainsPath;
                    writeFile(basePath, ((Task)taskNode.getObject()).getCode() + ".xml", xmls[0]);
                    writeFile(basePath, ((Task)taskNode.getObject()).getCode() + "_pp.xml", xmls[1]);
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(descriptor);
                        writeFile(basePath, ((Task)taskNode.getObject()).getCode() + ".json", content);
                        logger.info("{}/{} => {}, {}", basePath,((Task)taskNode.getObject()).getCode() + ".json", content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info("{} => {}, {}", basePath, xmls[0], xmls[1]);
                    System.out.println("the change task is : " + taskNode);
                }
            }
        };
        monitor.addSubMonitor(new ConfigMapMonitor<Task>(5) {
            @Override
            protected String keyProperty(Task t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Task> fetch() throws Exception {
                return taskSV.getTaskAll();
            }
        }.addFetcher(Model.class, new Fetcher<Task, String>() {
            @Override
            public String fetch(Task task) {
                return String.valueOf(task.getModelId());
            }
        }));

        monitor.addSubMonitor(new ConfigMapMonitor<Qna>(5) {
            @Override
            protected String keyProperty(Qna t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Qna> fetch() throws Exception {
                return qnaSV.getQnaAll();
            }
        }.addFetcher(Options.class, new Fetcher<Qna, String>() {
            @Override
            public String fetch(Qna qna) {
                return String.valueOf(qna.getOptionsId());
            }
        }).addFetcher(SemanticParser.class, new Fetcher<Qna, String>() {
            @Override
            public String fetch(Qna qna) {
                return qna.getSemanticParsers();
            }
        }).addFetcher(Task.class, new Fetcher<Qna, String>() {
            @Override
            public String fetch(Qna qna) {
                return String.valueOf(qna.getTaskId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<QnaActions>(5) {
            @Override
            protected String keyProperty(QnaActions t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<QnaActions> fetch() throws Exception {
                return qnaActionsSV.getQnaActionsAll();
            }
        }.addFetcher(Qna.class, new Fetcher<QnaActions, String>() {
            @Override
            public String fetch(QnaActions qnaActions) {
                return String.valueOf(qnaActions.getAnswerId());
            }
        }).addFetcher(Action.class, new Fetcher<QnaActions, String>() {
            @Override
            public String fetch(QnaActions qnaActions) {
                return String.valueOf(qnaActions.getActionId());
            }
        }).addFetcher(Utterance.class, new Fetcher<QnaActions, String>() {
            @Override
            public String fetch(QnaActions qnaActions) {
                return String.valueOf(qnaActions.getUtterances());
            }
        }));

        monitor.addSubMonitor(new ConfigMapMonitor<TaskActions>(5) {
            @Override
            protected String keyProperty(TaskActions t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<TaskActions> fetch() throws Exception {
                return taskActionsSV.getTaskActionsAll();
            }
        }.addFetcher(Task.class, new Fetcher<TaskActions, String>() {
            @Override
            public String fetch(TaskActions task) {
                return String.valueOf(task.getTaskId());
            }
        }).addFetcher(Action.class, new Fetcher<TaskActions, String>() {
            @Override
            public String fetch(TaskActions taskActions) {
                return String.valueOf(taskActions.getActionId());
            }
        }).addFetcher(Utterance.class, new Fetcher<TaskActions, String>() {
            @Override
            public String fetch(TaskActions taskActions) {
                return String.valueOf(taskActions.getUtterances());
            }
        }));


        monitor.addSubMonitor(new ConfigMapMonitor<Model>(5) {
            @Override
            protected String keyProperty(Model t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Model> fetch() throws Exception {
                return modelSV.getModelAll();
            }
        });
        monitor.addSubMonitor(new ConfigMapMonitor<Action>(5) {
            @Override
            protected String keyProperty(Action t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Action> fetch() throws Exception {
                return actionSV.getActionAll();
            }
        }.addFetcher(Utterance.class, new Fetcher<Action, String>() {
            @Override
            public String fetch(Action action) {
                return String.valueOf(action.getDefaultUtteranceId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<ModelActions>(5) {
            @Override
            protected String keyProperty(ModelActions t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<ModelActions> fetch() throws Exception {
                return modelActionsSV.getModelActionsAll();
            }
        }.addFetcher(Model.class, new Fetcher<ModelActions, String>() {
            @Override
            public String fetch(ModelActions modelActions) {
                return String.valueOf(modelActions.getModelId());
            }
        }).addFetcher(Action.class, new Fetcher<ModelActions, String>() {
            @Override
            public String fetch(ModelActions modelActions) {
                return String.valueOf(modelActions.getActionId());
            }
        }).addFetcher(Utterance.class, new Fetcher<ModelActions, String>() {
            @Override
            public String fetch(ModelActions modelActions) {
                return String.valueOf(modelActions.getUtterances());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<Utterance>(5) {
            @Override
            protected String keyProperty(Utterance t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Utterance> fetch() throws Exception {
                return utteranceSV.getUtteranceAll();
            }
        }.addFetcher(Action.class, new Fetcher<Utterance, String>() {
            @Override
            public String fetch(Utterance utterance) {
                return String.valueOf(utterance.getActionId());
            }
        }).addFetcher(Task.class, new Fetcher<Utterance, String>() {
            @Override
            public String fetch(Utterance utterance) {
                return String.valueOf(utterance.getTaskId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<Corpus>(5) {
            @Override
            protected String keyProperty(Corpus t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Corpus> fetch() throws Exception {
                List<Corpus> corpusAll = corpusSV.getCorpusAll();
                Collections.sort(corpusAll, new Comparator<Corpus>() {
                    @Override
                    public int compare(Corpus o1, Corpus o2) {
                        return o1.getSlotNumbers().compareTo(o2.getSlotNumbers()) *1000 + o1.getContent().length() - o2.getContent().length();
                    }
                });
                return corpusAll;
            }
        }.addFetcher(Task.class, new Fetcher<Corpus, String>() {
            @Override
            public String fetch(Corpus corpus) {
                return String.valueOf(corpus.getTaskId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<P2pReply>(5) {
            @Override
            protected String keyProperty(P2pReply t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<P2pReply> fetch() throws Exception {
                List<P2pReply> corpusAll = ip2pReplySV.getP2pReplyAll();
                return corpusAll;
            }
        }.addFetcher(Task.class, new Fetcher<P2pReply, String>() {
            @Override
            public String fetch(P2pReply corpus) {
                return String.valueOf(corpus.getTaskId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<P2pCorpus>(5) {
            @Override
            protected String keyProperty(P2pCorpus t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<P2pCorpus> fetch() throws Exception {
                List<P2pCorpus> corpusAll = p2pCorpusSV.getP2pCorpusAll();
                return corpusAll;
            }
        }.addFetcher(P2pReply.class, new Fetcher<P2pCorpus, String>() {
            @Override
            public String fetch(P2pCorpus corpus) {
                return String.valueOf(corpus.getP2pRepayId());
            }
        }));
        monitor.addSubMonitor(new ConfigMapMonitor<Options>(5) {
            @Override
            protected String keyProperty(Options t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Options> fetch() throws Exception {
                return optionsSV.getOptionsAll();
            }
        });
        monitor.addSubMonitor(new ConfigMapMonitor<Option>(5) {
            @Override
            protected String keyProperty(Option t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Option> fetch() throws Exception {
                return optionSV.getOptionAll();
            }
        }.addFetcher(Options.class, new Fetcher<Option, String>() {
            @Override
            public String fetch(Option option) {
                return String.valueOf(option.getOptionsId());
            }
        }));

        monitor.addSubMonitor(new ConfigMapMonitor<Thesaurus>(5) {
            @Override
            protected String keyProperty(Thesaurus t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Thesaurus> fetch() throws Exception {
                return thesaurusSV.getThesaurusAll();
            }
        });

        monitor.addSubMonitor(new ConfigMapMonitor<Words>(5) {
            @Override
            protected String keyProperty(Words t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<Words> fetch() throws Exception {
                return wordsSV.getWordsAll();
            }
        }.addFetcher(Thesaurus.class, new Fetcher<Words, String>() {
            @Override
            public String fetch(Words words) {
                return String.valueOf(words.getThesaurusId());
            }
        }));

        monitor.addSubMonitor(new ConfigMapMonitor<SemanticParser>(5) {
            @Override
            protected String keyProperty(SemanticParser t) {
                return String.valueOf(t.getId());
            }

            @Override
            public List<SemanticParser> fetch() throws Exception {
                return semanticParserSV.getSemanticParserAll();
            }
        });
    }

    private void writeFile(String basePath, String fileName, String xml) {
        if(!basePath.endsWith("/") && !basePath.endsWith("\\")) basePath += "/";
        String filePath = basePath + fileName;
        FileUtils.newFile(filePath);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)),"UTF-8"));
            bw.write(xml);
            bw.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void start() throws Exception {
        monitor.start();
    }

    public static ConfigurationWatcher getInstance(){
        if(watcher == null) {
            synchronized (ConfigurationWatcher.class) {
                if(watcher == null) {
                    watcher = new ConfigurationWatcher();
                }
            }
        }
        return watcher;
    }

    public ConfigMultiMonitor getMonitor() {
        return monitor;
    }

    public Map<String, ConfigurationDescriptor> getConfigurationDescriptors() {
        return configurationDescriptors;
    }
}
