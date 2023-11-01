package com.web.board.model.dao;

import static com.web.common.JDBCTemplate.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.board.model.dto.Board;
import com.web.board.model.dto.BoardComment;

public class BoardDao {
	private static BoardDao dao = new BoardDao();
	private Properties sql = new Properties();
	private BoardDao() {
		String path = BoardDao.class.getResource("/sql/board/boardsql.properties").getPath();
		try(FileReader fr = new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BoardDao getDao() {
		return dao;
	}
	
	public List<Board> selectBoardAll(Connection conn, int cPage, int numPerpage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board>result = new ArrayList<>();
		int startData = (cPage -1)*numPerpage +1;
		int endData = cPage * numPerpage;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardAll"));
			pstmt.setInt(1, startData);
			pstmt.setInt(2, endData);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				result.add(getBoardList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public int selectBoardAllCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardAllCount"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public List<Board> selectBoardByKeyword(Connection conn, int cPage, int numPerpage, String type, String keyword){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board>result = new ArrayList<>();
		int startData = (cPage -1)*numPerpage +1;
		int endData = cPage * numPerpage;
		String query = sql.getProperty("selectBoardByKeyword").replace("#type", type);
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, startData);
			pstmt.setInt(3, endData);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				result.add(getBoardList(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public int selectBoardByKeywordCount(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		String query = sql.getProperty("selectBoardByKeywordCount").replace("#type", type); 
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public Board selectBoardByBoardNo(Connection conn, int BoardNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardByBoardNo"));
			pstmt.setInt(1, BoardNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getBoard(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public int updateBoardReadCount(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateBoardReadCount"));
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int insertBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoard"));
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardWriter());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getBoardOriginalFileName());
			pstmt.setString(5, board.getBoardRenamedFileName());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		int result = -1;
		String query = sql.getProperty("updateBoard");
		if(board.getBoardOriginalFileName()==null) {
			query = query.replace(", BOARD_ORIGINAL_FILENAME = ?, BOARD_RENAMED_FILENAME = ?","");
		}
		System.out.println(query);
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			if(board.getBoardOriginalFileName()!=null) {
				pstmt.setString(3, board.getBoardOriginalFileName());
				pstmt.setString(4, board.getBoardRenamedFileName());
				pstmt.setInt(5, board.getBoardNo());
			}else {
				pstmt.setInt(3, board.getBoardNo());
			}
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public int deleteBoard(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteBoard"));
			pstmt.setInt(1, boardNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public Board getBoardList(ResultSet rs) throws SQLException{
		return Board.builder().boardNo(rs.getInt("BOARD_NO"))
							  .boardTitle(rs.getString("BOARD_TITLE"))
							  .boardWriter(rs.getString("BOARD_WRITER"))
							  .boardDate(rs.getDate("BOARD_DATE"))
							  .boardRenamedFileName(rs.getString("BOARD_RENAMED_FILENAME"))
							  .boardReadCount(rs.getInt("BOARD_READCOUNT"))
							  .build();
	}
	
	public Board getBoard(ResultSet rs) throws SQLException{
		return Board.builder().boardNo(rs.getInt("BOARD_NO"))
							  .boardTitle(rs.getString("BOARD_TITLE"))
							  .boardWriter(rs.getString("BOARD_WRITER"))
							  .boardContent(rs.getString("BOARD_CONTENT"))
							  .boardDate(rs.getDate("BOARD_DATE"))
							  .boardOriginalFileName(rs.getString("BOARD_ORIGINAL_FILENAME"))
							  .boardRenamedFileName(rs.getString("BOARD_RENAMED_FILENAME"))
							  .boardReadCount(rs.getInt("BOARD_READCOUNT"))
							  .build();
	}

	public int insertBoardComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoardComment"));
			pstmt.setInt(1, bc.getLevel());
			pstmt.setString(2,bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4,bc.getBoardRef());
			if(bc.getBoardCommentRef()>0) {
				pstmt.setInt(5, bc.getBoardCommentRef());
			}else {
				pstmt.setString(5, null);
			}
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public List<BoardComment> selectBoardComments(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardComment> comments = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardComments"));
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				comments.add(getComment(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return comments;
	}
	
	private BoardComment getComment(ResultSet rs) throws SQLException{
		return BoardComment.builder().boardCommentNo(rs.getInt("BOARD_COMMENT_NO"))
									 .level(rs.getInt("BOARD_COMMENT_LEVEL"))
									 .boardCommentWriter(rs.getString("BOARD_COMMENT_WRITER"))
									 .boardCommentContent(rs.getString("BOARD_COMMENT_CONTENT"))
									 .boardRef(rs.getInt("BOARD_REF"))
									 .boardCommentRef(rs.getInt("BOARD_COMMENT_REF"))
									 .boardCommentDate(rs.getDate("BOARD_COMMENT_DATE"))
									 .build();
	}
}
