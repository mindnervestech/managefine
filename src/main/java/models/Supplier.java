package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import com.custom.domain.Currency;
import com.custom.domain.Locality;
import com.custom.domain.Salutation;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;
import com.mnt.orghierarchy.model.Organization;
import com.mnt.time.controller.routes;

@Entity
public class Supplier extends Model {
	
	public static final String ENTITY = "Supplier";
	
//	private static final String COUNTRY = "Country";
	
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
	@Validation(required=true,digits=true)    /* ,maxlength=10*/
	public String phoneNo;
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=3,label="Email")
	@SearchColumnOnUI(rank=3,colName="Email")
	@Validation(required=true,email=true)
	public String email;
	
	/*@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label="Password")
	@SearchColumnOnUI(rank=4,colName="Password")
	@Validation(required=true)
	public String password;*/
	
	@WizardCardUI(name="Basic Info",step=1)
	@UIFields(order=4,label="Fax")
	public Integer fax;
	
	//Second Wizard
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=5,label="Address")
	@Validation(required=true)
	public String address;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=6,label="Street")
	public String street;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=7,label="City")
	@Validation(required=true)
	public String city;
	
	/*@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=9,label=COUNTRY, autocomplete=true)
	@Validation(required = true)
	@OneToOne
	public Country country;
	*/
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=8,label="Country")
	@SearchFilterOnUI(label="Country")
	@SearchColumnOnUI(rank=4,colName="Country")
	@Validation(required=true)
	public String country;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=9,label="PIN")
	@Validation(required=true)
	public String pin;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=10,label="WEBSITE")
	@Validation(required=true)
	public String website;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=11,label="Type")
	@Enumerated(EnumType.STRING)
	public Locality locality;
	
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=12,label="Currency")
	@Enumerated(EnumType.STRING)
	public Currency	currency;
		
	@WizardCardUI(name="Basic Info",step=2)
	@UIFields(order=13,label=" Montly sale/purchase amount ", mandatory = true)
	@Validation(required=true,digits=true)
    public String amount;
	
	//Third Wizard
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=11,label="Salutation")
	@Validation(required=true)
	@Enumerated(EnumType.STRING)
	public Salutation salutation;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=12,label="Contact Name")
	@SearchFilterOnUI(label="Contact Name")
	@Validation(required=true)
	public String contactName;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=13,label="Contact Phone")
	@Validation(required=true,digits=true)
	public String contactPhone;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=14,label="Contact Email")
	@SearchColumnOnUI(rank=5,colName="Contact Email")
	@Validation(required=true,email=true)
	public String contactEmail;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=15,label="Designation")
	@Validation(required=true)
	public String designation;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=16,label="Company")
	@Validation(required=true)
	public String companyName;
	
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=17,label="Salutation 1")
	@Enumerated(EnumType.STRING)
	public Salutation salutation1;
	
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=18,label="Contact Name 1")
	public String contactName1;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=19,label="Contact Phone 1")
	@Validation(required=true,digits=true)
	public String contactPhone1;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=20,label="Contact Email 1")
	@SearchColumnOnUI(rank=5,colName="Contact Email 1")
	public String contactEmail1;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=21,label="Designation 1")
	public String designation1;
	
	@WizardCardUI(name="Contact Point",step=3)
	@UIFields(order=22,label="Company 1")
	public String companyName1;
	
	
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

	/*public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}*/

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

	/*public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
*/
	public void setPin(String pin) {
		this.pin = pin;
	}

	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}


	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

	public Salutation getSalutation1() {
		return salutation1;
	}

	public void setSalutation1(Salutation salutation1) {
		this.salutation1 = salutation1;
	}

	public String getContactName1() {
		return contactName1;
	}

	public void setContactName1(String contactName1) {
		this.contactName1 = contactName1;
	}

	public String getContactPhone1() {
		return contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	public String getContactEmail1() {
		return contactEmail1;
	}

	public void setContactEmail1(String contactEmail1) {
		this.contactEmail1 = contactEmail1;
	}

	public String getDesignation1() {
		return designation1;
	}

	public void setDesignation1(String designation1) {
		this.designation1 = designation1;
	}

	public String getCompanyName1() {
		return companyName1;
	}

	public void setCompanyName1(String companyName1) {
		this.companyName1 = companyName1;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}


	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}




	static {
	//	autoCompleteAction.put(COUNTRY, routes.Suppliers.findCountry.url);
	}
	
}
