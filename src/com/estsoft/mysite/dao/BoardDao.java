package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.vo.BoardVo;

public class BoardDao {
	private MySQLWebDBConnection dbConnection;
	
	public BoardDao(MySQLWebDBConnection dbConnection){
		this.dbConnection = dbConnection;
	}
	
	public List<BoardVo> getList(String kwd, int page){
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = dbConnection.getConnection();
			String sql = null;
			if(kwd==null){
				sql = "select b.no, b.title, u.no as user_no, u.name, b.hit,"
						+ " date_format(b.reg_date,'%Y-%m-%d %h:%i:%s'), b.depth"
						+ " from board b, user u where b.user_no = u.no"
						+ " order by b.group_no desc, b.order_no asc"
						+ " limit ?, 5";
				pstmt = conn.prepareStatement(sql);
			}else{
				sql = "select distinct b.no, b.title, u.no as user_no, u.name, b.hit,"
						+ " date_format(b.reg_date,'%Y-%m-%d %h:%i:%s'), b.depth, b.group_no, b.order_no"
						+ " from board b, user u where b.user_no = u.no and (title like '%"+kwd+"%'"
						+ " or content like '%"+kwd+"%')"
						+ " order by b.group_no desc, b.order_no asc"
						+ " limit ?, 5";
				pstmt = conn.prepareStatement(sql);
			}
			
			pstmt.setInt(1, (page-1)*5);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				Long boardNo = rs.getLong(1);
				String title = rs.getString(2);
				Long userNo = rs.getLong(3);
				String userName = rs.getString(4);
				int hit = rs.getInt(5);
				String reg_date = rs.getString(6);
				int depth = rs.getInt(7);
				
				BoardVo vo = new BoardVo();
				vo.setNo(boardNo);
				vo.setTitle(title);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDepth(depth);
				
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
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return list;
	}
	
	public void insert(BoardVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = null;
			System.out.println("insert - group_no: " + vo.getGroupNo());
			if(vo.getGroupNo() == 0){
				sql = "insert into board values(null, ?, ?, now(), 0, ?, (select * from (select ifnull(max(group_no),0) + 1 from board) as a), 1, 0)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUserNo());
			}else{
				sql = "insert into board values(null, ?, ?, now(), 0, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUserNo());
				pstmt.setInt(4, vo.getGroupNo());
				increaseOrder(vo.getGroupNo(), vo.getOrderNo());
				pstmt.setInt(5, vo.getOrderNo()+1);
				
				pstmt.setInt(6, vo.getDepth()+1);
			}

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
	
	public void increaseOrder(int groupNo, int orderNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select no, order_no from board where group_no = ? and order_no > ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				stmt = conn.createStatement();
				sql = "update board set order_no = "+ (rs.getInt(2)+1) + " where no = "+rs.getLong(1);
				stmt.executeUpdate(sql);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
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
	}
	
	public BoardVo getContent(Long no){
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select no, title, content, reg_date, hit, user_no, group_no, order_no, depth from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContent(rs.getString(3));
				vo.setReg_date(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setUserNo(rs.getLong(6));
				vo.setGroupNo(rs.getInt(7));
				vo.setOrderNo(rs.getInt(8));
				vo.setDepth(rs.getInt(9));
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
		return vo;
	}
	
	public void update(BoardVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "update board set title = ?, content = ? where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
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
	
	public void delete(Long no){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,no);
			
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
	
	public void increaseHit(Long no){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement stmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "select hit from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,no);

			rs = pstmt.executeQuery();
			if(rs.next()){
				int hit = rs.getInt(1);
				hit++;
				sql = "update board set hit = " + hit + " where no = " + no;
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
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
	}
	
	public int getCount(String kwd){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = dbConnection.getConnection();
			
			String sql = null;
			if(kwd == null || "".equals(kwd)){
				sql = "select count(*) from board";
			}else{
				sql = "select count(*) from board"
					+ " where title like '%"+kwd+"%' or content like'%"+kwd+"%'";
			}
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
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
		return count;
	}
}
