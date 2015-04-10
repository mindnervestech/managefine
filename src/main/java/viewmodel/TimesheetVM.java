package viewmodel;

import java.util.List;

public class TimesheetVM {

	public Long userId;
	public Long id;
	public String status;
	public Integer weekOfYear;
	public Integer year;
	public String firstName;
	public String lastName;
	
	public List<TimesheetRowVM> timesheetRows;
	public List<TimesheetRowVM> timesheetRowsList;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getWeekOfYear() {
		return weekOfYear;
	}
	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<TimesheetRowVM> getTimesheetRows() {
		return timesheetRows;
	}
	public void setTimesheetRows(List<TimesheetRowVM> timesheetRows) {
		this.timesheetRows = timesheetRows;
	}
	public List<TimesheetRowVM> getTimesheetRowsList() {
		return timesheetRowsList;
	}
	public void setTimesheetRowsList(List<TimesheetRowVM> timesheetRowsList) {
		this.timesheetRowsList = timesheetRowsList;
	}
	
	
}
