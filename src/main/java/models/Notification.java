package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Notification extends Model{
		
		@Id
		public Long id;
		
        @Lob
		public String  settingAsJson;
		
		@OneToOne(cascade=CascadeType.ALL)
		public Company company;

		
		public static Finder<Long,Notification> find=new Finder<Long, Notification>(Long.class, Notification.class);
        
		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getSettingAsJson() {
			return settingAsJson;
		}


		public void setSettingAsJson(String settingAsJson) {
			this.settingAsJson = settingAsJson;
		}


		public Company getCompany() {
			return company;
		}


		public void setCompany(Company company) {
			this.company = company;
		}



}

