<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.board.model.dto.Board,com.web.board.model.dto.BoardComment,java.util.List" %>
<%@ include file="/views/common/header.jsp"%>
<% 
Board board = (Board)request.getAttribute("board");
List<BoardComment> comments = (List<BoardComment>)request.getAttribute("comments");
%>
<style>
    section#board-container{width:600px; margin:0 auto; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
   	span#download-container{cursor: pointer;}
   	 div#comment-container button#btn-insert{width:60px;height:50px; color:white;
    background-color:#3300FF;position:relative;top:-20px;}
        /*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px; text-align: left;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
    /*답글관련*/
    table#tbl-comment textarea{margin: 4px 0 0 0;}
    table#tbl-comment button.btn-insert{width:60px; height:23px; color:white; background:#3300ff; position:relative; top:-5px; left:10px;}
</style>
   
		<section id="board-container">
		<h2>게시판</h2>
		<table id="tbl-board">
			<tr>
				<th>글번호</th>
				<td><%=board.getBoardNo() %></td>
			</tr>
			<tr>
				<th>제 목</th>
				<td><%=board.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=board.getBoardWriter() %></td>
			</tr>
			<tr>
				<th>조회수</th>
				<td><%=board.getBoardReadCount() %></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
				 <!-- 있으면 이미지출력 없으면 공란, 클릭하면 다운로드할 수 있게 구현 -->
				 <% if(board.getBoardRenamedFileName()!=null){ %>
				 	<span id="download-container"><img src="<%=request.getContextPath()%>/img/file.png"><sub><%=board.getBoardOriginalFileName() %></sub></span>
				 <%} %>
				</td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><%=board.getBoardContent() %></td>
			</tr>
			<%--글작성자/관리자인경우 수정삭제 가능 --%>
			<%if(loginMember!=null && (loginMember.getUserid().equals(board.getBoardWriter())||
								loginMember.getUserid().equals("admin"))){ %>
			<tr>
				<th colspan="2">
					<button id="updateBtn">수정하기</button>
					<button id="deleteBtn">삭제하기</button>
				</th>
			</tr>
			<%} %>
		</table>
		
		<div id="comment-container">
			<div class="comment-editor">
				<form action="<%=request.getContextPath() %>/board/boardCommentInsert.do" method="post" onsubmit="return loginCheck()">
					<input type="hidden" name="boardRef" value="<%=board.getBoardNo() %>">
					<input type="hidden" name="level" value="1">
					<input type="hidden" name="writer" value="<%=loginMember!=null?loginMember.getUserid():""%>">
					<input type="hidden" name="boardCommentRef" value="0">
					<textarea name="content" rows="3" cols="55" style="resize: none;"></textarea>
					<button class="btn btn-outline-primary" type="submit" id="btn-insert">등록</button>
				</form>
			</div>
		</div>
		<% if(!comments.isEmpty()){ %>
		<table id="tbl-comment">
			<%for(BoardComment comment:comments){ %>
			<tr class="level<%=comment.getLevel()%>">
				<td <%=comment.getLevel()==2?"colspan='2'":"" %>>
					<sub class="comment-writer"><%=comment.getBoardCommentWriter() %></sub>
					<sub class="comment-date"><%=comment.getBoardCommentDate() %></sub>
					<br>
					<%=comment.getBoardCommentContent() %>
				</td>
				<%if(comment.getLevel()==1){ %>
				<td>
					<!-- 댓글(로그인한 사용자만), 삭제버튼만들기 (작성자, 관리자만삭제가능) -->
					<button class="btn-reply" value="<%=comment.getBoardCommentNo()%>">답글</button>
					<button class="btn-delete" value="<%=comment.getBoardCommentNo()%>">삭제</button>
				</td>
				<%} %>
			</tr>
			<%} %>
		</table>	
		<%} %>
    </section>
    <script>
    $('#updateBtn').click(e=>{
    	location.assign("<%=request.getContextPath()%>/board/boardUpdate.do?boardNo=<%=board.getBoardNo()%>");
    });
    $('#deleteBtn').click(e=>{
    	if(confirm('정말로 삭제하시겠습니까?')){
    		location.assign("<%=request.getContextPath()%>/board/boardDelete.do?boardNo=<%=board.getBoardNo()%>");
    	}
    });
    $('#download-container').click(e=>{
    	location.assign("<%=request.getContextPath()%>/board/filedownload.do?boardNo=<%=board.getBoardNo()%>");
    });
    const loginCheck = ()=>{
    	if(<%=loginMember==null%>){
    		alert('댓글을 작성하려면 로그인이 필요합니다.');
    		$('#userId').focus();
    		return false;
    	}
    };
    $('button.btn-reply').click(e=>{
    	const $parent = $(e.target).parents('tr');
    	console.log($parent.next().find('form'));
    	const $tr = $('<tr>');
    	const $td = $('<td>').attr('colspan','2');
    	const $frm = $('.comment-editor > form').clone();
    	$frm.find('input[name=level]').val('2');
    	$frm.find('input[name=boardCommentRef]').val($(e.target).val());
    	$frm.find('textarea').attr('rows','1');
    	$frm.find('button').removeAttr('id').addClass('btn-insert2');
    	
    	$tr.append($td.append($frm))
    	$parent.after($tr);
    });
    </script>
<%@ include file="/views/common/footer.jsp"%>