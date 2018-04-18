-- 创建数据库 --
CREATE DATABASE  IF NOT EXISTS `permission`  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;

USE `permission`;

-- 创建部门表--
DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL COMMENT '上级部门ID',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级下的顺序 小->大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建用户表 --
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户名称',
  `telephone` varchar(13) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '手机号码',
  `mail` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `dept_id` int(11) NOT NULL COMMENT '用户所在部门ID',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态(1.正常2.冻结3.离职)',
  `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  `operator` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最后操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 权限模块表 --
DROP TABLE IF EXISTS `sys_acl_module`;

CREATE TABLE `sys_acl_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块ID',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` int(11) NOT NULL COMMENT '上级权限模块ID',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级下的顺序 小->大',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(1.正常2.冻结)',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 权限表 --
DROP TABLE IF EXISTS `sys_acl`;

CREATE TABLE `sys_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `code` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_model_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限所在的权限模块ID',
  `url` varchar(100) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '请求的URL 可以填写正则',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型:1菜单2按钮3其他',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：1正常0冻结',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '在当前模块下的排序',
  `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT '' COMMENT '备注',
  `operator` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '最后一次更新操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 角色表 --
DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '角色类型：1.管理员2.其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '角色状态：1.正常2.冻结',
  `remark` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `operator` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最后操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- 角色 权限 用户 关联关系表 --
# 角色权限表
DROP TABLE IF EXISTS `sys_role_acl`;

CREATE TABLE `sys_role_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `acl_id` int(11) NOT NULL COMMENT '权限ID',
  `operator` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
# 用户角色表
DROP TABLE IF EXISTS `sys_role_user`;

CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `operator` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '操作者',
  `operate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT '最后一次更新操作者的IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
-- 操作日志表 --
DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(11) NOT NULL COMMENT '权限更新的类型：1.部门2用户3.权限模块4.权限5.角色6.角色用户关系7.角色权限关系',
  `target_id` int(11) NOT NULL COMMENT '基于type类型后指定的ID;比如用户 权限 角色等表的主键',
  `old_value` text COLLATE utf8mb4_bin COMMENT '更新前值',
  `new_value` text COLLATE utf8mb4_bin COMMENT '更新后值',
  `operator` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的IP',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前操作是否被恢复过：0没有1有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;