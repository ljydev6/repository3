<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<script src="<%=request.getContextPath()%>/js/common.js"></script>
<section id="content">
	<h2 align="center" style="margin-top:200px">안녕하세요, MVC 입니다.</h2>
	<h2>아이디검색</h2>
	<input type="search" id="searchId" list="data"><button>검색</button>
	<datalist id="data"></datalist>
	<script>
		$('#searchId').keyup(e=>{
			const value = e.target.value;
			console.log(value);
			$.ajax({
				url:"<%=request.getContextPath()%>/member/searchId.do",
				data:{'keyword':value},
				success:data=>{
					console.log(data);
					const ids = data.split(',');
					const $datalist = $('datalist#data');
					$datalist.html('');
					ids.forEach(e=>{
						const $option = $('<option>').attr('value',e).text(e);
						$datalist.append($option);
					});
					$('#searchcontainer').html($datalist);
				},
				fail:(r,e)=>{
					console.log(r);
					console.log(e);
				}
			});
		});
	</script>
	<button onclick="searchAllMember();">회원조회</button>
	<input type="text" id="key"><button onclick="searchName();">이름으로 조회</button>
	<div id="members"></div>
	<button id="member">회원저장</button>
	<script>
		$('#member').click(e=>{
			const member={
					userid:"bsyoo",
					password:"bslove",
					username:"유병승",
					gender:"M",
					age:19,
					email:"teacherdev09@gmail.com",
					phone:"01055556666",
					address:"경기도 시흥시",
					hobby:["운동","독서"]
			}
			$.post("<%=request.getContextPath()%>/ajax/inserMember.do",
					{data:JSON.stringify(member)},
					data=>{
						console.log(data);
					});
		});
		const searchAllMember = ()=>{
			$.get('<%=request.getContextPath()%>/ajax/searchAllMember.do',
					data=>{
						console.log(data);
						$('div#members').append(maketable(data));
					});
		};
		const searchName = ()=>{
			$.get('<%=request.getContextPath()%>/ajax/searchMemberById.do?keyword='+$('#key').val(),
					data=>{
						$('div#members').html('');
						$('div#members').append(maketable(data));
					});
		}
	</script>
</section>
<%@ include file="/views/common/footer.jsp"%>