package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.dto.Member;
import com.web.member.service.MemberService;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet(name = "updateMember",urlPatterns = {"/member/memberUpdate.do"})
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
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
		Member result = MemberService.getService().updateMember(m);
		String msg = null;
		String loc = null;
		if(result!= null) {
			msg = "회원 정보 수정에 성공하였습니다.";
			loc = "/";
			request.getSession().setAttribute("loginMember", result);
		}else {
			msg = "회원 정보 수정에 실패하였습니다.";
			loc = "/member/memberView.do";
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
