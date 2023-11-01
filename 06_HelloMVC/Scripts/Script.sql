SELECT * 
FROM (SELECT ROWNUM AS RNUM, M.* FROM (
	SELECT *
	FROM MEMBER
	ORDER BY ENROLLDATE DESC) M)
WHERE RNUM BETWEEN 1 AND 5;

SELECT count (*) FROM MEMBER;

SELECT * FROM MEMBER WHERE userId LIKE '%user%';


   CREATE TABLE NOTICE(
        NOTICE_NO NUMBER PRIMARY KEY,
        NOTICE_TITLE VARCHAR2(100) NOT NULL,
        NOTICE_WRITER VARCHAR2(15) NOT NULL,
        NOTICE_CONTENT VARCHAR2(4000) NOT NULL,
        NOTICE_DATE DATE DEFAULT SYSDATE,
        FILEPATH VARCHAR2(300),
        STATUS VARCHAR2(1) DEFAULT 'Y',
        constraint fk_notice_writer FOREIGN KEY (NOTICE_WRITER) REFERENCES MEMBER (USERID)
    );



    CREATE SEQUENCE SEQ_NOTICE_NO;

    INSERT INTO NOTICE VALUES(SEQ_NOTICE_NO.NEXTVAL,'공지사항테스트','admin','공지사항테스트입니다',default,null,default);
    INSERT INTO NOTICE VALUES(SEQ_NOTICE_NO.NEXTVAL,'코로나 힘내자','admin','코로나가 없어지길.',default,null,default);
    COMMIT;
    
SELECT * FROM notice ORDER BY 1 desc;

SELECT * FROM board ORDER BY 1 DESC;

UPDATE BOARD SET BOARD_READCOUNT = (SELECT BOARD_READCOUNT FROM BOARD WHERE BOARD_NO = 31)+1 WHERE BOARD_NO = 31;
UPDATE BOARD SET BOARD_READCOUNT = BOARD_READCOUNT+1 WHERE BOARD_NO = 34;

SELECT * FROM BOARD_COMMENT;

CREATE OR REPLACE VIEW BOARD_COMMENT_VIEW
AS
SELECT 