<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.notice.model.dto.Notice" %>
<%@ include file="/views/common/header.jsp"%>
<% Notice notice = (Notice)request.getAttribute("notice"); %>
<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
<div id="notice-container">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><%=notice.getNoticeTitle() %></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%=notice.getNoticeWriter() %></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
            <!-- 있으면 이미지출력하기 없으면 공란 -->
           	<%=notice.getFilePath()!=null?"<img src='"+request.getContextPath()+"/img/file.png'>":"" %>
            </td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><%=notice.getNoticeContent() %></td>
        </tr>
        <%if(loginMember!=null && loginMember.getUserid().equals("admin")){ %>
        <tr>
            <th colspan="2">
                <input type="button" value="수정하기" onclick="noticeUpdate();">
                <input type="button" value="삭제하기" onclick="noticeDelete();">
            </th>
        </tr>
        <%} %>
    </table>
</div>
<script>
	const noticeUpdate = ()=>{
		location.assign("<%=request.getContextPath() %>/notice/noticeupdate.do?noticeNo=<%=notice.getNoticeNo()%>");
	};
	const noticeDelete = ()=>{
		if(confirm('정말로 삭제하시겠습니까?')){
			location.assign("<%=request.getContextPath()%>/notice/noticedelete.do?noticeNo=<%=notice.getNoticeNo()%>");
		}
	}
</script>
<%@ include file="/views/common/footer.jsp"%>