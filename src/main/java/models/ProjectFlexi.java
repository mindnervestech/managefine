package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;

import com.mnt.core.domain.FlexiAttributes;

@Entity
public class ProjectFlexi extends Model implements FlexiAttributes {
	
	@Id
	public Long id; // need to have as such
	
	
	@ManyToOne
	public Project project;
	
	public Long flexiId; // need to have as such
	
	public String value; // need to have as such
	
	@Transient
	public String type; // need to have as such
	
	@Transient
	public FlexiAttribute flexiAttribute; // need to have as such 

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
	
	
    public static Model.Finder<Long,ProjectFlexi> find = new Model.Finder<Long,ProjectFlexi>(Long.class, ProjectFlexi.class);
	
	public static ProjectFlexi getUserIdById(Long id) {
		// TODO , handle nullpointer
		return find.byId(id);
	}
  	  
	



}
