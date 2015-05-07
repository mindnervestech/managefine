package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.mnt.orghierarchy.model.Organization;

import play.db.ebean.Model;

@Entity
public class City extends Model {

	@Id
	public Long id;
	public String cityName;
	@OneToOne
	public State state;
	
	public static Model.Finder<Long, City> find = new Model.Finder<Long,City>(Long.class, City.class);
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public static List<City> getCountryList() {
		return find.all();
	}
	
		public static City findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
		
		public static List<City> findCityByStateId(Long id) {
	        return find.where().eq("state.id", id).findList();
	    }	
	
	@Override
	public String toString() {
		return getCityName() + " ("+getId()+")";
	}
	
}
