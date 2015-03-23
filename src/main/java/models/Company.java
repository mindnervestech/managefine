package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import play.db.ebean.Model;

import com.custom.domain.Status;
import com.mnt.core.ui.annotation.Validation;

@Entity
public class Company extends Model{

	@Id
	public Long id;
	public String companyName;
	public String companyCode;
	
	public String getCompanyCode() {
		return companyCode;
	}
	
	@Validation(email=true,required=true)
	public String companyEmail;
	
	public String companyPhone;
	public String address;
	
	@Enumerated(EnumType.STRING)
	public Status companyStatus;
	
	public Status getCompanyStatus() {
		return companyStatus;
	}
	
	public void setCompanyStatus(Status companyStatus) {
		this.companyStatus = companyStatus;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public static Finder<Long,Company> find=new Finder<Long, Company>(Long.class, Company.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
