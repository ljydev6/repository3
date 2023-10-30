package com.web.notice.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public List<Notice> getNoticeList(Connection conn) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		return null;
	}
	
	
}
