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

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView.do")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardViewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bNo = request.getParameter("boardNo");
		int boardNo = Integer.parseInt(bNo!=null?bNo:"-1");
		if(boardNo>0) {
			Board board = BoardService.getService().selectBoardByBoardNo(boardNo);
			if(board!=null) {
				request.setAttribute("board", board);
				request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
			}else {
				throw new BadAccessException("유효하지 않은 게시글번호입니다.");
			}
		}else {
			throw new BadAccessException("유효하지 않은 게시글번호입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
