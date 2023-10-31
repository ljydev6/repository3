package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class NoticeWriteServlet
 */
@WebServlet("/notice/noticewrite.do")
public class NoticeWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		if(loginMember!=null && loginMember.getUserid().equals("admin")) {
			request.setAttribute("type", "write");
			request.getRequestDispatcher("/views/notice/noticeWrite.jsp").forward(request, response);
		}else {
			throw new BadAccessException("공지사항은 관리자만 작성할 수 있습니다.");
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
