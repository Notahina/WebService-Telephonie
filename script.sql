CREATE DATABASE TELEPHONIE;
CREATE USER ETU with password '1234';
GRANT CONNECT ON DATABASE  TELEPHONIE TO ETU;
ALTER DATABASE TELEPHONIE OWNER TO ETU;
CREATE ROLE EtuRole;
ALTER ROLE EtuRole SUPERUSER;
GRANT EtuRole TO ETU;

psql -U etu -d telephonie
------------------------------------------------------

-------------------------DROP---------------------
DROP table Token CASCADe
-----------EXtension
CREATE EXTENSION pgcrypto;
--------------------------------------------SEQUENCE---------------------------------
CREATE SEQUENCE IF NOT EXISTS user_seq START 1;
CREATE SEQUENCE IF NOT EXISTS money_seq START 10;
CREATE SEQUENCE IF NOT EXISTS user_seq START 1;
---------------------------------------------POSTGRES------------------------------

CREATE TABLE UTILISATEUR 
(
	ID_UTILISATEUR INT PRIMARY KEY,
	NOM VARCHAR(30),
	PRENOM VARCHAR(30),
	LOGIN VARCHAR(30),
	TELEPHONE VARCHAR(15) unique,
	MDP VARCHAR(100),
	Types varchar(30)
);
CREATE TABLE IF NOT EXISTS Token(
	ID_TOKEN varchar(150) PRIMARY KEY,
	ID_UTILISATEUR int,
	DATE_EXPIRATION timestamp,
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);
CREATE TABLE IF NOT EXISTS Offre(
	ID_OFFRE int  PRIMARY KEY,
	NOM_OFFRE varchar(50),
	DATE_OFFRE date,
	VALIDITE int,
	prix double precision
);

CREATE TABLE UTILISATION
(
	ID_UTILISATION SERIAL PRIMARY KEY,
	NOM_UTILISATION VARCHAR(30),
	UNITE VARCHAR(30)
);

CREATE TABLE FORFAIT_OFFRE
(
	ID_FORFAIT_OFFRE SERIAL PRIMARY KEY,
	ID_OFFRE INT,
	ID_UTILISATION INT,
	VALEUR DOUBLE PRECISION,
	FOREIGN KEY (ID_UTILISATION) REFERENCES UTILISATION(ID_UTILISATION),
	FOREIGN KEY (ID_OFFRE) REFERENCES OFFRE(ID_OFFRE)
);

CREATE TABLE ACHAT_OFFRE 
(
	ID_ACHAT SERIAL PRIMARY KEY,
	ID_UTILISATEUR INT,
	ID_OFFRE INT,
	DATES timestamp,
	FOREIGN KEY (ID_OFFRE) REFERENCES OFFRE(ID_OFFRE),
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);

CREATE TABLE MOUVEMENT_FORFAIT 
(
	ID_MOUVEMENT SERIAL PRIMARY KEY,
	ID_UTILISATEUR INT,
	ID_UTILISATION INT,
	ID_OFFRE INT,
	VALEUR DOUBLE PRECISION,
	TYPE_MOUVEMENT INT, 
	DATES timestamp
);

CREATE TABLE TARIF
(
	ID_TARIF SERIAL PRIMARY KEY,
	ID_OFFRE INT,
	TYPE_TARIF VARCHAR(30),
	VALEUR DOUBLE PRECISION,
	FOREIGN KEY (ID_OFFRE) REFERENCES OFFRE(ID_OFFRE)
);

CREATE TABLE IF NOT EXISTS TAUX_OFFRE
(
	ID_TAUX INT,
	ID_OFFRE INT,
	VALEUR DOUBLE precision,
	SPECIFICATION VARCHAR(30),
	FOREIGN KEY (ID_OFFRE) REFERENCES Offre(ID_OFFRE)
);
CREATE TABLE IF NOT EXISTS OFFRE_MOUVEMENT(
	ID_MOUVEMENT int  PRIMARY KEY,
	ID_OFFRE int,
	VALEUR_OFFRE double precision,
	ID_UTILISATEUR int,
	TYPE_MOUVEMENT int,
	DATE_MOUVEMENT timestamp,
	FOREIGN KEY (ID_OFFRE) REFERENCES Offre(ID_OFFRE),
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);

--OFFRE VALIDE
CREATE OR REPLACE VIEW OFFRE_VALIDE AS SELECT OFFRE_MOUVEMENT.* 
FROM OFFRE,OFFRE_MOUVEMENT 
WHERE OFFRE_MOUVEMENT.ID_OFFRE=OFFRE.ID_OFFRE 
AND (OFFRE_MOUVEMENT.DATE_MOUVEMENT::timestamp+ OFFRE.VALIDITE*interval '24 HOUR')>=NOW();

--STATISTIQUES
--OFFRE LE PLSU ACHETER
SELECT sum(VALEUR_OFFRE),idoffre from OFFRE_MOUVEMENT group by ID_OFFRE;
--VALEUR OFFRE JOUR
CREATE OR REPLACE VIEW STATISTIQUE_OFFRE_JOUR AS select
    date_trunc('DAY', ACHAT_OFFRE.DATES::timestamp) as DATES,
    COUNT(ACHAT_OFFRE.ID_ACHAT) as VALEUR,
    OFFRE.ID_OFFRE,
    OFFRE.NOM_OFFRE
FROM ACHAT_OFFRE,OFFRE
WHERE ACHAT_OFFRE.ID_OFFRE=OFFRE.ID_OFFRE
group by 1,OFFRE.ID_OFFRE;

--VALEUR OFFRE SEMAINE
CREATE OR REPLACE VIEW STATISTIQUE_OFFRE_SEMAINE AS select
    date_trunc('WEEK', ACHAT_OFFRE.DATES::timestamp) as DATES,
    COUNT(ACHAT_OFFRE.ID_ACHAT) as VALEUR,
    OFFRE.ID_OFFRE,
    OFFRE.NOM_OFFRE
FROM ACHAT_OFFRE,OFFRE
WHERE ACHAT_OFFRE.ID_OFFRE=OFFRE.ID_OFFRE
group by 1,OFFRE.ID_OFFRE;



--VALEUR OFFRE MOIS
CREATE OR REPLACE VIEW STATISTIQUE_OFFRE_MOIS AS select
    date_trunc('MONTH', ACHAT_OFFRE.DATES::timestamp) as DATES,
    COUNT(ACHAT_OFFRE.ID_ACHAT) as VALEUR,
    OFFRE.ID_OFFRE,
    OFFRE.NOM_OFFRE
FROM ACHAT_OFFRE,OFFRE
WHERE ACHAT_OFFRE.ID_OFFRE=OFFRE.ID_OFFRE
group by 1,OFFRE.ID_OFFRE;





CREATE TABLE IF NOT EXISTS AchatOffre(
	ID_ACHAT int  PRIMARY KEY,
	ID_OFFRE int,
	ID_UTILISATEUR int,
	TYPES_ACHAT varchar(50),
	DATE_ACHAT timestamp,
	FOREIGN KEY (ID_OFFRE) REFERENCES Offre(ID_OFFRE),
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);
CREATE TABLE IF NOT EXISTS MONEY_MOUVEMENT(
	ID_MOUVEMENT SERIAL PRIMARY KEY,
	ID_UTILISATEUR int,
	TYPES int,
	MONTANT double precision,
	DATE_TRANSACTION timestamp,
	ETAT int,
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);

CREATE TABLE IF NOT EXISTS CREDIT(
	ID_CREDIT int ,
	CODE varchar(15) unique,
	MONTANT double precision,
	DATE_EXPIRATION timestamp
);

CREATE TABLE IF NOT EXISTS CREDIT_MOUVEMENT(
	ID_UTILISATEUR int,
	TYPES int,
	MONTANT double precision,
	DATE_MOUVEMENT timestamp
);
--MOBILE MONEY

CREATE TABLE IF NOT EXISTS TAUX_CREDIT
(
	ID_TAUX SERIAL PRIMARY KEY,
	VALEUR DOUBLE precision,
	ID_UTILISATION INT,
	SPECIFICATION VARCHAR(30),
	FOREIGN KEY (ID_UTILISATION) REFERENCES UTILISATION(ID_UTILISATION)
);

CREATE TABLE IF NOT EXISTS APPEL(
	ID_APPEL serial primary key,
	NUMERO1 varchar(15),
	NUMERO2 varchar(15),
	DUREE double precision,
	DATE_APPEL timestamp
);
CREATE OR REPLACE FUNCTION get_forfait_offre(idOffre int) RETURNS SETOF FORFAIT_OFFRE AS $$
    SELECT *
    FROM FORFAIT_OFFRE WHERE ID_OFFRE = idOffre;
$$ LANGUAGE SQL;

----------get solde credit--------------------
CREATE OR REPLACE FUNCTION get_solde_credit(idUtilisateur int, daty timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION ;
BEGIN
        SELECT  SUM(CREDIT_MOUVEMENT.TYPES*CREDIT_MOUVEMENT.MONTANT) INTO val
        FROM    CREDIT_MOUVEMENT
        WHERE   CREDIT_MOUVEMENT.ID_UTILISATEUR = idUtilisateur 
        AND CREDIT_MOUVEMENT.DATE_MOUVEMENT<=daty;

        RETURN val;
END;
$$  LANGUAGE plpgsql;

----------get solde Money--------------------
CREATE OR REPLACE FUNCTION get_solde_money(idUtilisateur int, dates timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION ;
BEGIN
        SELECT  SUM(MONEY_MOUVEMENT.TYPES*MONEY_MOUVEMENT.MONTANT) INTO val
        FROM    MONEY_MOUVEMENT
        WHERE   MONEY_MOUVEMENT.ID_UTILISATEUR = idUtilisateur 
        AND MONEY_MOUVEMENT.DATE_TRANSACTION<=dates
        AND MONEY_MOUVEMENT.ETAT=1;

        RETURN val;
END;
$$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION acheter_offre(idUtilisateur int,idOffre int,typeAchat varchar(10), dates timestamp)
RETURNS BOOLEAN AS $$
DECLARE solde DOUBLE PRECISION;
		prix_offre DOUBLE PRECISION;
		ligne RECORD;
		val BOOLEAN;
BEGIN
		val=FALSE;
		IF typeAchat='MONEY' THEN
			SELECT get_solde_money(idUtilisateur,dates) INTO solde;
		END IF;

		IF typeAchat='CREDIT' THEN
			SELECT get_solde_credit(idUtilisateur,dates) INTO solde;
		END IF;
		
		
		SELECT OFFRE.PRIX_OFFRE INTO prix_offre
		FROM OFFRE 
		WHERE ID_OFFRE=idOffre;
		
		IF solde>=prix_offre THEN
			
			INSERT INTO ACHAT_OFFRE (ID_UTILISATEUR,ID_OFFRE,DATES) VALUES(idUtilisateur,idOffre,dates);
			IF typeAchat='CREDIT' THEN
				INSERT INTO CREDIT_MOUVEMENT VALUES(idUtilisateur,-1,prix_offre,dates);
			END IF;
			IF typeAchat='CREDIT' THEN
				INSERT INTO MONEY_MOUVEMENT VALUES(idUtilisateur,-1,prix_offre,dates,1);
			END IF;

			FOR ligne IN SELECT*FROM get_forfait_offre(idOffre) 
			LOOP
				INSERT INTO 
				MOUVEMENT_FORFAIT (ID_UTILISATEUR,ID_UTILISATION,ID_OFFRE,VALEUR,TYPE_MOUVEMENT,DATES) 
				VALUES(idUtilisateur,ligne.ID_UTILISATION,idOffre,ligne.VALEUR,1,dates); 
			END LOOP;
			val=TRUE;
		END IF;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;

select acheter_offre(14,1,'MONEY','now()');
 
CREATE OR REPLACE FUNCTION get_mouvement_forfait_valide(daty timestamp) RETURNS SETOF MOUVEMENT_FORFAIT AS $$
    SELECT MOUVEMENT_FORFAIT.* 
	FROM OFFRE,MOUVEMENT_FORFAIT 
	WHERE OFFRE.ID_OFFRE=MOUVEMENT_FORFAIT.ID_OFFRE 
	AND (MOUVEMENT_FORFAIT.DATES::timestamp+ OFFRE.VALIDITE*interval '24 HOUR')>=daty
$$ LANGUAGE SQL;

create or replace function get_solde_forfait(
 	idUtilisateur int,
 	idOffre int,
 	daty timestamp
) 
	returns table (
		ID_UTILISATEUR INT,
		ID_UTILISATION INT,
		VALEUR_FORFAIT DOUBLE PRECISION
	) 
	language plpgsql
as $$
begin
	return query 
		SELECT 
		table1.ID_UTILISATEUR,
		table1.ID_UTILISATION,
		SUM(TYPE_MOUVEMENT*VALEUR) AS VALEUR_FORFAIT
		FROM get_mouvement_forfait_valide(daty) as table1
		WHERE table1.ID_UTILISATEUR=idUtilisateur
		AND table1.ID_OFFRE=idOffre
		GROUP BY table1.ID_UTILISATION,table1.ID_UTILISATEUR;
end;$$;
create or replace function get_solde_forfait_join(
 	idUtilisateur int,
 	idOffre int,
 	daty timestamp
) 
	returns table (
		ID_UTILISATEUR INT,
		ID_UTILISATION INT,
		VALEUR_FORFAIT DOUBLE PRECISION,
		UNITE VARCHAR(10),
		NOM_UTILISATION VARCHAR(20)
	) 
	language plpgsql
as $$
begin
	return query 
		SELECT
		table1.ID_UTILISATEUR,
		table1.ID_UTILISATION,
		table1.VALEUR_FORFAIT,
		UTILISATION.UNITE,
		UTILISATION.NOM_UTILISATION
		FROM get_solde_forfait(idUtilisateur,idOffre,daty) AS table1,UTILISATION
		WHERE table1.ID_UTILISATION=UTILISATION.ID_UTILISATION;
end;$$;
CREATE OR REPLACE FUNCTION consommer_forfait(idUtilisateur int,idOffre int,idUtilisation int,valeur double precision, daty timestamp)
RETURNS BOOLEAN AS $$
DECLARE solde_forfait DOUBLE PRECISION;
		val BOOLEAN;
BEGIN
		val=FALSE;
		
		SELECT VALEUR_FORFAIT INTO solde_forfait 
		FROM get_solde_forfait(idUtilisateur,idOffre,daty) 
		WHERE ID_UTILISATION=idUtilisation;		

		IF valeur>solde_forfait THEN
			valeur=solde_forfait;
		END IF;

		IF(valeur>0) THEN

			INSERT INTO 
			MOUVEMENT_FORFAIT (ID_UTILISATEUR,ID_UTILISATION,ID_OFFRE,VALEUR,TYPE_MOUVEMENT,DATES) 
			VALUES(idUtilisateur,idUtilisation,idOffre,valeur,-1,daty); 
			val=TRUE;
		END IF;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION consommer_credit(idUtilisateur int,idUtilisation int,valeur double precision, daty timestamp)
RETURNS BOOLEAN AS $$
DECLARE soldeCredit DOUBLE PRECISION;
		val BOOLEAN;
BEGIN
		val=FALSE;
		
		SELECT get_solde_credit(idUtilisateur,daty) INTO soldeCredit;

		IF valeur>get_solde_credit THEN
			valeur=soldeCredit;
		END IF;

		IF(valeur>0) THEN

			INSERT INTO CREDIT_MOUVEMENT VALUES(idUtilisateur,1,valeur,daty); 
			val=TRUE;
		END IF;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;
create or replace function TARIF_APPEL(
 	numero1 varchar(20),
 	numero2 varchar(20),
 	idOffre int
) 
	returns table (
		ID_TARIF INT,
		ID_OFFRE INT,
		TYPE_TARIF VARCHAR(30),
		VALEUR DOUBLE PRECISION
	) 
	language plpgsql
as $$
	DECLARE typeTarif varchar(20);
begin
	IF numero1 LIKE '034%' AND numero2 LIKE '034%' THEN
		typeTarif='MEME OPERATEUR';
	END IF;
	IF numero1 LIKE '034%' AND numero2 NOT LIKE '034%' THEN
		typeTarif='AUTRE OPERATEUR';
	END IF;
	return query 
		SELECT * FROM TARIF 
		WHERE TARIF.TYPE_TARIF=typeTarif 
		AND TARIF.ID_OFFRE=idOffre;
end;$$;
create or replace function taux_credit(
 	numero1 varchar(20),
 	numero2 varchar(20)
) 
	returns table (
		ID_TAUX INT,
		VALEUR DOUBLE precision,
		ID_UTILISATION INT,
		SPECIFICATION VARCHAR(30)
	) 
	language plpgsql
as $$
	DECLARE spec varchar(20);
begin
	IF numero1 LIKE '034%' AND numero2 LIKE '034%' THEN
		spec='MEME OPERATEUR';
	END IF;
	IF numero1 LIKE '034%' AND numero2 NOT LIKE '034%' THEN
		spec='AUTRE OPERATEUR';
	END IF;
	return query 
		SELECT * FROM TAUX_CREDIT AS T1
		WHERE T1.SPECIFICATION=spec
		AND T1.ID_UTILISATION=1;
end;$$;
CREATE OR REPLACE FUNCTION cout_appel(numero1 varchar(20),numero2 varchar(20),idOffre int,duree double precision)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION;
		tarifAppel double precision;
BEGIN
		
		
		SELECT valeur INTO tarifAppel
		FROM TARIF_APPEL(numero1,numero2,idOffre);
		val=tarifAppel*duree;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;
create or replace function get_priorite_offre(idUtilisation int) 
	returns table (
		ID_OFFRE INT
	) 
	language plpgsql
as $$
begin
	
	return query 
		SELECT OFFRE.ID_OFFRE 
		FROM OFFRE,FORFAIT_OFFRE 
		WHERE FORFAIT_OFFRE.ID_UTILISATION=idUtilisation 
		AND OFFRE.ID_OFFRE=FORFAIT_OFFRE.ID_OFFRE
		ORDER BY OFFRE.VALIDITE ASC;
end;$$;
CREATE OR REPLACE FUNCTION duree_of_forfait(idUtilisateur int,numero2 varchar(20),idOffre int,dates timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION;
		tarifAppel double precision;
		numero1 VARCHAR(20);
		soldeForfait DOUBLE precision;
BEGIN
		----GET NUMERO1
		SELECT UTILISATEUR.TELEPHONE INTO numero1
		FROM UTILISATEUR 
		WHERE UTILISATEUR.ID_UTILISATEUR=idUtilisateur;

		SELECT valeur INTO tarifAppel
		FROM TARIF_APPEL(numero1,numero2,idOffre);
		
		----GET SOLDE FORFAIT APPEL

		SELECT TABLE1.VALEUR_FORFAIT INTO soldeForfait
		FROM  get_solde_forfait(idUtilisateur,idOffre,dates) AS TABLE1
		WHERE TABLE1.ID_UTILISATION=1;	

		---tarifAppel=5Ar/s
		---solde_forfait=200Ar
		---duree_forfait=soldeForfait/tarifAppel
        
        val=soldeForfait/tarifAppel;

        RETURN val;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION duree_of_all_forfait(idUtilisateur int,numero2 varchar(20),daty timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION;
		tarifAppel double precision;
		numero1 VARCHAR(20);
		soldeForfait DOUBLE precision;
BEGIN
		SELECT SUM(duree_of_forfait(TABLE1.ID_UTILISATEUR,numero2,TABLE1.ID_OFFRE,TABLE1.DATES)) INTO val
		FROM get_mouvement_forfait_valide(daty) AS TABLE1 WHERE TABLE1.ID_UTILISATEUR=idUtilisateur
		AND TABLE1.ID_UTILISATION=1;

        RETURN val;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION duree_credit(idUtilisateur int,numero2 varchar(20),daty timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION;
		tauxCredit double precision;
		numero1 VARCHAR(20);
		soldeCredit DOUBLE precision;
BEGIN
		SELECT UTILISATEUR.TELEPHONE INTO numero1 
		FROM UTILISATEUR 
		WHERE UTILISATEUR.ID_UTILISATEUR=idUtilisateur;
		SELECT TABLE1.VALEUR INTO tauxCredit FROM taux_credit(numero1,numero2) AS TABLE1;
		SELECT get_solde_credit(idUtilisateur,daty) INTO soldeCredit;

		val=soldeCredit/tauxCredit;

        RETURN val;
END;
$$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION duree_credit_forfait(idUtilisateur int,numero2 varchar(20),daty timestamp)
RETURNS DOUBLE PRECISION AS $$
DECLARE val DOUBLE PRECISION;
		dureeCredit double precision;
		dureeForfait double precision;
BEGIN
		
		SELECT duree_credit(idUtilisateur,numero2,daty) INTO dureeCredit;
		SELECT duree_of_all_forfait(idUtilisateur,numero2,daty) INTO dureeForfait;
		val=dureeCredit+dureeForfait;

        RETURN val;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION appeler(idUtilisateur int,numero2 varchar(20),duree double precision,dates timestamp)
RETURNS BOOLEAN AS $$
DECLARE val BOOLEAN;
		tarifAppel double precision;
		numero1 VARCHAR(20);
		coutAppel double precision;
		idUtilisation int;
		ligne RECORD;
		dureeTemp double precision;
		dureeForfait double precision;
		dureeEncours double precision;
		tauxCredit double precision;
		coutCredit double precision;
		soldeCredit double precision;
BEGIN
		val=FALSE;
		---GET NUMERO OF THE USER
		SELECT UTILISATEUR.TELEPHONE INTO numero1 
		FROM UTILISATEUR 
		WHERE UTILISATEUR.ID_UTILISATEUR=idUtilisateur;
		
        ---GET ID_UTILISATION APPEL
        SELECT UTILISATION.ID_UTILISATION INTO idUtilisation
        FROM UTILISATION 
        WHERE UTILISATION.NOM_UTILISATION='APPEL';
        
        dureeTemp=duree;
        FOR ligne IN SELECT*FROM get_priorite_offre(idUtilisation) 
			LOOP
				SELECT duree_of_forfait(idUtilisateur,numero2,ligne.ID_OFFRE,dates) INTO dureeForfait;
				IF dureeTemp>dureeForfait THEN
					dureeTemp=dureeTemp-dureeForfait;
					dureeEncours=dureeForfait;
				END IF;
				IF dureeTemp<=dureeForfait THEN
					dureeEncours=dureeTemp;
					dureeTemp=0;
				END IF;
				SELECT cout_appel(numero1,numero2,ligne.ID_OFFRE,dureeEncours) INTO coutAppel;
				SELECT consommer_forfait(idUtilisateur,ligne.ID_OFFRE,idUtilisation,coutAppel, dates) INTO val;
				IF dureeTemp=0 THEN
					VAL=TRUE;
					EXIT;
				END IF; 

			END LOOP;

		IF dureeTemp>0 THEN
			val=FALSE;
			SELECT VALEUR INTO tauxCredit FROM taux_credit(numero1,numero2);
			SELECT get_solde_credit(idUtilisateur,dates) INTO soldeCredit;

			coutCredit=tauxCredit*dureeTemp;

			IF soldeCredit<coutCredit THEN
				coutCredit=soldeCredit;
			END IF;
			INSERT INTO CREDIT_MOUVEMENT VALUES(idUtilisateur,-1,coutCredit,dates);
			val=TRUE;

		END IF;
		INSERT INTO APPEL (NUMERO1,NUMERO2,DUREE,DATE_APPEL) VALUES(numero1,numero2,duree-dureeTemp,dates);

        RETURN val;
END;
$$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION achat_credit_money(idUtilisateur int,montantCredit double precision,dates timestamp)
RETURNS BOOLEAN AS $$
DECLARE solde DOUBLE PRECISION;
		prix_offre DOUBLE PRECISION;
		ligne RECORD;
		val BOOLEAN;
BEGIN
		val=FALSE;
		
		SELECT get_solde_money(idUtilisateur,dates) INTO solde;
		
		IF solde>=montantCredit THEN
			INSERT INTO CREDIT_MOUVEMENT VALUES(idUtilisateur,1,montantCredit,dates);
			val=TRUE;
		END IF;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION achat_credit_code(idUtilisateur int,cody varchar(15),dates timestamp)
RETURNS BOOLEAN AS $$
DECLARE montants INT;
		idCredits INT;
		val BOOLEAN;
BEGIN
		val=FALSE;
		
		SELECT MONTANT INTO montants	
		FROM CREDIT WHERE CODE=cody;
		
		SELECT ID_CREDIT INTO idCredits	
		FROM CREDIT WHERE CODE=cody;
		
		
		IF Montant!=NULL THEN
			INSERT INTO CREDIT_MOUVEMENT VALUES(idUtilisateur,1,montants,dates);
			DELETE FROM CREDIT WHERE ID_CREDIT=idCredits;
			val=TRUE;
		END IF;	
        
        RETURN val;
END;
$$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION valider_transaction(idMouvement int)
RETURNS BOOLEAN AS $$
DECLARE val BOOLEAN;
BEGIN
		IF idMouvement<0 THEN
			UPDATE MONEY_MOUVEMENT SET ETAT=1 WHERE types=1;	
		END IF;	
		UPDATE MONEY_MOUVEMENT SET ETAT=1 WHERE ID_MOUVEMENT=idMouvement;	
        RETURN TRUE;
END;
$$  LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_mouvement_non_valide(daty timestamp) RETURNS SETOF MONEY_MOUVEMENT AS $$
    SELECT *
    FROM MONEY_MOUVEMENT WHERE ETAT = 0
    AND DATE_TRANSACTION<=daty;
$$ LANGUAGE SQL;
CREATE OR REPLACE FUNCTION get_forfait_offre(idOffre int) RETURNS SETOF FORFAIT_OFFRE AS $$
    SELECT *
    FROM FORFAIT_OFFRE WHERE ID_OFFRE = idOffre;
$$ LANGUAGE SQL;
create or replace function historique_appel(idUtilisateur int,type varchar(20),dates timestamp) 
	returns table (
		ID_APPEL int,
		NUMERO1 varchar(15),
		NUMERO2 varchar(15),
		DUREE double precision,
		DATE_APPEL timestamp
	) 
	language plpgsql
as $$
DECLARE numero varchar(20);
begin
	
	SELECT TELEPHONE INTO numero 
	FROM UTILISATEUR 
	WHERE ID_UTILISATEUR=idUtilisateur;

	IF type='ENTREE' THEN
		return query 
		SELECT *
		FROM APPEL 
		WHERE APPEL.numero2=numero 
		AND APPEL.DATE_APPEL<=dates;
	END IF;	

	IF type='SORTIE' THEN
		return query 
		SELECT *
		FROM APPEL 
		WHERE APPEL.numero1=numero 
		AND APPEL.DATE_APPEL<=dates;
	END IF;		
end;$$;
CREATE OR REPLACE FUNCTION depot_money(idUtilisateur int,montants double precision,daty timestamp)
RETURNS BOOLEAN AS $$
DECLARE val BOOLEAN;

BEGIN
		INSERT INTO MONEY_MOUVEMENT(ID_UTILISATEUR,TYPES,MONTANT,DATE_TRANSACTION,ETAT) 
		VALUES (idUtilisateur,1,montants,daty,0);
		RETURN TRUE;
END;
$$  LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION retrait_money(idUtilisateur int,montants double precision,daty timestamp)
RETURNS BOOLEAN AS $$
DECLARE val BOOLEAN;
		solde double precision;
BEGIN
		val=FALSE;
		SELECT get_solde_money(idUtilisateur, daty) INTO solde;
		IF solde>=montants THEN

			INSERT INTO MONEY_MOUVEMENT(ID_UTILISATEUR,TYPES,MONTANT,DATE_TRANSACTION,ETAT) 
			VALUES (idUtilisateur,-1,montants,daty,1);
			val=TRUE;
		END IF;
		RETURN val;
END;
$$  LANGUAGE plpgsql;

CREATE VIEW Forfait AS SELECT fo.Id_forfait_offre,o.Nom_offre ,u.nom_utilisation,fo.VAleur,u.unite
FROM FORFAIT_OFFRE fo join OFFRE o on fo.Id_offre=o.Id_offre
Join Utilisation u on fo.ID_UTILISATion=u.ID_UTILISATion;

--------------------------OFFRE UTILISATEUR-------------------------------
CREATE OR REPLACE FUNCTION get_offre_utilisateur(idUtilisateur int,daty timestamp) RETURNS SETOF OFFRE AS $$
    SELECT DISTINCT(OFFRE.*) 
	FROM OFFRE,ACHAT_OFFRE
	WHERE OFFRE.ID_OFFRE=ACHAT_OFFRE.ID_OFFRE 
	AND (ACHAT_OFFRE.DATES::timestamp+ OFFRE.VALIDITE*interval '24 HOUR')>=daty
$$ LANGUAGE SQL;


CREATE VIEW MONEYNONVALIDE as 
	SELECT m.ID_MOUVEMENT,u.ID_UTILISATEUR,m.TYPES,u.TELEPHONE,m.DATE_TRANSACTION,m.MONTANT,m.ETAT
	FROM UTILISATEUR u join MONEY_MOUVEMENT m on u.ID_UTILISATEUR =m.ID_UTILISATEUR 
	where m.TYPES=1 and etat=0;











