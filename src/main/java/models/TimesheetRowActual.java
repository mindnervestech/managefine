package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class TimesheetRowActual extends Model {

	 	@Id
	    public Long id;
	    
	    @ManyToOne
	    public TimesheetActual timesheetActual;
	    
	    public String projectCode;
	    
	    public String taskCode;
	    
	    public boolean overTime;
	    
	    @OneToMany
	    public List<TimesheetDaysActual> timesheetDaysActual;
	     
	    
	    public static Model.Finder<Long, TimesheetRowActual> find = new Model.Finder<Long,TimesheetRowActual>(Long.class, TimesheetRowActual.class);


		public List<TimesheetDaysActual> getTimesheetDaysActual() {
			return timesheetDaysActual;
		}
		public void setTimesheetDaysActual(List<TimesheetDaysActual> timesheetDaysActual) {
			this.timesheetDaysActual = timesheetDaysActual;
		}
		
		public static TimesheetRowActual findById(Long id) {
			return find.byId(id);
		}
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public TimesheetActual getTimesheetActual() {
			return timesheetActual;
		}
		
		public void setTimesheetActual(TimesheetActual timesheetActual) {
			this.timesheetActual = timesheetActual;
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

		public static List<TimesheetRowActual> getByTimesheet(TimesheetActual timesheetActual) {
			return find.where().eq("timesheetActual", timesheetActual).findList();
		}
		
	
}
