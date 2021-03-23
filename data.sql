SELECT array_to_json(array_agg(row_to_json (r))) FROM ( select nom,prenom,login,telephone from utilisateur) r;
--------------------ADD DIGEST----------------
create extension pgcrypto;
-----------------------UTILISATEUR-------------------------------
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Francois','Fran56','0381569876',digest('1234','sha1'));
INSERT INTO UTILISATEUR VALUES(nextval('user_seq'),'Rakoto','Jean','RakotoJean','0385669876', crypt('12345', gen_salt('bf', 8)),'USER');
----------Admin------------------------
INSERT INTO  UserAdmin values (nextval('user_seq'),'Njanah','Feliciano','Njanah','0343941152', crypt('12345', gen_salt('bf', 8)),'ADMIN');
INSERT INTO  UserAdmin values (nextval('user_seq'),'Notahina','','Nota','0343941152', crypt('12345', gen_salt('bf', 8)),'ADMIN');

INSERT INTO MONEY_MOUVEMENT VALUES(nexvat('money_seq'),12,'depot',5000,'2021-03-07 10:11:00',0);
