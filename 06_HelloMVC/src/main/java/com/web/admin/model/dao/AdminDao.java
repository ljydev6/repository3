package com.web.admin.model.dao;


import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.member.model.dto.Member;

public class AdminDao {
	private static AdminDao dao = new AdminDao();
	private AdminDao() {}
	private Properties sql = new Properties();
	{
		String path = AdminDao.class.getResource("/sql/admin/adminsql.properties").getPath();
		try(FileReader fr = new FileReader(path)) {
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static AdminDao getDao() {
		return dao;
	}
	
	public List<Member> searchMemberList(Connection conn, int cPage, int numPerPage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getMembers"));
			pstmt.setInt(1, startData);
			pstmt.setInt(2, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				members.add(getMember(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return members;
	}
	
	public Member getMember(ResultSet rs) throws SQLException{
		String hobby = rs.getString("hobby");
		String[] hobbyList = null;
		if(hobby !=null && hobby.length()>0) {
			hobbyList = hobby.split(",");
		}
		Member m = Member.builder()
								.userid(rs.getString("userid"))
								.username(rs.getString("username"))
								.gender(rs.getString("gender"))
								.age(rs.getInt("age"))
								.email(rs.getString("email"))
								.phone(rs.getString("phone"))
								.address(rs.getString("address"))
								.hobby(hobbyList)
								.enrolldate(rs.getDate("enrolldate"))
								.build();
		return m;
	}

	public int selectMemberCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getMemberCount"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}else {
				count = 0;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return count;
	}

	public List<Member> searchMember(Connection conn, String type, String keyword, int cPage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int startData = (cPage -1)*numPerPage +1;
		int endData = cPage * numPerPage;
		List<Member> result = new ArrayList<>();
		String query = this.sql.getProperty("selectMember");
		query = query.replace("#type", type);
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			pstmt.setInt(2, startData);
			pstmt.setInt(3, endData);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.add(getMember(rs));
			}
		}catch(SQLException e) {
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int searchMemberCount(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1;
		String query = sql.getProperty("selectMemberCount");
		query = query.replace("#type", type);
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

}
