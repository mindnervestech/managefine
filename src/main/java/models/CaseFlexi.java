package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.mnt.core.domain.FlexiAttributes;
import com.mnt.createProject.model.Projectinstance;
import com.mnt.createProject.model.Projectinstancenode;

@Entity
public class CaseFlexi extends Model implements FlexiAttributes {
	
	@Id
	public Long id; 
	
	
	@ManyToOne
	public CaseData CaseData;
	
	public Long flexiId; 
	
	public String value; 
	
	@Transient
	public String type; 
	
	@Transient
	public FlexiAttribute flexiAttribute; // need to have as such 

	
	public static Finder<Long,CaseFlexi> find = new Finder<Long,CaseFlexi>(Long.class,CaseFlexi.class);
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getFlexiId() {
		return flexiId;
	}

	public void setFlexiId(Long flexiId) {
		this.flexiId = flexiId;
	}
	
	public static List<CaseFlexi> getCasesAttId(Long id) {
		return find.where().eq("CaseData.id", id).findList();
	}
	
	public static CaseFlexi getById(Long id) {
		return find.byId(id);
	}

	@Override
	@JsonIgnore
	public String getName() {
		if(flexiAttribute == null) {
			flexiAttribute = FlexiAttribute.getById(flexiId);
		} else {
			flexiAttribute = FlexiAttribute.getById(flexiId);
		}
		return flexiAttribute.getName();
		
	}
	
	@Override
	@JsonIgnore
	public String getType() {
		if(flexiAttribute == null) {
			flexiAttribute = FlexiAttribute.getById(flexiId);
		} else {
			flexiAttribute = FlexiAttribute.getById(flexiId);
		}
		return flexiAttribute.getType();
		
	}

	public FlexiAttribute getFlexiAttribute() {
		return flexiAttribute;
	}

	public void setFlexiAttribute(FlexiAttribute flexiAttribute) {
		this.flexiAttribute = flexiAttribute;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CaseData getCaseData() {
		return CaseData;
	}

	public void setCaseData(CaseData caseData) {
		CaseData = caseData;
	}


}
