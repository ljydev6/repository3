package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.Board;
import com.web.board.model.dto.BoardComment;
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
		Cookie[] cookies = request.getCookies();
		String readBoard = "";
		boolean readResult = false;
		for(Cookie c: cookies) {
			String name = c.getName();
			if(name.equals("readBoard")) {
				readBoard = c.getValue();
				if(readBoard.contains("|"+boardNo+"|")) readResult = true;
				break;
			}
		}
		
		if(boardNo>0) {
			Board board = BoardService.getService().selectBoardByBoardNo(boardNo, readResult);
			if(board!=null) {
				if(!readResult) {
					Cookie c = new Cookie("readBoard",readBoard+"|"+boardNo+"|");
					c.setMaxAge(60*60*24);
					response.addCookie(c);
				}
				List<BoardComment> comments = BoardService.getService().selectBoardComments(boardNo);
				
				request.setAttribute("board", board);
				request.setAttribute("comments", comments);
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
