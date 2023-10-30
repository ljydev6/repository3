package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.security.AESEncryptor;
import com.web.member.model.dto.Member;
import com.web.member.service.MemberService;

/**
 * Servlet implementation class EnrollMemberEndServlet
 */
@WebServlet(name = "enrollMember", urlPatterns = {"/member/enrollMemberEnd.do"})
public class EnrollMemberEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollMemberEndServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원 가입서비스 처리하는 서블릿
		// 1. 클라이언트가 보낸 데이터 가져오기
		// 2. 가져온 데이터 Member 테이블에 추가하기
		// 3. 추가한 결과 사용자에게 보여주기
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		try {
			email = AESEncryptor.encryptData(email);
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			phone = AESEncryptor.encryptData(phone);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String h = request.getParameter("hobby");
		String[] hobby = null;
		if(h!=null) {
			hobby = h.split(",");
		}else {
			hobby = new String[] {};
		}
		
		Member m = Member.builder().userid(userId)
								   .password(password)
								   .username(userName)
								   .age(age)
								   .email(email)
								   .phone(phone)
								   .address(address)
								   .gender(gender)
								   .hobby(hobby)
								   .build();
//		PasswordEncoder pe = new PasswordEncoder();
//		String encodePassword =pe.getSHA512(password); 
		System.out.println(m);
		int result = MemberService.getService().insertMember(m);
		String msg, loc;
		if(result>0) {
			msg = "회원가입에 성공하였습니다.";
			loc = "/";
		}else {
			msg = "회원가입에 실패하였습니다.";
			loc = request.getContextPath()+"/enrollMember.do";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
