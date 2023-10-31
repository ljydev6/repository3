package com.web.board.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.web.board.model.dao.BoardDao;
import com.web.board.model.dto.Board;

public class BoardService {
	private static BoardService service = new BoardService();
	private BoardService() {}
	public static BoardService getService() {
		return service;
	}
	
	public List<Board> selectBoardAll(int cPage, int numPerpage){
		Connection conn = getConnection();
		List<Board> result = BoardDao.getDao().selectBoardAll(conn, cPage, numPerpage);
		close(conn);
		return result;
	}
	
	public int selectBoardAllCount() {
		Connection conn = getConnection();
		int result = BoardDao.getDao().selectBoardAllCount(conn);
		close(conn);
		return result;
	}
	
	public List<Board> selectBoardByKeyword(int cPage, int numPerpage, String type, String keyword){
		Connection conn = getConnection();
		List<Board> result = BoardDao.getDao().selectBoardByKeyword(conn, cPage, numPerpage, type, keyword);
		close(conn);
		return result;
	}
	
	public int selectBoardByKeywordCount(String type, String keyword) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().selectBoardByKeywordCount(conn, type, keyword);
		close(conn);
		return result;
	}
	
	public Board selectBoardByBoardNo(int BoardNo) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().updateBoardReadCount(conn, BoardNo);
		Board board = null; 
		if(result>0) {
			commit(conn);
			board = BoardDao.getDao().selectBoardByBoardNo(conn, BoardNo);
		}else {
			rollback(conn);
		}
		close(conn);
		return board;
	}
	
	
}
