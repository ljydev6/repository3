<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.web.board.model.dto.Board" %>
<%@ include file="/views/common/header.jsp"%>
<% List<Board>boardList = (List<Board>)request.getAttribute("boardList");
	String searchType = request.getParameter("searchType");
	if(searchType==null) searchType = "userId";
	String searchKeyword = request.getParameter("searchKeyword");
%>
<style>
	section#board-container{width:600px; margin:0 auto; text-align:center;}
	section#board-container h2{margin:10px 0;}
	table#tbl-board{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-board th, table#tbl-board td {border:1px solid; padding: 5px 0; text-align:center;} 
	/*글쓰기버튼*/
	input#btn-add{float:right; margin: 0 0 15px;}
	/*페이지바*/
	div#pageBar{margin-top:10px; text-align:center; background-color:rgba(0, 188, 212, 0.3);}
	div#pageBar span.cPage{color: #0066ff;}
	</style>
	<section id="board-container">
		<h2>게시판 </h2>
		<table class="table table-hover table-striped" id="tbl-board">
			<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>첨부파일</th>
				<th>조회수</th>
			</tr>
			</thead>
			<tbody>
			<%for(Board b:boardList){ %>
			<tr>
				<td><%=b.getBoardNo() %></td>
				<td><%=b.getBoardTitle() %></td>
				<td><%=b.getBoardWriter() %></td>
				<td><%=b.getBoardDate() %></td>
				<td><%=b.getBoardRenamedFileName()!=null?
						"<img src='"+request.getContextPath()+"/img/file.png' width='25px'>":
						""	%></td>
				<td><%=b.getBoardReadCount() %></td>
			</tr>
			<%} %>
			</tbody>
		</table>
		<%=loginMember!=null?"<button class='btn btn-outline-primary' type='button' id='btnBoardWrite'>글 쓰기</button>":"" %>
		<%=(String)request.getAttribute("pageBar") %>
	</section>
	<script>
	$('#tbl-board > tbody > tr').click((e)=>{
		const target = $(e.target);
		let path = '<%=request.getContextPath() %>/board/boardView.do?boardNo='
		if(target.is('td')){
			path = path + target.parent().children().first().text();
		}else{
			path = path + target.children().first().text();
		}
		location.assign(path);
	});
	$('#btnBoardWrite').click(()=>{
		location.assign('<%=request.getContextPath()%>/board/boardWrite.do');
	});
	</script>
<%@ include file="/views/common/footer.jsp"%>