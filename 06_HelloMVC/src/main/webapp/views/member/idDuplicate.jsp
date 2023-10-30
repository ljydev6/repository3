<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
boolean result=(boolean)request.getAttribute("result");
 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복확인</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/testmodule.js"></script>
<style>
	div#checkId-container{
	text-align: center;
	padding-top: 50px;
	}
	span#duplicated{
	color:red;
	font-weight: bolder;
	}
</style>
</head>
<body>
<div id="checkId-container">
			<%if(result){ %>
			[<span id="userId"><%=request.getParameter("userId") %></span>]는 사용가능합니다.	
			<br><br>
			<button type="button" onclick="close();">닫기</button>
			<%}else{ %>
			[<span id="duplicated"><%=request.getParameter("userId") %></span>]는 사용중입니다.
			<br><br>
			<!-- 아이디 재입력창 구성 -->
			<form action="<%=request.getContextPath() %>/member/idDuplicate.do" method="get" onsubmit="return vali();">
				<input type="text" name="userId" id="userId" onsubmit="">
				<input type="submit" value="중복검사" >
			</form>
			<%} %>
	</div>
	<script type="text/javascript">
		const btn = document.querySelector("button[type=button]");
		btn.addEventListener("click",e=>{
			//부모 window객체 접근할 수 있음
			const $userId = opener.document.querySelector("#userId_");
			$userId.value = '<%= request.getParameter("userId")%>';
			$userId.readOnly = true;
			$userId.style.backgroundColor="lightgray";
			close();
		});
		
		const vali = ()=>{
			const userId = document.querySelecor("#userId");
			if(userId.value().length<=4){
				alert('아이디는 4글자 이상 입력해주세요.');
				userId.value("");
				userId.focus();
				return false;
			}
		};
	</script>
</body>
</html>