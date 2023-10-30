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
 * Servlet implementation class NoticeServlet
 */
@WebServlet("/notice/notice.do")
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nNo = request.getParameter("noticeNo");
		int noticeNo = Integer.parseInt(nNo!=null?nNo:"-1");
		if(noticeNo>0) {
			Notice notice = NoticeService.getService().selectNotice(noticeNo);
			if(notice != null) {
				request.setAttribute("notice", notice);
				request.getRequestDispatcher(request.getContextPath()+"/views/notice/noticeDetail.jsp").forward(request, response);
			}else {
				throw new BadAccessException("없는 공지사항 번호입니다.");
			}
		}else {
			throw new BadAccessException("잘못된 접근입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
