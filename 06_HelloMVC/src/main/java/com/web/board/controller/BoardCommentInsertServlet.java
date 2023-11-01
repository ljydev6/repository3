package com.web.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.BoardComment;
import com.web.board.service.BoardService;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/insertComment.do")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("boardRef"));
		int level = Integer.parseInt(request.getParameter("level"));
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		int boardCommentRef = Integer.parseInt(request.getParameter("boardCommentRef"));
		
		BoardComment bc = BoardComment.builder().boardCommentWriter(writer)
												.boardCommentContent(content)
												.boardRef(boardNo)
												.boardCommentRef(boardCommentRef)
												.level(level)
												.build();
		int result = BoardService.getService().insertBoardComment(bc);
		response.sendRedirect(request.getContextPath()+"/board/boardView.do?boardNo="+boardNo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
