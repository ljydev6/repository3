package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.dto.Member;
import com.web.member.service.MemberService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="login",urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 클라이언트가 보낸 아이디와 패스워드를 받아온다.
		// request.getParameter(), request.getParameterValues()
//		String userId = request.getParameter("userId");
//		String password = request.getParameter("password");
		// 2. DB의 MEMBER 테이블에서 보낸 아이디와 패스워드가 일치하는 회원을 가져온다.
		// 3. 결과를 출력한다(응답 페이지를 선택한다.)
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = (String)request.getParameter("userId");
		String password = (String)request.getParameter("password");
		
		if(request.getParameter("saveId")!=null) {
			Cookie c = new Cookie("saveId",userid);
			c.setMaxAge(60*60*24*30);
			response.addCookie(c);
		}else {
			Cookie c = new Cookie("saveId",null);
			c.setMaxAge(0);
			response.addCookie(c);
		}
		Member loginResult = MemberService.getService().login(userid,password);
		if(loginResult != null) {
			System.out.println("로그인 성공");
			request.getSession().setAttribute("loginMember", loginResult);
			response.sendRedirect(request.getHeader("Referer"));
		}else {
			System.out.println("로그인 실패");
			request.setAttribute("msg", "ID나 패스워드가 일치하지 않습니다.");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}
	}

}
