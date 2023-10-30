package com.web.member.service;

import static com.web.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.web.admin.model.dao.AdminDao;
import com.web.member.model.dao.MemberDao;
import com.web.member.model.dto.Member;


public class MemberService {
	private static MemberService service = new MemberService();
	private MemberService() {}
	
	public static MemberService getService() {
		return service;
	}

	public Member login(String userid, String password) {
		Connection conn = getConnection();
		Member result = MemberDao.getDao().doLogin(conn, userid, password);
		
		close(conn);
		return result;
	}

	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = MemberDao.getDao().insertMember(conn, m);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member selectMemberById(String userId) {
		Connection conn = getConnection();
		Member m = MemberDao.getDao().searchMemberById(conn, userId);
		close(conn);
		return m;
	}

	public Member updateMember(Member m) {
		Connection conn = getConnection();
		
		int result = MemberDao.getDao().updateMember(conn,m);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		if(result>0) {
			return m;
		}else {
			return null;
		}
	}
	public int updatePassword(String userId, String passwordOld, String passwordNew) {
		
		Connection conn = getConnection();
		Member m = MemberDao.getDao().doLogin(conn, userId, passwordOld);
		int result = -1;
		if(m!=null) {
			result = MemberDao.getDao().updatePassword(conn,userId,passwordNew);
		}
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	public Member getMember(Map<String,String[]> param) {
		return Member.builder().userid(param.get("userId")[0])
				.password(param.get("password")[0])
				.username(param.get("userName")[0])
				.age(Integer.parseInt(param.get("age")[0]))
				.email(param.get("email")[0])
				.phone(param.get("phone")[0])
				.address(param.get("address")[0])
				.gender(param.get("gender")[0])
				.hobby(param.get("hobby"))
				.build();
	}


}
