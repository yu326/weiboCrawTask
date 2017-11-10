-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.7.18 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 v4 的数据库结构
CREATE DATABASE IF NOT EXISTS `v4` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `v4`;


-- 导出  表 v4.project 结构
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '项目名称',
  `cache_name` tinyint(3) unsigned DEFAULT '0',
  `column` varchar(50) COLLATE utf8_bin NOT NULL,
  `column1` varchar(50) COLLATE utf8_bin NOT NULL,
  `is_avaliable` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目表';

-- 正在导出表  v4.project 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT IGNORE INTO `project` (`id`, `name`, `cache_name`, `column`, `column1`, `is_avaliable`) VALUES
	(1, 'q4', 3, '云计算_微博', '微博', b'1');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- 导出  表 v4.task 结构
CREATE TABLE IF NOT EXISTS `task` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tasktype` tinyint(2) NOT NULL COMMENT '分析或抓取',
  `task` tinyint(4) NOT NULL COMMENT '定义任务',
  `tasklevel` tinyint(1) NOT NULL COMMENT '任务级别',
  `timeout` int(11) DEFAULT NULL,
  `activatetime` int(11) NOT NULL DEFAULT '0',
  `conflictdelay` int(11) DEFAULT NULL,
  `starttime` int(11) NOT NULL COMMENT '任务开始时间',
  `endtime` int(11) DEFAULT NULL COMMENT '任务结束时间',
  `taskstatus` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0等待启动，2停止',
  `datastatus` int(10) NOT NULL DEFAULT '0' COMMENT '抓取数据量',
  `taskparams` longtext COLLATE utf8_bin COMMENT '任务参数',
  `remarks` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '摘要',
  `machine` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `tenantid` int(11) DEFAULT NULL COMMENT '租户id',
  `userid` int(11) DEFAULT NULL COMMENT '用户id',
  `error_code` int(11) DEFAULT NULL COMMENT '错误码',
  `spcfdmac` char(23) COLLATE utf8_bin DEFAULT NULL COMMENT '指派特定的机器执行该任务',
  `error_msg` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息',
  `taskclassify` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '任务类别',
  `project_id` tinyint(3) unsigned NOT NULL COMMENT '项目id',
  PRIMARY KEY (`id`),
  KEY `tasktype` (`tasktype`),
  KEY `task` (`task`),
  KEY `taskstatus` (`taskstatus`),
  KEY `activatetime` (`activatetime`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 正在导出表  v4.task 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT IGNORE INTO `task` (`id`, `tasktype`, `task`, `tasklevel`, `timeout`, `activatetime`, `conflictdelay`, `starttime`, `endtime`, `taskstatus`, `datastatus`, `taskparams`, `remarks`, `machine`, `tenantid`, `userid`, `error_code`, `spcfdmac`, `error_msg`, `taskclassify`, `project_id`) VALUES
	(1, 2, 18, 1, NULL, 0, 60, 1, NULL, 0, 0, '{"iscommit":true,"dictionaryPlan":"[[]]","source":"1","each_count":"10","inputtype":"id","config":55,"isseed":1,"users":["123"],"is_grab_repost":"0"}', 'cs ', NULL, -1, 40, NULL, NULL, NULL, NULL, 1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
