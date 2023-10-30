package com.web.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.service.MemberService;

/**
 * Servlet implementation class PasswordChangeEndServlet
 */
@WebServlet(name="updatePassword",urlPatterns = {"/member/passwordChangeEnd.do"})
public class UpdatePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePasswordEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = (String)request.getParameter("userId");
		String passwordOld = (String)request.getParameter("password");
		String passwordNew = (String)request.getParameter("password_new");
		System.out.println(passwordOld);
		System.out.println(passwordNew);
		int result = MemberService.getService().updatePassword(userId, passwordOld, passwordNew);
		String msg = null;
		String loc = null;
		if(result>0) {
			msg = "비밀번호 변경에 성공하였습니다. 다시 로그인해 주세요";
			loc = "pwchangeclose";
		}else {
			msg = "비밀번호 변경에 실패하였습니다.";
			loc = "/member/passwordchange.do?userId="+userId;
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
