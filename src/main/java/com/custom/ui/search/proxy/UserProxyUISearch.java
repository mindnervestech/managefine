package com.custom.ui.search.proxy;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.custom.domain.Status;
import com.mnt.core.domain.FlexiAttributes;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;

public class UserProxyUISearch extends Model {
	
	@Id
	public Long id;
	
	@SearchFilterOnUI(label="Status")
	@UIFields(order=1,label="Status")
	@Enumerated(EnumType.STRING)
	@WizardCardUI(name="#",step=1)
	public Status status;
	
	
	@SearchColumnOnUI(rank=1,colName="First Name")
	public String	firstName;
	
	@SearchColumnOnUI(rank=2,colName="Last Name")
	public String	lastName;
	
	@SearchColumnOnUI(rank=3,colName="E-Mail ID")
	public String	email;
	
	@SearchColumnOnUI(rank=4,colName="Designation")
	public String	designation;
	
	
	public String	companyName;
	
	public static Model.Finder<Long, UserProxyUISearch> find = new Model.Finder<Long,UserProxyUISearch>(Long.class, UserProxyUISearch.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
