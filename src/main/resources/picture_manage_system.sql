CREATE TABLE `file_info` (`id` int(11) NOT NULL AUTO_INCREMENT,`name` varchar(100) NULL COMMENT '文件名称',`title` varchar(200) NULL COMMENT '相片标题',`description` varchar(1000) NULL COMMENT '描述',`size` int(11) NULL COMMENT '文件大小',`type` varchar(50) NULL COMMENT '文件类型ID',`group_id` int(11) NULL COMMENT '分组ID',`group_name` varchar(100) NULL COMMENT '分组名称',`url` varchar(255) NULL COMMENT '路径',`create_time` timestamp(0) NULL  COMMENT '文件创建时间',`record_time` timestamp(0) NULL COMMENT '记录时间',PRIMARY KEY (`id`) );CREATE TABLE `group_info` (`id` int(11) NOT NULL AUTO_INCREMENT,`description` varchar(255) NULL COMMENT '分组描述',`name` varchar(255) NULL COMMENT '分组名称',`record_time` timestamp NULL COMMENT '记录时间',PRIMARY KEY (`id`) );