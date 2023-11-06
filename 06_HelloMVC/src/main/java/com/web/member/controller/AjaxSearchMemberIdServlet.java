package com.web.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class AjaxSearchMemberIdServlet
 */
@WebServlet("/member/searchId.do")
public class AjaxSearchMemberIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxSearchMemberIdServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/csv;charset=utf-8");
		String keyword = request.getParameter("keyword");
		List<Member> searchMember = AdminService.getService().searchMember("USERID", keyword, 1, 20);
		String csv="";
		if(searchMember!=null) {
			for(int i=0; i<searchMember.size(); i++) {
				if(i!=0) csv+=",";
				csv+=searchMember.get(i).getUserid();
			}
		}
		response.getWriter().print(csv);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
