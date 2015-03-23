package models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import play.db.ebean.Model;

@Entity
public class MailSetting extends Model{
	
	
	@Version
    public Timestamp lastUpdate;
	
	@Id
	public Long id;
	
	public String hostName;
	public String portNumber;
	
	public Boolean sslPort;
	
	public Boolean tlsPort;
	
	public String userName;
	public String password;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company companyObject;
	
	public static Finder<Long,MailSetting> find=new Finder<Long, MailSetting>(Long.class, MailSetting.class);
	
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public Boolean getSslPort() {
		return sslPort;
	}

	public void setSslPort(Boolean sslPort) {
		this.sslPort = sslPort;
	}

	public Boolean getTlsPort() {
		return tlsPort;
	}

	public void setTlsPort(Boolean tlsPort) {
		this.tlsPort = tlsPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompanyObject() {
		return companyObject;
	}

	public void setCompanyObject(Company companyObject) {
		this.companyObject = companyObject;
	}

}
