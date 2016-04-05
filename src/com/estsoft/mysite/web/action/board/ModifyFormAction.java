package com.estsoft.mysite.web.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardDao(new MySQLWebDBConnection()).getContent(no);
		request.setAttribute("content", vo);
		WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
	}

}
