use alarm;
DROP TABLE  if exists `user`;
CREATE TABLE `user` (
`uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
`account` char(11)  UNIQUE ,
`username` varchar(50)  DEFAULT NULL,
 `password` varchar(200) NOT NULL,
`sex` tinyint  NOT NULL DEFAULT 1 COMMENT '性别',
`start_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '开始时间',
`user_status` tinyint  NOT NULL DEFAULT 1 COMMENT '账号可用性，0：不可用',
`limits` int(11)  NOT NULL DEFAULT 1 COMMENT '使用年限',
PRIMARY KEY (`uid`)，
) ENGINE=InnoDB DEFAULT CHARSET=utf8;