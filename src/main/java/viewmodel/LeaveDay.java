package viewmodel;

public class LeaveDay {
	public String day;
	public Boolean isLeave;
	public String reason;
	public Integer leaveType;
	public Long orgId;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Boolean getIsLeave() {
		return isLeave;
	}
	public void setIsLeave(Boolean isLeave) {
		this.isLeave = isLeave;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}
