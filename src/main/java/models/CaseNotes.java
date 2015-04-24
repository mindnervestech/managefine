package models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.custom.domain.TimesheetStatus;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.WizardCardUI;

@Entity
public class CaseNotes extends Model{

    public static final String ENTITY = "CaseNotes";


	public static String getEntity() {
		return ENTITY;
	}

	@Id
	@WizardCardUI(name="Timesheet Status Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
    
    @ManyToOne
    public CaseData casedata;
    
	public String casenote;
	
	public Long noteUser;
	
	public Date noteDate;


	public static Model.Finder<Long, CaseNotes> find = new Model.Finder<Long,CaseNotes>(Long.class, CaseNotes.class);


	public static List<CaseNotes> getCasesNotesId(Long id) {
		return find.where().eq("casedata.id", id).findList();
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public CaseData getCasedata() {
		return casedata;
	}


	public void setCasedata(CaseData casedata) {
		this.casedata = casedata;
	}


	public String getCasenote() {
		return casenote;
	}


	public void setCasenote(String casenote) {
		this.casenote = casenote;
	}


	public Long getNoteUser() {
		return noteUser;
	}


	public void setNoteUser(Long noteUser) {
		this.noteUser = noteUser;
	}


	public Date getNoteDate() {
		return noteDate;
	}


	public void setNoteDate(Date noteDate) {
		this.noteDate = noteDate;
	}
    
 
    
}