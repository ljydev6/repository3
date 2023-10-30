<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
	String loc = (String)request.getAttribute("loc");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시스템메세지</title>
</head>
<body>
	<script>
		alert("<%=msg%> ");
		<% if(!loc.equals("pwchangeclose")){ %>
		location.replace('<%=request.getContextPath()%><%=loc%>');
		<%}else{%>
		opener.location.replace('<%=request.getContextPath()%>/logout.do?state=pwchange');
		close();
		<%}%>
	</script>
</body>
</html>