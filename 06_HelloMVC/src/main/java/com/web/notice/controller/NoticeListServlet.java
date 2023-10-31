package com.web.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.PageBarBuilder;
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
		int totalData = NoticeService.getService().selectNoticeCount();
		int pageBarSize = 5;
		
		request.setAttribute("pageBar", PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI()));
		
		request.getRequestDispatcher("/views/notice/noticeList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
