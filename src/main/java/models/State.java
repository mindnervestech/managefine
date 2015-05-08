package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;

@Entity
public class State extends Model {

	@Id
	public Long id;
	public String stateName;
	@OneToOne
	public Country country;
	
	public static Model.Finder<Long, State> find = new Model.Finder<Long,State>(Long.class, State.class);
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public static List<State> getCountryList() {
		return find.all();
	}
	
		public static State findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
		
		public static List<State> findByCountryId(String query, Long id) {
			List<State> sList = State.find.where().eq("country.id", id).like("stateName", query+"%").findList();
	        
			return sList;
	       // return find.where().eq("country.id", id).findList();
	    }	
		
		
		
		/*List<State> sList = State.find.where().eq("country.id", id).like("stateName", query+"%").findList();
        
		return sList;
        List<User> users = User.find.where().
    			and(Expr.eq("companyobject.id",_thisUser.getCompanyobject().getId()),
    			Expr.or(Expr.ilike("firstName", query+"%"), 
    				Expr.or(Expr.ilike("lastName", query+"%"),
    						Expr.ilike("middleName", query+"%")))).findList();
        
        
        //List<State> coList = State.find.where().ilike("countryName", query+"%").findList();
*/	
	@Override
	public String toString() {
		return getStateName() + " ("+getId()+")";
	}
	
}
