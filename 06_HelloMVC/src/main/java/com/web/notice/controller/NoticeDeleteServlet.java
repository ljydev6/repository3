package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.exception.BadAccessException;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteServlet
 */
@WebServlet(name="deleteNotice", urlPatterns = "/notice/noticedelete.do")
public class NoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDeleteServlet() {
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
			int result = NoticeService.getService().deleteNotice(noticeNo);
			String msg, loc;
			if(result>0) {
				msg = "정상적으로 삭제되었습니다.";
				loc = "/notice/noticeList.do";
			}else {
				msg = "삭제 중 오류가 발생하였습니다.";
				loc = "/notice/notice.do?noticeNo="+noticeNo;
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		}else {
			throw new BadAccessException("유효하지 않은 글 번호입니다.");
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
