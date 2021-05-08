DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `phone_number` varchar(15) not null COMMENT '用户手机号',
  `nickname` varchar(255) NOT NULL COMMENT '登录昵称',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '城市主键',
  `city_name` varchar(15) not null COMMENT '城市名称',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `city` VALUES ('1', '江苏');
INSERT INTO `city` VALUES ('2', '河南');
INSERT INTO `city` VALUES ('3', '安徽');

DROP TABLE IF EXISTS `map`;
CREATE TABLE `map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '地图主键',
  `name` varchar(50) not null COMMENT '地图名称',
  `user_id` bigint(20) NOT NULL  COMMENT '用户主键，创建地图的用户id',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `map_data`;
CREATE TABLE `map_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据主键',
  `map_id`  bigint(20) NOT NULL  COMMENT'地图主键',
  `city_id`  bigint(20) NOT NULL  COMMENT'城市主键',
  `value` varchar(15) not null COMMENT '数据值',
  `Color` varchar(2) not null COMMENT '颜色级别数字越低颜色越深',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`),
    UNIQUE KEY `u_mid_cid` (`map_id`,`city_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;