package viewmodel;

import java.util.List;

public class MonthVM {

	public String monthName;
	public Integer monthIndex;
	public Integer year;
	List<DayVM> days;
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public Integer getMonthIndex() {
		return monthIndex;
	}
	public void setMonthIndex(Integer monthIndex) {
		this.monthIndex = monthIndex;
	}
	public List<DayVM> getDays() {
		return days;
	}
	public void setDays(List<DayVM> days) {
		this.days = days;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
}