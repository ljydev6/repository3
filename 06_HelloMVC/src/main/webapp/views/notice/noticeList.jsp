<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>    
<section id="notice-container">
        <h2>공지사항</h2>
        <table id="tbl-notice">
	        <thead>
	            <tr>
	                <th>번호</th>
	                <th>제목</th>
	                <th>작성자</th>
	                <th>첨부파일</th>
	                <th>작성일</th>
	            </tr>
            </thead>
            <tbody>
<!-- 	내용출력할것
	첨부파일 있으면 이미지, 없으면 공란으로 표시
	이미지파일은 web/images/file.png에 저장 -->
            </tbody>
        </table>
    </section>
<%@ include file="/views/common/footer.jsp" %>