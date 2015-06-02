package viewmodel;

import java.util.List;

public class TimesheetRowVM {

	public Long rowId;
	public Long projectCode;
	public Long taskCode;
	public Integer totalmins;
	public String monFrom;
	public String monTo;
	public String tueFrom;
	public String tueTo;
	public String wedFrom;
	public String wedTo;
	public String thuFrom;
	public String thuTo;
	public String friFrom;
	public String friTo;
	public String satFrom;
	public String satTo;
	public String sunFrom;
	public String sunTo;
	public Long mondayId;
	public Long tuesdayId;
	public Long wednesdayId;
	public Long thursdayId;
	public Long fridayId;
	public Long saturdayId;
	public Long sundayId;
	public boolean isOverTime;
	public String projectName;
	public String taskName;
	public Integer totalHrs;
	public String tCode;
	public String monFromTo;
	public String tueFromTo;
	public String wedFromTo;
	public String thuFromTo;
	public String friFromTo;
	public String satFromTo;
	public String sunFromTo;
	public String monSupplier;
	public String monCustomer;
	public String monNotes;
	public String tueSupplier;
	public String tueCustomer;
	public String tueNotes;
	public String wedSupplier;
	public String wedCustomer;
	public String wedNotes;
	public String thuSupplier;
	public String thuCustomer;
	public String thuNotes;
	public String friSupplier;
	public String friCustomer;
	public String friNotes;
	public String satSupplier;
	public String satCustomer;
	public String satNotes;
	public String sunSupplier;
	public String sunCustomer;
	public String sunNotes;
	public List<TimesheetDaysVM> timesheetRowDays;
	
	
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public Long getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(Long projectCode) {
		this.projectCode = projectCode;
	}
	public Long getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(Long taskCode) {
		this.taskCode = taskCode;
	}
	public Integer getTotalmins() {
		return totalmins;
	}
	public void setTotalmins(Integer totalmins) {
		this.totalmins = totalmins;
	}
	public String getMonFrom() {
		return monFrom;
	}
	public void setMonFrom(String monFrom) {
		this.monFrom = monFrom;
	}
	public String getMonTo() {
		return monTo;
	}
	public void setMonTo(String monTo) {
		this.monTo = monTo;
	}
	public String getTueFrom() {
		return tueFrom;
	}
	public void setTueFrom(String tueFrom) {
		this.tueFrom = tueFrom;
	}
	public String getTueTo() {
		return tueTo;
	}
	public void setTueTo(String tueTo) {
		this.tueTo = tueTo;
	}
	public String getWedFrom() {
		return wedFrom;
	}
	public void setWedFrom(String wedFrom) {
		this.wedFrom = wedFrom;
	}
	public String getWedTo() {
		return wedTo;
	}
	public void setWedTo(String wedTo) {
		this.wedTo = wedTo;
	}
	public String getThuFrom() {
		return thuFrom;
	}
	public void setThuFrom(String thuFrom) {
		this.thuFrom = thuFrom;
	}
	public String getThuTo() {
		return thuTo;
	}
	public void setThuTo(String thuTo) {
		this.thuTo = thuTo;
	}
	public String getFriFrom() {
		return friFrom;
	}
	public void setFriFrom(String friFrom) {
		this.friFrom = friFrom;
	}
	public String getFriTo() {
		return friTo;
	}
	public void setFriTo(String friTo) {
		this.friTo = friTo;
	}
	public String getSatFrom() {
		return satFrom;
	}
	public void setSatFrom(String satFrom) {
		this.satFrom = satFrom;
	}
	public String getSatTo() {
		return satTo;
	}
	public void setSatTo(String satTo) {
		this.satTo = satTo;
	}
	public String getSunFrom() {
		return sunFrom;
	}
	public void setSunFrom(String sunFrom) {
		this.sunFrom = sunFrom;
	}
	public String getSunTo() {
		return sunTo;
	}
	public void setSunTo(String sunTo) {
		this.sunTo = sunTo;
	}
	public Long getMondayId() {
		return mondayId;
	}
	public void setMondayId(Long mondayId) {
		this.mondayId = mondayId;
	}
	public Long getTuesdayId() {
		return tuesdayId;
	}
	public void setTuesdayId(Long tuesdayId) {
		this.tuesdayId = tuesdayId;
	}
	public Long getWednesdayId() {
		return wednesdayId;
	}
	public void setWednesdayId(Long wednesdayId) {
		this.wednesdayId = wednesdayId;
	}
	public Long getThursdayId() {
		return thursdayId;
	}
	public void setThursdayId(Long thursdayId) {
		this.thursdayId = thursdayId;
	}
	public Long getFridayId() {
		return fridayId;
	}
	public void setFridayId(Long fridayId) {
		this.fridayId = fridayId;
	}
	public Long getSaturdayId() {
		return saturdayId;
	}
	public void setSaturdayId(Long saturdayId) {
		this.saturdayId = saturdayId;
	}
	public Long getSundayId() {
		return sundayId;
	}
	public void setSundayId(Long sundayId) {
		this.sundayId = sundayId;
	}
	public boolean isOverTime() {
		return isOverTime;
	}
	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTotalHrs() {
		return totalHrs;
	}
	public void setTotalHrs(Integer totalHrs) {
		this.totalHrs = totalHrs;
	}
	public List<TimesheetDaysVM> getTimesheetRowDays() {
		return timesheetRowDays;
	}
	public void setTimesheetRowDays(List<TimesheetDaysVM> timesheetRowDays) {
		this.timesheetRowDays = timesheetRowDays;
	}
	public String gettCode() {
		return tCode;
	}
	public void settCode(String tCode) {
		this.tCode = tCode;
	}
	public String getMonFromTo() {
		if(monFrom !=null) monFromTo = monFrom + "-" + monTo;
		return monFromTo;
	}
	public void setMonFromTo(String monFromTo) {
		this.monFromTo = monFromTo;
	}
	public String getTueFromTo() {
		if(tueFrom !=null) tueFromTo = tueFrom + "-" + tueTo;
		return tueFromTo;
	}
	public void setTueFromTo(String tueFromTo) {
		this.tueFromTo = tueFromTo;
	}
	public String getWedFromTo() {
		if(wedFrom !=null) wedFromTo = wedFrom + "-" + wedTo;
		return wedFromTo;
	}
	public void setWedFromTo(String wedFromTo) {
		this.wedFromTo = wedFromTo;
	}
	public String getThuFromTo() {
		if(thuFrom !=null)  thuFromTo = thuFrom + "-" + thuTo;
		return thuFromTo;
	}
	public void setThuFromTo(String thuFromTo) {
		this.thuFromTo = thuFromTo;
	}
	public String getFriFromTo() {
		if(friFrom !=null) friFromTo = friFrom + "-" + friTo; 
		return friFromTo;
	}
	public void setFriFromTo(String friFromTo) {
		this.friFromTo = friFromTo;
	}
	public String getSatFromTo() {
		if(satFrom !=null) satFromTo = satFrom + "-" + satTo;
		return satFromTo;
	}
	public void setSatFromTo(String satFromTo) {
		this.satFromTo = satFromTo;
	}
	public String getSunFromTo() {
		if(sunFrom !=null) sunFromTo = sunFrom + "-" + sunTo;
		return sunFromTo;
	}
	public void setSunFromTo(String sunFromTo) {
		this.sunFromTo = sunFromTo;
	}
	public String getMonSupplier() {
		return monSupplier;
	}
	public void setMonSupplier(String monSupplier) {
		this.monSupplier = monSupplier;
	}
	public String getMonCustomer() {
		return monCustomer;
	}
	public void setMonCustomer(String monCustomer) {
		this.monCustomer = monCustomer;
	}
	public String getMonNotes() {
		return monNotes;
	}
	public void setMonNotes(String monNotes) {
		this.monNotes = monNotes;
	}
	public String getTueSupplier() {
		return tueSupplier;
	}
	public void setTueSupplier(String tueSupplier) {
		this.tueSupplier = tueSupplier;
	}
	public String getTueCustomer() {
		return tueCustomer;
	}
	public void setTueCustomer(String tueCustomer) {
		this.tueCustomer = tueCustomer;
	}
	public String getTueNotes() {
		return tueNotes;
	}
	public void setTueNotes(String tueNotes) {
		this.tueNotes = tueNotes;
	}
	public String getWedSupplier() {
		return wedSupplier;
	}
	public void setWedSupplier(String wedSupplier) {
		this.wedSupplier = wedSupplier;
	}
	public String getWedCustomer() {
		return wedCustomer;
	}
	public void setWedCustomer(String wedCustomer) {
		this.wedCustomer = wedCustomer;
	}
	public String getWedNotes() {
		return wedNotes;
	}
	public void setWedNotes(String wedNotes) {
		this.wedNotes = wedNotes;
	}
	public String getThuSupplier() {
		return thuSupplier;
	}
	public void setThuSupplier(String thuSupplier) {
		this.thuSupplier = thuSupplier;
	}
	public String getThuCustomer() {
		return thuCustomer;
	}
	public void setThuCustomer(String thuCustomer) {
		this.thuCustomer = thuCustomer;
	}
	public String getThuNotes() {
		return thuNotes;
	}
	public void setThuNotes(String thuNotes) {
		this.thuNotes = thuNotes;
	}
	public String getFriSupplier() {
		return friSupplier;
	}
	public void setFriSupplier(String friSupplier) {
		this.friSupplier = friSupplier;
	}
	public String getFriCustomer() {
		return friCustomer;
	}
	public void setFriCustomer(String friCustomer) {
		this.friCustomer = friCustomer;
	}
	public String getFriNotes() {
		return friNotes;
	}
	public void setFriNotes(String friNotes) {
		this.friNotes = friNotes;
	}
	public String getSatSupplier() {
		return satSupplier;
	}
	public void setSatSupplier(String satSupplier) {
		this.satSupplier = satSupplier;
	}
	public String getSatCustomer() {
		return satCustomer;
	}
	public void setSatCustomer(String satCustomer) {
		this.satCustomer = satCustomer;
	}
	public String getSatNotes() {
		return satNotes;
	}
	public void setSatNotes(String satNotes) {
		this.satNotes = satNotes;
	}
	public String getSunSupplier() {
		return sunSupplier;
	}
	public void setSunSupplier(String sunSupplier) {
		this.sunSupplier = sunSupplier;
	}
	public String getSunCustomer() {
		return sunCustomer;
	}
	public void setSunCustomer(String sunCustomer) {
		this.sunCustomer = sunCustomer;
	}
	public String getSunNotes() {
		return sunNotes;
	}
	public void setSunNotes(String sunNotes) {
		this.sunNotes = sunNotes;
	}
	
	
}

