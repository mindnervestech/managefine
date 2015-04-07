package viewmodel;

import java.util.List;

public class LeaveMonth {
	public Integer monthIndex;
	public String monthName;
	public Integer year;
	public List<LeaveDay> monthDays;
	public Integer getMonthIndex() {
		return monthIndex;
	}
	public void setMonthIndex(Integer monthIndex) {
		this.monthIndex = monthIndex;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<LeaveDay> getMonthDays() {
		return monthDays;
	}
	public void setMonthDays(List<LeaveDay> monthDays) {
		this.monthDays = monthDays;
	}
	
}
