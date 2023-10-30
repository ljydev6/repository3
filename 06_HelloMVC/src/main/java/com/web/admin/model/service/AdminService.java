package com.web.admin.model.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.web.admin.model.dao.AdminDao;
import com.web.member.model.dto.Member;

public class AdminService {
	
	private static AdminService service = new AdminService();
	
	private AdminService() {}
	
	public static AdminService getService() {
		return service;
	}
	
	public List<Member> getMembers(int cPage, int numPerPage){
		
		Connection conn = getConnection();
		List<Member> members = AdminDao.getDao().searchMemberList(conn, cPage, numPerPage);
		close(conn);
		return members;
	}

	public int selectMemberCount() {
		Connection conn = getConnection();
		int count = AdminDao.getDao().selectMemberCount(conn);
		close(conn);
		return count;
	}

	public List<Member> searchMember(String type, String keyword, int cPage, int numPerPage) {
		Connection conn = getConnection();
		List<Member> members = AdminDao.getDao().searchMember(conn, type, keyword, cPage, numPerPage);
		close(conn);
		return members;
	}

	public int selectMemberCount(String type, String keyword) {
		Connection conn = getConnection();
		int count = AdminDao.getDao().searchMemberCount(conn, type, keyword);
		close(conn);
		return count;
	}
}
