package dto;

import javax.persistence.Id;

import play.db.ebean.Model;

import com.mnt.core.domain.DomainEnum;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;

public class TimeSheetBucket extends Model{

	public static final String ENTITY = "My_TimeSheet_Approval_Bucket";

	@Id
	public Long id;

	@SearchFilterOnUI(label="Employee First Name")
	@SearchColumnOnUI(colName="First Name", rank=1 ,width =40)
	public String firstName;
	
	@SearchFilterOnUI(label="Employee Last Name")
	@SearchColumnOnUI(colName="Last Name", rank=2 ,width =40)
	public String lastName;
	
	@SearchFilterOnUI(label="Project")
	@SearchColumnOnUI(colName="Project Name", rank=3 , width =40)
	public String projectName;
	
	@SearchColumnOnUI(colName="Week", rank=4 ,width =10 )
	public int weekOfYear;
	
	@SearchColumnOnUI(colName="Year", rank=5 , width =10)
	public int year;
	
	public static Model.Finder<Long, TimeSheetBucket> find = new Model.Finder<Long,TimeSheetBucket>(Long.class, TimeSheetBucket.class);
	
	public enum TimesheetStatus implements DomainEnum{
		Submitted("Submitted"),
		Approved("Approved"),
		Rejected("Rejected"),;
		
		private String forName;
		
		private TimesheetStatus(String forName){
			this.forName=forName;
		}
		
		public String getName(){
			return forName;
		}

		@Override
		public boolean uiHidden() {
			return false;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(int weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
