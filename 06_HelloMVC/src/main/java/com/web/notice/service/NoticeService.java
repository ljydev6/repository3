package com.web.notice.service;

import static com.web.common.JDBCTemplate.*;


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

	public int writeNotice(Notice notice) {
		Connection conn = getConnection();
		int result = NoticeDao.getDao().writeNotice(conn, notice);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateNotice(Notice notice) {
		Connection conn = getConnection();
		int result = NoticeDao.getDao().updateNotice(conn, notice);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteNotice(int noticeNo) {
		Connection conn = getConnection();
		int result = NoticeDao.getDao().deleteNotice(conn,noticeNo);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	
	
}
