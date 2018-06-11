CREATE TABLE user_types( id INTEGER NOT NULL, name VARCHAR2(15) NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR2(8) NULL) TABLESPACE %TABLES%
CREATE TABLE users( id INTEGER NOT NULL, city VARCHAR2(45) NULL, zipcode INTEGER NULL, address VARCHAR2(45) NULL, sign_date DATE NULL, warning NUMBER(1) NOT NULL, phone VARCHAR2(100) NULL, email VARCHAR2(30) NULL, user_type INTEGER NOT NULL)  TABLESPACE %TABLES%
CREATE TABLE single( dob DATE NULL, first_name VARCHAR2(30) NOT NULL, permission NUMBER(1) NULL, last_name VARCHAR2(30) NOT NULL, occupation VARCHAR2(30) NULL, country VARCHAR2(30) NULL, password VARCHAR2(8) NULL, passport VARCHAR2(15) NULL, organization VARCHAR2(45) NULL, org_address VARCHAR2(45) NULL, org_city VARCHAR2(30) NULL, org_zip INTEGER NULL, org_phone VARCHAR2(15) NULL, temp_addr VARCHAR2(100) NULL, temp_city VARCHAR2(100) NULL, temp_zip INTEGER NULL, temp_phone VARCHAR2(15) NULL, index_no VARCHAR2(15) NULL, jmbg NUMBER(13) NULL, edu_lvl INTEGER NULL, user_ctgr INTEGER NOT NULL, mmbr_type INTEGER NOT NULL, grp_id INTEGER NULL, user_id INTEGER NOT NULL, parent_name varchar2(30), doc_id integer, doc_no varchar2(15), doc_city varchar2(30), title varchar2(30), note varchar2(200), interests varchar2(60), gender integer ) TABLESPACE %TABLES%
CREATE TABLE institutions( inst_name VARCHAR2(45) NOT NULL, cont_fname VARCHAR2(30) NOT NULL, cont_lname VARCHAR2(30) NOT NULL, cont_email VARCHAR2(30) NULL, cont_phone VARCHAR2(15) NULL, sec_phone VARCHAR2(15) NULL, fax VARCHAR2(15) NULL, telex VARCHAR2(15) NULL, user_id INTEGER NOT NULL ) TABLESPACE %TABLES%
CREATE TABLE groups( inst_name VARCHAR2(45) NOT NULL, sec_address VARCHAR2(45) NULL, sec_city VARCHAR2(30) NULL, sec_zip INTEGER NULL, sec_phone VARCHAR2(30) NULL, fax VARCHAR2(15) NULL, telex VARCHAR2(15) NULL, cont_fname VARCHAR2(30) NULL, cont_lname VARCHAR2(30) NULL, cont_email VARCHAR2(30) NULL, user_id INTEGER NOT NULL )  TABLESPACE %TABLES%
CREATE TABLE user_categs( id INTEGER NOT NULL, name VARCHAR2(30) NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR2(8) NULL) TABLESPACE %TABLES%
CREATE TABLE edu_lvl( id INTEGER NOT NULL, name VARCHAR2(30) NOT NULL) TABLESPACE %TABLES%
CREATE TABLE mmbr_types( id INTEGER NOT NULL, name VARCHAR2(100) NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR2(8) NULL ) TABLESPACE %TABLES%
CREATE TABLE membership( user_ctgr INTEGER NOT NULL, mmbr_type INTEGER NOT NULL, mdate DATE NOT NULL, cost NUMBER(8,2) NULL, hard_curr NUMBER(8,2) NULL) TABLESPACE %TABLES%
CREATE TABLE signing( single_id INTEGER NOT NULL, official_id INTEGER NOT NULL, sdate DATE NOT NULL, cost NUMBER(8,2) NULL, hard_curr NUMBER(8,2) NULL) TABLESPACE %TABLES%
CREATE TABLE officials( id INTEGER NOT NULL, last_name VARCHAR2(30) NOT NULL, first_name VARCHAR2(15) NOT NULL, email VARCHAR2(45) NULL) TABLESPACE %TABLES%
CREATE TABLE location( id INTEGER NOT NULL, name VARCHAR2(100) NOT NULL) TABLESPACE %TABLES%
CREATE TABLE lend_types( id INTEGER NOT NULL, name VARCHAR2(45) NOT NULL, titles_no INTEGER NOT NULL, period VARCHAR2(8) NULL) TABLESPACE %TABLES%
CREATE TABLE lending( ctlg_no VARCHAR2(30) NOT NULL, single_id INTEGER NOT NULL, lend_date DATE NOT NULL, return_date DATE NULL, resume_date DATE NULL, official_id INTEGER NOT NULL, lend_type INTEGER NOT NULL, location INTEGER NOT NULL, description VARCHAR2(30) NULL ) TABLESPACE %TABLES%
CREATE TABLE circ_docs( sig VARCHAR2(255) NULL, ctlg_no VARCHAR2(255) NOT NULL, docid NUMBER(38) NOT NULL, status INTEGER NOT NULL, rcv_req_id INTEGER, type VARCHAR2(10) NULL, state CHAR(15) NULL ) TABLESPACE %TABLES%
CREATE TABLE publications_reservations( ctlg_no VARCHAR2(30) NOT NULL, rsrv_date DATE NOT NULL, single_id INTEGER NOT NULL, pub_ctlg_no VARCHAR2(30) NOT NULL) TABLESPACE %TABLES%
CREATE TABLE reservations( single_id INTEGER NOT NULL, ctlg_no VARCHAR2(30) NOT NULL, rsrv_date DATE NOT NULL, notification NUMBER(1) NOT NULL, resume_date DATE NULL, official_id INTEGER NOT NULL) TABLESPACE %TABLES%
CREATE TABLE self_rsrv( single_id INTEGER NOT NULL, ctlg_no VARCHAR2(30) NOT NULL, rsrv_date DATE NOT NULL, notification NUMBER(1) NOT NULL, resume_date DATE NULL) TABLESPACE %TABLES%
CREATE TABLE lend_brw( id INTEGER NOT NULL, author VARCHAR2(60) NOT NULL, payment VARCHAR2(15) NOT NULL, title VARCHAR2(60) NOT NULL, cost NUMBER(8,2) NOT NULL, type VARCHAR2(15) NOT NULL, pieces_no INTEGER NULL, comments VARCHAR2(60) NULL, hard_curr NUMBER(8,2) NULL) TABLESPACE %TABLES%
CREATE TABLE borrowed_pub( id INTEGER NOT NULL, catalogue_no VARCHAR2(30) NOT NULL, author VARCHAR2(60) NOT NULL, title VARCHAR2(60) NOT NULL, sent_req_id INTEGER NOT NULL) TABLESPACE %TABLES%
CREATE TABLE received_req( lendbrw_id INTEGER NOT NULL, accept_date DATE NOT NULL, institution VARCHAR2(45) NOT NULL, lend_date DATE NULL, return_date DATE NULL, inst_id INTEGER NOT NULL) TABLESPACE %TABLES%
CREATE TABLE sent_req( lendbrw_id INTEGER NOT NULL, send_date DATE NOT NULL, institution VARCHAR2(45) NOT NULL, accept_date DATE NULL, return_date DATE NULL, inst_id INTEGER NOT NULL) TABLESPACE %TABLES%
CREATE TABLE warnings( warn_type INTEGER NOT NULL, user_id INTEGER NOT NULL, ctlg_no VARCHAR2(30) NOT NULL, wdate DATE NOT NULL, returned CHAR(1) NOT NULL) TABLESPACE %TABLES%
CREATE TABLE warning_types( id INTEGER NOT NULL, wtext VARCHAR2(500) NOT NULL)  TABLESPACE %TABLES%
CREATE INDEX USER_USTP_IDX ON USERS(user_type )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SNGL_USCT_IDX ON SINGLE( user_ctgr )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SNGL_GRP_IDX ON SINGLE(grp_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SNGL_EDU_IDX ON SINGLE(edu_lvl )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SNGL_MBTP_IDX ON SINGLE(mmbr_type )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SNRQ_INST_IDX ON SENT_REQ(inst_id)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX BRPB_SNRQ_IDX ON BORROWED_PUB(sent_req_id)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX PUBL_RSRV_PUBL_IDX ON PUBLICATIONS_RESERVATIONS(ctlg_no )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX PUB_REZ_REZ_IDX ON PUBLICATIONS_RESERVATIONS( rsrv_date, single_id, pub_ctlg_no )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX RCRQ_INST_IDX ON RECEIVED_REQ(inst_id)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX RSRV_SNGL_IDX ON RESERVATIONS(single_id)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX RSRV_PUBL_IDX ON RESERVATIONS(ctlg_no )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX RSRV_OFCL_IDX ON RESERVATIONS(official_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SLFR_SNGL_IDX ON SELF_RSRV(single_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SLFR_PUBL_IDX ON SELF_RSRV(ctlg_no )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SIGN_SNGL_IDX ON SIGNING (single_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX SIGN_OFCL_IDX ON SIGNING (official_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX MMBR_USCT_IDX ON MEMBERSHIP(user_ctgr)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX MMBR_MBTP_IDX ON MEMBERSHIP(mmbr_type)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX LEND_SNGL_IDX ON LENDING(single_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX LEND_PUBL_IDX ON LENDING(ctlg_no )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX LEND_OFCL_IDX ON LENDING(official_id )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX LEND_LNDT_IDX ON LENDING(lend_type ,location )PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX WARN_DATE_IDX ON WARNINGS(wdate)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX CIRC_DOCS_IDX ON CIRC_DOCS(docid)PCTFREE 40 TABLESPACE %INDEXES%
CREATE INDEX CIRC_DOCS_2 ON CIRC_DOCS(sig)PCTFREE 40 TABLESPACE %INDEXES%
ALTER TABLE USERS ADD ( CONSTRAINT USR_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_PK PRIMARY KEY (user_id)USING INDEX PCTFREE 10)
ALTER TABLE LEND_BRW ADD ( CONSTRAINT LNDBRW_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE INSTITUTIONS ADD ( CONSTRAINT INST_PK PRIMARY KEY (user_id)USING INDEX PCTFREE 10)
ALTER TABLE SENT_REQ ADD ( CONSTRAINT SNT_REQ_PK PRIMARY KEY (lendbrw_id)USING INDEX PCTFREE 10)
ALTER TABLE USER_CATEGS ADD ( CONSTRAINT USR_CTG_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE GROUPS ADD ( CONSTRAINT GRP_PK PRIMARY KEY (user_id)USING INDEX PCTFREE 10)
ALTER TABLE BORROWED_PUB ADD ( CONSTRAINT BRW_PUB_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE RECEIVED_REQ ADD ( CONSTRAINT RCV_REQ_PK PRIMARY KEY (lendbrw_id)USING INDEX PCTFREE 10)
ALTER TABLE LOCATION ADD ( CONSTRAINT LOC_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE PUBLICATIONS_RESERVATIONS ADD ( CONSTRAINT PUB_RES_PK PRIMARY KEY (ctlg_no, rsrv_date, single_id, pub_ctlg_no)USING INDEX PCTFREE 10)
ALTER TABLE RESERVATIONS ADD ( CONSTRAINT RES_PK PRIMARY KEY (rsrv_date, ctlg_no, single_id)USING INDEX PCTFREE 10)
ALTER TABLE SELF_RSRV ADD ( CONSTRAINT SLFR_PK PRIMARY KEY (rsrv_date, ctlg_no, single_id)USING INDEX PCTFREE 10)
ALTER TABLE OFFICIALS ADD ( CONSTRAINT OFCL_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE EDU_LVL ADD ( CONSTRAINT EDU_LVL_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE USER_TYPES ADD ( CONSTRAINT USER_TYPE_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE SIGNING ADD ( CONSTRAINT SGN_PK PRIMARY KEY (sdate, official_id, single_id)USING INDEX PCTFREE 10)
ALTER TABLE MEMBERSHIP ADD ( CONSTRAINT MMBR_PK PRIMARY KEY (mdate, user_ctgr, mmbr_type)USING INDEX PCTFREE 10)
ALTER TABLE MMBR_TYPES ADD ( CONSTRAINT MMBR_TYPE_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE LEND_TYPES ADD ( CONSTRAINT LND_TYPE_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE LENDING ADD ( CONSTRAINT LND_PK PRIMARY KEY (lend_date, single_id, ctlg_no)USING INDEX PCTFREE 10)
ALTER TABLE WARNINGS ADD ( CONSTRAINT WRN_PK PRIMARY KEY (warn_type, user_id, ctlg_no)USING INDEX PCTFREE 10)
ALTER TABLE WARNING_TYPES ADD ( CONSTRAINT WRN_TYPE_PK PRIMARY KEY (id)USING INDEX PCTFREE 10)
ALTER TABLE CIRC_DOCS ADD ( CONSTRAINT CIRC_DOCS_PK PRIMARY KEY (ctlg_no)USING INDEX PCTFREE 10)
ALTER TABLE USERS ADD ( CONSTRAINT USR_USTYPE_FK FOREIGN KEY (user_type) REFERENCES USER_TYPES (id))
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_EDLVL_FK FOREIGN KEY (edu_lvl) REFERENCES EDU_LVL (id))
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_USRCAT_FK FOREIGN KEY (user_ctgr) REFERENCES USER_CATEGS (id))
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_MBRTYP_FK FOREIGN KEY (mmbr_type) REFERENCES MMBR_TYPES (id))
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_GRP_FK FOREIGN KEY (grp_id) REFERENCES GROUPS (user_id))
ALTER TABLE SINGLE ADD ( CONSTRAINT SNGL_USR_FK FOREIGN KEY (user_id) REFERENCES USERS (id))
ALTER TABLE INSTITUTIONS ADD ( CONSTRAINT INST_USR_FK FOREIGN KEY (user_id) REFERENCES USERS (id))
ALTER TABLE SENT_REQ ADD ( CONSTRAINT SNTREQ_INST_FK FOREIGN KEY (inst_id) REFERENCES INSTITUTIONS (user_id))
ALTER TABLE SENT_REQ ADD ( CONSTRAINT SNTREQ_LNDBRW_FK FOREIGN KEY (lendbrw_id) REFERENCES LEND_BRW(id))
ALTER TABLE GROUPS ADD ( CONSTRAINT GRP_USR_FK FOREIGN KEY (user_id) REFERENCES USERS (id))
ALTER TABLE BORROWED_PUB ADD ( CONSTRAINT BRWPUB_IZ_FK FOREIGN KEY (sent_req_id) REFERENCES SENT_REQ (lendbrw_id))
ALTER TABLE RECEIVED_REQ ADD ( CONSTRAINT RCVREQ_INST_FK FOREIGN KEY (inst_id) REFERENCES INSTITUTIONS (user_id))
ALTER TABLE RECEIVED_REQ ADD ( CONSTRAINT RCVREQ_BRW_FK FOREIGN KEY (lendbrw_id) REFERENCES LEND_BRW(id))
ALTER TABLE PUBLICATIONS_RESERVATIONS ADD ( CONSTRAINT PUB_RES_RES_FK FOREIGN KEY (rsrv_date, pub_ctlg_no, single_id) REFERENCES RESERVATIONS ( rsrv_date, ctlg_no, single_id))
ALTER TABLE RESERVATIONS ADD ( CONSTRAINT RES_SNGL_FK FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
ALTER TABLE SELF_RSRV ADD ( CONSTRAINT SLFRSRV_SNGL_FK FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
ALTER TABLE SIGNING ADD ( CONSTRAINT SGN_SNGL_FK FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
ALTER TABLE MEMBERSHIP ADD ( CONSTRAINT MMBR_USRCTG_FK FOREIGN KEY (user_ctgr) REFERENCES USER_CATEGS (id))
ALTER TABLE MEMBERSHIP ADD ( CONSTRAINT MMBR_MBRTYP_FK FOREIGN KEY (mmbr_type) REFERENCES MMBR_TYPES (id))
ALTER TABLE LENDING ADD ( CONSTRAINT LND_SNGL_FK FOREIGN KEY (single_id) REFERENCES SINGLE (user_id))
ALTER TABLE LENDING ADD ( CONSTRAINT LND_LNDT_FK FOREIGN KEY (lend_type) REFERENCES LEND_TYPES (id))
ALTER TABLE LENDING ADD ( CONSTRAINT LND_LOC_FK FOREIGN KEY (location) REFERENCES LOCATION (id))
ALTER TABLE WARNINGS ADD ( CONSTRAINT WARN_TYPE_FK FOREIGN KEY (warn_type) REFERENCES WARNING_TYPES (id))
ALTER TABLE WARNINGS ADD ( CONSTRAINT WRN_USR_FK FOREIGN KEY (user_id) REFERENCES USERS (id))
INSERT INTO user_types VALUES(1,'Individue',5,10)
INSERT INTO user_types VALUES(2,'Kolektivni',10,15)
INSERT INTO user_types VALUES(3,'Instit Razmene',30,30)
INSERT INTO user_categs VALUES(1,'Profesor',15,15)
INSERT INTO user_categs VALUES(2,'Student',15,15)
INSERT INTO user_categs VALUES(3,'Postdiplomac',15,15)
INSERT INTO user_categs VALUES(4,'Zaposlen',15,15)
INSERT INTO user_categs VALUES(5,'Penzioner',15,15)
INSERT INTO user_categs VALUES(6,'Radnik biblioteke',15,15)
INSERT INTO user_categs VALUES(7,'Po~asni ~lan',15,15)
INSERT INTO user_categs VALUES(8,'U~enik',15,15)
INSERT INTO users VALUES (0, ' ', 123, ' ', TRUNC(SYSDATE), 0, NULL, NULL, 1)
INSERT INTO edu_lvl VALUES(0,'      ')
INSERT INTO edu_lvl VALUES(1,'Osnovno')
INSERT INTO edu_lvl VALUES(2,'Srednje')
INSERT INTO edu_lvl VALUES(3,'Vi{e')
INSERT INTO edu_lvl VALUES(4,'Visoko')
INSERT INTO mmbr_types VALUES(1,'Redovan',5,15)
INSERT INTO mmbr_types VALUES(2,'Privremeni',5,15)
INSERT INTO mmbr_types VALUES(3,'Po~asni',5,15)
INSERT INTO location VALUES (1, '~itaonica A')
INSERT INTO location VALUES (2, '~itaonica B')
INSERT INTO lend_types VALUES (1, 'U ~itaonici', 5, 1)
INSERT INTO lend_types VALUES (2, 'Van ~itaonice', 5, 15)
INSERT INTO groups VALUES ('        ',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0)
CREATE SEQUENCE MMBRIDSEQ START WITH 1 NOCACHE