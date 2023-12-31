package com.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class MemberSearchServlet
 */
@WebServlet(name="searchMember",urlPatterns = "/admin/searchMember")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSearchServlet() {
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
			String type = request.getParameter("searchType");
			String keyword = request.getParameter("searchKeyword");
			
			List<Member> members = AdminService.getService().searchMember(type,keyword, cPage, numPerPage);
			request.setAttribute("memberList", members);
			
			//PageBar 만들기
			int totalData = AdminService.getService().selectMemberCount(type,keyword);
			int totalPage = (int)Math.ceil((double)totalData/numPerPage);
			int pageBarSize = 5;
			int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
			int pageEnd = pageNo + pageBarSize -1;
			String pageBar="";
			
			pageBar = "<ul class='pagination justify-content-center'>";
			if(pageNo==1) {
				pageBar += "<li class='page-item disabled'><a class='page-link' href='#'>이전</a></li>";
			}else {
				pageBar += "<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"&numPerpage="+numPerPage+"&searchType="+type+"&searchKeyword="+keyword+"'>이전</a></li>";
			}
			while(!(pageNo>pageEnd||pageNo>totalPage)) {
				if(pageNo==cPage) {
					pageBar+="<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>";
				}else {
					pageBar+="<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+pageNo+"&numPerpage="+numPerPage+"&searchType="+type+"&searchKeyword="+keyword+"'>"+pageNo+"</a></li>";
				}
				pageNo++;
			}
			if(pageNo>=totalPage) {
				pageBar +="<li class='page-item disabled'><a class='page-link' href='#'>다음</a></li>";
			}else {
				pageBar +="<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo)+"&numPerpage="+numPerPage+"&searchType="+type+"&searchKeyword="+keyword+"'>다음</a></li>";
			}
			pageBar+="</ul>";
			request.setAttribute("pageBar", pageBar);
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
