package com.web.notice.model.dao;

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

import com.web.notice.model.dto.Notice;

public class NoticeDao {
	private static NoticeDao dao= new NoticeDao();
	private Properties sql = new Properties();
	
	private NoticeDao() {}
	
	public static NoticeDao getDao() {
		return dao;
	}
	{
		String path = NoticeDao.class.getResource("/sql/notice/noticesql.properties").getPath();
		try(FileReader fr = new FileReader(path)) {
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public List<Notice> getNoticeList(Connection conn, int cPage, int numPerpage) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Notice> result = new ArrayList<>();
		int startData = (cPage -1)*numPerpage +1;
		int endData = cPage * numPerpage;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getNoticeList"));
			pstmt.setInt(1, startData);
			pstmt.setInt(2, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getNotice(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}


	public Notice selectNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Notice result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectNotice"));
			pstmt.setInt(1, noticeNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = getNotice(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
	
	private Notice getNotice(ResultSet rs) throws SQLException{
		return Notice.builder().noticeNo(rs.getInt("NOTICE_NO"))
				.noticeTitle(rs.getString("NOTICE_TITLE"))
				.noticeContent(rs.getString("NOTICE_CONTENT"))
				.noticeWriter(rs.getString("NOTICE_WRITER"))
				.noticeDate(rs.getDate("NOTICE_DATE"))
				.filePath(rs.getString("FILEPATH"))
				.build();
	}
	
}
