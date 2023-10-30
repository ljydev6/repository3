package com.web.member.model.dao;

import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.web.member.model.dto.Member;


public class MemberDao {
	private static MemberDao dao = new MemberDao();
	private MemberDao() {}
	public static MemberDao getDao() {
		return dao;
	}
	
	private Properties sql = new Properties();
	{
		String path = MemberDao.class.getResource("/sql.member.properties").getPath();
		try (FileReader fr = new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Member doLogin(Connection conn,String userid, String password) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member result = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("login"));
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = Member.builder().userid(rs.getString("USERID"))
										 .username(rs.getString("USERNAME"))
										 .gender(rs.getString("GENDER"))
										 .age(rs.getInt("AGE"))
										 .email(rs.getString("EMAIL"))
										 .phone(rs.getString("PHONE"))
										 .address(rs.getString("ADDRESS"))
										 .hobby(rs.getString("HOBBY").split(","))
										 .enrolldate(rs.getDate("ENROLLDATE"))
										 .build();
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public int insertMember(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertMember"));
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getUsername());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7,member.getPhone());
			pstmt.setString(8, member.getAddress());
			String[] hobby = member.getHobby();
			String hobbyString = "";
			if(hobby != null && hobby.length>0) {
				hobbyString = String.join(",", hobby);
			}
			pstmt.setString(9, hobbyString);
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	public Member searchMemberById(Connection conn, String userId) {
			PreparedStatement pstmt = null;
			Member m = null;
			ResultSet rs = null;
			
			try {
				pstmt = conn.prepareStatement(sql.getProperty("selectMemberById"));
				pstmt.setString(1, userId);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					m = getMember(rs);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				close(rs);
				close(pstmt);
			}
			
		return m;
	}
	
	public int updateMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = -1;
		
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateMember"));
			pstmt.setString(1, m.getUsername());
			pstmt.setInt(2, m.getAge());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getAddress());
			pstmt.setString(6, m.getGender());
			String[] hobby = m.getHobby();
			String h = "";
			if(hobby!=null && hobby.length>0) {
				h = String.join(",", hobby);
			}
			pstmt.setString(7, h);
			pstmt.setString(8, m.getUserid());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	public int updatePassword(Connection conn,String userId, String passwordNew) {
		
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updatePassword"));
			pstmt.setString(1, passwordNew);
			pstmt.setString(2, userId);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	private Member getMember(ResultSet rs) throws SQLException{
		return Member.builder()
				.userid(rs.getString("userid"))
				.username(rs.getString("username"))
				.gender(rs.getString("gender"))
				.age(rs.getInt("age"))
				.email(rs.getString("email"))
				.phone(rs.getString("phone"))
				.address(rs.getString("address"))
				.hobby(rs.getString("hobby").split(","))
				.enrolldate(rs.getDate("enrolldate"))
				.build();
	}
}
