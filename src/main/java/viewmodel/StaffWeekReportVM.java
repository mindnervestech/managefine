package viewmodel;

import java.util.List;

public class StaffWeekReportVM {
	public Long staffId;
	public String name;
	public Integer week;
	public Integer year;
	public boolean flag;
	public List<WeekReportVM> weekReport;
	
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<WeekReportVM> getWeekReport() {
		return weekReport;
	}
	public void setWeekReport(List<WeekReportVM> weekReport) {
		this.weekReport = weekReport;
	}
}
