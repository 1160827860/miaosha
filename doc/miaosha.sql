SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `phone_number` varchar(15) not null COMMENT '用户手机号',
  `nickname` varchar(255) NOT NULL COMMENT '登录昵称',
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
  `salt` varchar(10) DEFAULT NULL COMMENT '加密的字符串（随机生成）',
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的id',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
   `authority` varchar (255) COMMENT'用户类型1代表用户，2代表商家，3代表被封号,0代表管理员',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
--   反设计模式准则
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `shop_name` varchar(64) NOT NULL COMMENT '店铺名称',
  `status` tinyint(4) DEFAULT '0' COMMENT '商平状态 0 代表正常售卖，1代表货物已经被删除仅仅是数据留存',
    `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `miaosha_goods`;
CREATE TABLE `miaosha_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表,主键',
  `goods_id` bigint(20) NOT NULL  COMMENT '商品主键',
  `miaosha_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀单价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS `miaosha_order`;
CREATE TABLE `miaosha_order` (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单表',
  `user_id`  bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` varchar(32) NOT NULL COMMENT '订单表，主键',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;





DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(32) NOT NULL COMMENT '订单表，主键',
  `user_id`  bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id`  bigint(20) DEFAULT NULL  COMMENT '商品id',
  `delivery_add_id` bigint(20) DEFAULT NULL COMMENT '收获地址id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT '0' COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `status` tinyint(4) DEFAULT '0' COMMENT '订单状态：0新建未支付，1待发货，2已发货，3已收货，4已退款，5已完成，6申请退款,7已投诉',
  `create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `miaosha_order`;
CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单表',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` varchar(32) DEFAULT NULL COMMENT '订单id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地址表主键',
  `user_id` bigint(40) DEFAULT NULL COMMENT '用户id',
  `phone_number` varchar(15) not null COMMENT '用户手机号',
  `name` varchar(255) NOT NULL COMMENT '联系人姓名',
  `user_address` varchar(255) NOT NULL COMMENT '收货地址',
  `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '店铺表主键',
--   一个用户只能申请一次
  `user_id` bigint(40) NOT NULL unique key COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '店铺名称',
  `info` varchar(255) NOT NULL COMMENT '店铺介绍',
  `authority` char(1) NOT NULL COMMENT '3代表店铺被封了，0代表没有申请过，1代表正在审核，2代表审核通过可以正常经营',
  `front_pic` varchar(128) DEFAULT NULL COMMENT'身份证照片正面',
   `back_pic` varchar(128) DEFAULT NULL COMMENT '身份证照片背面',
    `people_name` varchar (255) COMMENT'真实姓名',
   `people_num` varchar (255) COMMENT '身份证号码',
   `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shop_goods`;
CREATE TABLE `shop_goods` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '店铺商品表id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
   `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_sid_gid` (`shop_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `infomation`;
CREATE TABLE `infomation` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息的主键',
  `received_id` bigint(20) DEFAULT NULL COMMENT '接收者id',
   `received_name`varchar(255) NOT NULL COMMENT '接收者的名称',
   `sender_name`varchar(255) NOT NULL COMMENT '发送者的名称',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '发送者id',
  `infomation_info` bigint(20) DEFAULT NULL COMMENT '消息主体',
   `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `shoping_cart`;
CREATE TABLE `shoping_cart` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物车主键',
   `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
   `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
   `address_id` bigint(20) DEFAULT NULL COMMENT '商品id',
    `number` bigint(20) DEFAULT 1 COMMENT '购买数量',
   `reserved_filds` varchar (255) COMMENT'预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
   `reserved_filds3` varchar (255) COMMENT '预留位3',
    `reserved_filds4` varchar (255) COMMENT '预留位4',
     `reserved_filds5` varchar (255) COMMENT '预留位5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint` (
 `id` tinyint(4) NOT NULL  COMMENT '投诉主键',
  `content` varchar (20) DEFAULT NULL COMMENT '投诉类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_complaint`;
CREATE TABLE `user_complaint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '投诉主键',
  `complaint_id` tinyint(20) NOT NULL  COMMENT '投诉类型主键',
  `order_id` varchar(32) NOT NULL COMMENT '订单表，主键',
  `content` varchar (20) DEFAULT NULL COMMENT '投诉类型',
  `apply_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
  `phone_number` varchar(15) not null COMMENT '用户手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;