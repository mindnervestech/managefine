package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import play.db.ebean.Model;

@Entity
public class TimesheetRow extends Model{

    @Id
    public Long id;
    
    @ManyToOne
    public Timesheet timesheet;
    
    public String projectCode;
    
    public String taskCode;
    
    public boolean overTime;
    
    @OneToMany
    public List<TimesheetDays> timesheetDays;
     
    
    public static Model.Finder<Long, TimesheetRow> find = new Model.Finder<Long,TimesheetRow>(Long.class, TimesheetRow.class);


	public List<TimesheetDays> getTimesheetDays() {
		return timesheetDays;
	}

	public void setTimesheetDays(List<TimesheetDays> timesheetDays) {
		this.timesheetDays = timesheetDays;
	}

	public static TimesheetRow findById(Long id) {
		return find.byId(id);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public boolean isOverTime() {
		return overTime;
	}

	public void setOverTime(boolean overTime) {
		this.overTime = overTime;
	}

	public static List<TimesheetRow> getByTimesheet(Timesheet timesheet) {
		return find.where().eq("timesheet", timesheet).findList();
	}
	
	
}