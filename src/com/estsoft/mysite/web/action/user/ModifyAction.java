package com.estsoft.mysite.web.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserVo vo = new UserVo();
		vo.setNo(Long.parseLong(no));
		vo.setName(name);
		vo.setEmail(email);
		vo.setPassword(password);
		vo.setGender(gender);
		
		System.out.println(vo);
		
		if("".equals(password) || password == null){
			new UserDao(new MySQLWebDBConnection()).update(vo);
		}else{
			new UserDao(new MySQLWebDBConnection()).update(vo, true);
		}

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		authUser.setName(name);
		
		WebUtil.redirect(request, response, "/mysite/main");
	}

}
