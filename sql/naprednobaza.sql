/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - kladionica
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`naprednobaza` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `naprednobaza`;

/*Table structure for table `grad` */

DROP TABLE IF EXISTS `grad`;

CREATE TABLE `grad` (
  `idGrad` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `postanskiBroj` varchar(10) NOT NULL,
  PRIMARY KEY (`idGrad`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `grad` */

insert  into `grad`(`idGrad`,`naziv`,`postanskiBroj`) values 
(1,'Beograd','11000'),
(2,'Novi Sad','21000');

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `idKorisnik` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `jmbg` varchar(13) NOT NULL,
  `idGrad` int(11) NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  UNIQUE KEY `jmbg` (`jmbg`),
  KEY `korisnik_ibfk_1` (`idGrad`),
  CONSTRAINT `korisnik_ibfk_1` FOREIGN KEY (`idGrad`) REFERENCES `grad` (`idGrad`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`idKorisnik`,`ime`,`prezime`,`jmbg`,`idGrad`) values 
(4,'Petar','Petrovic','2948603959688',2),
(5,'Jovana','Jovanovic','9876543210123',2),
(7,'Darko','Serafinovski','2010001860014',2),
(16,'Uros','Serafinovski','2905006860014',1),
(19,'Iva','Drobnjak','1704005860014',1);

/*Table structure for table `par` */

DROP TABLE IF EXISTS `par`;

CREATE TABLE `par` (
  `idPar` int(11) NOT NULL AUTO_INCREMENT,
  `idTiket` int(11) NOT NULL,
  `idUtakmica` int(11) NOT NULL,
  `tipOpklade` enum('POBEDA_DOMACIN','NERESENO','POBEDA_GOST','ILI_1X','ILI_X2','ILI_12','GG','NG','VISE_OD_2_5','GG_VISE_OD_2_5') NOT NULL,
  `kvota` decimal(5,2) NOT NULL,
  `redosled` int(11) NOT NULL,
  PRIMARY KEY (`idPar`),
  UNIQUE KEY `idTiket` (`idTiket`,`idUtakmica`,`tipOpklade`),
  KEY `par_ibfk_2` (`idUtakmica`),
  CONSTRAINT `par_ibfk_1` FOREIGN KEY (`idTiket`) REFERENCES `tiket` (`idTiket`) ON UPDATE CASCADE,
  CONSTRAINT `par_ibfk_2` FOREIGN KEY (`idUtakmica`) REFERENCES `utakmica` (`idUtakmica`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `par` */

insert  into `par`(`idPar`,`idTiket`,`idUtakmica`,`tipOpklade`,`kvota`,`redosled`) values 
(14,1,1,'GG_VISE_OD_2_5',1.80,4),
(21,1,2,'POBEDA_DOMACIN',1.90,1),
(65,2,1,'ILI_12',2.05,5),
(87,25,2,'POBEDA_DOMACIN',1.90,1),
(88,25,1,'POBEDA_GOST',2.80,2),
(89,26,2,'GG',2.10,1),
(90,26,1,'POBEDA_GOST',2.80,2),
(91,27,2,'POBEDA_DOMACIN',1.90,1),
(92,27,1,'NERESENO',3.20,2),
(93,28,7,'GG',2.00,1),
(94,28,5,'POBEDA_GOST',2.50,2),
(95,28,18,'POBEDA_GOST',2.30,3),
(96,28,16,'GG',1.90,4);

/*Table structure for table `poslovnica` */

DROP TABLE IF EXISTS `poslovnica`;

CREATE TABLE `poslovnica` (
  `idPoslovnica` int(11) NOT NULL AUTO_INCREMENT,
  `adresa` varchar(100) NOT NULL,
  `brojTelefona` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idPoslovnica`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `poslovnica` */

insert  into `poslovnica`(`idPoslovnica`,`adresa`,`brojTelefona`) values 
(1,'Beograd, Nemanjina 12','011/123456'),
(2,'Novi Sad, Bulevar Oslobodjenja 10','021/654321'),
(4,'Kolarceva 1','011111111'),
(5,'Kolarceva 1','090000339');

/*Table structure for table `radnik` */

DROP TABLE IF EXISTS `radnik`;

CREATE TABLE `radnik` (
  `idRadnik` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `korisnickoIme` varchar(50) NOT NULL,
  `lozinka` varchar(100) NOT NULL,
  `brojTelefona` varchar(15) NOT NULL,
  PRIMARY KEY (`idRadnik`),
  UNIQUE KEY `korisnickoIme` (`korisnickoIme`),
  CONSTRAINT `chk_brojTelefona` CHECK (`brojTelefona` like '06%')
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `radnik` */

insert  into `radnik`(`idRadnik`,`ime`,`prezime`,`korisnickoIme`,`lozinka`,`brojTelefona`) values 
(3,'Marko','Markovic','marko123','lozinka1','069304456'),
(100,'Ana','Anic','ana123','lozinka123','069222222');

/*Table structure for table `tiket` */

DROP TABLE IF EXISTS `tiket`;

CREATE TABLE `tiket` (
  `idTiket` int(11) NOT NULL AUTO_INCREMENT,
  `ulog` decimal(10,2) NOT NULL,
  `ukupnaKvota` decimal(10,2) NOT NULL,
  `dobitak` decimal(10,2) DEFAULT NULL,
  `statusTiket` tinyint(4) NOT NULL DEFAULT 0,
  `idRadnik` int(11) NOT NULL,
  `idKorisnik` int(11) NOT NULL,
  PRIMARY KEY (`idTiket`),
  KEY `idKorisnik` (`idKorisnik`),
  KEY `tiket_ibfk_1` (`idRadnik`),
  CONSTRAINT `tiket_ibfk_1` FOREIGN KEY (`idRadnik`) REFERENCES `radnik` (`idRadnik`) ON UPDATE CASCADE,
  CONSTRAINT `tiket_ibfk_2` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idKorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `tiket` */

insert  into `tiket`(`idTiket`,`ulog`,`ukupnaKvota`,`dobitak`,`statusTiket`,`idRadnik`,`idKorisnik`) values 
(1,100.00,3.42,342.00,1,3,4),
(2,50.00,2.05,102.50,0,100,5),
(25,0.00,5.32,0.00,2,3,19),
(26,500.00,5.88,2940.00,0,100,7),
(27,600.00,6.08,3648.00,0,100,19),
(28,200.00,21.85,4370.00,1,3,7);

/*Table structure for table `utakmica` */

DROP TABLE IF EXISTS `utakmica`;

CREATE TABLE `utakmica` (
  `idUtakmica` int(11) NOT NULL AUTO_INCREMENT,
  `domacin` varchar(100) NOT NULL,
  `gost` varchar(100) NOT NULL,
  `termin` datetime NOT NULL,
  PRIMARY KEY (`idUtakmica`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `utakmica` */

insert  into `utakmica`(`idUtakmica`,`domacin`,`gost`,`termin`) values 
(1,'Crvena Zvezda','Partizan','2025-09-20 18:00:00'),
(2,'Barcelona','Real Madrid','2025-09-21 20:45:00'),
(3,'Bayern Munchen','Borussia Dortmund','2025-09-27 18:30:00'),
(4,'Manchester United','Liverpool','2025-09-27 21:00:00'),
(5,'Juventus','AC Milan','2025-09-28 19:00:00'),
(6,'PSG','Lyon','2025-09-28 21:30:00'),
(7,'Chelsea','Arsenal','2025-09-29 18:45:00'),
(8,'Atletico Madrid','Sevilla','2025-09-29 21:00:00'),
(9,'Inter Milan','Napoli','2025-09-30 20:00:00'),
(10,'Tottenham','Manchester City','2025-09-30 22:00:00'),
(11,'Benfica','Porto','2025-10-01 18:00:00'),
(12,'Ajax','PSV','2025-10-01 20:30:00'),
(13,'Lazio','Roma','2025-10-02 19:15:00'),
(14,'Monaco','Marseille','2025-10-02 21:00:00'),
(15,'Dortmund','Leipzig','2025-10-03 18:30:00'),
(16,'Real Sociedad','Valencia','2025-10-03 20:45:00'),
(17,'Schalke 04','Bayer Leverkusen','2025-10-04 18:00:00'),
(18,'Sevilla','Villarreal','2025-10-04 20:30:00'),
(19,'Fiorentina','Torino','2025-10-05 19:00:00'),
(20,'Marseille','Lille','2025-10-05 21:15:00');

/*Table structure for table `zaposlenje` */

DROP TABLE IF EXISTS `zaposlenje`;

CREATE TABLE `zaposlenje` (
  `idRadnik` int(11) NOT NULL,
  `idPoslovnica` int(11) NOT NULL,
  `datumZaposlenja` date NOT NULL,
  `datumOtpustanja` date DEFAULT NULL,
  PRIMARY KEY (`idRadnik`,`idPoslovnica`,`datumZaposlenja`),
  KEY `zaposlenje_ibfk_2` (`idPoslovnica`),
  CONSTRAINT `zaposlenje_ibfk_1` FOREIGN KEY (`idRadnik`) REFERENCES `radnik` (`idRadnik`) ON UPDATE CASCADE,
  CONSTRAINT `zaposlenje_ibfk_2` FOREIGN KEY (`idPoslovnica`) REFERENCES `poslovnica` (`idPoslovnica`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `zaposlenje` */

insert  into `zaposlenje`(`idRadnik`,`idPoslovnica`,`datumZaposlenja`,`datumOtpustanja`) values 
(3,1,'2025-01-01','2025-09-15'),
(3,1,'2025-09-15','2025-09-15'),
(3,2,'2025-09-15',NULL),
(100,2,'2025-02-15',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
