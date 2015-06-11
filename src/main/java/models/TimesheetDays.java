package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

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

	public String supplierId;
	
	public String customerId;
	
	public String notes;
	
	@ManyToOne
	public TimesheetRow timesheetRow;
	
	public String stage;
	 
	@OneToOne
	public User user;
	
	public Integer weekOfYear;
	
	public String taskCode;
	
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
	
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
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
	
	public static List<SqlRow> getMinutesTotalByDay(String date,Long userId) {
		String sql = "select sum(timesheet_days.work_minutes) as minutes,stage from timesheet_days where timesheet_days.timesheet_date = :date and timesheet_days.user_id = :id group by timesheet_days.stage";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("date", date);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	public static List<SqlRow> getWeekReportOfTask(Integer weekOfYear,Long userId) {
		String sql = "select sum(timesheet_days.work_minutes) as minutes,task_code from timesheet_days where timesheet_days.week_of_year = :week and timesheet_days.user_id = :id group by timesheet_days.task_code";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("week", weekOfYear);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	public static List<SqlRow> getWeekReportOfStage(Integer weekOfYear,Long userId) {
		String sql = "select sum(timesheet_days.work_minutes) as minutes,stage from timesheet_days where timesheet_days.week_of_year = :week and timesheet_days.user_id = :id group by timesheet_days.stage";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("week", weekOfYear);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
}
