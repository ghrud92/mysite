package com.estsoft.mysite.web.action.main;

import com.estsoft.web.action.Action;
import com.estsoft.web.action.ActionFactory;

public class MainActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new IndexAction();
	}

}
