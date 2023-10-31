package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.exception.BadAccessException;
import com.web.notice.model.dto.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet(name="noticeUpdate",urlPatterns = "/notice/noticeupdate.do")
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("noticeNo");
		int noticeNo = Integer.parseInt(no!=null?no:"-1");
		if(noticeNo>0) {
			Notice notice = NoticeService.getService().selectNotice(noticeNo);
			request.setAttribute("notice", notice);
			request.setAttribute("type", "update");
			request.getRequestDispatcher("/views/notice/noticeWrite.jsp").forward(request, response);
		}else {
			throw new BadAccessException("부적합한 글 번호 입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
