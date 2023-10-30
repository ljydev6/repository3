<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp"%>
<div id="notice-container">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
           	있으면 이미지출력하기 없으면 공란
            </td>
        </tr>
        <tr>
            <th>내 용</th>
            <td></td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="button" value="수정하기" onclick="">
                <input type="button" value="삭제하기" onclick="">
            </th>
        </tr>
    </table>
</div>
<%@ include file="/views/common/footer.jsp"%>