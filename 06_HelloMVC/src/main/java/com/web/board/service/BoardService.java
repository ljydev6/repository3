package com.web.board.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.web.board.model.dao.BoardDao;
import com.web.board.model.dto.Board;
import com.web.board.model.dto.BoardComment;

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
	
	public Board selectBoardByBoardNo(int BoardNo, boolean read) {
		Connection conn = getConnection();
		Board board = BoardDao.getDao().selectBoardByBoardNo(conn, BoardNo); 
		if(board!=null && !read) {
			int result = BoardDao.getDao().updateBoardReadCount(conn, BoardNo);
			
			if(result>0) {
				commit(conn);
				board.setBoardReadCount(board.getBoardReadCount()+1);
				}
			else rollback(conn);
		}
		close(conn);
		return board;
	}
	
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().insertBoard(conn, board);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public int updateBoard(Board board) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().updateBoard(conn, board);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public int deleteBoard(int boardNo) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().deleteBoard(conn, boardNo);
		if(result>0) commit(conn);
		else rollback(conn);
		return result;
	}
	public int insertBoardComment(BoardComment bc) {
		Connection conn = getConnection();
		int result = BoardDao.getDao().insertBoardComment(conn, bc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	public List<BoardComment> selectBoardComments(int boardNo) {
		Connection conn = getConnection();
		List<BoardComment> comments = BoardDao.getDao().selectBoardComments(conn, boardNo);
		close(conn);
		return comments;
	}
	
	
}
