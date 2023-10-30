<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
    div#updatePassword-container{
        background:skyblue;
    }
    div#updatePassword-container table {
        margin:0 auto;
        border-spacing: 20px;
    }
    div#updatePassword-container table tr:last-of-type td {
        text-align:center;
    }
    </style>
    <script type="text/javascript" src='<%=request.getContextPath() %>/js/jquery-3.7.0.min.js'></script>
</head>
<body>
    <div id="updatePassword-container">
		<form name="updatePwdFrm" action="<%=request.getContextPath() %>/member/passwordChangeEnd.do" method="post" >
		<input type="hidden" name="userId" value="<%=request.getParameter("userId") %>">
			<table>
				<tr>
					<th>현재 비밀번호</th>
					<td>
					<input type="password" name="password" id="password" required>
					</td>
				</tr>
				<tr>
					<th>변경할 비밀번호</th>
					<td>
						<input type="password" name="password_new" id="password_new" required>
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>	
						<input type="password" id="password_chk" required><br>
						<span id="result"></span>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="변경" disabled/>&nbsp;
						<input type="button" value="닫기" onclick="close();"/>						
					</td>
				</tr>
			</table>
		</form>
		<script>
			const passwordck = $('#password_chk');
			passwordck.on('keyup',(e)=>{
				const pw = $('#password_new').val();
				const pwck = passwordck.val();
				let msg, color, result;
				if(pw==pwck){
					msg="일치합니다";
					color="lime";
					result = false;
				}else{
					msg="일치하지 않습니다";
					color="magenta";
					result = true;
				}
				$('#result').text(msg);
				$('#result').css('color',color);
				$('[type=submit]')[0].disabled = result;
			});
		
			const fn_pw_update_validate = ()=>{
				const reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
				const password = $("#password_new").val();
				if(!reg.test(password)){
					alert('비밀번호는 영문자, 특수기호, 숫자를 반드시 포함해야 합니다.');
					$('#password_').val("");
					$('#password_2').val("");
					$('#password_').focus();
					return false;
				}
				const password2 = $("#password_chk").val();
				if(password != password2){
					alert("패스워드내용과 패스워드 확인 내용이 일치하지 않습니다.");
					return false;
				}
			}
		</script>
	</div>
</body>
</html>