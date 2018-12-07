
ALTER TABLE jr_portal_dev.ecf_user ADD create_time DATETIME NOT NULL COMMENT '创建时间';
ALTER TABLE jr_portal_dev.ecf_user ADD update_time DATETIME COMMENT '修改时间';
ALTER TABLE jr_portal_dev.ecf_user ADD password VARCHAR(100) NOT NULL COMMENT '密码';
ALTER TABLE jr_portal_dev.ecf_user MODIFY COLUMN user_id int NOT NULL AUTO_INCREMENT COMMENT 'ID';
ALTER TABLE jr_portal_dev.ecf_user ADD emial VARCHAR(100) COMMENT '邮箱';
ALTER TABLE jr_portal_dev.ecf_user ADD client_type int NOT NULL COMMENT '客户端类型 1：移动端；2：web端；3：web 端ajax';
ALTER TABLE jr_portal_dev.ecf_order ADD order_status VARCHAR(2) NOT NULL COMMENT '订单状态01:已结算；02：未结算';
ALTER TABLE jr_portal_dev.ecf_order ADD count_time timestamp NOT NULL COMMENT '订单结算日期';
ALTER TABLE jr_portal_dev.ecf_order ADD mobile VARCHAR(11) NOT NULL COMMENT '客户手机';

CREATE TABLE jr_portal_dev.valid_code (
	id int NOT NULL AUTO_INCREMENT COMMENT 'ID',
	sendto VARCHAR(12) NOT NULL COMMENT '发送手机',
	code VARCHAR(6) NOT NULL COMMENT '短信码',
	expire DATETIME NOT NULL COMMENT ' 过期日期',
	createdate DATETIME NOT NULL COMMENT '生成日期',
	  PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci
COMMENT='短信验证表';