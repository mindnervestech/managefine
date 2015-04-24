package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;

@Entity
public class Supplier extends Model {
	
	public static final String ENTITY = "Supplier";
	
	@Id
	@WizardCardUI(name="Basic Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
	//first Wizard
	@SearchColumnOnUI(rank=1,colName="Supplier Name")
	@SearchFilterOnUI(label="Supplier Name")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=1,label="Supplier Name")
	@Validation(required=true)
	public String supplierName;
	
	@SearchColumnOnUI(rank=2,colName="Phone No")
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=2,label="Phone No")
	@Validation(required=true,digits=true,maxlength=10)
	public String phoneNo;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=3,label="Email")
	@SearchColumnOnUI(rank=3,colName="Email")
	@Validation(required=true,email=true)
	public String email;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label="Password")
	@SearchColumnOnUI(rank=4,colName="Password")
	@Validation(required=true)
	public String password;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=5,label="Fax")
	public Integer fax;
	
	//Second Wizard
	@WizardCardUI(name="Location",step=2)
	@UIFields(order=6,label="Address")
	@Validation(required=true)
	public String address;
	
	@WizardCardUI(name="Location",step=2)
	@UIFields(order=7,label="Street")
	public String street;
	
	@WizardCardUI(name="Location",step=2)
	@UIFields(order=8,label="City")
	@Validation(required=true)
	public String city;
	
	@WizardCardUI(name="Location",step=2)
	@UIFields(order=9,label="Country")
	@SearchFilterOnUI(label="Country")
	@SearchColumnOnUI(rank=4,colName="Country")
	@Validation(required=true)
	public String country;
	
	@WizardCardUI(name="Location",step=2)
	@UIFields(order=10,label="PIN")
	@Validation(required=true)
	public String pin;
	
	//Third Wizard
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=11,label="Contact Name")
	@SearchFilterOnUI(label="Contact Name")
	@Validation(required=true)
	public String contactName;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=12,label="Contact Phone")
	@Validation(required=true,digits=true,maxlength=10)
	public String contactPhone;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=13,label="Contact Email")
	@SearchColumnOnUI(rank=5,colName="Contact Email")
	@Validation(required=true,email=true)
	public String contactEmail;
	
	@WizardCardUI(name="Flexi Attribute",step=3)
	@UIFields(order=1,label="flexiAttributes")
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<SupplierFlexi> flexiAttributes;
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company company;
	
	@OneToOne(cascade=CascadeType.ALL)
	public User user;
	
	public static Model.Finder<Long,Supplier> find = new Model.Finder<Long,Supplier>(Long.class, Supplier.class);
	
	
	public static Supplier findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static List<Supplier> getSupplierList() {
		return find.all();
	}
	
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFax() {
		return fax;
	}

	public void setFax(Integer fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<SupplierFlexi> getFlexiAttributes() {
		return flexiAttributes;
	}

	public void setFlexiAttributes(List<SupplierFlexi> flexiAttributes) {
		this.flexiAttributes = flexiAttributes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
