package com.estsoft.mysite.web.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.GuestBookDao;
import com.estsoft.mysite.vo.GuestBookVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String passwd = request.getParameter("password");
		
		GuestBookVo vo = new GuestBookVo();
		
		vo.setNo(Long.parseLong(no));
		vo.setPasswd(passwd);
		new GuestBookDao(new MySQLWebDBConnection()).delete(vo);
		
		WebUtil.redirect(request, response, "/mysite/guestbook");
	}

}
