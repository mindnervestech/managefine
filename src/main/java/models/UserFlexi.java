package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;

import com.mnt.core.domain.FlexiAttributes;

@Entity
public class UserFlexi extends Model implements FlexiAttributes {
	
	@Id
	public Long id;
	
	
	@ManyToOne
	public User user;
	
	public Long flexiId;
	
	public String value;
	
	@Transient
	public String type;
	
	@Transient
	public FlexiAttribute flexiAttribute; 

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	

}