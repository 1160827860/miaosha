DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `phone_number` varchar(15) not null COMMENT '用户手机号',
  `nickname` varchar(255) NOT NULL COMMENT '登录昵称',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `register_date` datetime NOT NULL COMMENT '注册时间',
   `status` tinyint(4) DEFAULT '0' COMMENT '1代表用户，2代表管理员',
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
  `color` varchar(2) not null COMMENT '颜色级别数字越低颜色越深',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`),
    UNIQUE KEY `u_mid_cid` (`map_id`,`city_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `scenic_spot`;
CREATE TABLE `scenic_spot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '景点主键',
  `city_id`  bigint(20) NOT NULL  COMMENT'城市主键',
  `name` varchar(15) not null COMMENT '景点名称',
   `content` varchar (20) DEFAULT NULL COMMENT '景点简述',
   `price` decimal(10,2) DEFAULT '0.00' COMMENT '景点票价',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价主键',
  `user_id` bigint(20) NOT NULL  COMMENT '用户主键，创建地图的用户id',
   `scenic_spot_id` bigint(20) NOT NULL  COMMENT '景点主键',
   `content` varchar (255) DEFAULT NULL COMMENT '评价内容',
   `status` tinyint(5) DEFAULT '0' COMMENT '评分',
   `reserved_filds1` varchar (255) COMMENT '预留位1',
   `reserved_filds2` varchar (255) COMMENT '预留位2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT INTO `city`(id,city_name) VALUES ('1', '江苏');
INSERT INTO `city`(id,city_name) VALUES ('2', '河南');
INSERT INTO `city`(id,city_name) VALUES ('3', '安徽');
INSERT INTO `city`(id,city_name) VALUES ('4', '浙江');
INSERT INTO `city`(id,city_name) VALUES ('5', '辽宁');
INSERT INTO `city`(id,city_name) VALUES ('6', '北京');
INSERT INTO `city`(id,city_name) VALUES ('7', '湖北');
INSERT INTO `city`(id,city_name) VALUES ('8', '吉林');
INSERT INTO `city`(id,city_name) VALUES ('9', '上海');
INSERT INTO `city`(id,city_name) VALUES ('10', '广西');
INSERT INTO `city`(id,city_name) VALUES ('11', '四川');
INSERT INTO `city`(id,city_name) VALUES ('12', '贵州');
INSERT INTO `city`(id,city_name) VALUES ('13', '湖南');
INSERT INTO `city`(id,city_name) VALUES ('14', '山东');
INSERT INTO `city`(id,city_name) VALUES ('15', '广东');
INSERT INTO `city`(id,city_name) VALUES ('16', '江西');
INSERT INTO `city`(id,city_name) VALUES ('17', '福建');
INSERT INTO `city`(id,city_name) VALUES ('18', '云南');
INSERT INTO `city`(id,city_name) VALUES ('19', '海南');
INSERT INTO `city`(id,city_name) VALUES ('20', '山西');
INSERT INTO `city`(id,city_name) VALUES ('21', '河北');
INSERT INTO `city`(id,city_name) VALUES ('22', '内蒙古');
INSERT INTO `city`(id,city_name) VALUES ('23', '天津');
INSERT INTO `city`(id,city_name) VALUES ('24', '甘肃');
INSERT INTO `city`(id,city_name) VALUES ('25', '陕西');
INSERT INTO `city`(id,city_name) VALUES ('26', '澳门');
INSERT INTO `city`(id,city_name) VALUES ('27', '香港');
INSERT INTO `city`(id,city_name) VALUES ('28', '台湾');
INSERT INTO `city`(id,city_name) VALUES ('29', '青海');
INSERT INTO `city`(id,city_name) VALUES ('30', '西藏');
INSERT INTO `city`(id,city_name) VALUES ('31', '宁夏');
INSERT INTO `city`(id,city_name) VALUES ('32', '新疆');
INSERT INTO `city`(id,city_name) VALUES ('33', '黑龙江');
INSERT INTO `city`(id,city_name) VALUES ('34', '重庆');

