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
 * Servlet implementation class MemberViewServlet
 */
@WebServlet(name="memberView",urlPatterns = "/member/memberView.do")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String userId = (String) request.getParameter("userId");
		
		String views = "";
		String msg = "";
		String loc = "/";
		
//		if(loginMember != null) {
			if(loginMember.getUserid().equals(userId)||loginMember.getUserid().equals("admin")) {
				Member m = MemberService.getService().selectMemberById(userId);
				try {
					m.setEmail(AESEncryptor.decryptData(m.getEmail()));
				}catch(Exception e) {
					e.printStackTrace();
				}
				try {
					m.setPhone(AESEncryptor.decryptData(m.getPhone()));
				}catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println(m.getPassword());
				request.getSession().setAttribute("member", m);
				views = "/views/member/memberview.jsp";
			}else {
				msg = "자기 자신의 정보만 확인 할 수 있습니다.";
				loc = "/views/member/memberview.jsp?userId="+loginMember.getUserid();
			}
//		}else {
//			msg = "로그인이 필요한 페이지입니다.";
//			loc = "/";
//			views = "/views/common/msg.jsp";
//		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(views).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
