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

/**
 * Servlet implementation class BoardWriteEndServlet
 */
@WebServlet(name="boardWriteEnd",urlPatterns = "/board/boardWriteEnd.do")
public class BoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 파일을 저장할 위치를 절대경로로 가져옴
		// ServletContext 클래스에서 getRealPath() 메소드를 제공 -> webapp폴더
		String path = getServletContext().getRealPath("/upload/board/");
		
//		String path = getServletContext().getRealPath("/upload")+"board";
//		File dir = new File(path);
//		if(!dir.exists()) dir.mkdirs();
		// -> upload 까지만 폴더를 만들어놓고 없으면 하위 폴더를 만들게하기
		
		// 2. 최대 파일 크기 지정
		int maxSize = 1024*1024*100;//100MB
		// 3. 인코딩 설정
		String encoding = "UTF-8";
		// 4. rename규칙
		DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
		// 매개변수값을 이용해서 MultipartRequest객체 생성하기
		MultipartRequest mrequest = new MultipartRequest(request, path, maxSize, encoding, dfr);
		
		String boardTitle = mrequest.getParameter("title");
		String boardWriter = mrequest.getParameter("writer");
		String boardContent = mrequest.getParameter("content");
		String originalFileName = mrequest.getOriginalFileName("attach");
		String renameFileName = mrequest.getFilesystemName("attach");
		Board board = Board.builder().boardTitle(boardTitle)
									 .boardWriter(boardWriter)
									 .boardContent(boardContent)
									 .boardOriginalFileName(originalFileName)
									 .boardRenamedFileName(renameFileName)
									 .build();
		int result = BoardService.getService().insertBoard(board);
		
		String msg, loc;
		if(result>0) {
			msg = "정상적으로 등록되었습니다.";
			loc = "/board/boardList.do";
		}else {
			msg = "등록에 실패하였습니다.";
			loc = "/board/boardWrite.do";
			File delFile = new File(path + "/" + renameFileName);
			if(delFile.exists())delFile.delete();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
