CREATE TABLE `red_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `red_packet` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '红包全局唯一标识串',
  `total` int(11) NOT NULL COMMENT '人数',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总金额（单位为分）',
  `is_active` tinyint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='发红包记录';



CREATE TABLE `red_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL COMMENT '红包记录id',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '金额（单位为分）',
  `is_active` tinyint(4) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8 COMMENT='红包明细金额';


CREATE TABLE `red_rob_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户账号',
  `red_packet` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '红包标识串',
  `amount` decimal(8,2) DEFAULT NULL COMMENT '红包金额（单位为分）',
  `rob_time` datetime DEFAULT NULL COMMENT '时间',
  `is_active` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='抢红包记录';