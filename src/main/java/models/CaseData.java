package models;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.custom.domain.CaseTypes;
import com.custom.domain.Status;
import com.custom.helpers.CaseSearchContext;
import com.mnt.core.domain.DomainEnum;
import com.mnt.core.helper.SearchContext;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.time.controller.routes;

@Entity
public class CaseData extends Model {
	
	public static final String ENTITY = "Case";
	
	private static final String PROJECTS = "Projects";
	
	public static final String ASSIGNTO_USER = "Assignto User"; 

	@Id
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	@SearchColumnOnUI(rank=2,colName="Title")
	@WizardCardUI(name="Basic Info",step=1)
	@Validation(required = true)
	@UIFields(order=1,label="Title")
	public String title;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=2,label="Description",uitype="textarea")
	@Validation(required = true)
	@SearchColumnOnUI(rank=3,colName="Description")
	public String description;
	
	@SearchColumnOnUI(rank=4,colName="Status",width=40)
	//@Enumerated(EnumType.STRING)
	//@WizardCardUI(name="Basic Info",step=1)
	//@UIFields(order=3,label="Status")
	public String status;
    
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=5,label="Notes", mandatory = true)
    public String notes;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<CaseNotes> casenotes;
    
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=3,label=PROJECTS, autocomplete=true)
	@Validation(required = true)
	@OneToOne(cascade = CascadeType.ALL)
	public Projectinstance projects;
	
	@SearchColumnOnUI(rank=1,colName="Assigned")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label=ASSIGNTO_USER, autocomplete=true,ajaxDependantField="projects")
	@OneToOne(cascade = CascadeType.ALL)
	@Validation(required=true)
	public User assignto;
	
	@Validation(required = true)
	public Long userid;
    
	//@SearchColumnOnUI(rank=5,colName="Due Date")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=6,label="Due Date")
	@Validation(required = true)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	//@SearchFilterOnUI(label="DueDate")
	public Date	dueDate;
	
	//@SearchColumnOnUI(rank=5,colName="Types")
	@Enumerated(EnumType.STRING)
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=7,label="Types")
	@SearchFilterOnUI(label="Type")
	public CaseTypes type;
	
	@Transient
	@SearchFilterOnUI(label="From Due Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date startDateWindow;
	
	@Transient
	@SearchFilterOnUI(label="To Due Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date endDateWindow;
	
	@WizardCardUI(name="Flexi Attributes",step=2)
	@UIFields(order=8)
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<CaseFlexi> flexiAttributes;
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company company;
	
	@Transient
	@SearchColumnOnUI(rank=5,colName="Due Date")
	public String startDateGrid;
    
	public static Model.Finder<Long,CaseData> find = new Model.Finder<Long,CaseData>(Long.class, CaseData.class);
	
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	public static SearchContext  getSearchContext(String onFieldNamePrefix){
		return  CaseSearchContext.getInstance().withFieldNamePrefix(onFieldNamePrefix);
	}

	static {
		autoCompleteAction.put(PROJECTS, routes.Cases.findProject.url);
		autoCompleteAction.put(ASSIGNTO_USER, routes.Users.findAssigntoProjectManagers.url);
	}
	
	public static CaseData findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public List<CaseFlexi> getFlexiAttributes() {
		return flexiAttributes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setFlexiAttributes(List<CaseFlexi> flexiAttributes) {
		this.flexiAttributes = flexiAttributes;
	}

	public User getAssignto() {
		return assignto;
	}

	public List<CaseNotes> getCasenotes() {
		return casenotes;
	}

	public void setCasenotes(List<CaseNotes> casenotes) {
		this.casenotes = casenotes;
	}

	public void setAssignto(User assignto) {
		this.assignto = assignto;
	}


	public Projectinstance getProjects() {
		return projects;
	}

	public void setProjects(Projectinstance projects) {
		this.projects = projects;
	}

	public CaseTypes getType() {
		return type;
	}

	public void setType(CaseTypes type) {
		this.type = type;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getStartDateGrid() {
		return startDateGrid;
	}

	public void setStartDateGrid(String startDateGrid) {
		this.startDateGrid = startDateGrid;
	}

	
}
