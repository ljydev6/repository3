package com.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.Board;
import com.web.board.service.BoardService;
import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet(name="boardUpdate",urlPatterns = "/board/boardUpdate.do")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String no = request.getParameter("boardNo");
		int boardNo = Integer.parseInt(no!=null?no:"-1");
		if(boardNo>0) {
			Board board = BoardService.getService().selectBoardByBoardNo(boardNo,true);
			if(board!=null) {
				if(loginMember.getUserid().equals(board.getBoardWriter())||loginMember.getUserid().equals("admin")) {
					request.setAttribute("board", board);
					request.setAttribute("type", "update");
					request.getRequestDispatcher("/views/board/boardWrite.jsp").forward(request, response);
				} else {
					throw new BadAccessException("권한이 부족합니다.");
				}
			}else {
				throw new BadAccessException("부적합한 글 번호 입니다.");
			}
		}else {
			throw new BadAccessException("부적합한 글 번호 입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
