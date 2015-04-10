package viewmodel;

import java.util.List;

public class WeekReportVM {
	public List<ReportEvent> data;
	public String date;
	public String freeHours;
	public String totalHours;

	public String getFreeHours() {
		return freeHours;
	}

	public void setFreeHours(String freeHours) {
		this.freeHours = freeHours;
	}

	public List<ReportEvent> getData() {
		return data;
	}

	public void setData(List<ReportEvent> data) {
		this.data = data;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}
}
