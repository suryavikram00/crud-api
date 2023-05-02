/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  NMSLAP570
 * Created: 31 Oct, 2022
 */

-- ppo new TABLESPACE

CREATE TABLE `nuttycrunch`.`ncr_ppo` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ppo_id` VARCHAR(64) NOT NULL UNIQUE,
  `fccode` VARCHAR(8) NOT NULL,
  `supplier_code` VARCHAR(128) NOT NULL,
  `payload` JSON NULL,
  `push_status` TINYINT NOT NULL DEFAULT 0,
  `pull_status` TINYINT NOT NULL DEFAULT 0,
  `process_status` TINYINT NOT NULL DEFAULT 0,
  `dispatch_status` TINYINT NOT NULL DEFAULT 0,
  `active` TINYINT NOT NULL DEFAULT 0,
  `created_on` DATETIME NOT NULL,
  `created_by` INT NOT NULL,
  PRIMARY KEY (`ppo_id`),
  UNIQUE INDEX `ppo_id_UNIQUE` (`ppo_id` ASC),
  INDEX `ppo_pull_status` (`pull_status` ASC),
  INDEX `ppo_push_status` (`push_status` ASC),
  INDEX `ppo_process_status` (`process_status` ASC),
  INDEX `ppo_dispatch_status` (`dispatch_status` ASC))
  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
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
  `response` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ppo_id_UNIQUE` (`ppo_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


INSERT INTO `nuttycrunch`.`ncr_config` (`config_name`, `config_value`, `write_enabled`, `config_active`, `created_by`, `created_on`, `updated_by`, `updated_on`, `protected`) VALUES ('magnum_server_base_url', 'http://localhost:8081/nmncr/magnum/v1', '0', '1', '74', now(), '74', now(), '1');
INSERT INTO `nuttycrunch`.`ncr_config` (`config_name`, `config_value`, `write_enabled`, `config_active`, `created_by`, `created_on`, `updated_by`, `updated_on`, `protected`) VALUES ('po_generation_via_external_api', 'MAGNUM', '0', '1', '74', now(), '74', now(), '1');




UPDATE `nuttycrunch`.`ncr_generated_po` SET `po_no` = 'RTSO-513-17-10005_v1' WHERE (`id` = '35894');
UPDATE `nuttycrunch`.`ncr_generated_po` SET `po_no` = 'RTSO-513-17-10006_v1' WHERE (`id` = '35896');
UPDATE `nuttycrunch`.`ncr_generated_po` SET `po_no` = 'RTSO-513-17-10013_v1' WHERE (`id` = '35908');
UPDATE `nuttycrunch`.`ncr_generated_po` SET `po_no` = 'RTSO-513-17-10089_v1' WHERE (`id` = '35985');

INSERT INTO `nuttycrunch`.`ncr_config` (`config_name`, `config_value`, `write_enabled`, `config_active`, `protected`, `created_by`, `created_on`, `updated_by`, `updated_on`) VALUES ('magnum_server_user', 'nuttycrunch@netmeds.com', '0', '1', '1', '74', now(), '74', now());
INSERT INTO `nuttycrunch`.`ncr_config` (`config_name`, `config_value`, `write_enabled`, `config_active`, `protected`, `created_by`, `created_on`, `updated_by`, `updated_on`) VALUES ('magnum_server_password', 'MTIzNDU2', '0', '1', '1', '74', now(), '74', now());
