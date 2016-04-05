package com.estsoft.mysite.web.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo boardVo = new BoardVo();
		BoardDao boardDao = new BoardDao(new MySQLWebDBConnection());
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		String title = request.getParameter("title");
		Long userNo = userVo.getNo();
		String content = request.getParameter("content");
		
		if(request.getParameter("group_no") != ""){
			boardVo.setGroupNo(Integer.parseInt(request.getParameter("group_no")));
			boardVo.setOrderNo(Integer.parseInt(request.getParameter("order_no")));
			boardVo.setDepth(Integer.parseInt(request.getParameter("depth")));
		}
		
		boardVo.setTitle(title);
		boardVo.setUserNo(userNo);
		boardVo.setContent(content);
		
		boardDao.insert(boardVo);
		
		WebUtil.redirect(request, response, "/mysite/board");
	}

}
