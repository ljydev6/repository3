<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.web.notice.model.dto.Notice" %>
<%@ include file="/views/common/header.jsp" %>
<%List<Notice> noticeList = (List<Notice>)request.getAttribute("noticeList");
%>
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
			<%if(noticeList!=null && !noticeList.isEmpty()){ %>
			<%for(Notice n: noticeList){ %>
			<tr>
				<td><%=n.getNoticeNo() %></td>
				<td><%=n.getNoticeTitle() %></td>
				<td><%=n.getNoticeWriter() %></td>
				<td><%=n.getFilePath()!=null?"<img src="+request.getContextPath()+"/img/file.png"
											:"" %></td>
				<td><%=n.getNoticeDate() %></td>
			</tr>
			<%}}else{ %>
			<tr>
				<td colspan="5">아직 공지사항이 등록되지 않았습니다.</td>
			</tr>
			<%} %>
            </tbody>
        </table>
        <%=(String)request.getAttribute("pageBar") %>
        <%if(loginMember!=null && loginMember.getUserid().equals("admin")){ %>
        	<button id="writeNotice">공지사항작성하기</button>
        <%} %>
    </section>
    <script>
    	$('#tbl-notice > tbody > tr').on('click',(e)=>{
    		const target=$(e.target);
    		let path = '<%=request.getContextPath() %>/notice/notice.do?noticeNo=';
    		if(target.is(td)){
    			path = path + target.parent().child().first().text;
    		}else{
    			path = path + target.child().first().text;
    		}
    		location.assign(path);
    	});
    	$('#writeNotice').on('click',()=>{
    		location.assign('<%=request.getContextPath() %>/notice/writenotice.do');
    	});
    </script>
<%@ include file="/views/common/footer.jsp" %>