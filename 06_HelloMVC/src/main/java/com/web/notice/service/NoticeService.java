package com.web.notice.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.web.notice.model.dao.NoticeDao;
import com.web.notice.model.dto.Notice;

public class NoticeService {
	private static NoticeService service = new NoticeService();
	private NoticeService() {}
	
	public static NoticeService getService() {
		return service;
	}

	public List<Notice> getNoticeList(int cPage, int numPerpage) {
		
		Connection conn = getConnection();
		
		List<Notice> result = NoticeDao.getDao().getNoticeList(conn, cPage, numPerpage);
		
		close(conn);
		
		return result;
	}

	public Notice selectNotice(int noticeNo) {
		
		Connection conn = getConnection();
		
		Notice result = NoticeDao.getDao().selectNotice(conn, noticeNo);
		
		close(conn);
		
		return result;
	}
	
	
}
