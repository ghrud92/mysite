package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.vo.GuestBookVo;

public class GuestBookDao {
	private MySQLWebDBConnection dbConnection;
	
	public GuestBookDao(MySQLWebDBConnection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	public void insert(GuestBookVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "insert into guestbook values(null, ?, now(), ?, password(?))";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getMessage());
			pstmt.setString(3, vo.getPasswd());
			
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public List<GuestBookVo> getList(){
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select no, name, date_format(reg_date,'%Y-%m-%d %h:%i:%s'), message from guestbook order by reg_date desc";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setReg_date(reg_date);
				vo.setMessage(message);
				list.add(vo);
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
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void delete(GuestBookVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "delete from guestbook where no = ? and passwd = password(?)";
			pstmt = conn.prepareStatement(sql);
					
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPasswd());
			
			pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
