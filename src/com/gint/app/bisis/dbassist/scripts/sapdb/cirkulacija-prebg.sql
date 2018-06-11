CREATE TABLE branch ( id INTEGER NOT NULL, name VARCHAR(50) UNICODE NOT NULL, last_user_id INTEGER NULL, PRIMARY KEY (id))
CREATE TABLE language ( id INTEGER NOT NULL , name VARCHAR(30) UNICODE NOT NULL, PRIMARY KEY (id))
CREATE TABLE user_types( id INTEGER NOT NULL, name VARCHAR(25) UNICODE NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR(8) UNICODE, PRIMARY KEY (id))
CREATE TABLE users( id VARCHAR(11) NOT NULL, city VARCHAR(45) UNICODE NULL, zipcode INTEGER NULL, address VARCHAR(150) UNICODE NULL, sign_date DATE NULL, warning NUMBER(1) NULL, phone VARCHAR(50) UNICODE NULL, email VARCHAR(50) UNICODE NULL, user_type INTEGER NOT NULL, branch_id INTEGER NULL, branch_card VARCHAR(20) UNICODE NULL, branch_sign_date DATE NULL, PRIMARY KEY (id), FOREIGN KEY (user_type) REFERENCES USER_TYPES (id), FOREIGN KEY (branch_id) REFERENCES BRANCH (id))
CREATE TABLE edu_lvl( id INTEGER NOT NULL, name VARCHAR(30) UNICODE NOT NULL, PRIMARY KEY (id))
CREATE TABLE user_categs( id INTEGER NOT NULL, name VARCHAR(30) UNICODE NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR(8) UNICODE NULL, PRIMARY KEY (id))
CREATE TABLE mmbr_types( id INTEGER NOT NULL, name VARCHAR(100) UNICODE NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR(8) UNICODE NULL, PRIMARY KEY (id) )
CREATE TABLE groups( inst_name VARCHAR(45) UNICODE NOT NULL, sec_address VARCHAR(45) UNICODE NULL, sec_city VARCHAR(30) UNICODE NULL, sec_zip INTEGER NULL, sec_phone VARCHAR(30) UNICODE NULL, fax VARCHAR(15) UNICODE NULL, telex VARCHAR(15) UNICODE NULL, cont_fname VARCHAR(30) UNICODE NULL, cont_lname VARCHAR(30) UNICODE NULL, cont_email VARCHAR(30) UNICODE NULL, user_id VARCHAR(11) NOT NULL, PRIMARY KEY (user_id), FOREIGN KEY (user_id) REFERENCES USERS (id) )
CREATE TABLE single( dob DATE NULL, first_name VARCHAR(30) UNICODE NULL, permission NUMBER(1) NULL, last_name VARCHAR(30) UNICODE NULL, occupation VARCHAR(30) UNICODE NULL, country VARCHAR(30) UNICODE NULL, password VARCHAR(8) UNICODE NULL, passport VARCHAR(15) UNICODE NULL, organization VARCHAR(150) UNICODE NULL, org_address VARCHAR(150) UNICODE NULL, org_city VARCHAR(30) UNICODE NULL, org_zip INTEGER NULL, org_phone VARCHAR(30) UNICODE NULL, temp_addr VARCHAR(150) UNICODE NULL, temp_city VARCHAR(50) UNICODE NULL, temp_zip INTEGER NULL, temp_phone VARCHAR(15) UNICODE NULL, index_no VARCHAR(15) UNICODE NULL, jmbg CHAR(13) NULL, edu_lvl INTEGER NULL, user_ctgr INTEGER NOT NULL, mmbr_type INTEGER NOT NULL, grp_id VARCHAR(11) NULL, user_id VARCHAR(11) NOT NULL, parent_name VARCHAR(30) UNICODE, doc_id integer, doc_no VARCHAR(15) UNICODE, doc_city VARCHAR(30) UNICODE, title VARCHAR(30) UNICODE, note VARCHAR(200) UNICODE, interests VARCHAR(60) UNICODE, gender integer, language INTEGER NULL, age INTEGER NULL, PRIMARY KEY (user_id), FOREIGN KEY (edu_lvl) REFERENCES EDU_LVL (id), FOREIGN KEY (user_ctgr) REFERENCES USER_CATEGS (id), FOREIGN KEY (mmbr_type) REFERENCES MMBR_TYPES (id), FOREIGN KEY (grp_id) REFERENCES GROUPS (user_id), FOREIGN KEY (user_id) REFERENCES USERS (id), FOREIGN KEY (language) REFERENCES LANGUAGE (id))
CREATE TABLE institutions( inst_name VARCHAR(45) UNICODE NOT NULL, cont_fname VARCHAR(30) UNICODE NOT NULL, cont_lname VARCHAR(30) UNICODE NOT NULL, cont_email VARCHAR(30) UNICODE NULL, cont_phone VARCHAR(15) UNICODE NULL, sec_phone VARCHAR(15) UNICODE NULL, fax VARCHAR(15) UNICODE NULL, telex VARCHAR(15) UNICODE NULL, user_id VARCHAR(11) NOT NULL, PRIMARY KEY (user_id), FOREIGN KEY (user_id) REFERENCES USERS (id) )
CREATE TABLE slikovnice( user_id VARCHAR(11) NOT NULL, datum DATE NOT NULL, izdato INTEGER NULL, vraceno INTEGER NULL, stanje INTEGER NULL, bib_id CHAR(50) UNICODE NOT NULL, PRIMARY KEY (user_id, datum), FOREIGN KEY (user_id) REFERENCES SINGLE (user_id))
CREATE TABLE membership( user_ctgr INTEGER NOT NULL, mmbr_type INTEGER NOT NULL, mdate DATE NOT NULL, cost NUMBER(8,2) NULL, hard_curr NUMBER(8,2) NULL, PRIMARY KEY (mdate, user_ctgr, mmbr_type), FOREIGN KEY (user_ctgr) REFERENCES USER_CATEGS (id), FOREIGN KEY (mmbr_type) REFERENCES MMBR_TYPES (id))
CREATE TABLE signing( single_id VARCHAR(11) NOT NULL, bib_id CHAR(50) UNICODE NOT NULL, sdate DATE NOT NULL, cost NUMBER(8,2) NULL, hard_curr NUMBER(8,2) NULL, receipt_id VARCHAR(30) UNICODE NULL, until_date DATE NULL, PRIMARY KEY (sdate, bib_id, single_id), FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
CREATE TABLE officials( id INTEGER NOT NULL, last_name VARCHAR(30) UNICODE NOT NULL, first_name VARCHAR(15) UNICODE NOT NULL, email VARCHAR(45) UNICODE NULL, PRIMARY KEY (id))
CREATE TABLE location( id INTEGER NOT NULL, name VARCHAR(100) UNICODE NOT NULL, PRIMARY KEY (id))
CREATE TABLE lend_types( id INTEGER NOT NULL, name VARCHAR(45) UNICODE NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR(8) UNICODE NULL, PRIMARY KEY (id))
CREATE TABLE lending( ctlg_no VARCHAR(30) UNICODE NOT NULL, single_id VARCHAR(11) NOT NULL, lend_date DATE NOT NULL, return_date DATE NULL, resume_date DATE NULL, bib_id CHAR(50) UNICODE NOT NULL, lend_type INTEGER NOT NULL, location INTEGER NOT NULL, description VARCHAR(30) UNICODE NULL, counter INTEGER NOT NULL, PRIMARY KEY (lend_date, single_id, ctlg_no, counter), FOREIGN KEY (single_id) REFERENCES SINGLE (user_id), FOREIGN KEY (lend_type) REFERENCES LEND_TYPES (id), FOREIGN KEY (location) REFERENCES LOCATION (id))
CREATE TABLE circ_docs( sig VARCHAR(255) UNICODE NULL, ctlg_no VARCHAR(255) UNICODE NOT NULL, docid NUMBER(38) NOT NULL, status INTEGER NOT NULL, rcv_req_id INTEGER, type VARCHAR(10) UNICODE NULL, state CHAR(15) UNICODE NULL, PRIMARY KEY (ctlg_no) )
CREATE TABLE reservations( single_id VARCHAR(11) NOT NULL, ctlg_no VARCHAR(30) UNICODE NOT NULL, rsrv_date DATE NOT NULL, notification NUMBER(1) NOT NULL, resume_date DATE NULL, bib_id CHAR(50) UNICODE NOT NULL, PRIMARY KEY (rsrv_date, ctlg_no, single_id), FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
CREATE TABLE publications_reservations( ctlg_no VARCHAR(30) UNICODE NOT NULL, rsrv_date DATE NOT NULL, single_id VARCHAR(11) NOT NULL, pub_ctlg_no VARCHAR(30) UNICODE NOT NULL, PRIMARY KEY (ctlg_no, rsrv_date, single_id, pub_ctlg_no), FOREIGN KEY (rsrv_date, pub_ctlg_no, single_id) REFERENCES RESERVATIONS ( rsrv_date, ctlg_no, single_id))
CREATE TABLE self_rsrv( single_id VARCHAR(11) NOT NULL, ctlg_no VARCHAR(30) UNICODE NOT NULL, rsrv_date DATE NOT NULL, notification NUMBER(1) NOT NULL, resume_date DATE NULL, PRIMARY KEY (rsrv_date, ctlg_no, single_id), FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
CREATE TABLE lend_brw( id INTEGER NOT NULL, author VARCHAR(60) UNICODE NOT NULL, payment VARCHAR(15) UNICODE NOT NULL, title VARCHAR(60) UNICODE NOT NULL, cost NUMBER(8,2) NOT NULL, type VARCHAR(15) UNICODE NOT NULL, pieces_no INTEGER NULL, comments VARCHAR(60) UNICODE NULL, hard_curr NUMBER(8,2) NULL, PRIMARY KEY (id))
CREATE TABLE received_req( lendbrw_id INTEGER NOT NULL, accept_date DATE NOT NULL, institution VARCHAR(45) UNICODE NOT NULL, lend_date DATE NULL, return_date DATE NULL, inst_id VARCHAR(11) NOT NULL, PRIMARY KEY (lendbrw_id), FOREIGN KEY (inst_id) REFERENCES INSTITUTIONS (user_id), FOREIGN KEY (lendbrw_id) REFERENCES LEND_BRW(id))
CREATE TABLE sent_req( lendbrw_id INTEGER NOT NULL, send_date DATE NOT NULL, institution VARCHAR(45) UNICODE NOT NULL, accept_date DATE NULL, return_date DATE NULL, inst_id VARCHAR(11) NOT NULL, PRIMARY KEY (lendbrw_id), FOREIGN KEY (inst_id) REFERENCES INSTITUTIONS (user_id), FOREIGN KEY (lendbrw_id) REFERENCES LEND_BRW(id))
CREATE TABLE borrowed_pub( id INTEGER NOT NULL, catalogue_no VARCHAR(30) UNICODE NOT NULL, author VARCHAR(60) UNICODE NOT NULL, title VARCHAR(60) UNICODE NOT NULL, sent_req_id INTEGER NOT NULL, PRIMARY KEY (id), FOREIGN KEY (sent_req_id) REFERENCES SENT_REQ (lendbrw_id))
CREATE TABLE warning_types( id INTEGER NOT NULL, wtext VARCHAR(500) UNICODE NOT NULL, PRIMARY KEY (id))
CREATE TABLE warnings( warn_type INTEGER NOT NULL, user_id VARCHAR(11) NOT NULL, ctlg_no VARCHAR(30) UNICODE NOT NULL, wdate DATE NOT NULL, returned CHAR(1) UNICODE NOT NULL, PRIMARY KEY (warn_type, user_id, ctlg_no), FOREIGN KEY (warn_type) REFERENCES WARNING_TYPES (id), FOREIGN KEY (user_id) REFERENCES USERS (id))
CREATE INDEX USER_USTP_IDX ON USERS(user_type )
CREATE INDEX USER_BRCH_IDX ON USERS(branch_id )
CREATE INDEX SNGL_USCT_IDX ON SINGLE( user_ctgr )
CREATE INDEX SNGL_GRP_IDX ON SINGLE(grp_id )
CREATE INDEX SNGL_EDU_IDX ON SINGLE(edu_lvl )
CREATE INDEX SNGL_MBTP_IDX ON SINGLE(mmbr_type )
CREATE INDEX SNGL_LANG_IDX ON SINGLE(language )
CREATE INDEX SNRQ_INST_IDX ON SENT_REQ(inst_id)
CREATE INDEX BRPB_SNRQ_IDX ON BORROWED_PUB(sent_req_id)
CREATE INDEX PUBL_RSRV_PUBL_IDX ON PUBLICATIONS_RESERVATIONS(ctlg_no )
CREATE INDEX PUB_REZ_REZ_IDX ON PUBLICATIONS_RESERVATIONS( rsrv_date, single_id, pub_ctlg_no )
CREATE INDEX RCRQ_INST_IDX ON RECEIVED_REQ(inst_id)
CREATE INDEX RSRV_SNGL_IDX ON RESERVATIONS(single_id)
CREATE INDEX RSRV_PUBL_IDX ON RESERVATIONS(ctlg_no )
CREATE INDEX SLFR_SNGL_IDX ON SELF_RSRV(single_id )
CREATE INDEX SLFR_PUBL_IDX ON SELF_RSRV(ctlg_no )
CREATE INDEX SIGN_SNGL_IDX ON SIGNING (single_id )
CREATE INDEX MMBR_USCT_IDX ON MEMBERSHIP(user_ctgr)
CREATE INDEX MMBR_MBTP_IDX ON MEMBERSHIP(mmbr_type)
CREATE INDEX LEND_SNGL_IDX ON LENDING(single_id )
CREATE INDEX LEND_PUBL_IDX ON LENDING(ctlg_no )
CREATE INDEX LEND_LNDT_IDX ON LENDING(lend_type ,location )
CREATE INDEX WARN_DATE_IDX ON WARNINGS(wdate)
CREATE INDEX CIRC_DOCS_IDX ON CIRC_DOCS(docid)
CREATE INDEX CIRC_DOCS_2 ON CIRC_DOCS(sig)
INSERT INTO user_types VALUES(1,'Individue',5,10)
INSERT INTO user_types VALUES(2,'Kolektivni',10,15)
INSERT INTO user_types VALUES(3,'Instit Razmene',30,30)
INSERT INTO user_categs VALUES(1,'Deca do 14 godina',15,15)
INSERT INTO user_categs VALUES(2,'U~enici srednjih {kola',15,15)
INSERT INTO user_categs VALUES(3,'Studenti',15,15)
INSERT INTO user_categs VALUES(4,'Zaposleni',15,15)
INSERT INTO user_categs VALUES(5,'Penzioneri',15,15)
INSERT INTO user_categs VALUES(6,'Zemljoradnici',15,15)
INSERT INTO user_categs VALUES(7,'Ostali',15,15)
INSERT INTO users VALUES (0, 'x', 123, 'x', TRUNC(SYSDATE), 0, NULL, NULL, 1, NULL, NULL, NULL)
INSERT INTO edu_lvl VALUES(0,'x')
INSERT INTO edu_lvl VALUES(1,'Osnovno')
INSERT INTO edu_lvl VALUES(2,'Srednje')
INSERT INTO edu_lvl VALUES(3,'Vi{e')
INSERT INTO edu_lvl VALUES(4,'Visoko')
INSERT INTO mmbr_types VALUES(1,'R(redovno pla}anje)',5,365)
INSERT INTO mmbr_types VALUES(2,'U1(deca do 14 godina)',5,365)
INSERT INTO mmbr_types VALUES(3,'U2(penzioneri)',5,365)
INSERT INTO mmbr_types VALUES(4,'U3(vojnici)',5,365)
INSERT INTO mmbr_types VALUES(5,'U4(iz RO)',5,365)
INSERT INTO mmbr_types VALUES(6,'U5(50%popust)',5,365)
INSERT INTO mmbr_types VALUES(7,'UE(EURO-26 20%popust)',5,365)
INSERT INTO mmbr_types VALUES(8,'UK(u~enici)',5,365)
INSERT INTO mmbr_types VALUES(9,'PP(porodi~no-pla}anje)',5,365)
INSERT INTO mmbr_types VALUES(10,'PB(porodi~no-besplatno)',5,365)
INSERT INTO mmbr_types VALUES(11,'B1(slu`bena zadu`enja)',5,365)
INSERT INTO mmbr_types VALUES(12,'B2(darodavci)',5,365)
INSERT INTO mmbr_types VALUES(13,'B3(sa knjigom)',5,365)
INSERT INTO mmbr_types VALUES(14,'B4(socijalni slu~ajevi)',5,365)
INSERT INTO mmbr_types VALUES(15,'B5(po~asni ~lanovi)',5,365)
INSERT INTO mmbr_types VALUES(16,'B6(iz drugih ogranaka)',5,365)
INSERT INTO mmbr_types VALUES(17,'BP(prvaci)',5,365)
INSERT INTO mmbr_types VALUES(18,'CM(jedan mesec)',5,30)
INSERT INTO mmbr_types VALUES(19,'CT(tri meseca)',5,90)
INSERT INTO mmbr_types VALUES(20,'CP({est meseci)',5,180)
INSERT INTO mmbr_types VALUES(21,'N(posebna zadu`enja)',5,180)
insert into location (id, name) values (0,'Gradska biblioteka NS') 
insert into location (id, name) values (1,'@ura Dani~i}')
insert into location (id, name) values (2,'Stevan Sremac')
insert into location (id, name) values (3,'Petefi [andor')
insert into location (id, name) values (4,'To{a Trifunov')
insert into location (id, name) values (5,'Kosta Trifkovi}')
insert into location (id, name) values (6,'Jovan Jovanovi} Zmaj')
insert into location (id, name) values (7,'De~je odeljenje')
insert into location (id, name) values (8,'Vidovdansko naselje')
insert into location (id, name) values (9,'Petar Koci}')
insert into location (id, name) values (10,'Milica Stojadinovi} Srpkinja')
insert into location (id, name) values (11,'\orde Aracki')
insert into location (id, name) values (12,'Majur')
insert into location (id, name) values (13,'Mihal Babinka')
insert into location (id, name) values (14,'Endre Adi')
insert into location (id, name) values (15,'7. jul')
insert into location (id, name) values (16,'Nikola Tesla')
insert into location (id, name) values (17,'Jovan Jovanovi} Zmaj')
insert into location (id, name) values (18,'\ura Jak{i}')
insert into location (id, name) values (19,'Veljko Petrovi}')
insert into location (id, name) values (20,'Laza Kosti}')
insert into location (id, name) values (21,'Rumunska baza')
insert into location (id, name) values (22,'Vladimir Nazor')
insert into location (id, name) values (23,'^itaonica')
insert into location (id, name) values (24,'Medicinska {kola')
insert into location (id, name) values (25,'Branko Radi~evi}')
insert into location (id, name) values (26,'@arko Zrenjanin')
insert into location (id, name) values (27,'Danilo Ki{')
insert into location (id, name) values (28,'Ivo Andri}')
insert into location (id, name) values (29,'Serijske publikacije')
insert into location (id, name) values (30,'Knjigobus')
insert into location (id, name) values (31,'Zavi~ajna zbirka')
insert into location (id, name) values (39,'B fond')
INSERT INTO lend_types VALUES (1, 'U ~itaonici', 5, 1)
INSERT INTO lend_types VALUES (2, 'Van ~itaonice', 5, 15)
INSERT INTO language VALUES(0,'Srpski jezik - latinica')
INSERT INTO language VALUES(1,'Srpski jezik - }irilica')
INSERT INTO language VALUES(2,'Ma|arski jezik')
INSERT INTO language VALUES(3,'Nema~ki jezik')
INSERT INTO language VALUES(4,'Engleski jezik')
INSERT INTO language VALUES(5,'Ruski jezik')
INSERT INTO language VALUES(6,'Rumunski jezik')
INSERT INTO language VALUES(7,'Slova~ki jezik')
INSERT INTO language VALUES(8,'Francuski jezik')
INSERT INTO language VALUES(9,'Slovena~ki jezik')
INSERT INTO language VALUES(10,'Italijanski jezik')
INSERT INTO language VALUES(11,'Latinski jezik')
INSERT INTO language VALUES(12,'Makedonski jezik')
INSERT INTO language VALUES(13,'Japanski jezik')
INSERT INTO language VALUES(14,'[vedski jezik')
INSERT INTO language VALUES(15,'Romski jezik')
INSERT INTO language VALUES(16,'Rusinski jezik')
INSERT INTO language VALUES(17,'Poljski jezik')
INSERT INTO language VALUES(18,'Gr~ki jezik')
INSERT INTO groups VALUES ('x',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0)
CREATE SEQUENCE LENDIDSEQ START WITH 1 NOCACHE