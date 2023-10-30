package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout.do")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그아웃 위해 세션정보 삭제
		// HttpSession을 삭제하는 기능
		// getSession(boolean)
		// true : session객체가 있으면 가져오고 없으면 생성해서 가져옴
		// false : session객체가 있으면 가져오고 없으면 null 반환
		String state = request.getParameter("state");
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}if(state != null && state.equals("pwchange")){
			response.sendRedirect(request.getContextPath());
			return;
		}else {
			request.setAttribute("msg", "안전하게 로그아웃 되었습니다.");
			request.setAttribute("loc", "/");
		}
		//메인화면으로 이동
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
