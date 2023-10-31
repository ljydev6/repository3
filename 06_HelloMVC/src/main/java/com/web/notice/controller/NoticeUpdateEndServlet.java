package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;
import com.web.notice.model.dto.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeupdateend.do")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		if(loginMember.getUserid().equals("admin")) {
			String no = request.getParameter("no");
			int noticeNo = Integer.parseInt(no!=null?no:"-1");
			if(noticeNo>0) {
				String noticeTitle = request.getParameter("title");
				String noticeContent = request.getParameter("content");
				Notice notice = Notice.builder().noticeNo(noticeNo)
												.noticeTitle(noticeTitle)
												.noticeContent(noticeContent)
												.build();
				int result = NoticeService.getService().updateNotice(notice);
				String msg, loc;
				if(result>0) {
					msg = "글이 정상적으로 수정되었습니다.";
					loc = "/notice/notice.do?noticeNo="+noticeNo;
				}else {
					msg = "글이 정상적으로 수정되지 않았습니다. 관리자에게 문의하세요";
					loc = "/notice/noticeupdate.do?no="+noticeNo;
				}
				request.setAttribute("msg", msg);
				request.setAttribute("loc", loc);
				request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			}else {
				throw new BadAccessException("부정확한 글 번호 입니다.");
			}
		}else {
			throw new BadAccessException("공지사항 수정은 관리자만 가능합니다.");
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
