package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.Expr;
import com.custom.domain.TimesheetStatus;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class TimesheetActual extends Model {

    public static final String ENTITY = "TimesheetActual";


	public static String getEntity() {
		return ENTITY;
	}

	@Id
	@WizardCardUI(name="Timesheet Status Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
    
    @ManyToOne
    public User user;
    
    
    @WizardCardUI(name="Timesheet Status Info",step=1)
    @UIFields(order=1,label="Status")
    @SearchColumnOnUI(rank=3,colName="Status")
	@SearchFilterOnUI(label="Status")
    public TimesheetStatus status;

    @SearchColumnOnUI(rank=2,colName="Week")
    public Integer weekOfYear;
    
    @SearchColumnOnUI(rank=1,colName="Year")
    public Integer year;
    
    @OneToMany
    public List<TimesheetRowActual> timesheetRowsActual;
    
    @Transient
	@SearchFilterOnUI(label="From")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromWeekWindow;
	
	@Transient
	@SearchFilterOnUI(label="To")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toWeekWindow;
    
	@OneToOne
	@SearchColumnOnUI(rank=4,colName="Pending With")
    public User timesheetWith;
	
	public Integer level=0;
	
	public String processInstanceId;
	
	//Guid
	public String tid;

	@Version
	public Timestamp lastUpdateDate;
	
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public static Model.Finder<Long, TimesheetActual> find = new Model.Finder<Long,TimesheetActual>(Long.class, TimesheetActual.class);
    
    public static List<TimesheetActual> byUser_Week_Year(Long id, int week, int year){
    	return TimesheetActual.find.where(Expr.and(Expr.eq("user", User.findById(id)), Expr.and(Expr.eq("year", year),Expr.eq("weekOfYear", week)))).findList();
    }
    
    public static TimesheetActual getByUserWeekAndYear(User user,int week,int year) {
    	return find.where().eq("user", user).eq("weekOfYear", week).eq("year", year).findUnique();
    }
    
    public static TimesheetActual findById(long _id){
    	return find.byId(_id);
    }
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<TimesheetRowActual> getTimesheetRowsActual() {
		return timesheetRowsActual;
	}

	public void setTimesheetRowsActual(List<TimesheetRowActual> timesheetRowsActual) {
		this.timesheetRowsActual = timesheetRowsActual;
	}

	public TimesheetStatus getStatus() {
		return status;
	}

	public void setStatus(TimesheetStatus status) {
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

	public Date getFromWeekWindow() {
		return fromWeekWindow;
	}

	public void setFromWeekWindow(Date fromWeekWindow) {
		this.fromWeekWindow = fromWeekWindow;
	}

	public Date getToWeekWindow() {
		return toWeekWindow;
	}

	public void setToWeekWindow(Date toWeekWindow) {
		this.toWeekWindow = toWeekWindow;
	}

	public User getTimesheetWith() {
		return timesheetWith;
	}

	public void setTimesheetWith(User timesheetWith) {
		this.timesheetWith = timesheetWith;
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

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
}
