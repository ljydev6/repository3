package com.web.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.notice.model.dto.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet(name = "noticeWriteEnd",urlPatterns = "/notice/noticewriteend.do")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String noticeTitle = request.getParameter("title");
		String noticeWriter = request.getParameter("author");
		String noticeContent = request.getParameter("content");
		String filePath = request.getParameter("filePath");
		Notice notice = Notice.builder().noticeTitle(noticeTitle)
										.noticeWriter(noticeWriter)
										.noticeContent(noticeContent)
										.filePath(filePath)
										.build();
		int result = NoticeService.getService().writeNotice(notice);
		
		String msg,loc;
		if(result>0) {
			msg = "정상적으로 등록 되었습니다.";
			loc = "/notice/noticeList.do";
		}else {
			msg = "등록에 실패하였습니다.";
			loc = "/notice/noticewrite.do";
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
