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

CREATE TABLE `nuttycrunch`.`acc_request` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NOT NULL,
  `unique_identifier` BIGINT NOT NULL,
  `new_value` JSON NOT NULL,
  `existing_value` JSON NULL,
  `action` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `submittedBy` VARCHAR(45) NOT NULL,
  `submittedOn` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `nuttycrunch`.`acc_accredit_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NOT NULL,
  `level` VARCHAR(45) NOT NULL,
  `approver_email` VARCHAR(45) NOT NULL,
  `next_level` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `TAG_LEVEL_IDX` (`tag` ASC, `level` ASC) INVISIBLE);

CREATE TABLE `nuttycrunch`.`acc_request_detail` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `request_id` BIGINT NOT NULL,  
  `status` VARCHAR(45) NOT NULL,
  `approver_comment` VARCHAR(200) NULL,
    `approver_email` VARCHAR(45) NOT NULL ,
    `accredit_group_level` VARCHAR(45) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UNIQ_IDX` (`request_id` ASC, `accredit_group_level` ASC) INVISIBLE);

ALTER TABLE acc_request_detail ADD CONSTRAINT FK_REQ_DET_REQ_ID 
    FOREIGN KEY (request_id) REFERENCES acc_request(id);
    

