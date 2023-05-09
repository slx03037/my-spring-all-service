DROP TABLE IF EXISTS T_PERMISSION ;
CREATE TABLE T_PERMISSION (
   ID int(10) not null COMMENT '主键id',
   URL VARCHAR(256)  COMMENT 'url地址',
   NAME VARCHAR(64)  COMMENT 'url描述'
);
-- ----------------------------
-- Records of T_PERMISSION
-- ----------------------------
INSERT INTO T_PERMISSION VALUES ('1', '/userDO', 'userDO:userDO');
INSERT INTO T_PERMISSION VALUES ('2', '/userDO/add', 'userDO:add');
INSERT INTO T_PERMISSION VALUES ('3', '/userDO/delete', 'userDO:delete');
-- ----------------------------
-- Table structure for T_ROLE
-- ----------------------------
DROP TABLE IF EXISTS T_ROLE ;
CREATE TABLE T_ROLE (
   ID int  NOT NULL COMMENT '主键id',
   NAME VARCHAR(32) NULL COMMENT '角色名称',
   MEMO VARCHAR(32) NULL COMMENT '角色描述'
);
-- ----------------------------
-- Records of T_ROLE
-- ----------------------------
INSERT INTO T_ROLE VALUES ('1', 'admin', '超级管理员');
INSERT INTO T_ROLE VALUES ('2', 'test', '测试账户');
-- ----------------------------
-- Table structure for T_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS T_ROLE_PERMISSION ;
CREATE TABLE T_ROLE_PERMISSION (
   RID INT(10) NULL COMMENT '角色id',
   PID INT(10) NULL COMMENT '权限id'
);
-- ----------------------------
-- Records of T_ROLE_PERMISSION
-- ----------------------------
INSERT INTO T_ROLE_PERMISSION VALUES ('1', '2');
INSERT INTO T_ROLE_PERMISSION VALUES ('1', '3');
INSERT INTO T_ROLE_PERMISSION VALUES ('2', '1');
INSERT INTO T_ROLE_PERMISSION VALUES ('1', '1');
-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE IF EXISTS T_USER ;
CREATE TABLE T_USER (
   ID INT NOT NULL ,
   USERNAME VARCHAR(20) NOT NULL COMMENT '用户名',
   PASSWD VARCHAR(128) NOT NULL COMMENT '密码',
   CREATE_TIME timestamp(0) NULL COMMENT '创建时间',
   STATUS VARCHAR(1) NOT NULL COMMENT '是否有效 1：有效  0：锁定'
);
-- ----------------------------
-- Records of T_USER
-- ----------------------------
INSERT INTO T_USER VALUES ('2', 'tester', '243e29429b340192700677d48c09d992', now(), '1');
INSERT INTO T_USER VALUES ('1', 'mrbird', '42ee25d1e43e9f57119a00d0a39e5250', now(), '1');
-- ----------------------------
-- Table structure for T_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS T_USER_ROLE ;
CREATE TABLE T_USER_ROLE (
   USER_ID int(10) NULL COMMENT '用户id',
   RID int(10) NULL COMMENT '角色id'
);

-- ----------------------------
-- Records of T_USER_ROLE
-- ----------------------------
INSERT INTO T_USER_ROLE VALUES ('1', '1');
INSERT INTO T_USER_ROLE VALUES ('2', '2');