<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.web.member.model.dto.Member,java.util.Arrays" %>
<%@ include file="/views/common/header.jsp" %>
<% List<Member> memberList = (List<Member>)request.getAttribute("memberList"); 
	String searchType = request.getParameter("searchType");
	if(searchType==null) searchType = "userId";
	String searchKeyword = request.getParameter("searchKeyword");
%>
    <section id="memberList-container">
        <h2 class="text-info">회원관리</h2>
                <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" <%=searchType.equals("userId")?"selected":"" %>>아이디</option>
        		<option value="userName" <%=searchType.equals("userName")?"selected":"" %>>회원이름</option>
        		<option value="gender" <%=searchType.equals("gender")?"selected":"" %>>성별</option>
        	</select>
        	<div id="search-userId" style="display:<%=searchType.equals("userId")?"inline-block":"hidden" %>;">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요" <%=searchType.equals("userId")&&searchKeyword!=null?"value='"+searchKeyword+"'":"" %>>
        			<button class="btn btn-outline-primary" type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName"  style="display:<%=searchType.equals("userName")?"inline-block":"hidden" %>;">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요" <%=searchType.equals("userName")&&searchKeyword!=null?"value='"+searchKeyword+"'":"" %>>
        			<button class="btn btn-outline-primary" type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender"  style="display:<%=searchType.equals("gender")?"inline-block":"hidden" %>;">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M" <%=searchType.equals("gender")&&searchKeyword.equals("M")?"checked":"" %>>남</label>
        			<label><input type="radio" name="searchKeyword" value="F" <%=searchType.equals("gender")&&searchKeyword.equals("F")?"checked":"" %>>여</label>
        			<button class="btn btn-outline-primary" type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<form id="numPerFrm" action="">
        		<select name="numPerpage" id="numPerpage">
        			<option value="10">10</option>
        			<option value="5" selected>5</option>
        			<option value="3" >3</option>
        		</select>
        	</form>
        </div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>아이디</th>
		    <th>이름</th>
		    <th>성별</th>
		    <th>나이</th>
		    <th>이메일</th>
		    <th>전화번호</th>
		    <th>주소</th>
		    <th>취미</th>
		    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
       	    <!-- 조회된 결과가 없으면 한줄(row)로 결과가 없습니다 출력하고
	    조회된 결과가 있으면 각 객체를 tr로 출력하는 구문을 작성할것 -->
	    		<% if(memberList!=null && memberList.size()>0){ %>
	    			<% for(Member t : memberList){ %>
	    			<tr>
	    				<td><%=t.getUserid() %></td>
	    				<td><%=t.getUsername() %></td>
	    				<td><%=t.getGender() %></td>
	    				<td><%=t.getAge() %></td>
	    				<td><%=t.getEmail() %></td>
	    				<td><%=t.getPhone() %></td>
	    				<td><%=t.getAddress() %></td>
	    				<%-- <td><%String[] hobby = t.getHobby();
	    				if(hobby != null && hobby.length >0){%>
	    					<ul>
	    						<%for(String h:hobby){ %>
	    							<li><%=h %></li>
	    						<%} %>
	    					</ul>
	    				<%}%>
	    				</td> --%>
	    				<% if(t.getHobby()!=null){ %>
	    				<td><%=Arrays.toString(t.getHobby()) %></td>
	    				<%}else{ %>
	    				<td></td>
	    				<%} %>
	    				<td><%=t.getEnrolldate() %></td>
	    			</tr>
	    		<%}}else{ %>
	    		<tr><td colspan="9">조회된 결과가 없습니다.</td></tr>
	    		<%} %>
            </tbody>
        </table>
        <%=(String)request.getAttribute("pageBar") %>
    </section>
<script>
	
	$('select#searchType').on('change',()=>{
		$('div#search-container > div').css('display','none');
		$('div#search-'+$('select#searchType').val()).css('display','inline-block');
	});
</script>
<%@ include file="/views/common/footer.jsp" %>