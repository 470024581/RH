

DROP TABLE IF EXISTS `RhBom`;
CREATE TABLE `RhBom`  (
  `id` int(20) NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `spec` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY (`code`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `RhBomRecord`;
CREATE TABLE `RhBomRecord`  (
  `id` int(20) NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) NOT NULL,
  `num` int(11) NOT NULL,
  `time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE `RhBomRecord` ADD INDEX code_index(code);










