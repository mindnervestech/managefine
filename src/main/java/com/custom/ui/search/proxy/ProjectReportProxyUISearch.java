package com.custom.ui.search.proxy;

import javax.persistence.Id;

import play.data.format.Formats.DateTime;
import play.db.ebean.Model;

import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;

public class ProjectReportProxyUISearch extends Model {
	@Id
	public Long id;
	
	@SearchColumnOnUI(rank=1,colName="Project Name")
	@SearchFilterOnUI(label="Project Name")
	@UIFields(order=1,label="Project Name")
	@WizardCardUI(name="#",step=1)
	public String projectName;
	
	@SearchColumnOnUI(rank=2,colName="Start Date")
	@UIFields(order=1,label="Start Date")
	@WizardCardUI(name="#",step=1)
	@DateTime(pattern = "dd-MM-yyyy")
	public String projectStart;

	@SearchColumnOnUI(rank=3,colName="End Date")
	@UIFields(order=1,label="End Date")
	@WizardCardUI(name="#",step=1)
	@DateTime(pattern = "dd-MM-yyyy")
	public String projectEnd;
	

	@SearchColumnOnUI(rank=4,colName="Estimation")
	@UIFields(order=1,label="Estimation")
	@WizardCardUI(name="#",step=1)
	public Double estimatedMoney;
	
	@SearchColumnOnUI(rank=5,colName="Hrs Spend")
	@UIFields(order=1,label="Hrs Spend")
	@WizardCardUI(name="#",step=1)
	public Integer hrsSpent;
	
	@SearchColumnOnUI(rank=6,colName="Money Spend")
	@UIFields(order=1,label="Money Spend")
	@WizardCardUI(name="#",step=1)
	public Double moneySpent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(String projectStart) {
		this.projectStart = projectStart;
	}

	public String getProjectEnd() {
		return projectEnd;
	}

	public void setProjectEnd(String projectEnd) {
		this.projectEnd = projectEnd;
	}

	public Double getEstimatedMoney() {
		return estimatedMoney;
	}

	public void setEstimatedMoney(Double estimatedMoney) {
		this.estimatedMoney = estimatedMoney;
	}

	public Integer getHrsSpent() {
		return hrsSpent;
	}

	public void setHrsSpent(Integer hrsSpent) {
		this.hrsSpent = hrsSpent;
	}

	public Double getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(Double moneySpent) {
		this.moneySpent = moneySpent;
	}
	
}
