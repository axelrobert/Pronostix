CREATE DATABASE  IF NOT EXISTS `db_pronostix`
USE `db_pronostix`;

--
-- Table structure for table `Group`
--

DROP TABLE IF EXISTS `Group`;
CREATE TABLE `Group` (
  `idGroup` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `winner_award` int(11) NOT NULL,
  PRIMARY KEY (`idGroup`),
  UNIQUE KEY `idGroup_UNIQUE` (`idGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Bettor`
--

DROP TABLE IF EXISTS `Bettor`;
CREATE TABLE `Bettor` (
  `idBettor` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` char(60) NOT NULL,
  `good_bets` int(11) DEFAULT NULL,
  `bad_bets` int(11) DEFAULT NULL,
  `id_group` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBettor`),
  UNIQUE KEY `idBettor_UNIQUE` (`idBettor`),
  KEY `fk_id_group_idx` (`id_group`),
  CONSTRAINT `fk_id_group` FOREIGN KEY (`id_group`) REFERENCES `Group` (`idGroup`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Team`
--

DROP TABLE IF EXISTS `Team`;
CREATE TABLE `Team` (
  `idTeam` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(3) NOT NULL,
  `conference` varchar(4) NOT NULL,
  `division` varchar(30) NOT NULL,
  `wins` int(11) DEFAULT NULL,
  `losses` int(11) DEFAULT NULL,
  `home_wins` int(11) DEFAULT NULL,
  `home_losses` int(11) DEFAULT NULL,
  `road_wins` int(11) DEFAULT NULL,
  `road_losses` int(11) DEFAULT NULL,
  `scored_points` int(11) DEFAULT NULL,
  `conceded_points` int(11) DEFAULT NULL,
  `conf_wins` int(11) DEFAULT NULL,
  `conf_losses` int(11) DEFAULT NULL,
  `div_wins` int(11) DEFAULT NULL,
  `div_losses` int(11) DEFAULT NULL,
  `wins_on_last_ten` int(11) DEFAULT NULL,
  `losses_on_last_ten` int(11) DEFAULT NULL,
  `streak` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`idTeam`),
  UNIQUE KEY `idTeam_UNIQUE` (`idTeam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `Match`
--

DROP TABLE IF EXISTS `Match`;
CREATE TABLE `Match` (
  `idMatch` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `home_score` int(11) DEFAULT NULL,
  `road_score` int(11) DEFAULT NULL,
  `id_home_team` int(11) DEFAULT NULL,
  `id_road_team` int(11) DEFAULT NULL,
  `id_winner_team` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMatch`),
  UNIQUE KEY `idMatch_UNIQUE` (`idMatch`),
  KEY `fk_id_home_team_idx` (`id_home_team`),
  KEY `fk_id_road_team_idx` (`id_road_team`),
  KEY `fk_id_winner_team_idx` (`id_winner_team`),
  CONSTRAINT `fk_id_home_team` FOREIGN KEY (`id_home_team`) REFERENCES `Team` (`idTeam`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_road_team` FOREIGN KEY (`id_road_team`) REFERENCES `Team` (`idTeam`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_winner_team` FOREIGN KEY (`id_winner_team`) REFERENCES `Team` (`idTeam`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `Pronostics`
--

DROP TABLE IF EXISTS `Pronostics`;
CREATE TABLE `Pronostics` (
  `id_match` int(11) NOT NULL,
  `id_bettor` int(11) NOT NULL,
  `id_team_prono` int(11) DEFAULT NULL,
  `points_gap` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_match`,`id_bettor`),
  KEY `fk_id_bettor_idx` (`id_bettor`),
  CONSTRAINT `fk_id_bettor` FOREIGN KEY (`id_bettor`) REFERENCES `Bettor` (`idBettor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_match` FOREIGN KEY (`id_match`) REFERENCES `Match` (`idMatch`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
