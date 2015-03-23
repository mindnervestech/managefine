package com.custom.helpers;

import java.util.Map;

import models.User;
import play.data.Form;
import play.db.ebean.Model;

import com.mnt.core.helper.SaveModel;

public class UserSave extends SaveModel<User> {

	public UserSave() {
		ctx = User.class;
	}
	
	private Map<String,Object> extras=null;
	public UserSave(Map<String,Object> extras) {
		ctx = User.class;
		this.extras = extras;
	}

	@Override
	protected void preSave(Form<? extends Model> form) throws Exception {
		if(extras !=null){
			for(String key: extras.keySet()){
				form.get().getClass().getField(key).set(form.get(), extras.get(key));
			}
		}
		super.preSave(form);
	}
}
