package com.web.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.common.exception.BadAccessException;
import com.web.notice.model.dto.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet(name = "noticeWriteEnd",urlPatterns = "/notice/noticewriteend.do")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항을 등록하기
		// 1. 클라이언트가 보낸 데이터 저장
		// 파일 저장하기
		// 전송된 파일은 서버의 지정 경로에 저장하고 DB에는 파일명만 저장함
		// 파일 업로드 처리를 하기 위해 cos.jar 라이브러리를 이용
		// cos.jar가 제공하는 MultipartRequest 클래스를 이용
		// MultipartRequest 클래스는 매개변수 있는 생성자로 생성해서 이용
		// 1 : HttpServletRequest를 대입
		// 2 : 파일을 저장할 위치 대입 -> 절대경로 -> String
		// 3 : 업로드파일의 최대 크기 지정 -> byte단위 ex) 10Mb -> 10 * 1024 * 1024 ->int
		//						byte -> Kbyte(1024) -> Mbyte(1024) -> Gbyte(1024) -> Tbyte
		// 4 : 인코딩 방식 : String -> UTF-8
		// 5 : fileRename 규칙 설정(클래스) -> DefaultFileRenamePolicy클래스 제공
//		if(!ServletFileUpload.isMultipartContent(request)) {
//			throw new BadAccessException("잘못된 접근입니다. 관리자에게 문의하세요");
//		} -> 필터 설정 걸어줌
		// 1. 파일을 저장할 위치를 절대경로로 가져옴
		// ServletContext 클래스에서 getRealPath() 메소드를 제공 -> webapp폴더
		String path = getServletContext().getRealPath("/upload/notice/");
		// 2. 최대 파일 크기 지정
		int maxSize = 1024*1024*100;//100MB
		// 3. 인코딩 설정
		String encoding = "UTF-8";
		// 4. rename규칙
		DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
		// 매개변수값을 이용해서 MultipartRequest객체 생성하기
		MultipartRequest mrequest = new MultipartRequest(request, path, maxSize, encoding, dfr);
		// DB에 해당내용을 저장
		String noticeTitle = mrequest.getParameter("title");
		String noticeWriter = mrequest.getParameter("author");
		String noticeContent = mrequest.getParameter("content");
		// 업로드된 파일 명
		// MultipartRequest클래스가 제공하는 메소드를 이용해서 가져온다.
		// MultipartRequest.getOriginalFileName("key"); -> type=file 의 name 속성값
		// MultipartRequest.getFileSystemName("key");
//		String ori = mrequest.getOriginalFileName("attach");
		String rename = mrequest.getFilesystemName("attach");
		String filePath = rename;
		Notice notice = Notice.builder().noticeTitle(noticeTitle)
										.noticeWriter(noticeWriter)
										.noticeContent(noticeContent)
										.filePath(filePath)
										.build();
		int result = NoticeService.getService().writeNotice(notice);
		
		String msg,loc;
		if(result>0) {
			msg = "정상적으로 등록 되었습니다.";
			loc = "/notice/noticeList.do";
		}else {
			msg = "등록에 실패하였습니다.";
			loc = "/notice/noticewrite.do";
			File delFile = new File(path+"/"+rename);
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
