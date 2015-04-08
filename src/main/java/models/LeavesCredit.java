package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class LeavesCredit extends Model {

	@Id
	public Long id;
	
	public String policyName;
	
	

	public static Model.Finder<Long, LeavesCredit> find = new Model.Finder<Long,LeavesCredit>

(Long.class, LeavesCredit.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	
}
