package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.dto.Member;
import com.web.member.service.MemberService;

/**
 * Servlet implementation class IdDuplicateCheckServlet
 */
@WebServlet("/member/idDuplicate.do")
public class IdDuplicateCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdDuplicateCheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//아이디가 중복인지 아닌지 여부를 판단
		//클라이언트가 전달한 아이디값이 DB(member테이블)에 있는지 확인하는 기능
		//확인결과를 저장
		
		//아이디 중복확인 결과를 출력해주는 화면 출력
		String userId = (String)request.getParameter("userId");
		Member m = MemberService.getService().selectMemberById(userId);
		
		request.setAttribute("result", m==null);
		
		request.getRequestDispatcher("/views/member/idDuplicate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
