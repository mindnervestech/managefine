package com.custom.helpers;

import java.util.Map;

import models.Client;
import play.data.Form;
import play.db.ebean.Model;

import com.mnt.core.helper.SaveModel;

public class ClientSave extends SaveModel<Client> {

	public ClientSave() {
		ctx = Client.class;
	}
	
	private Map<String,Object> extras=null;
	public ClientSave(Map<String,Object> extras) {
		ctx = Client.class;
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