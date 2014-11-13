/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : yd_gongdan

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2014-09-15 09:34:46
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_id` varchar(11) DEFAULT NULL COMMENT '客户编号--来自移动',
  `customer_name` varchar(255) DEFAULT NULL COMMENT '客户名称 - client_name',
  `customer_address` varchar(255) DEFAULT NULL COMMENT '客户地址 - client_address',
  `postal_code` varchar(6) DEFAULT NULL COMMENT '客户邮编 - client_code',
  `category` varchar(255) DEFAULT NULL COMMENT '客户所属行业 - industry',
  `is_use` int(11) DEFAULT '1' COMMENT '1为可用、0为不可用',
  `b_contact` varchar(255) DEFAULT NULL COMMENT '业务联系人 - business_contact',
  `b_phone` varchar(255) DEFAULT NULL COMMENT '业务联系电话 - business_phone',
  `b_email` varchar(255) DEFAULT NULL COMMENT '业务联系人邮箱 - business_email',
  `t_contact` varchar(255) DEFAULT NULL COMMENT '技术联系人 - technology_contact',
  `t_phone` varchar(255) DEFAULT NULL COMMENT '技术联系人电话 - technology_phone',
  `t_email` varchar(255) DEFAULT NULL COMMENT '技术联系人邮箱 - technology_email',
  `c_contact` varchar(255) DEFAULT NULL COMMENT '客户经理名称 - customer_manager_contact',
  `c_phone` varchar(255) DEFAULT NULL COMMENT '客户经理电话 - customer_manager_phone',
  `c_email` varchar(255) DEFAULT NULL COMMENT '客户经理邮箱 - customer_manager_email',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` varchar(255) DEFAULT NULL COMMENT '工单编号 - ticketId',
  `contract_id` varchar(255) DEFAULT NULL COMMENT '同合编号 - contractId',
  `contract_signed_date` timestamp NULL DEFAULT NULL COMMENT '合同起始时间 - contractSignedDate',
  `contract_expired_date` timestamp NULL DEFAULT NULL COMMENT '合同截止时间 - contractExpiredDate',
  `available_at` timestamp NULL DEFAULT NULL COMMENT '资源开通时间',
  `product_type` enum('share','exclusive','storage') DEFAULT NULL COMMENT ' 产品类型 - productType:专享云,共享云,云存储',
  `is_website` enum('yes','no') DEFAULT NULL COMMENT '是否做网站 - forWebsite',
  `open_ports` varchar(255) DEFAULT NULL COMMENT '开放端口',
  `ports_need_to_open` varchar(255) DEFAULT NULL COMMENT '要求开放端口,如有多个端口，请用英文半角的逗号分隔 - portsNeedToOpen',
  `band_width` varchar(255) DEFAULT NULL COMMENT '带宽 - bandwidth',
  `sizeInGB` varchar(255) DEFAULT NULL COMMENT '存储大小',
  `discount` varchar(255) DEFAULT NULL COMMENT '折扣率',
  `snapshot` varchar(255) DEFAULT NULL COMMENT '快照',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `ip_count` varchar(255) DEFAULT NULL COMMENT 'ip个数',
  `keypairs` varchar(255) DEFAULT NULL COMMENT '密钥',
  `securitys` varchar(255) DEFAULT NULL COMMENT '安全规则组',
  `ha` varchar(255) DEFAULT NULL COMMENT '主机保护',
  `elb` varchar(255) DEFAULT NULL COMMENT '负载均衡',
  `memo` varchar(255) DEFAULT NULL COMMENT '工单备注(移动传值接收)',
  `filing_no` varchar(255) DEFAULT NULL COMMENT '备案号',
  `filing_ip` varchar(255) DEFAULT NULL COMMENT '备案IP',
  `filing_domain` varchar(255) DEFAULT NULL COMMENT '备案域名',
  `is_fees_owed` varchar(255) DEFAULT NULL COMMENT ' 是否欠费：yes/no',
  `close_time` timestamp NULL DEFAULT NULL COMMENT ' 撤销时间',
  `reason` varchar(255) DEFAULT NULL COMMENT ' 撤销原因',
  `detailed_reason` varchar(255) DEFAULT NULL COMMENT ' 详细原因',
  `login_url` varchar(255) DEFAULT NULL COMMENT '登录云平台的URL',
  `cloud_platform` varchar(255) DEFAULT NULL COMMENT '所属云平台',
  `login_username` varchar(255) DEFAULT NULL COMMENT '登录云平台用户名',
  `login_password` varchar(255) DEFAULT NULL COMMENT '登陆云平台密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for `privileges`
-- ----------------------------
DROP TABLE IF EXISTS `privileges`;
CREATE TABLE `privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sn` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privileges
-- ----------------------------
INSERT INTO `privileges` VALUES ('1', '用户管理', '/user', '/user.htm', '1');
INSERT INTO `privileges` VALUES ('2', 'index', '/index', '/index.htm', '100');
INSERT INTO `privileges` VALUES ('3', '客户管理', '/client', '/client.htm', '3');
INSERT INTO `privileges` VALUES ('4', '工单管理', '/ticket', '/ticket.htm', '2');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role_type` enum('user','cfm','sys') DEFAULT NULL COMMENT 'view - user(普通用户); cfm - configuration manager(资源分配管理员);sys - system manager(系统管理员)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员', 'sys');
INSERT INTO `role` VALUES ('2', '资源管理员', 'cfm');
INSERT INTO `role` VALUES ('3', '业务管理员', 'user');

-- ----------------------------
-- Table structure for `role_privileges`
-- ----------------------------
DROP TABLE IF EXISTS `role_privileges`;
CREATE TABLE `role_privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_type` varchar(20) DEFAULT NULL COMMENT '角色ID - role_id',
  `priv_id` int(11) DEFAULT NULL COMMENT '权限ID - privileges_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_privileges
-- ----------------------------
INSERT INTO `role_privileges` VALUES ('1', 'sys', '1');
INSERT INTO `role_privileges` VALUES ('2', 'sys', '2');
INSERT INTO `role_privileges` VALUES ('3', 'cfm', '3');
INSERT INTO `role_privileges` VALUES ('4', 'cfm', '2');
INSERT INTO `role_privileges` VALUES ('5', 'cfm', '4');
INSERT INTO `role_privileges` VALUES ('6', 'user', '2');
INSERT INTO `role_privileges` VALUES ('7', 'user', '4');
INSERT INTO `role_privileges` VALUES ('8', 'user', '3');

-- ----------------------------
-- Table structure for `suite`
-- ----------------------------
DROP TABLE IF EXISTS `suite`;
CREATE TABLE `suite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` varchar(255) DEFAULT NULL COMMENT '工单ID，移动传过来的',
  `order_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '套餐名称',
  `discount` varchar(255) DEFAULT NULL COMMENT '套餐折扣率',
  `count` varchar(255) DEFAULT NULL COMMENT '套餐数量',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_use` int(11) DEFAULT '1' COMMENT '是否可用(1:可用；0:不可用)',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of suite
-- ----------------------------

-- ----------------------------
-- Table structure for `ticket`
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_id` varchar(255) DEFAULT NULL COMMENT '工单编号 - ticket_id',
  `customer_id` varchar(255) DEFAULT NULL COMMENT '客户编号 - customer_id',
  `ticket_type` enum('open','modify','close') DEFAULT NULL COMMENT '单工类型 - ticket_type',
  `ticket_state` varchar(255) DEFAULT NULL COMMENT '工单状态 - ticket_state',
  `receive_time` timestamp NULL DEFAULT NULL COMMENT '工单接收时间 yyyy-MM-dd HH:mm:ss',
  `is_use` int(11) DEFAULT '1' COMMENT '是否在用 在用=1 ;作废：0；',
  `remark` varchar(255) DEFAULT NULL,
  `is_modify` int(11) DEFAULT '1' COMMENT '是否编辑过 0为已编辑过 1为可编辑',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ticket
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_type` enum('user','cfm','sys') DEFAULT NULL COMMENT '角色类型 - role_type',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'sysadmin', 'sysadmin', '123', 'sys', '2014-08-12 12:22:34');
INSERT INTO `user` VALUES ('2', 'admin', 'admin', 'admin', 'cfm', '2014-09-03 15:24:21');
INSERT INTO `user` VALUES ('3', 'testuser', 'user', 'user', 'user', '2014-08-11 16:19:47');