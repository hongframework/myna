# myna -八哥

	* 基于贝叶斯网络客服聊天系统，支持将一个概率分布作为输入语句，如：“我要去北京 (0.69);我要去上海 (0.39)”
	* 支持每一轮QA根据预先设定概率阈值将输入识别为三种情况：认可并下一步、不认可并重问、对答案进行确认。由动作【AskRepeat、Confirm】控制，比如分别配置6,9表明概率6成，9成为阈值

	* 支持各答案选项配置先验概率，针对于输入概率分布进行概率加成，比如北京先验概率 > 天津先验概率
	* 支持不识别重问场景对输入进行记忆，对下一次回答进行概率加成，比如配置上轮识别概率为3，那么当下轮输入概率为a，最终加成后的概率为实际大于a，由动作【PrioriProb】进行配置
	* 支持机器不识别次数阈值设定，大于重试阈值时退出当前QA问答或者退出会话，分别由动作【 BreakQA、 BreakTask】进行配置
	* 支持等待客户回答超时设定，大于超时阈值时进行客户询问，累计阈值退出会话，分别由动作【 WaitTimeOut、 BreakQA】进行配置
	* 支持每一轮QA前置与后置处理，比如订单提交前“库存查询”作为前置校验，“订单提交”作为后置处理，分别有动作【 PreHandle、 PostHandle】进行配置
	* 支持所有的配置热加载，监控模块针对于修改每三秒加载内存一次，新起会话即可生效

	* 支持聊天内容自动快照备份，备份聊天内容存储服务器指定目录
	* 支持多条话术轮询/随机选择
	* 支持通过反向推理识别答非所问、一问多答、返回作答场景，通过添加辅助识别语料实现
	* 支持对聊天记录实时评分，对评分不满100分的聊天记录支持在线查阅，并支持在线语料、字典、点对点应答的快捷配置
	* 增加点对点应答语料，比如A：我要回家，Q：你家在哪里
	* 增加同义词词典，比如A:北平，=> 北京

