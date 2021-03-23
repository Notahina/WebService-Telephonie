SELECT array_to_json(array_agg(row_to_json (r))) FROM ( select nom,prenom,login,telephone from utilisateur) r;
--------------------ADD DIGEST----------------
create extension pgcrypto;
-----------------------UTILISATEUR-------------------------------
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Jean','RakotoJean','0345669876', crypt('12345', gen_salt('bf', 8)),'USER');
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Jean','Pierre','0347829876', crypt('12345', gen_salt('bf', 8)),'USER');
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'RaBe','Pierrette','Pierety','0345869876', crypt('12345', gen_salt('bf', 8)),'USER');
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Jeannette','Jeannette','0345669176', crypt('12345', gen_salt('bf', 8)),'USER');
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Boto','Boto','0345669816', crypt('12345', gen_salt('bf', 8)),'USER');

INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Notahina','Notahina','Nota','0349815520', crypt('12345', gen_salt('bf', 8)),'ADMIN');


INSERT INTO MONEY_MOUVEMENT VALUES(nexvat('money_seq'),12,'depot',5000,'2021-03-07 10:11:00',0);
	ID_MOUVEMENT int PRIMARY KEY,
	ID_UTILISATEUR int,
	TYPES varchar(30),
	MONTANT double precision,
	DATE_TRANSACTION TIMESTAMP,
	ETAT int,
	FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID_UTILISATEUR)
);

INSERT INTO OFFRE VALUES(2,'FETY MORA','2019-01-01 ',1,'APPEL',800);
---------------
INSERT INTO offre_mouvement values(1,1,1,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(2,1,1,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(3,2,1,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(4,2,2,1,1,'2021-03-10' );
INSERT INTO offre_mouvement values(5,2,2,1,1,'2021-03-10' );