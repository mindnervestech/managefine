package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Expr;
import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;

@Entity
public class Country extends Model {

	@Id
	public Long id;
	public String countryName;
	
	public static Model.Finder<Long, Country> find = new Model.Finder<Long,Country>(Long.class, Country.class);
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public static List<Country> getCountryList(String query) {
		
		List<Country> coList = Country.find.where().ilike("countryName", query+"%").findList();
		return coList;
		//return find.all();
	}
	
	/*public static Country getCountryById(Long id) {
		return find.where().eq("id", id).findUnique();
	}*/
	
	public static Country getCountryById(Long id) {
		return find.byId(id);
	}
	
	public static Country findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	@Override
	public String toString() {
		return getCountryName() + " ("+getId()+")";
	}
	
}
