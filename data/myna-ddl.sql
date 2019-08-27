
/*Table structure for table `action` */

CREATE TABLE `action` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '行为id,ID',
  `code` varchar(64) NOT NULL COMMENT '行为编码,编码',
  `name` varchar(128) NOT NULL COMMENT '行为名称,名称',
  `description` varchar(128) DEFAULT NULL COMMENT '行为描述,描述',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '行为类型,类型',
  `action_scope` tinyint(4) NOT NULL COMMENT '作用域',
  `trigger_result` varchar(64) DEFAULT NULL COMMENT '触发结果',
  `default_utterance_id` bigint(12) DEFAULT NULL COMMENT '话术id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `trigger_case` tinyint(4) NOT NULL COMMENT '触发条件',
  `trigger_default_value` varchar(64) NOT NULL COMMENT '触发默认值,触发值',
  `trigger_value_type` tinyint(4) NOT NULL COMMENT '触发值类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='行为';

/*Table structure for table `corpus` */

CREATE TABLE `corpus` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '语料id,ID',
  `content` varchar(512) NOT NULL COMMENT '语料内容',
  `slot_numbers` tinyint(4) NOT NULL COMMENT '卡槽数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `task_id` bigint(12) NOT NULL COMMENT '任务id,ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='语料库';

/*Table structure for table `dictionary` */

CREATE TABLE `dictionary` (
  `dictionary_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dictionary_name` varchar(32) DEFAULT NULL COMMENT '字典名称',
  `dictionary_code` varchar(64) DEFAULT NULL COMMENT '字典编码',
  `dictionary_desc` varchar(128) DEFAULT NULL COMMENT '字典描述',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COMMENT='字典';

/*Table structure for table `dictionary_item` */

CREATE TABLE `dictionary_item` (
  `dictionary_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  `value` varchar(32) DEFAULT NULL COMMENT '字典项值',
  `text` varchar(32) DEFAULT NULL COMMENT '字典项文本',
  `desc` varchar(128) DEFAULT NULL COMMENT '字典项描述',
  `is_default` int(2) DEFAULT NULL COMMENT '是否默认',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `dictionary_id` bigint(20) DEFAULT NULL COMMENT '字典ID',
  `dictionary_code` varchar(32) DEFAULT NULL,
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`dictionary_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='字典项';

/*Table structure for table `menu` */

CREATE TABLE `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `menu_name` varchar(128) DEFAULT NULL COMMENT '菜单名称',
  `menu_desc` varchar(128) DEFAULT NULL COMMENT '菜单描述',
  `menu_level` int(2) DEFAULT NULL COMMENT '菜单级别',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `url` varchar(128) NOT NULL COMMENT '地址',
  `parent_menu_id` bigint(20) NOT NULL COMMENT '父级菜单ID',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='菜单';

/*Table structure for table `model` */

CREATE TABLE `model` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '工作模式id,ID',
  `name` varchar(128) NOT NULL COMMENT '工作模式名称,名称',
  `utterance_select_method` smallint(4) NOT NULL COMMENT '话术选择方式',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='工作模式';

/*Table structure for table `model_actions` */

CREATE TABLE `model_actions` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '模式关联行为id,ID',
  `model_id` bigint(12) NOT NULL COMMENT '工作模式id,ID',
  `action_id` bigint(12) NOT NULL COMMENT '行为id,ID',
  `action_trigger_value` varchar(64) DEFAULT NULL COMMENT '行为触发值,',
  `utterances` varchar(12) NOT NULL COMMENT '话术id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='模式关联行为';

/*Table structure for table `option` */

CREATE TABLE `option` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '选项id,ID',
  `name` varchar(1024) NOT NULL COMMENT '选项名称,名称',
  `utility` decimal(10,0) DEFAULT NULL COMMENT '效用,选项识别的权重，用于输入不确定性猜测倾向',
  `options_id` bigint(12) NOT NULL COMMENT '选项集id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='选项';

/*Table structure for table `options` */

CREATE TABLE `options` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '选项集id,ID',
  `code` varchar(64) NOT NULL COMMENT '选项集编码,编码',
  `name` varchar(128) NOT NULL COMMENT '选项集名称,名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='选项集';

/*Table structure for table `organize` */

CREATE TABLE `organize` (
  `organize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `organize_code` varchar(64) NOT NULL COMMENT '组织编码',
  `organize_name` varchar(128) NOT NULL COMMENT '组织名称',
  `organize_type` tinyint(4) DEFAULT NULL COMMENT '组织类型',
  `organize_level` tinyint(4) DEFAULT NULL COMMENT '组织级别',
  `parent_organize_id` bigint(12) DEFAULT NULL COMMENT '上级组织id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`organize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='组织';

/*Table structure for table `p2p_corpus` */

CREATE TABLE `p2p_corpus` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '点对点语料id,ID',
  `matching_type` tinyint(4) NOT NULL COMMENT '匹配类型',
  `corpuss` varchar(256) NOT NULL COMMENT '语料内容',
  `p2p_repay_id` bigint(12) NOT NULL COMMENT '点对点应答id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='点对点语料';

/*Table structure for table `p2p_reply` */

CREATE TABLE `p2p_reply` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '点对点应答id,ID',
  `answer` varchar(512) NOT NULL COMMENT '答案',
  `title` varchar(128) NOT NULL COMMENT '标题',
  `type` tinyint(4) DEFAULT NULL COMMENT '点对点应答类型,类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `task_id` bigint(12) NOT NULL COMMENT '任务id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='点对点应答';

/*Table structure for table `qna` */

CREATE TABLE `qna` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '问答id,ID',
  `description` varchar(128) NOT NULL COMMENT '问题描述,描述',
  `keyword` varchar(256) DEFAULT NULL COMMENT '关键字',
  `answer_type` tinyint(4) DEFAULT NULL COMMENT '答案类型,类型',
  `options_id` bigint(12) DEFAULT NULL COMMENT '答案选项集,ID',
  `semantic_parsers` varchar(12) DEFAULT NULL COMMENT '语义解析器id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `task_id` bigint(12) NOT NULL COMMENT '任务id,ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='问答';

/*Table structure for table `qna_actions` */

CREATE TABLE `qna_actions` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '问答行为id,ID',
  `answer_id` bigint(12) NOT NULL COMMENT '问答id,ID',
  `action_id` bigint(12) NOT NULL COMMENT '行为id,ID',
  `action_trigger_value` varchar(128) DEFAULT NULL COMMENT '行为触发值',
  `utterances` varchar(12) DEFAULT NULL COMMENT '话术id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='问答行为';

/*Table structure for table `robot` */

CREATE TABLE `robot` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '机器人id,ID',
  `code` varchar(64) NOT NULL COMMENT '机器人编码,编码',
  `name` varchar(128) NOT NULL COMMENT '机器人名称,名称',
  `logo` varchar(256) DEFAULT NULL COMMENT '机器人logo',
  `type` tinyint(4) DEFAULT NULL COMMENT '机器人类型,类型',
  `description` varchar(128) DEFAULT NULL COMMENT '机器人描述,描述',
  `services` varchar(256) DEFAULT NULL COMMENT '服务内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='机器人';

/*Table structure for table `role` */

CREATE TABLE `role` (
  `role_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `role_name` varchar(128) NOT NULL COMMENT '角色名称',
  `role_type` tinyint(4) DEFAULT NULL COMMENT '角色类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Table structure for table `role_authorize` */

CREATE TABLE `role_authorize` (
  `role_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色授权id',
  `role_authorize_type` tinyint(4) DEFAULT NULL COMMENT '角色授权类型',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色授权';

/*Table structure for table `semantic_parser` */

CREATE TABLE `semantic_parser` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '语义解析器id,ID',
  `name` varchar(128) NOT NULL COMMENT '语义解析器名称,名称',
  `parse_type` tinyint(4) NOT NULL COMMENT '解析类型',
  `parse_method` tinyint(4) NOT NULL COMMENT '解析方式',
  `parse_addr` varchar(64) NOT NULL COMMENT '解析地址',
  `is_default` tinyint(2) NOT NULL COMMENT '是否为默认解析器,',
  `description` varchar(128) DEFAULT NULL COMMENT '语义解析器描述,描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='语义解析器';

/*Table structure for table `syntax_def` */

CREATE TABLE `syntax_def` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '句法定义id,ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '句类',
  `expression` varchar(128) NOT NULL COMMENT '表达式',
  `name` varchar(256) NOT NULL COMMENT '标识',
  `keywords` varchar(256) NOT NULL COMMENT '关键词',
  `example` varchar(128) DEFAULT NULL COMMENT '举例',
  `limits` varchar(256) NOT NULL COMMENT '约束',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='句法定义';

/*Table structure for table `task` */

CREATE TABLE `task` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务id,ID',
  `code` varchar(64) NOT NULL COMMENT '任务编码,编码',
  `name` varchar(128) NOT NULL COMMENT '任务名称,名称',
  `model_id` bigint(12) NOT NULL COMMENT '工作模式id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `close_utterance` varchar(256) NOT NULL COMMENT '离开话术',
  `final_utterance` varchar(256) NOT NULL COMMENT '重置话术',
  `welcome_utterance` varchar(256) NOT NULL COMMENT '欢迎话术',
  `faq_pattern` tinyint(4) NOT NULL COMMENT 'FAQ切换模式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='任务';

/*Table structure for table `task_actions` */

CREATE TABLE `task_actions` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务行为id,ID',
  `action_trigger_value` varchar(128) DEFAULT NULL COMMENT '行为触发值',
  `task_id` bigint(12) NOT NULL COMMENT '任务id,ID',
  `action_id` bigint(12) NOT NULL COMMENT '行为id,ID',
  `utterances` varchar(12) NOT NULL COMMENT '话术id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='任务行为';

/*Table structure for table `task_version` */

CREATE TABLE `task_version` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '任务版本信息id,ID',
  `task_id` bigint(12) NOT NULL COMMENT '任务id,ID',
  `descriptor` text NOT NULL COMMENT '内容描述符',
  `description` varchar(128) DEFAULT NULL COMMENT '任务版本信息描述,描述',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务版本信息';

/*Table structure for table `thesaurus` */

CREATE TABLE `thesaurus` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '词库id,ID',
  `name` varchar(128) NOT NULL COMMENT '词库名称,名称',
  `description` varchar(128) DEFAULT NULL COMMENT '词库描述,描述',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '词库类型,类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='词库';

/*Table structure for table `user` */

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `account` varchar(64) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `gender` int(2) DEFAULT NULL COMMENT '性别',
  `mobile` varchar(6) DEFAULT NULL COMMENT '手机号',
  `email` int(2) DEFAULT NULL COMMENT '邮箱',
  `addr` int(2) DEFAULT NULL COMMENT '地址',
  `avatar` varchar(512) DEFAULT NULL COMMENT '头像',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `organize_id` bigint(20) NOT NULL COMMENT '组织ID',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

/*Table structure for table `user_authorize` */

CREATE TABLE `user_authorize` (
  `user_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户授权id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `organize_id` bigint(12) NOT NULL COMMENT '组织id',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_authorize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户授权';

/*Table structure for table `utterance` */

CREATE TABLE `utterance` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '话术id,ID',
  `content` varchar(256) NOT NULL COMMENT '话术内容',
  `resource_id` varchar(256) DEFAULT NULL COMMENT '资源ID',
  `action_id` bigint(20) DEFAULT NULL COMMENT '行为ID',
  `task_id` bigint(12) DEFAULT NULL COMMENT '任务id,ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='话术';

/*Table structure for table `words` */

CREATE TABLE `words` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '词汇id,ID',
  `name` varchar(128) NOT NULL COMMENT '词汇名称,名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `thesaurus_id` bigint(12) NOT NULL COMMENT '词库id,ID',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='词汇';
