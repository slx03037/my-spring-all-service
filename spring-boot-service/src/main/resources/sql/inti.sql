-- 测试不设置索引
CREATE TABLE STUDENT (
    SNO VARCHAR(3) NOT NULL ,
    SNAME VARCHAR(9) NOT NULL ,
    SSEX CHAR(2) NOT NULL
);
INSERT INTO STUDENT VALUES ('001', 'KangKang', 'M ');
INSERT INTO STUDENT VALUES ('002', 'Mike', 'M ');
INSERT INTO STUDENT VALUES ('003', 'Jane', 'F ');


CREATE TABLE `tbl_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (1, 'angle1', 'angle1', '1234561', NULL);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (2, 'angle2', 'angle2', '1234562', NULL);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (3, '张三', NULL, '12345', NULL);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (4, '张三', NULL, '12345', NULL);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (5, 'zhangsan', NULL, '12345', NULL);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (6, '张三1', '123456', '1234561', 0);
INSERT INTO `demo`.`tbl_user_info`(`id`, `user_name`, `name`, `pwd`, `state`) VALUES (7, '张三', NULL, '123456', 0);

-- 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id`                        INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  `username`                  VARCHAR(32) NOT NULL COMMENT '账号',
  `name`                      VARCHAR(16) DEFAULT '' COMMENT '名字',
  `password`                  VARCHAR(128) DEFAULT '' COMMENT '密码',
  `salt`                      VARCHAR(64) DEFAULT '' COMMENT 'md5密码盐',
  `phone`                     VARCHAR(32) DEFAULT '' COMMENT '联系电话',
  `tips`                      VARCHAR(255) COMMENT '备注',
  `state`                     TINYINT(1) DEFAULT 1 COMMENT '状态 1:正常 2:禁用',
  `created_time`              DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time`              DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='后台管理用户表';
INSERT INTO `t_user` VALUES (1,'admin','系统管理员','123456','www', '17890908889', '系统管理员', 1, '2017-12-12 09:46:12', '2017-12-12 09:46:12');
INSERT INTO `t_user` VALUES (2,'aix','张三','123456','eee', '17859569358', '', 1, '2017-12-12 09:46:12', '2017-12-12 09:46:12');
