/**
 * 
 */
const fn_membervalidate = ()=>{
	//반환값이 false면 submit을 실행하지 않음
	//true면 정상실행
	
	const userId = $("#userId_").val().trim();
	if(userId.length<4){
		alert("아이디는 4글자 이상 입력하세요");
		return false;
	}
	const reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
	const password = $("#password_").val();
	if(!reg.test(password)){
		alert('비밀번호는 영문자, 특수기호, 숫자를 반드시 포함해야 합니다.');
		$('#password_').val("");
		$('#password_2').val("");
		$('#password_').focus();
		return false;
	}
	const password2 = $("#password_2").val();
	if(password != password2){
		alert("패스워드내용과 패스워드 확인 내용이 일치하지 않습니다.");
		return false;
	}
}

const fn_idDuplicateCheck = (contextPath)=>{
	const userId = $("#userId_").val().trim();
	
	if(userId.length>=4){
		const child = open(contextPath+'/member/idDuplicate.do?userId='+userId,'_blank','width=300px, height=200px'); 
	}else{
		alert("아이디는 4글자이상 입력하세요");
		$("#userId_").val("");
		$("#userId_").focus();
	}
}