USE MEU_DB;

ALTER TABLE PROFESSOR_SUBS ADD CONSTRAINT FOREIGN KEY(fk_idProfessor) REFERENCES PROFESSOR(ID)