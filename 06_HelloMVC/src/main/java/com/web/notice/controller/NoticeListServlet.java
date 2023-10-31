package com.web.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.notice.model.dto.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/notice/noticeList.do")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nowPage = request.getParameter("cPage");
		int cPage = Integer.parseInt(nowPage!=null?nowPage:"1");
		String nPP = request.getParameter("numPerpage");
		int numPerpage = Integer.parseInt(nPP!=null?nPP:"10");
		List<Notice> notices = NoticeService.getService().getNoticeList(cPage, numPerpage);
		
		request.setAttribute("noticeList", notices);
		//PageBar 만들기
		int totalData = AdminService.getService().selectMemberCount();
		int totalPage = (int)Math.ceil((double)totalData/numPerpage);
		int pageBarSize = 5;
		int pageNo = ((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd = pageNo + pageBarSize -1;
		String pageBar="";
		
		pageBar = "<ul class='pagination justify-content-center'>";
		if(pageNo==1) {
			pageBar += "<li class='page-item disabled'><a class='page-link' href='#'>이전</a></li>";
		}else {
			pageBar += "<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>이전</a></li>";
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>";
			}else {
				pageBar+="<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+pageNo+"'>"+pageNo+"</a></li>";
			}
			pageNo++;
		}
		if(pageNo>=totalPage) {
			pageBar +="<li class='page-item disabled'><a class='page-link' href='#'>다음</a></li>";
		}else {
			pageBar +="<li class='page-item'><a class='page-link' href='"+request.getRequestURI()+"?cPage="+(pageNo)+"'>다음</a></li>";
		}
		pageBar+="</ul>";
		request.setAttribute("pageBar", pageBar);
		
		request.getRequestDispatcher("/views/notice/noticeList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
