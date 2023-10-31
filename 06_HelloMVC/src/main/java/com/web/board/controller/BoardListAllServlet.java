package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.Board;
import com.web.board.service.BoardService;
import com.web.common.PageBarBuilder;

/**
 * Servlet implementation class BoardListAllServlet
 */
@WebServlet("/board/boardList.do")
public class BoardListAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListAllServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cp = request.getParameter("cPage");
		int cPage = Integer.parseInt(cp!=null?cp:"1");
		String nPP = request.getParameter("numPerpage");
		int numPerpage = Integer.parseInt(nPP!=null?nPP:"10");
		List<Board> boardList = BoardService.getService().selectBoardAll(cPage, numPerpage);
		
		request.setAttribute("boardList", boardList);
		
		int totalData = BoardService.getService().selectBoardAllCount();
		String pBS = request.getParameter("pageBarSize");
		int pageBarSize = Integer.parseInt(pBS!=null?pBS:"10");
		String pageBar = PageBarBuilder.pageBarBuilder(cPage, numPerpage, totalData, pageBarSize, request.getRequestURI());
		
		request.setAttribute("pageBar", pageBar);
		
		request.getRequestDispatcher("/views/board/boardList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
