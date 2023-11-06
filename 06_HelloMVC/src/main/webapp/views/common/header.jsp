<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.member.model.dto.Member, javax.servlet.http.Cookie" %>
<%
	Member loginMember=(Member)session.getAttribute("loginMember");
	Cookie[] cookies = request.getCookies();
	String saveid = null;
	if(cookies!=null){
		for(Cookie c:cookies){
			if(c.getName().equals("saveId")){
				saveid = c.getValue();
				break;
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">

<!-- Latest compiled and minified CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-3.7.0.min.js"></script>
</head>
<body >
	<div id="container">
		<header>
		<h1>HelloMVC</h1>
			<div class="login-container" >
				<% if(loginMember == null){ %>
				<form id="loginFrm" action="<%=request.getContextPath()%>/login.do" method="post" >
					<table>
						<tr>
							<td><input type="text" name="userId" id="userId" placeholder="아이디" value="<%=saveid!=null?saveid:"" %>"></td><td></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password" placeholder="비밀번호"></td>
							<td><input class="btn btn-primary" type="submit" value="로그인"></td>
						</tr>
						<tr>
							<td colspan="2"><label><input type="checkbox" name="saveId" id="saveId" <%=saveid!=null?"checked":"" %>>아이디저장</label>
							<button class="btn btn-primary" type="button" onclick="location.assign('<%=request.getContextPath() %>/member/enrollMember.do')">회원가입</button></td>
						</tr>
					</table>
				</form>
				<% }else{ %>
					<table id="logged-in">
						<tr>
							<td colspan="2"><span style="color:blue; font-weight:bold;"><%=loginMember.getUsername() %></span>님 환영합니다</td>
						</tr>
						<tr>
							<td><button class="btn btn-primary" onclick="location.assign('<%=request.getContextPath()%>/member/memberView.do?userId=<%=loginMember.getUserid() %>')">내정보보기</button></td>
							<td><button class="btn btn-primary" onclick="location.replace('<%=request.getContextPath() %>/logout.do')">로그아웃</button></td>
						</tr>
					</table>
				<%} %>
			</div>
			<nav>
				<ul class="main-nav">
					<li class="home">
						<a href="<%=request.getContextPath() %>">Home</a>
					</li>
					<li class="notice">
						<a href="<%=request.getContextPath() %>/notice/noticeList.do">공지사항</a>
					</li>
					<li class="board">
						<a href="<%=request.getContextPath() %>/board/boardList.do">게시판</a>
					</li>
					<% if(loginMember != null && loginMember.getUserid().equals("admin")){ %>
					<li class="memberList">
						<a href="<%=request.getContextPath() %>/admin/memberList.do">회원정보</a>
					</li>
					<%} %>
				</ul>
			</nav>
		</header>