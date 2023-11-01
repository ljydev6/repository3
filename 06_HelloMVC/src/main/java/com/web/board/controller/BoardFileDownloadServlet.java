package com.web.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.Board;
import com.web.board.service.BoardService;
import com.web.common.exception.BadAccessException;

/**
 * Servlet implementation class BoardFileDownloadServlet
 */
@WebServlet("/board/filedownload.do")
public class BoardFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFileDownloadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bNo = request.getParameter("boardNo");
		int boardNo = Integer.parseInt(bNo!=null?bNo:"-1");
		if(boardNo>0) {
//		String fileName=request.getParameter("fname");
		Board board = BoardService.getService().selectBoardByBoardNo(boardNo,true);
		String oriFileName = board.getBoardOriginalFileName();
		String reFileName = board.getBoardRenamedFileName();
		
		//파일 스트림연결하기
		//실제 경로가져오기
		
		String path = getServletContext().getRealPath("/upload/board/");
		FileInputStream fis = new FileInputStream(path+reFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		//파일명 인코딩 처리하기
		String encodingName = new String(oriFileName.getBytes("UTF-8"),"ISO-8859-1");
		
		//응답 헤더 설정 -> contentType 설정
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="+encodingName);
		
		//사용자에게 파일 데이터 전송
		ServletOutputStream sos = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(sos);
		
		int read=-1;
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		
		bis.close();
		bos.close();
		}else {
			throw new BadAccessException("유효하지 않은 접근입니다.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
