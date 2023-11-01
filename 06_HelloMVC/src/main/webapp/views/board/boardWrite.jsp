<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.board.model.dto.Board" %>
<%@ include file="/views/common/header.jsp"%>
<%	String type = (String)request.getAttribute("type"); 
	Board board = (Board)request.getAttribute("board");
%>
<style>
	div#board-container
	{
		width:600px;
		margin:0 auto;
		text-align:center;
	}
	div#board-container h2
	{
		margin:10px 0;
	}
	table#tbl-board
	{
		width:500px;
		margin:0 auto;
		border:1px solid black;
		border-collapse:collapse;
	}
	table#tbl-board th
	{
		width:125px;
		border:1px solid;
		padding:5px 0;
		text-align:center;
	}
	table#tbl-board td
	{
		border:1px solid;
		padding:5px 0 5px 10px;
		text-align:left;
	}

</style>
<script>
	내용입력여부 확인 후 전송
</script>

	<div id='board-container'>
		<h2>게시판 작성</h2>
		<form action='<%=type.equals("write")?
					request.getContextPath()+"/board/boardWriteEnd.do":
					request.getContextPath()+"/board/boardUpdateEnd.do" %>'  method="post" enctype="multipart/form-data">
			<%=type.equals("write")?"":"<input type='hidden' name='boardNo' value='"+board.getBoardNo()+"'>" %>
			<table class="table" id='tbl-board'>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" value="<%=type.equals("write")?"":board.getBoardTitle() %>" required></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><input type="text" name="writer" value="<%=type.equals("write")?loginMember.getUserid():board.getBoardWriter()%>" readOnly></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<%if(type.equals("write")){ %>
						<input type="file" name="attach">
						<%}else{ %>
						<p><img src="<%=request.getContextPath() %>/img/file.png"><sub><%=board.getBoardOriginalFileName() %></sub></p>
						<p>업로드 파일 수정 :<br>
						<input type="hidden" name="exReFileName" value="<%=board.getBoardRenamedFileName()%>">
						<input type="file" name="attach"></p>
						<%} %>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<th colspan="2">
						<textarea name="content" rows="10" cols="50" style="resize: none;"><%=type.equals("write")?"":board.getBoardContent() %></textarea>
					</th>
				</tr>
				<tr>
					<td colspan="2">
							<button class="btn btn-outline-primary" type="submit" ><%=type.equals("write")?"등록하기":"수정하기" %></button>
							<button class="btn btn-outline-warning" type="reset">초기화</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
<%@ include file="/views/common/footer.jsp"%>