package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class LeaveX extends Model {
	
	private static final long serialVersionUID = -7692739284963294943L;
	
	@Id
	public Long id;
	
	@OneToOne
	public Company company;
	
	@OneToMany(cascade=CascadeType.ALL)
	List<LeaveLevel> leaveLevels;
	
	
	public static Model.Finder<Long, LeaveX> find = new Model.Finder<Long,LeaveX>(Long.class, LeaveX.class);
    
	public static LeaveX findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static LeaveX findByCompany(Long id) {
        return find.where().eq("company.id", id).findUnique();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<LeaveLevel> getLeaveLevels() {
		return leaveLevels;
	}

	public void setLeaveLevels(List<LeaveLevel> leaveLevels) {
		this.leaveLevels = leaveLevels;
	}

}
