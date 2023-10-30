<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.member.model.dto.Member,java.util.List,java.util.Arrays" %>
<%@ include file="/views/common/header.jsp"%>
<% Member m = (Member)request.getSession().getAttribute("member"); 
	List<String> hobby = Arrays.asList(m.getHobby());
	
%>

<section id=enroll-container>
		<h2>회원 정보 수정</h2>
		<form id="memberFrm" method="post" action="<%=request.getContextPath() %>/member/memberUpdate.do" onsubmit="return fn_membervalidate();">
			<table>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" name="userId" id="userId_" value="<%=m.getUserid() %>" readonly="readonly" style="background-color: lightgray">
					</td>
				</tr>
				<%-- <tr>
					<th>패스워드</th>
					<td>
						<input type="password" name="password" id="password_" value="<%=m.getPassword()%>">
					</td>
				</tr>
				<tr>
					<th>패스워드확인</th>
					<td>	
						<input type="password" id="password_2"><br>
					</td>
				</tr>   --%>
				<tr>
					<th>이름</th>
					<td>	
					<input type="text"  name="userName" id="userName" required value="<%=m.getUsername() %>"><br>
					</td>
				</tr>
				<tr>
					<th>나이</th>
					<td>	
					<input type="number" name="age" id="age" value="<%=m.getAge()%>"><br>
					</td>
				</tr> 
				<tr>
					<th>이메일</th>
					<td>	
						<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%=m.getEmail()%>"><br>
					</td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td>	
						<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%=m.getPhone() %>"><br>
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>	
						<input type="text" placeholder="" name="address" id="address" value="<%=m.getAddress()%>"><br>
					</td>
				</tr>
				<tr>
					<th>성별 </th>
					<td>
							<input type="radio" name="gender" id="gender0" value="M" <%=(m.getGender().equals("M"))?"checked":"" %>>
							<label for="gender0">남</label>
							<input type="radio" name="gender" id="gender1" value="F" <%=(m.getGender().equals("F"))?"checked":"" %>>
							<label for="gender1">여</label>
						
						
					</td>
				</tr>
				<tr>
					<th>취미 </th>
					<td>
					
						<input type="checkbox" name="hobby" id="hobby0" value="운동" <%=hobby.contains("운동")?"checked":"" %>><label for="hobby0">운동</label>
						<input type="checkbox" name="hobby" id="hobby1" value="등산" <%=hobby.contains("등산")?"checked":"" %>><label for="hobby1">등산</label>
						<input type="checkbox" name="hobby" id="hobby2" value="독서" <%=hobby.contains("독서")?"checked":"" %>><label for="hobby2">독서</label><br />
						<input type="checkbox" name="hobby" id="hobby3" value="게임" <%=hobby.contains("게임")?"checked":"" %>><label for="hobby3">게임</label>
						<input type="checkbox" name="hobby" id="hobby4" value="여행" <%=hobby.contains("여행")?"checked":"" %>><label for="hobby4">여행</label><br />
						

					</td>
				</tr>
			</table>
			<input type="button" value="정보수정" id="updateBtn"/>
			<!-- 비밀번호수정 버튼을 누르면 새 창이 open되고, 비밀번호 수정시키기 -->
			<input type="button" value="비밀번호 수정" id="updatePasswordBtn"/>
			<input type="button" value="탈퇴"/>
		</form>
	</section>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/testmodule.js"></script>
	<script>
	$("#updateBtn").on('click',(e=>{
		$("#memberFrm").submit();
	}));
	$("#updatePasswordBtn").on('click',(e=>{
		open('<%=request.getContextPath()%>/member/passwordchange.do?userId=<%=m.getUserid()%>','_blank','width=400px, height=210px, left=500px, top=200px, toolbar=no, location=no');
	}));
	</script>
<%@ include file="/views/common/footer.jsp"%>