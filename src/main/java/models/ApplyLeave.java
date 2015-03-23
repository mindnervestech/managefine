package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.custom.domain.LeaveStatus;
import com.custom.domain.TypeOfLeave;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;

@Entity
public class ApplyLeave extends Model {
  
	public static final String ENTITY = "Leave";  
	@Id
	@WizardCardUI(name="Leave",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	@WizardCardUI(name="Leave",step=1)
	@UIFields(order=1,label="Start Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	@Validation(required=true)
	public Date startDate;
	
	@SearchColumnOnUI(rank=2,colName="No of Days")
	@WizardCardUI(name="Leave",step=1)
	@UIFields(order=2,label="No of Days")
	@Validation(required=true)
	public String noOfDays;
	
	@Transient
	@WizardCardUI(name="Leave",step=1)
	@UIFields(order=3,label="Type of Leave",name="typeOfLeave")
	@Validation(required = true)
	public static List<DomainEnum> leave_domain;
	
	
	@SearchColumnOnUI(rank=3,colName="Type of Leave")
	public String typeOfLeave;
	
	@SearchColumnOnUI(rank=4,colName="Remarks")
	@WizardCardUI(name="Leave",step=1)
	@UIFields(order=4,label="Remarks")
	@Validation(required=true)
	public String	remarks;
	
	@SearchColumnOnUI(rank=5,colName="Status")
	@Enumerated(EnumType.STRING)
	public LeaveStatus status;
	
	@Transient
	@SearchColumnOnUI(colName="Start Date", rank=1)
	public String startDateGrid;
	
	@Transient
	@SearchFilterOnUI(label="Start Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date startDateWindow;
	
	@Transient
	@SearchFilterOnUI(label="End Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date endDateWindow;
	
	@ManyToOne
	public User user;
	
	@OneToOne
    public User pendingWith;
	
	@Version
	public Timestamp lastUpdateDate;
	
	public Integer level=0;
	
	public String processInstanceId;
	
	//Guid
	public String leaveGuid;
	
	public static Model.Finder<Long,ApplyLeave> find = new Model.Finder<Long,ApplyLeave>(Long.class, ApplyLeave.class);
	
	public static ApplyLeave findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static List<ApplyLeave> findByIds(List<Long> ids) {
        return find.where().idIn(ids).findList();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

/*	public TypeOfLeave getTypeOfLeave() {
		return typeOfLeave;
	}

	public void setTypeOfLeave(TypeOfLeave typeOfLeave) {
		this.typeOfLeave = typeOfLeave;
	}
*/
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public String getStartDateGrid() {
		return startDateGrid;
	}

	public void setStartDateGrid(String startDateGrid) {
		this.startDateGrid = startDateGrid;
	}

	public Date getStartDateWindow() {
		return startDateWindow;
	}

	public void setStartDateWindow(Date startDateWindow) {
		this.startDateWindow = startDateWindow;
	}

	public Date getEndDateWindow() {
		return endDateWindow;
	}

	public void setEndDateWindow(Date endDateWindow) {
		this.endDateWindow = endDateWindow;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(User pendingWith) {
		this.pendingWith = pendingWith;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getLeaveGuid() {
		return leaveGuid;
	}

	public void setLeaveGuid(String leaveGuid) {
		this.leaveGuid = leaveGuid;
	}

	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getTypeOfLeave() {
		return typeOfLeave;
	}

	public void setTypeOfLeave(String typeOfLeave) {
		this.typeOfLeave = typeOfLeave;
	}

	
}