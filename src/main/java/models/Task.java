package models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.custom.domain.Billable;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.time.controller.routes;

@Entity
public class Task extends Model {
	
	private static final String ATTACH_TO_PROJECT = "Attach To Project";
	
	public static final String ENTITY = "Task";

	@Id
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	@SearchColumnOnUI(rank=1,colName="Task Name")
	@SearchFilterOnUI(label="Task Name")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=1,label="Task Name")
	@Validation(required=true)
	public String taskName;

	@SearchColumnOnUI(rank=2,colName="Task Code")
	@SearchFilterOnUI(label="Task Code")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=2,label="Task Code")
	@Validation(required=true)
	public String taskCode;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=3,label="Start Date")
	@Validation(required=true)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date startDate;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label="End Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date endDate;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=5,label="Effort")
	public Integer effort;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=6,label="Is Billable")
	@Enumerated(EnumType.STRING)
	public Billable isBillable;
	
	/*@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=7,label=ATTACH_TO_PROJECT, autocomplete=true)
	@OneToOne(cascade = CascadeType.ALL )
	public Project project;*/
	
	@WizardCardUI(name="Assign Task",step=2)
	@UIFields(order=1,label=ATTACH_TO_PROJECT,multiselect=true)
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Project> projects;
	
	@Transient
	@SearchColumnOnUI(colName="Start Date", rank=3)
	public String startDateGrid;
	
	@Transient
	@SearchColumnOnUI(colName="End Date", rank=4)
	public String endDateGrid;
	
	public static Model.Finder<Long,Task> find = new Model.Finder<Long,Task>(Long.class, Task.class);
	
	
	public static Task findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	// Note from Dev: I want this code to be in model, dont move it to some other place in name of refactoring.
	// This field is more coupled with field level, may be it not Best to put the code here.
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	static {
		autoCompleteAction.put(ATTACH_TO_PROJECT, routes.Tasks.findProjectByName.url);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getEffort() {
		return effort;
	}
	public void setEffort(Integer effort) {
		this.effort = effort;
	}
	public Billable getIsBillable() {
		return isBillable;
	}
	public void setIsBillable(Billable isBillable) {
		this.isBillable = isBillable;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public String getStartDateGrid() {
		return startDateGrid;
	}
	public void setStartDateGrid(String startDateGrid) {
		this.startDateGrid = startDateGrid;
	}
	public String getEndDateGrid() {
		return endDateGrid;
	}
	public void setEndDateGrid(String endDateGrid) {
		this.endDateGrid = endDateGrid;
	}
	
	/*@Override
	public String toString() {
		return taskCode + "," + taskName + "("+project.projectName +")";
	}*/
}
