package com.web.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.board.model.dto.Board;
import com.web.board.service.BoardService;
import com.web.common.exception.BadAccessException;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class BoardUpdateEndServlet
 */
@WebServlet(name="boardUpdateEnd",urlPatterns = "/board/boardUpdateEnd.do")
public class BoardUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		// 1. 파일을 저장할 위치를 절대경로로 가져옴
		// ServletContext 클래스에서 getRealPath() 메소드를 제공 -> webapp폴더
		String path = getServletContext().getRealPath("/upload/board/");
		// 2. 최대 파일 크기 지정
		int maxSize = 1024*1024*100;//100MB
		// 3. 인코딩 설정
		String encoding = "UTF-8";
		// 4. rename규칙
		DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
		// 매개변수값을 이용해서 MultipartRequest객체 생성하기
		MultipartRequest mrequest = new MultipartRequest(request, path, maxSize, encoding, dfr);
		
		String bNo = mrequest.getParameter("boardNo");
		int boardNo = Integer.parseInt(bNo!=null?bNo:"-1");
		if(boardNo>0) {
			String boardWriter = mrequest.getParameter("writer");
			if(loginMember.getUserid().equals(boardWriter)||loginMember.getUserid().equals("admin")) {
				
				String boardTitle = mrequest.getParameter("title");
				String boardContent = mrequest.getParameter("content");
				String originalFileName = mrequest.getOriginalFileName("attach");
				String renameFileName = mrequest.getFilesystemName("attach");
				String exReFileName = mrequest.getParameter("exReFileName");
				Board board = Board.builder().boardNo(boardNo)
						.boardTitle(boardTitle)
						.boardWriter(boardWriter)
						.boardContent(boardContent)
						.boardOriginalFileName(originalFileName)
						.boardRenamedFileName(renameFileName)
						.build();
				int result = BoardService.getService().updateBoard(board);
				String msg, loc;
				if(result>0) {
					msg = "정상적으로 수정되었습니다.";
					loc = "/board/boardView.do?boardNo="+boardNo;
					if(originalFileName!= null && exReFileName != null) {
						File delFile = new File(path + "/" + exReFileName);
						if(delFile.exists())delFile.delete();
					}
				}else {
					msg = "수정중 오류가 발생하였습니다.";
					loc = "/board/boardUpdate.do?boardNo="+boardNo;
					if(renameFileName!= null) {
						File delFile = new File(path + "/" + renameFileName);
						if(delFile.exists())delFile.delete();
					}
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
