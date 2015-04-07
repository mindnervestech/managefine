package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class TimesheetDaysActual extends Model {

	@Id
	public Long id;
	
	public String day;
	
	public Date timesheetDate;
	
	public String timeFrom;
	
	public String timeTo;
	
	public Integer workMinutes;

	@ManyToOne
	public TimesheetRowActual timesheetRowActual;
	
	public static Model.Finder<Long, TimesheetDaysActual> find = new Model.Finder<Long,TimesheetDaysActual>(Long.class, TimesheetDaysActual.class);
	
	

	public TimesheetRowActual getTimesheetRowActual() {
		return timesheetRowActual;
	}

	public void setTimesheetRowActual(TimesheetRowActual timesheetRowActual) {
		this.timesheetRowActual = timesheetRowActual;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}


	public Date getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(Date timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public Integer getWorkMinutes() {
		return workMinutes;
	}

	public void setWorkMinutes(Integer workMinutes) {
		this.workMinutes = workMinutes;
	}

	
	public static List<TimesheetDaysActual> getByTimesheetRow(TimesheetRowActual timesheetRowActual) {
		return find.where().eq("timesheetRowActual", timesheetRowActual).findList();
	}
	
	public static TimesheetDaysActual findById(Long id) {
		return find.byId(id);
	}
	
	public static TimesheetDaysActual findByDateAndTimesheet(Date date,TimesheetRowActual timesheetRowActual) {
		return find.where().eq("timesheetDate", date).eq("timesheetRowActual", timesheetRowActual).findUnique();
	}
	
	public static TimesheetDaysActual findByDayAndTimesheet(String day,TimesheetRowActual timesheetRowActual) {
		return find.where().eq("day", day).eq("timesheetRowActual", timesheetRowActual).findUnique();
	}
	
}
