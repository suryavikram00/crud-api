/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  NMSLAP570
 * Created: 31 Oct, 2022
 */

/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  NMSLAP570
 * Created: 31 Oct, 2022
 */

-- ppo new TABLESPACE

CREATE TABLE `ncr_ppo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ppo_id` varchar(64) COLLATE utf8_bin NOT NULL,
  `fccode` varchar(8) COLLATE utf8_bin NOT NULL,
  `supplier_code` varchar(128) COLLATE utf8_bin NOT NULL,
  `payload` json DEFAULT NULL,
  `push_status` tinyint(4) NOT NULL DEFAULT '0',
  `pull_status` tinyint(4) NOT NULL DEFAULT '0',
  `process_status` tinyint(4) NOT NULL DEFAULT '0',
  `dispatch_status` tinyint(4) NOT NULL DEFAULT '0',
  `active` tinyint(4) NOT NULL DEFAULT '1',
  `created_on` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ppo_id_UNIQUE` (`ppo_id`),
  KEY `ppo_pull_status` (`pull_status`),
  KEY `ppo_push_status` (`push_status`),
  KEY `ppo_process_status` (`process_status`),
  KEY `ppo_dispatch_status` (`dispatch_status`),
  KEY `FK_CREATED_BY_USER_idx` (`created_by`),
  CONSTRAINT `FK_CREATED_BY_USER` FOREIGN KEY (`created_by`) REFERENCES `ncr_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
  ALTER TABLE `nuttycrunch`.`ncr_ppo` 
ADD INDEX `FK_CREATED_BY_USER_idx` (`created_by` ASC) VISIBLE;
;
ALTER TABLE `nuttycrunch`.`ncr_ppo` 
ADD CONSTRAINT `FK_CREATED_BY_USER`
  FOREIGN KEY (`created_by`)
  REFERENCES `nuttycrunch`.`ncr_user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE `nuttycrunch`.`ncr_ppo_response` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ppo_id` VARCHAR(64) NOT NULL,
  `response` JSON NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ppo_id_UNIQUE` (`ppo_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

ALTER TABLE `nuttycrunch`.`ncr_ppo_response` 
ADD COLUMN `created_on` DATETIME NOT NULL AFTER `response`;

ALTER TABLE `nuttycrunch`.`ncr_ppo` 
CHANGE COLUMN `active` `active` TINYINT(4) NOT NULL DEFAULT '1' ;

ALTER TABLE `nuttycrunch`.`ncr_generated_po` 
CHANGE COLUMN `open_po` `open_po` INT NULL DEFAULT 2 ;

ALTER TABLE `nuttycrunch`.`ncr_generated_po` 
ADD UNIQUE INDEX `po_no_UNIQUE` (`po_no` ASC) ;

ALTER TABLE `nuttycrunch`.`ncr_ppo` 
CHANGE COLUMN `push_status` `validated_ppo` TINYINT(4) NOT NULL DEFAULT '0' ,
CHANGE COLUMN `pull_status` `consumed_po` TINYINT(4) NOT NULL DEFAULT '0' ,
CHANGE COLUMN `process_status` `ncr_process_status` TINYINT(4) NOT NULL DEFAULT '0' ,
CHANGE COLUMN `dispatch_status` `ncr_dispatch_status` TINYINT(4) NOT NULL DEFAULT '0' ;

ALTER TABLE `nuttycrunch`.`ncr_ppo_response` 
DROP INDEX `ppo_id_UNIQUE` ;

ALTER TABLE `nuttycrunch`.`ncr_ppo` 
ADD COLUMN `sap_po_id` VARCHAR(45) ;
