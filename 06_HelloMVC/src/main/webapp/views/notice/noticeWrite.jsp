<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp"%>

 <div id="notice-container">
    <form action="" method="post">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><input type="text" name="title" placeholder="제목을 입력해주세요" required></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="author" value="<%= %>" readOnly></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><input type="file" name="attach"></td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><textarea name="content"></textarea></td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="submit" value="등록하기" onclick="">
            </th>
        </tr>
    </table>
    </form>
</div>

<%@ include file="/views/common/footer.jsp"%>