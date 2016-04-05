package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.UserVo;

public class UserDao {
	private DBConnection dbConnection;
	
	public UserDao(DBConnection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	public void insert(UserVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "insert into user values(null,?,?,password(?), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
	}
	
	public UserVo get(UserVo vo){
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select no, name, email from user where email = ? and passwd = password(?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return userVo;
	}
	
	//보안 = 인증 + 권한
	//인증(Auth)
//	public UserVo get(String email, String password){
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		try{
//			
//		}catch(SQLException sqle){
//			sqle.printStackTrace();
//		}finally{
//			try{
//				if(pstmt!=null)
//					pstmt.close();
//				if(conn!=null)
//					conn.close();
//			}catch(SQLException sqle){
//				sqle.printStackTrace();
//			}
//		}
//	}
//	
	//gender 포함
	public UserVo get(Long no){
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select no, name, email, gender from user where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				Long userNo = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				userVo = new UserVo();
				userVo.setNo(userNo);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return userVo;
	}
	
	//비밀번호는 변경하지 않음.
	public void update(UserVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "update user set name = ?, email = ?, gender = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getGender());
			pstmt.setLong(4, vo.getNo());
			
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
	}
	
	//비밀번호도 변경
	public void update(UserVo vo, boolean check){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "update user set name = ?, email = ?, passwd = password(?), gender = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			pstmt.setLong(5, vo.getNo());
			
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
	}
}
