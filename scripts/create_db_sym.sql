SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ShareYourMedia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ShareYourMedia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ShareYourMedia` ;
USE `ShareYourMedia` ;

-- -----------------------------------------------------
-- Table `ShareYourMedia`.`REGULAR_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`REGULAR_USER` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `salt` VARCHAR(45) NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`ADMIN_USER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`ADMIN_USER` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `salt` VARCHAR(45) NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`SEASON`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`SEASON` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`ALBUM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`ALBUM` (
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`MEDIAFILE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`MEDIAFILE` (
  `name` VARCHAR(45) NOT NULL,
  `artist` VARCHAR(45) NOT NULL,
  `SEASON_name` VARCHAR(45) NOT NULL,
  `ALBUM_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`, `artist`),
  INDEX `fk_MEDIAFILE_SEASON1_idx` (`SEASON_name` ASC) VISIBLE,
  INDEX `fk_MEDIAFILE_ALBUM1_idx` (`ALBUM_name` ASC) VISIBLE,
  CONSTRAINT `fk_MEDIAFILE_SEASON1`
    FOREIGN KEY (`SEASON_name`)
    REFERENCES `ShareYourMedia`.`SEASON` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MEDIAFILE_ALBUM1`
    FOREIGN KEY (`ALBUM_name`)
    REFERENCES `ShareYourMedia`.`ALBUM` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`PLAYLIST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`PLAYLIST` (
  `name` VARCHAR(45) NOT NULL,
  `criteria` VARCHAR(45) NULL,
  `creator` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`, `creator`),
  INDEX `fk_PLAYLIST_REGULAR_USER_idx` (`creator` ASC) VISIBLE,
  CONSTRAINT `fk_PLAYLIST_REGULAR_USER`
    FOREIGN KEY (`creator`)
    REFERENCES `ShareYourMedia`.`REGULAR_USER` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`PLAYLIST_has_MEDIAFILE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`PLAYLIST_has_MEDIAFILE` (
  `MEDIAFILE_name` VARCHAR(45) NOT NULL,
  `PLAYLIST_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`MEDIAFILE_name`, `PLAYLIST_name`),
  INDEX `fk_MEDIAFILE_has_PLAYLIST_PLAYLIST1_idx` (`PLAYLIST_name` ASC) VISIBLE,
  INDEX `fk_MEDIAFILE_has_PLAYLIST_MEDIAFILE1_idx` (`MEDIAFILE_name` ASC) VISIBLE,
  CONSTRAINT `fk_MEDIAFILE_has_PLAYLIST_MEDIAFILE1`
    FOREIGN KEY (`MEDIAFILE_name`)
    REFERENCES `ShareYourMedia`.`MEDIAFILE` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MEDIAFILE_has_PLAYLIST_PLAYLIST1`
    FOREIGN KEY (`PLAYLIST_name`)
    REFERENCES `ShareYourMedia`.`PLAYLIST` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ShareYourMedia`.`REGULAR_USER_has_MEDIAFILE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ShareYourMedia`.`REGULAR_USER_has_MEDIAFILE` (
  `REGULAR_USER_email` VARCHAR(45) NOT NULL,
  `MEDIAFILE_name` VARCHAR(45) NOT NULL,
  `MEDIAFILE_artist` VARCHAR(45) NOT NULL,
  `category1` VARCHAR(45) NULL,
  `category2` VARCHAR(45) NULL,
  `category3` VARCHAR(45) NULL,
  PRIMARY KEY (`REGULAR_USER_email`, `MEDIAFILE_name`, `MEDIAFILE_artist`),
  INDEX `fk_REGULAR_USER_has_MEDIAFILE_MEDIAFILE1_idx` (`MEDIAFILE_name` ASC, `MEDIAFILE_artist` ASC) VISIBLE,
  INDEX `fk_REGULAR_USER_has_MEDIAFILE_REGULAR_USER1_idx` (`REGULAR_USER_email` ASC) VISIBLE,
  CONSTRAINT `fk_REGULAR_USER_has_MEDIAFILE_REGULAR_USER1`
    FOREIGN KEY (`REGULAR_USER_email`)
    REFERENCES `ShareYourMedia`.`REGULAR_USER` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_REGULAR_USER_has_MEDIAFILE_MEDIAFILE1`
    FOREIGN KEY (`MEDIAFILE_name` , `MEDIAFILE_artist`)
    REFERENCES `ShareYourMedia`.`MEDIAFILE` (`name` , `artist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
