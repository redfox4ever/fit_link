

CREATE TABLE `fitlink`.`utilisateurs` (`ref_utilisateur` INT NOT NULL AUTO_INCREMENT , `nom` VARCHAR(50) NOT NULL , `prenom` VARCHAR(50) NOT NULL , `email` VARCHAR(50) NOT NULL , `mot_de_passe` VARCHAR(50) NOT NULL , PRIMARY KEY (`ref_utilisateur`)) ENGINE = InnoDB;



CREATE TABLE `fitlink`.`clients` (`ref_client` INT NOT NULL AUTO_INCREMENT , `objectif` VARCHAR(50) NOT NULL , `taille` INT NOT NULL , `poids` INT NOT NULL , `age` INT NOT NULL , `sexe` ENUM('homme','femme') NOT NULL , `type` VARCHAR(50) NOT NULL , `ref_utilisateur` INT NOT NULL , PRIMARY KEY (`ref_client`)) ENGINE = InnoDB;
ALTER TABLE `clients` ADD CONSTRAINT `fk_clients_utilisateurs_ref_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateurs`(`ref_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;



CREATE TABLE `fitlink`.`proprietaires_salle` (`ref_proprietaire_salle` INT NOT NULL AUTO_INCREMENT , `ref_utilisateur` INT NOT NULL , PRIMARY KEY (`ref_proprietaire_salle`)) ENGINE = InnoDB;
ALTER TABLE `proprietaires_salle` ADD CONSTRAINT `fk_proprietaires_salle_utilisateurs_ref_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateurs`(`ref_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;



CREATE TABLE `fitlink`.`coachs` (`ref_coach` INT NOT NULL AUTO_INCREMENT , `description` VARCHAR(255) NOT NULL , `ref_utilisateur` INT NOT NULL , PRIMARY KEY (`ref_coach`)) ENGINE = InnoDB;
ALTER TABLE `coachs` ADD CONSTRAINT `fk_coachs_utilisateurs_ref_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateurs`(`ref_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;



CREATE TABLE `fitlink`.`administrateurs` (`ref_administrateur` INT NOT NULL AUTO_INCREMENT , `ref_utilisateur` INT NOT NULL , PRIMARY KEY (`ref_administrateur`)) ENGINE = InnoDB;
ALTER TABLE `administrateurs` ADD CONSTRAINT `fk_administrateurs_utilisateurs_ref_utilisateur` FOREIGN KEY (`ref_utilisateur`) REFERENCES `utilisateurs`(`ref_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;



ALTER TABLE `utilisateurs` ADD `type_utilisateur` ENUM('client','coach','proprietaire_salle','administrateur') NULL AFTER `mot_de_passe`;
ALTER TABLE `clients` CHANGE `age` `date_de_naissance` DATE NOT NULL;

ALTER TABLE `coachs` ADD `sexe` ENUM('homme','femme') NOT NULL AFTER `ref_utilisateur`, ADD `date_de_naissance` DATE NOT NULL AFTER `sexe`;

ALTER TABLE `utilisateurs` CHANGE `nom` `nom` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL;
ALTER TABLE `utilisateurs` CHANGE `prenom` `prenom` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL;

ALTER TABLE `clients` CHANGE `type` `ref_coach` INT NULL;
ALTER TABLE `clients` ADD CONSTRAINT `fk_clients_coachs_ref_coach` FOREIGN KEY (`ref_coach`) REFERENCES `coachs`(`ref_coach`) ON DELETE SET NULL ON UPDATE RESTRICT;