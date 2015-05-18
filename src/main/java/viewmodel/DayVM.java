package viewmodel;

public class DayVM {
	public String day;
	public boolean isHoliday;
	public Integer appoinmentCount;
	public Boolean assigned;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getAppoinmentCount() {
		return appoinmentCount;
	}
	public void setAppoinmentCount(Integer appoinmentCount) {
		this.appoinmentCount = appoinmentCount;
	}
	public Boolean getAssigned() {
		return assigned;
	}
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	public boolean isHoliday() {
		return isHoliday;
	}
	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	
}
