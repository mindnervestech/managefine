package com.custom.helpers;

import java.util.Map;

import models.Project;
import play.data.Form;
import play.db.ebean.Model;

import com.mnt.core.helper.SaveModel;

public class ProjectSave extends SaveModel<Project> {

	public ProjectSave() {
		ctx = Project.class;
	}
		
	private Map<String,Object> extras = null;
	public ProjectSave(Map<String,Object> extras) {
		ctx = Project.class;
		this.extras = extras;
	}
		
	@Override
	protected void preSave(Form<? extends Model> form) throws Exception {
		if(extras !=null){
			for(String key: extras.keySet()){
				form.get().getClass().getField(key).set(form.get(), extras.get(key));
			}
		}
		String _idAsStr = form.data().get("users_ids");
		String projectMgrId = form.data().get("projectManager_id");
		if(projectMgrId!=null && projectMgrId.length()>0){
			_idAsStr = _idAsStr +","+ form.data().get("projectManager_id");
			form.data().put("users_ids", _idAsStr);
		}
		super.preSave(form);
	}
}
