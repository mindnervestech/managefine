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
import com.mnt.projectHierarchy.model.Projectclass;
import com.mnt.time.controller.routes;

@Entity
public class Client extends Model {
	
	public static final String ENTITY = "Customer";
	
	private static final String COUNTRY = "Country";
	
	private static final String STATE = "State";
	
	private static final String CITY = "City";

	
	@Id
	@WizardCardUI(name="Personal Info",step=0)
	@UIFields(order=0,label="id",hidden=true)
	public Long id;
	
		
	//first Wizard
	@SearchColumnOnUI(rank=1,colName="Customer Name")
	@SearchFilterOnUI(label="Customer Name")
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=1,label="Customer Name")
	@Validation(required=true)
	public String clientName;
	
	@SearchColumnOnUI(rank=2,colName="Phone No")
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=2,label="Phone No")
	@Validation(required=true,digits=true)  /* ,maxlength=10*/
	public String phoneNo;
	
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=3,label="Email")
	@SearchColumnOnUI(rank=3,colName="Email")
	@Validation(required=true,email=true)
	public String email;
	
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=4,label="Fax")
	public Integer fax;
	
	//Second Wizard
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=5,label="Address")
	@Validation(required=true)
	public String address;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=6,label="Billing Address")
	@Validation(required=true)
	public String billingAddress;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=7,label="Shipping Address")
	@Validation(required=true)
	public String shippingAddress;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=8,label="Street")
	public String street;
	
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=9,label=COUNTRY, autocomplete=true)
	@Validation(required = true)
	@OneToOne
	public Country country;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=10,label=STATE, autocomplete=true,ajaxDependantField="country")
	@Validation(required = true)
	@OneToOne
	public State state;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=11,label=CITY, autocomplete=true,ajaxDependantField="state")
	@Validation(required = true)
	@OneToOne
	public City city;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=12,label="PIN")
	@Validation(required=true)
	public String pin;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=13,label="WEBSITE")
	@Validation(required=true)
	public String website;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=14,label="Type")
	@Enumerated(EnumType.STRING)
	public Locality locality;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=13,label="Currency")
	@Enumerated(EnumType.STRING)
	public Currency	currency;
		
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=15,label=" Montly sale/purchase amount ", mandatory = true)
	@Validation(required=true,digits=true)
    public String amount;
	
	/*@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=16,label="Sagment ")
    public String sagment;*/
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=16,label="subsagment ")
    public String subsagment;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=17,label="Number of Employees ")
    public String numberOfEmployees;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=18,label="Estabilshment year")
    public String estabilshmentYear;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=19,label="Company Registration Nos")
    public String companyRegistrationNos;
	
	/*@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=20,label="Capital")
    public String capital;*/
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=20,label="Tier Large, Medium, Small")
    public String tierLargeMediumSmall;
	
	/*@WizardCardUI(name="Factory",step=2)
	@UIFields(order=21,label="Factory Address")
    public String factoryAddres;*/
	
	
	@WizardCardUI(name="Factory ",step=2)
	@UIFields(order=1,label="Production Head Name")
    public String productionHeadName;
	
	@WizardCardUI(name="Factory ",step=2)
	@UIFields(order=2,label="Mail Id of Production  Head")
    public String mailIdOfProductionHead;
	
	@WizardCardUI(name="Factory ",step=2)
	@UIFields(order=3,label="Average stay with Company ")
    public String averageStayWithCompany ;
	
	
	@WizardCardUI(name="Design",step=2)
	@UIFields(order=1,label="R&D Head Name")
    public String randDheadName;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=2,label="R&D Head Cell nos")
    public String randDno;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=2,label="Mail Id of R&D Head")
    public String mailIdOfRandDhead ;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=3,label="Average stay with Company")
    public String averageStayWithCompanyDesign;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=4,label="R&D Second Name")
    public String randDsecondName ;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=5,label="R&D Second Cell nos")
    public String randDsecondCellNo ;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=6,label="Mail Id of R&D Second Head")
    public String mailIdOfRandDsecondHead;
	
	@WizardCardUI(name="Design ",step=2)
	@UIFields(order=7,label="Average stay with Company")
    public String averageStayWithCompanyRandDsecond;
	
	
	

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
	public List<ClientFlexi> flexiAttributes;
	
	@OneToOne(cascade=CascadeType.ALL)
	public Company company;
	
	@OneToOne(cascade=CascadeType.ALL)
	public User user;
	
	public static Model.Finder<Long,Client> find = new Model.Finder<Long,Client>(Long.class, Client.class);
	
	
	public static Client findById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public static Client findByUserId(Long id) {
        return find.where().eq("user.id", id).findUnique();
    }
	public static List<Client> getClientList() {
		return find.all();
	}
	
	public static Map<String,String> autoCompleteAction=new HashMap<String, String>();
	
	
	@Override
	public String toString() {
		return getClientName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSubsagment() {
		return subsagment;
	}

	public void setSubsagment(String subsagment) {
		this.subsagment = subsagment;
	}
	
	public String getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(String numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getEstabilshmentYear() {
		return estabilshmentYear;
	}

	public void setEstabilshmentYear(String estabilshmentYear) {
		this.estabilshmentYear = estabilshmentYear;
	}
	
	public String getCompanyRegistrationNos() {
		return companyRegistrationNos;
	}

	public void setCompanyRegistrationNos(String companyRegistrationNos) {
		this.companyRegistrationNos = companyRegistrationNos;
	}
	

	/*public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
*/
	public String getTierLargeMediumSmall() {
		return tierLargeMediumSmall;
	}

	public void setTierLargeMediumSmall(String tierLargeMediumSmall) {
		this.tierLargeMediumSmall = tierLargeMediumSmall;
	}
	
	/*public String getFactoryAddres() {
		return factoryAddres;
	}

	public void setFactoryAddres(String factoryAddres) {
		this.factoryAddres = factoryAddres;
	}*/

	public String getProductionHeadName() {
		return productionHeadName;
	}

	public void setProductionHeadName(String productionHeadName) {
		this.productionHeadName = productionHeadName;
	}

	public String getMailIdOfProductionHead() {
		return mailIdOfProductionHead;
	}

	public void setMailIdOfProductionHead(String mailIdOfProductionHead) {
		this.mailIdOfProductionHead = mailIdOfProductionHead;
	}

	public String getAverageStayWithCompany() {
		return averageStayWithCompany;
	}

	public void setAverageStayWithCompany(String averageStayWithCompany) {
		this.averageStayWithCompany = averageStayWithCompany;
	}

	
	
	
	public String getRandDheadName() {
		return randDheadName;
	}

	public void setRandDheadName(String randDheadName) {
		this.randDheadName = randDheadName;
	}

	
	
	/*public String getrAndDno() {
		return rAndDno;
	}

	public void setrAndDno(String rAndDno) {
		this.rAndDno = rAndDno;
	}*/

	public String getMailIdOfRandDhead() {
		return mailIdOfRandDhead;
	}

	public void setMailIdOfRandDhead(String mailIdOfRandDhead) {
		this.mailIdOfRandDhead = mailIdOfRandDhead;
	}

	public String getAverageStayWithCompanyDesign() {
		return averageStayWithCompanyDesign;
	}

	public void setAverageStayWithCompanyDesign(String averageStayWithCompanyDesign) {
		this.averageStayWithCompanyDesign = averageStayWithCompanyDesign;
	}

	

	public String getRandDno() {
		return randDno;
	}

	public void setRandDno(String randDno) {
		this.randDno = randDno;
	}

	public String getRandDsecondName() {
		return randDsecondName;
	}

	public void setRandDsecondName(String randDsecondName) {
		this.randDsecondName = randDsecondName;
	}

	public String getRandDsecondCellNo() {
		return randDsecondCellNo;
	}

	public void setRandDsecondCellNo(String randDsecondCellNo) {
		this.randDsecondCellNo = randDsecondCellNo;
	}

	public String getrAndDsecondCellNo() {
		return rAndDsecondCellNo;
	}

	public void setrAndDsecondCellNo(String rAndDsecondCellNo) {
		this.rAndDsecondCellNo = rAndDsecondCellNo;
	}

	public String getMailIdOfRandDsecondHead() {
		return mailIdOfRandDsecondHead;
	}

	public void setMailIdOfRandDsecondHead(String mailIdOfRandDsecondHead) {
		this.mailIdOfRandDsecondHead = mailIdOfRandDsecondHead;
	}

	public String getAverageStayWithCompanyRandDsecond() {
		return averageStayWithCompanyRandDsecond;
	}

	public void setAverageStayWithCompanyRandDsecond(
			String averageStayWithCompanyRandDsecond) {
		this.averageStayWithCompanyRandDsecond = averageStayWithCompanyRandDsecond;
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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

/*	public String getCity() {
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
*/
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

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getContactEmail() {
		return contactEmail;
	}

	public List<ClientFlexi> getFlexiAttributes() {
		return flexiAttributes;
	}

	public void setFlexiAttributes(List<ClientFlexi> flexiAttributes) {
		this.flexiAttributes = flexiAttributes;
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
	public Salutation getSalutation() {
		return salutation;
	}
	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
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
	
	
	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}

	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	


	static {
		autoCompleteAction.put(COUNTRY, routes.Suppliers.findCountry.url);
		autoCompleteAction.put(STATE, routes.Suppliers.findStateByCountry.url);
		autoCompleteAction.put(CITY, routes.Suppliers.findCityByState.url);
	}
	
}
