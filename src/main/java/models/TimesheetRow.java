package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class TimesheetRow extends Model{

    @Id
    public Long id;
    
    @ManyToOne
    public Timesheet timesheet;
    
    public String projectCode;
    
    public String taskCode;
    
    public Integer totalHrs;
    
    public Integer sun;
    
    public Integer mon;
    
    public Integer tue;
    
    public Integer wed;
    
    public Integer thu;
    
    public Integer fri;
    
    public Integer sat;
    
    public Boolean overTime;
    
    public static Model.Finder<Long, TimesheetRow> find = new Model.Finder<Long,TimesheetRow>(Long.class, TimesheetRow.class);

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

	public Integer getTotalHrs() {
		return totalHrs;
	}

	public void setTotalHrs(Integer totalHrs) {
		this.totalHrs = totalHrs;
	}

	public Integer getSun() {
		return sun;
	}

	public void setSun(Integer sun) {
		this.sun = sun;
	}

	public Integer getMon() {
		return mon;
	}

	public void setMon(Integer mon) {
		this.mon = mon;
	}

	public Integer getTue() {
		return tue;
	}

	public void setTue(Integer tue) {
		this.tue = tue;
	}

	public Integer getWed() {
		return wed;
	}

	public void setWed(Integer wed) {
		this.wed = wed;
	}

	public Integer getThu() {
		return thu;
	}

	public void setThu(Integer thu) {
		this.thu = thu;
	}

	public Integer getFri() {
		return fri;
	}

	public void setFri(Integer fri) {
		this.fri = fri;
	}

	public Integer getSat() {
		return sat;
	}

	public void setSat(Integer sat) {
		this.sat = sat;
	}

	public Boolean getOverTime() {
		return overTime;
	}

	public void setOverTime(Boolean overTime) {
		this.overTime = overTime;
	}
}