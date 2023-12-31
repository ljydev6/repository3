<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.notice.model.dto.Notice" %>
<%@ include file="/views/common/header.jsp"%>
<% String type = (String)request.getAttribute("type");
	Notice notice = (Notice)request.getAttribute("notice");%>
<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
 <section id="notice-container">
    <form action="<%=type.equals("write")?request.getContextPath()+"/notice/noticewriteend.do"
    									:request.getContextPath()+"/notice/noticeupdateend.do" %>" method="post" enctype="multipart/form-data">
		<%=type.equals("write")?"":"<input type=\"hidden\" name=\"no\" value=\""+notice.getNoticeNo()+"\">" %>
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><input type="text" name="title" placeholder="제목을 입력해주세요" value="<%=type.equals("write")?"":notice.getNoticeTitle()%>" required></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="author" value="<%=type.equals("write")?loginMember.getUserid():notice.getNoticeWriter() %>" <%=type.equals("write")?"readOnly":"disabled"%>></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><input type="file" name="attach" value="<%=type.equals("write")?"":notice.getFilePath() %>"></td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><textarea name="content" cols="45" rows="10" style="resize: none;"><%=type.equals("write")?"":notice.getNoticeContent() %></textarea></td>
        </tr>
        <tr>
            <th colspan="2">
            	<%=type.equals("write")?
            			"<button class=\"btn btn-outline-primary\" type=\"submit\"onclick=\"\">등록하기</button>":
            			"<button class=\"btn btn-outline-primary\" type=\"submit\" onclick=\"\">수정하기</button>" %>
                
            </th>
        </tr>
    </table>
    </form>
</section>
<script>

</script>
<%@ include file="/views/common/footer.jsp"%>