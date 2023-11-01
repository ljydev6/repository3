package com.web.board.controller;

import java.io.File;
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
 * Servlet implementation class BoardDeleteServlet
 */
@WebServlet(name="boardDelete",urlPatterns = "/board/boardDelete.do")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String bNo = request.getParameter("boardNo");
		int boardNo = Integer.parseInt(bNo!=null?bNo:"-1");
		if(boardNo>0) {
			Board board = BoardService.getService().selectBoardByBoardNo(boardNo,true);
			if(board != null) {
				if(loginMember.getUserid().equals(board.getBoardWriter())||loginMember.getUserid().equals("admin")) {
					int result = BoardService.getService().deleteBoard(boardNo);
					String msg, loc;
					if(result>0) {
						msg = "성공적으로 삭제 되었습니다.";
						loc = "/board/boardList.do";
						if(board.getBoardRenamedFileName()!=null) {
						File delFile = new File(getServletContext().getRealPath("/upload/board/")+"/"+board.getBoardRenamedFileName());
						if(delFile.exists())delFile.delete();
						}
					}else {
						msg = "삭제중 오류가 발생하였습니다.";
						loc = "/board/boardUpdate.do?boardNo="+boardNo;
					}
					request.setAttribute("msg", msg);
					request.setAttribute("loc", loc);
					request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
				}else {
					throw new BadAccessException("권한이 부족합니다.");
				}
			}else {
				throw new BadAccessException("유효하지 않은 게시글 번호입니다.");
			}
		}else {
			throw new BadAccessException("유효하지 않은 게시글 번호입니다.");
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
