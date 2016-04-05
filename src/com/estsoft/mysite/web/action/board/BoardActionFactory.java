package com.estsoft.mysite.web.action.board;

import com.estsoft.web.action.Action;
import com.estsoft.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)){
			action = new WriteFormAction();
		}else if("write".equals(actionName)){
			action = new WriteAction();
		}else if("view".equals(actionName)){
			action = new ViewAction();
		}else if("modifyform".equals(actionName)){
			action = new ModifyFormAction();
		}else if("modify".equals(actionName)){
			action = new ModifyAction();
		}else if("delete".equals(actionName)){
			action = new DeleteAction();
		}else{
			action = new DefaultAction();
		}
		return action;
	}

}
