
--------------------------------
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
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Njanah','Njanah','Njanah','0349726520', crypt('12345', gen_salt('bf', 8)),'ADMIN');
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Admin','Admin','S518Moyenne','0349911520', crypt('12345', gen_salt('bf', 8)),'ADMIN');

-----------------Utilisation-------------------------
INSERT INTO UTILISATION VALUES(1,'APPEL','ARIARY');
INSERT INTO UTILISATION VALUES(2,'INTERNET','Mo');
INSERT INTO UTILISATION VALUES(3,'SMS','SMS');
-----------------MONEY MMOUVEMENT--------------------
INSERT INTO MONEY_MOUVEMENT VALUES(6,14,1,5000,'2021-03-31',0);
INSERT INTO MONEY_MOUVEMENT VALUES(7,16,1,5000,'2021-03-31',0);
INSERT INTO MONEY_MOUVEMENT VALUES(8,13,1,5000,'2021-03-31',0);
------------------OFRRE------------------------------------
INSERT INTO OFFRE VALUES(1,'TELMA MORA',500,1,'2011-01-01');
INSERT INTO OFFRE VALUES(2,'FIRST',5000,7,'2010-01-01');
----------------ACHAT OFFRE ------------------------------------------
INSERT INTO ACHAT_offre VALUES(1,14,1,'2021-03-05 15:12:00');
INSERT INTO ACHAT_offre VALUES(2,14,1,'2021-03-05 15:12:00');
INSERT INTO ACHAT_offre VALUES(3,14,2,'2021-03-05 15:12:00');
INSERT INTO ACHAT_offre VALUES(4,14,2,'2021-03-06 15:12:00');
INSERT INTO ACHAT_offre VALUES(5,14,2,'2021-03-07 15:12:00');
INSERT INTO ACHAT_offre VALUES(6,14,2,'2021-03-07 15:15:00');
INSERT INTO ACHAT_offre VALUES(7,14,2,'2021-03-07 15:15:00');
INSERT INTO ACHAT_offre VALUES(8,14,2,'2021-03-08 10:12:00');
INSERT INTO ACHAT_offre VALUES(9,14,1,'2021-03-08 15:12:00');
INSERT INTO ACHAT_offre VALUES(10,14,1,'2021-03-09 15:12:00');
---------------------------------------------------------------------
INSERT INTO MONEY_MOUVEMENT values (3,14,1,50000,'2021-03-18 00:00:00',1);
INSERT INTO MONEY_MOUVEMENT values (4,14,1,10000,'2021-04-18 00:00:00',0);
---------------------------------------
INSERT INTO OFFRE VALUES(2,'FETY MORA','2019-01-01',1,800);
---------------
INSERT INTO offre_mouvement values(1,1,800,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(2,1,800,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(3,2,800,1,1,'2021-03-09' );
INSERT INTO offre_mouvement values(4,2,800,1,1,'2021-03-10' );
INSERT INTO offre_mouvement values(5,2,2,1,1,'2021-03-10' );