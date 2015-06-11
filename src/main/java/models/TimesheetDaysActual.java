package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

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
	
	public String stage;
	 
	@OneToOne
	public User user;
	
	public Integer weekOfYear;
	
	public String taskCode;
	
	public String supplierId;
	
	public String customerId;
	
	public String notes;
	
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

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public static List<SqlRow> getMinutesTotalByDay(String date,Long userId) {
		String sql = "select sum(timesheet_days_actual.work_minutes) as minutes,stage from timesheet_days_actual where timesheet_days_actual.timesheet_date = :date and timesheet_days_actual.user_id = :id group by timesheet_days_actual.stage";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("date", date);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	public static List<SqlRow> getWeekReportOfTask(Integer weekOfYear,Long userId) {
		String sql = "select sum(timesheet_days_actual.work_minutes) as minutes,task_code from timesheet_days_actual where timesheet_days_actual.week_of_year = :week and timesheet_days_actual.user_id = :id group by timesheet_days_actual.task_code";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("week", weekOfYear);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
	public static List<SqlRow> getWeekReportOfStage(Integer weekOfYear,Long userId) {
		String sql = "select sum(timesheet_days_actual.work_minutes) as minutes,stage from timesheet_days_actual where timesheet_days_actual.week_of_year = :week and timesheet_days_actual.user_id = :id group by timesheet_days_actual.stage";
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("week", weekOfYear);
		sqlQuery.setParameter("id", userId);
		List<SqlRow> list = sqlQuery.findList();
		 return list;
	}
	
}
