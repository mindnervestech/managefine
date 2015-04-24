package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.mnt.core.domain.FlexiAttributes;
import com.mnt.createProject.model.ProjectComment;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;
	
	@Entity

	public class Attachment extends Model{
		
		@Id
		private Long id;
		private String fileName;
		private String type;
		@OneToOne
		private CaseNotes caseNotes;
		private Date fileDate;
		
		
		public static Finder<Long,Attachment> find = new Finder<Long,Attachment>(Long.class,Attachment.class);


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getFileName() {
			return fileName;
		}


		public void setFileName(String fileName) {
			this.fileName = fileName;
		}


		public CaseNotes getCaseNotes() {
			return caseNotes;
		}


		public void setCaseNotes(CaseNotes caseNotes) {
			this.caseNotes = caseNotes;
		}


		public Date getFileDate() {
			return fileDate;
		}


		public void setFileDate(Date fileDate) {
			this.fileDate = fileDate;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}

		public static Attachment getById(Long id) {
			return find.byId(id);
		}
		
		public static List<Attachment> getAttachmentByNotesId(Long id) {
			return find.where().eq("caseNotes.id", id).findList();
		}
		
		
	}
