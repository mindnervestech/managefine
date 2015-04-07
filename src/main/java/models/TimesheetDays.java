package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import play.db.ebean.Model;

@Entity
public class TimesheetDays extends Model {

	@Id
	public Long id;
	
	public String day;
	
	public Date timesheetDate;
	
	public String timeFrom;
	
	public String timeTo;
	
	public Integer workMinutes;

	@ManyToOne
	public TimesheetRow timesheetRow;
	
	public static Model.Finder<Long, TimesheetDays> find = new Model.Finder<Long,TimesheetDays>(Long.class, TimesheetDays.class);
	
	

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

	public TimesheetRow getTimesheetRow() {
		return timesheetRow;
	}

	public void setTimesheetRow(TimesheetRow timesheetRow) {
		this.timesheetRow = timesheetRow;
	}
	
	public static List<TimesheetDays> getByTimesheetRow(TimesheetRow timesheetRow) {
		return find.where().eq("timesheetRow", timesheetRow).findList();
	}
	
	public static TimesheetDays findById(Long id) {
		return find.byId(id);
	}
	
	public static TimesheetDays findByDateAndTimesheet(Date date,TimesheetRow timesheetRow) {
		return find.where().eq("timesheetDate", date).eq("timesheetRow", timesheetRow).findUnique();
	}
	
	public static TimesheetDays findByDayAndTimesheet(String day,TimesheetRow timesheetRow) {
		return find.where().eq("day", day).eq("timesheetRow", timesheetRow).findUnique();
	}
	
}
