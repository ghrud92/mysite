package com.estsoft.mysite.web.action.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.web.WebUtil;
import com.estsoft.web.action.Action;

public class DefaultAction implements Action {
	
	private static final int CountPage = 5;	// 페이지 당 게시글 수
	private static final int CountList = 5; // 페이지 리스트의 수

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page;
		if(request.getParameter("page") == null || request.getParameter("page") == ""){
			System.out.println("page = null");
			page = 1;
		}else{
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		BoardDao dao = new BoardDao(new MySQLWebDBConnection());
		List<BoardVo> list = dao.getList(request.getParameter("kwd"), page);
		request.setAttribute("list", list);
		
		int left = 1;
		int right = 0;
		int startPage, lastPage;
		int count = dao.getCount(request.getParameter("kwd"));
		int maxPage = count/CountPage;
		if(count % CountPage != 0)
			maxPage++;
		if(page < 1 || page > maxPage)
			page = 1;
		int maxPageGroup = maxPage/CountList;
		if(maxPage % CountList != 0)
			maxPageGroup++;
		int selectedPageGroup = page/CountList;
		if(page % CountList != 0){
			selectedPageGroup++;
		}
		if(selectedPageGroup == 1)
			left = 0;
		if(selectedPageGroup < maxPageGroup)
			right = 1;
		startPage = (selectedPageGroup - 1) * CountList + 1;
		lastPage = (selectedPageGroup) * CountList;
		if(lastPage > maxPage)
			lastPage = maxPage;
		
		System.out.println("selectedPageGroup: "+selectedPageGroup);
		System.out.println("left: "+left);
		System.out.println("right: "+right);
		System.out.println("startPage: "+startPage);
		System.out.println("lastPage: "+lastPage);
		System.out.println("page: "+page);
		System.out.println("total"+count);
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("left", left);
		map.put("right", right);
		map.put("startPage", startPage);
		map.put("lastPage", lastPage);
		map.put("page", page);
		map.put("total", count);
		request.setAttribute("pageMap", map);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
