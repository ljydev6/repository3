package com.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.common.PageBarBuilder;
import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet(name="memberList",urlPatterns = "/admin/memberList.do")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nowPage = request.getParameter("cPage");
		String nPP = request.getParameter("numPerpage");
		int cPage = Integer.parseInt(nowPage!=null?nowPage:"1");
		int numPerPage = Integer.parseInt(nPP!=null?nPP:"5");
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		if(loginMember.getUserid().equals("admin")) {
			
			List<Member> members = AdminService.getService().getMembers(cPage, numPerPage);
			
			request.setAttribute("memberList", members);
			
			//PageBar 만들기
			int totalData = AdminService.getService().selectMemberCount();
			int pageBarSize = 5;
			request.setAttribute("pageBar", PageBarBuilder.pageBarBuilder(cPage, numPerPage, totalData, pageBarSize, request.getRequestURI()));
			request.getRequestDispatcher("/views/admin/MemberList.jsp").forward(request, response);
			
			
			
		}else {
			throw new BadAccessException("관리자 계정이 아닙니다.");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
