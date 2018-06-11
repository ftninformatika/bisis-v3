CREATE TABLE InvCounters (counter_ID VARCHAR2(50) NOT NULL, last_value INTEGER NOT NULL) TABLESPACE %TABLES%
ALTER TABLE InvCounters ADD ( CONSTRAINT InvCounters_PK PRIMARY KEY (counter_ID))
CREATE TABLE Delimiters (configID integer NOT NULL , wordDelims varchar2(100) NOT NULL , sentDelims varchar2(20) NOT NULL) TABLESPACE %TABLES%
ALTER TABLE Delimiters ADD ( CONSTRAINT Delimiters_PK PRIMARY KEY (configID))
CREATE TABLE Documents ( doc_id integer NOT NULL , document long raw) TABLESPACE %TABLES%
CREATE UNIQUE INDEX Documents_un ON Documents ( doc_id ) PCTFREE 5 TABLESPACE %INDEXES%
ALTER TABLE Documents ADD ( CONSTRAINT Documents_PK PRIMARY KEY (doc_id) USING INDEX)
CREATE SEQUENCE DocSeq NOCACHE
CREATE TABLE Doc_shadow ( doc_id integer NOT NULL) TABLESPACE %TABLES%
CREATE UNIQUE INDEX Doc_shadow_un ON Doc_shadow ( doc_id ) PCTFREE 5 TABLESPACE %INDEXES%
ALTER TABLE Doc_shadow ADD ( CONSTRAINT Doc_shadow_PK PRIMARY KEY (doc_id) USING INDEX)
CREATE TABLE Prefixes ( name char(2) NOT NULL , type integer NOT NULL , indextype int NOT NULL) TABLESPACE %TABLES%
ALTER TABLE Prefixes ADD ( CONSTRAINT Prefixes_PK PRIMARY KEY (name))
CREATE TABLE Prefix_contents ( prefID integer NOT NULL , pref_name char(2) NOT NULL , content long raw, doc_id integer NOT NULL) TABLESPACE %TABLES%
CREATE INDEX Pref_cont_1 ON Prefix_contents ( doc_id, pref_name ) PCTFREE 5 TABLESPACE %INDEXES%
ALTER TABLE Prefix_contents ADD ( CONSTRAINT Prefix_contents_PK PRIMARY KEY (prefID))
ALTER TABLE Prefix_contents ADD ( CONSTRAINT Prefix_contents_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_id) ON DELETE CASCADE)
CREATE SEQUENCE PrefContSeq NOCACHE
CREATE TABLE Prefix_map ( id integer NOT NULL , pref_name char(2) NOT NULL , subfield_id varchar2(8) NOT NULL) TABLESPACE %TABLES%
ALTER TABLE Prefix_map ADD ( CONSTRAINT Prefix_map_PK PRIMARY KEY (id))
CREATE SEQUENCE PrefixMapSeq NOCACHE
CREATE TABLE pref_AB ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AB ADD ( CONSTRAINT pref_AB_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AB_in ON pref_AB (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_AM ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AM ADD ( CONSTRAINT pref_AM_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AM_in ON pref_AM (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_AN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AN ADD ( CONSTRAINT pref_AN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AN_in ON pref_AN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_AP ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AP ADD ( CONSTRAINT pref_AP_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AP_in ON pref_AP (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_AT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AT ADD ( CONSTRAINT pref_AT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AT_in ON pref_AT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_AU ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_AU ADD ( CONSTRAINT pref_AU_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_AU_in ON pref_AU (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_BI ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_BI ADD ( CONSTRAINT pref_BI_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_BI_in ON pref_BI (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_BN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_BN ADD ( CONSTRAINT pref_BN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_BN_in ON pref_BN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CB ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CB ADD ( CONSTRAINT pref_CB_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CB_in ON pref_CB (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CC ADD ( CONSTRAINT pref_CC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CC_in ON pref_CC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CD ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CD ADD ( CONSTRAINT pref_CD_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CD_in ON pref_CD (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CH ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CH ADD ( CONSTRAINT pref_CH_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CH_in ON pref_CH (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CL ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CL ADD ( CONSTRAINT pref_CL_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CL_in ON pref_CL (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CN ADD ( CONSTRAINT pref_CN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CN_in ON pref_CN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CO ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CO ADD ( CONSTRAINT pref_CO_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CO_in ON pref_CO (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CP ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CP ADD ( CONSTRAINT pref_CP_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CP_in ON pref_CP (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CR ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CR ADD ( CONSTRAINT pref_CR_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CR_in ON pref_CR (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_CS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_CS ADD ( CONSTRAINT pref_CS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_CS_in ON pref_CS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DA ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DA ADD ( CONSTRAINT pref_DA_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DA_in ON pref_DA (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DC ADD ( CONSTRAINT pref_DC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DC_in ON pref_DC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DE ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DE ADD ( CONSTRAINT pref_DE_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DE_in ON pref_DE (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DR ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DR ADD ( CONSTRAINT pref_DR_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DR_in ON pref_DR (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DS ADD ( CONSTRAINT pref_DS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DS_in ON pref_DS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_DT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_DT ADD ( CONSTRAINT pref_DT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_DT_in ON pref_DT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_ES ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_ES ADD ( CONSTRAINT pref_ES_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_ES_in ON pref_ES (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FC ADD ( CONSTRAINT pref_FC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FC_in ON pref_FC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FD ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FD ADD ( CONSTRAINT pref_FD_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FD_in ON pref_FD (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FI ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FI ADD ( CONSTRAINT pref_FI_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FI_in ON pref_FI (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FN ADD ( CONSTRAINT pref_FN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FN_in ON pref_FN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FQ ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FQ ADD ( CONSTRAINT pref_FQ_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FQ_in ON pref_FQ (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_FS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_FS ADD ( CONSTRAINT pref_FS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_FS_in ON pref_FS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_GE ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_GE ADD ( CONSTRAINT pref_GE_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_GE_in ON pref_GE (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_GM ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_GM ADD ( CONSTRAINT pref_GM_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_GM_in ON pref_GM (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_GN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_GN ADD ( CONSTRAINT pref_GN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_GN_in ON pref_GN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_GS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_GS ADD ( CONSTRAINT pref_GS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_GS_in ON pref_GS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_IC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_IC ADD ( CONSTRAINT pref_IC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_IC_in ON pref_IC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_II ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_II ADD ( CONSTRAINT pref_II_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_II_in ON pref_II (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_IN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_IN ADD ( CONSTRAINT pref_IN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_IN_in ON pref_IN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_IR ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_IR ADD ( CONSTRAINT pref_IR_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_IR_in ON pref_IR (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_KT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_KT ADD ( CONSTRAINT pref_KT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_KT_in ON pref_KT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_KW ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_KW ADD ( CONSTRAINT pref_KW_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_KW_in ON pref_KW (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_LA ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_LA ADD ( CONSTRAINT pref_LA_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_LA_in ON pref_LA (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_LC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_LC ADD ( CONSTRAINT pref_LC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_LC_in ON pref_LC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_LI ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_LI ADD ( CONSTRAINT pref_LI_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_LI_in ON pref_LI (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_LO ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_LO ADD ( CONSTRAINT pref_LO_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_LO_in ON pref_LO (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_ND ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_ND ADD ( CONSTRAINT pref_ND_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_ND_in ON pref_ND (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_NM ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_NM ADD ( CONSTRAINT pref_NM_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_NM_in ON pref_NM (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_NT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_NT ADD ( CONSTRAINT pref_NT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_NT_in ON pref_NT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_P2 ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_P2 ADD ( CONSTRAINT pref_P2_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_P2_in ON pref_P2 (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PA ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PA ADD ( CONSTRAINT pref_PA_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PA_in ON pref_PA (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PM ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PM ADD ( CONSTRAINT pref_PM_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PM_in ON pref_PM (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PN ADD ( CONSTRAINT pref_PN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PN_in ON pref_PN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PP ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PP ADD ( CONSTRAINT pref_PP_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PP_in ON pref_PP (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PR ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PR ADD ( CONSTRAINT pref_PR_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PR_in ON pref_PR (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PU ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PU ADD ( CONSTRAINT pref_PU_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PU_in ON pref_PU (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_PY ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_PY ADD ( CONSTRAINT pref_PY_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_PY_in ON pref_PY (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_RE ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_RE ADD ( CONSTRAINT pref_RE_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_RE_in ON pref_RE (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_RJ ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_RJ ADD ( CONSTRAINT pref_RJ_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_RJ_in ON pref_RJ (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_RS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_RS ADD ( CONSTRAINT pref_RS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_RS_in ON pref_RS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_RT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_RT ADD ( CONSTRAINT pref_RT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_RT_in ON pref_RT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SC ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SC ADD ( CONSTRAINT pref_SC_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SC_in ON pref_SC (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SG ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SG ADD ( CONSTRAINT pref_SG_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SG_in ON pref_SG (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SI ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SI ADD ( CONSTRAINT pref_SI_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SI_in ON pref_SI (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SK ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SK ADD ( CONSTRAINT pref_SK_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SK_in ON pref_SK (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SN ADD ( CONSTRAINT pref_SN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SN_in ON pref_SN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SP ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SP ADD ( CONSTRAINT pref_SP_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SP_in ON pref_SP (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SR ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SR ADD ( CONSTRAINT pref_SR_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SR_in ON pref_SR (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SS ADD ( CONSTRAINT pref_SS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SS_in ON pref_SS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_ST ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_ST ADD ( CONSTRAINT pref_ST_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_ST_in ON pref_ST (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_SU ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_SU ADD ( CONSTRAINT pref_SU_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_SU_in ON pref_SU (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_TI ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_TI ADD ( CONSTRAINT pref_TI_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_TI_in ON pref_TI (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_TN ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_TN ADD ( CONSTRAINT pref_TN_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_TN_in ON pref_TN (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_TP ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_TP ADD ( CONSTRAINT pref_TP_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_TP_in ON pref_TP (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_TS ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_TS ADD ( CONSTRAINT pref_TS_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_TS_in ON pref_TS (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_TY ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_TY ADD ( CONSTRAINT pref_TY_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_TY_in ON pref_TY (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_UG ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_UG ADD ( CONSTRAINT pref_UG_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_UG_in ON pref_UG (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_US ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_US ADD ( CONSTRAINT pref_US_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_US_in ON pref_US (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_UT ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_UT ADD ( CONSTRAINT pref_UT_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_UT_in ON pref_UT (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_Y1 ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_Y1 ADD ( CONSTRAINT pref_Y1_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_Y1_in ON pref_Y1 (content) PCTFREE 10 TABLESPACE %INDEXES%
CREATE TABLE pref_Y2 ( doc_id integer NOT NULL , content varchar2(255) NOT NULL , pref_id integer NOT NULL , word_pos integer NOT NULL , sent_pos integer NOT NULL) TABLESPACE %TABLES%
ALTER TABLE pref_Y2 ADD ( CONSTRAINT pref_Y2_FK FOREIGN KEY (doc_id) REFERENCES Doc_shadow (doc_ID) ON DELETE CASCADE)
CREATE INDEX pref_Y2_in ON pref_Y2 (content) PCTFREE 10 TABLESPACE %INDEXES%
INSERT INTO Prefixes (name, type, indextype) VALUES ('AN', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('AT', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CD', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CO', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DR', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FQ', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('KW', 2, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('P2', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('RJ', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('RS', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('RT', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SS', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('TY', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('US', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('Y1', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('Y2', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DE', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FI', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('LI', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('ND', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SR', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('IR', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('ES', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('GS', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('II', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('NM', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('NT', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PA', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PM', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PR', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CB', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FN', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PN', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('TS', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('TI', 3, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('AU', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('BN', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SN', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SP', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PY', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('LA', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PU', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('PP', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('LO', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DT', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('IC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CS', 2, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('LC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('GM', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SU', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CP', 2, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CR', 2, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('UG', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SG', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SI', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('IN', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DS', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('ST', 2, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('AM', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('AP', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CL', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('BI', 2, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('SK', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('TP', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('RE', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FD', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FC', 2, 2)
INSERT INTO Prefixes (name, type, indextype) VALUES ('KT', 1, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('AB', 1, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CN', 1, 1)
INSERT INTO Prefixes (name, type, indextype) VALUES ('UT', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('TN', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('GN', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('CH', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('FS', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('GE', 1, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('DA', 2, 3)
INSERT INTO Prefixes (name, type, indextype) VALUES ('ID', 2, 3)
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AT', '531a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AT', '531b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '700a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '700b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '701a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '701b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '702a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '702b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '900a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '900b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '901a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '901b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '902a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AU', '902b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'BI', '992b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'BN', '010a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'BN', '010z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CC', '105b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CD', '040a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CL', '225a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CL', '225e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CL', '225i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CL', '225h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CO', '102a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '710e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '711e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '712e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '910e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '911e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CP', '912e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CR', '000c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '503a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '503b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '710a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '710b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '711a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '711b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '712a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '712b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '910a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '910b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '911a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '911b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '912a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CS', '912b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DC', '675a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DC', '675u')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DR', '328f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DT', '001c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FC', '7008')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FC', '7018')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FC', '7028')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '7009')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '7019')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '7029')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '9009')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '9019')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FD', '9029')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FQ', '110b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GM', '200b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'IC', '105a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '200a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '200c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '200d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '200i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '327a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '330a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '500a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '501a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '510a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '511a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '512a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '513a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '514a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '515a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '516a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '517a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '530a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '531a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '532a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '540a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6006')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '600z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6016')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601g')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '601z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6026')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '602z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6056')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605k')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605l')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605m')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605n')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605q')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '605z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6062')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6066')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '606a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '606w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '606x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '606y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '606z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6076')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '607a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '607w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '607x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '607y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '607z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6086')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '608a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '608w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '608x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '608y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '608z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '6096')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '609a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '609w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '609x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '609y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '609z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '610a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '610b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '610z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KW', '627a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'LA', '101a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'LC', '105f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'LO', '101c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PP', '210a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PU', '210c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PY', '100c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'P2', '100d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'RE', '102b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'RJ', '391a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'RS', '001a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'RT', '001b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SC', '011y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SK', '992a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SN', '011a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SN', '011z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SP', '011e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SP', '011c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SS', '100b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SU', '200e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SU', '512e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '200a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '200c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '200d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '200i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '500a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '501a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '510a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '511a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '512a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '513a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '514a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '515a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '516a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '517a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '530a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '531a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '532a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TI', '540a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TP', '105i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TY', '110a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'UG', '675b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'US', '675s')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'Y1', '328d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'Y2', '328e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AM', '996v')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AM', '997v')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AP', '996w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AP', '997w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DA', '996o')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DA', '997o')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'DE', '9968')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FI', '9964')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FI', '9974')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'IN', '996f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'IN', '997f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'LI', '996p')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'LI', '997p')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '996x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '996y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '996z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '9961')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '9967')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '997x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '997y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '997z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '9971')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ND', '9977')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SG', '996d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SG', '996e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SG', '997d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SG', '997e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SI', '998b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SR', '9962')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'SR', '9972')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'IR', '996r')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'IR', '997r')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ST', '996q')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ST', '997q')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ES', '205a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ES', '205d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ES', '205f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ES', '205g')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'ES', '205b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GS', '300a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'II', '320a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'NM', '210g')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'NT', '200h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'NT', '200v')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PA', '210b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PM', '210e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PR', '9963')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PR', '9973')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'AB', '330a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601g')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CB', '601w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CH', '608a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CH', '608w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CH', '608x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CH', '608y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CH', '608z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'CN', '327a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FN', '602z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FS', '609a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FS', '609w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FS', '609x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FS', '609y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'FS', '609z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GE', '627a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GN', '607a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GN', '607w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GN', '607x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GN', '607y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'GN', '607z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'KT', '530a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600b')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600c')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600d')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600e')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600f')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'PN', '600y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TN', '606a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TN', '606w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TN', '606x')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TN', '606y')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TN', '606z')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605h')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605i')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605k')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605l')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605m')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605n')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605q')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'TS', '605w')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'UT', '610a')
INSERT INTO Prefix_map (id, pref_name, subfield_id) VALUES (PrefixMapSeq.NEXTVAL, 'UT', '610b')
insert into Delimiters (configID, wordDelims, sentDelims) values (1, ', ;:"()[]{}-+/', '.?!')