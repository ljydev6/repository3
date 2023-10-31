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
 * Servlet implementation class NoticeDetailServlet
 */
@WebServlet("/notice/notice.do")
public class NoticeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nNo = request.getParameter("noticeNo");
		int noticeNo = Integer.parseInt(nNo!=null?nNo:"-1");
		Notice notice = NoticeService.getService().selectNotice(noticeNo);
		if(notice!=null) {
			request.setAttribute("notice", notice);
			request.getRequestDispatcher("/views/notice/noticeDetail.jsp").forward(request, response);
		}else {
			throw new BadAccessException("유효하지 않은 공지사항글번호");
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
