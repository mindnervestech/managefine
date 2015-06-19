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
	
	
	
	
	
	
	
	//---------------------------------------------------------------------------------------------------------------------
	@SearchColumnOnUI(rank=1,colName="Customer Name")
	@SearchFilterOnUI(label="Customer Name")
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=1,label="Customer Name")
	@Validation(required=true)
	public String clientName;
	
	@SearchColumnOnUI(rank=2,colName="Phone No")
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=2,label="Phone No")
	@Validation(digits=true)  /* ,maxlength=10*/
	public String phoneNo;
	
	@WizardCardUI(name="Personal Info",step=1)
	@UIFields(order=3,label="Comman Email Id")
	@SearchColumnOnUI(rank=3,colName="Email")
	@Validation(email=true)
	public String email;
	
	
	
	//Second Wizard

	
	
	
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=4,label="Street")
	public String street;
	
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=5,label=COUNTRY, autocomplete=true)
	@Validation(required = true)
	@OneToOne
	public Country country;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=6,label=STATE, autocomplete=true,ajaxDependantField="country")
	@Validation(required = true)
	@OneToOne
	public State state;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=7,label=CITY, autocomplete=true,ajaxDependantField="state")
	@Validation(required = true)
	@OneToOne
	public City city;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=8,label="SUB Location ")
    public String subLocation;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=9,label="PIN")
	public String pin;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=10,label="Region")
	public String region;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=11,label="Capital")
	public String capital;
	
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=12,label="Type of Company(Pvt Ltd,LLC,Public Ltd,Govt Enterprise, Semi Govt) ")
	public String typeofcompany;
	

	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=13,label="WEBSITE")
	public String website;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=14,label="Type")
	@Enumerated(EnumType.STRING)
	public Locality locality;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=15,label="Customer Type")
    public String customerType;
		


	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=16,label="segment ")
    public String segment;
	
	/*@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=16,label="Sagment ")
    public String sagment;*/
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=17,label="subsegment ")
    public String subsagment;
	

		
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=18,label="Number of Employees ")
    public String numberOfEmployees;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=19,label="Estabilshment year")
    public String estabilshmentYear;
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=20,label="Company Registration Nos")
    public String companyRegistrationNos;
	
	/*@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=20,label="Capital")
    public String capital;*/
	
	@WizardCardUI(name="Personal Info",step=2)
	@UIFields(order=21,label="Tier Large, Medium, Small")
    public String tierLargeMediumSmall;
	
	/*@WizardCardUI(name="Factory",step=2)
	@UIFields(order=21,label="Factory Address")
    public String factoryAddres;*/
	
	
//00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=1,label="Office Address")
    public String officeAddress ;
	

	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=2,label="Land line Nos of Office")
	@Validation(digits=true)
    public String landlinenosofoffice;
	
	
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=3,label="Factory Address")
    public String factoryAddress ;
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=4,label="Production Head Name")
    public String productionHeadName ;
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=5,label="Production Head Cell nos")
	@Validation(digits=true)
    public String productionHeadCellnos ;
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=6,label="Mail Id of Production  Head")
	@Validation(email=true)
    public String mailIdofProductionHead ;
	
	@WizardCardUI(name="Office And Factory",step=3)
	@UIFields(order=7,label="Average stay with Company")
    public String averagestaywithCompanyFactory ;
	
	//0000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=1,label="R&D Head Name")
    public String randDheadName;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=2,label="R&D Head Cell nos")
	@Validation(digits=true)
    public String randDheadCellnos;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=3,label="Mail Id of R&D Head")
	@Validation(email=true)
    public String mailIdofRandDhead;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=4,label="Average stay with Company")
    public String averagestaywithCompanyRandDfirst;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=5,label="R&D Second Name")
    public String randDsecondName;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=6,label="R&D Second Cell nos")
	@Validation(digits=true)
    public String randDsecondCellnos;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=7,label="Mail Id of R&D Second Head")
	@Validation(email=true)
    public String mailIdofRandDsecondHead;
	
	@WizardCardUI(name="Design",step=4)
	@UIFields(order=8,label="Average stay with Company")
    public String averagestaywithcompanysecond;

	//000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=1,label="Accounts Head Name")
    public String accountsHeadName;
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=2,label="Accounts Head  Cell nos")
	@Validation(digits=true)
    public String accountsHeadCellnos;
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=3,label="Mail Id of Accounts Head")
	@Validation(email=true)
    public String mailIdofAccountsHead;
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=4,label="Accounts Second Name")
    public String accountsSecondName;
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=5,label="Accounts Second Cell nos")
	@Validation(digits=true)
    public String accountsSecondCellnos;
	
	@WizardCardUI(name="Accounts",step=5)
	@UIFields(order=6,label="Mail Id of Accounts Second Head")
	@Validation(email=true)
    public String mailIdofAccountsSecondHead;
	
	//00000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=1,label="Purchase Head Name")
    public String purchaseHeadName;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=2,label="Purchase Head Cell nos")
	@Validation(digits=true)
    public String purchaseHeadCellnos;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=3,label="Mail Id of Purchase Head")
	@Validation(email=true)
    public String mailIdofPurchaseHead;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=4,label="Average stay with Company")
    public String averagestaywithCompanyBuyerFirst;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=5,label="Purchase Second Head Name")
    public String purchaseSecondHeadName;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=6,label="Purchase Second Head Cell nos")
	@Validation(digits=true)
    public String purchaseSecondHeadCellnos;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=7,label="Mail Id of Second Purchase Head")
	@Validation(email=true)
    public String mailIdofSecondPurchaseHead;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=8,label="Average stay with Company")
    public String averagestaywithCompanyBuyerSecond;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=9,label="Present  overseas supplier 1")
    public String overseassupplier1;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=10,label="Present  overseas supplier 2")
    public String overseassupplier2;

	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=11,label="Present  overseas supplier 3")
    public String overseassupplier3;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=12,label="Present  overseas supplier 4")
    public String overseassupplier4;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=13,label="Present  overseas supplier 5")
    public String overseassupplier5;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=14,label="Present  Local  supplier 1")
    public String  localsupplier1 ;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=15,label="Present  Local  supplier 2")
    public String  localsupplier2;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=16,label="Present  Local  supplier 3")
    public String  localsupplier3 ;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=17,label="Present  Local  supplier 4")
    public String  localsupplier4 ;
	
	@WizardCardUI(name="Buyer",step=6)
	@UIFields(order=18,label="Present  Local  supplier 5")
    public String  localsupplier5 ;

	
	//000000000000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=1,label=" Director 1 Name")
    public String   director1name ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=2,label=" Director 1 Cell nos")
	@Validation(digits=true)
    public String   director1cellnos ;

	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=3,label=" Mail Id of Director 1 Head")
	@Validation(email=true)
    public String   mailIdofDirector1head ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=4,label=" Expert(sales,Purchase accounts)")
    public String   expert1 ;

	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=5,label=" Qualification")
    public String   qualification1 ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=6,label=" Age of Director 1")
	@Validation(digits=true)
    public String   ageofDirector1 ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=7,label=" Director work with before co. estabilshment")
    public String   directorworkwithbeforecoestabilshment1 ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=8,label=" Director 2 Name")
    public String   director2name ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=9,label="Director 2 Cell nos")
	@Validation(digits=true)
    public String   director2cellnos ;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=10,label="Mail Id of Director 2 Head")
	@Validation(email=true)
    public String   mailIdofDirector2head;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=11,label="Expert(sales,Purchase accounts)")
    public String   expert2;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=12,label="Qualification")
    public String   qualification2;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=13,label="Age of Director 2")
	@Validation(digits=true)
    public String   ageofDirector2;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=14,label="Director 3Name")
    public String   director3name;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=15,label="Director 3 Cell nos")
	@Validation(digits=true)
    public String   director3cellnos;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=16,label="Mail Id of Director 3 Head")
	@Validation(email=true)
    public String   mailIdofDirector3head;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=17,label="Expert(sales,Purchase accounts)")
	public String   expert3;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=18,label="Qualification")
	public String   qualification3;
	
	@WizardCardUI(name="Owner,Directors",step=7)
	@UIFields(order=19,label="Age of Director 3")
	@Validation(digits=true)
	public String   ageofDirector3;
	
	//0000000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=1,label="Product 1")
	public String product1;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=2,label="Product 2")
	public String product2;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=3,label="Product 3")
	public String product3;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=4,label="Product 4")
	public String product4;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=5,label="Product 5")
	public String product5;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=6,label="Product 6")
	public String product6;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=7,label="Product 7")
	public String product7;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=8,label="Product 8")
	public String product8;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=9,label="Product 9")
	public String product9;
	
	@WizardCardUI(name="Product",step=8)
	@UIFields(order=10,label="Product 10")
	public String product10;
	
	//00000000000000000000000000000000000000000000000000000000000000000
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=1,label="Turnover FY 2012")
	public String turnoverFy2012;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=2,label="Turnover FY 2013")
	public String turnoverFy2013;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=3,label="Turnover FY 2014")
	public String turnoverFy2014;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=4,label="Projected Turnover for FY 2015")
	public String projectedTurnoverforfy2015;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=5,label="Projected Turnover for FY 2016")
	public String projectedTurnoverforfy2016;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=6,label="Projected Turnover for FY 2017")
	public String projectedTurnoverforfy2017;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=7,label="Sales Head Name")
	public String salesHeadName;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=8,label="Average price of Product")
	public String averagepriceofProduct;
	
	@WizardCardUI(name="Turnover And Sales",step=9)
	@UIFields(order=9,label="Pursuing Approvals (UL, CE,Six sigma,ISO,TS16941)")
	public String pursuingApprovals;
	
	
	//0000000000000000000000000000000000000000000000000000000
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=1,label="Bank 1")
	public String bank1;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=2,label="Bank 2")
	public String bank2;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=3,label="Bank 3")
	public String bank3;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=4,label="Limits")
	public String limits;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=5,label="VAT No")
	public String vatNo;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=6,label="CST NO")
	public String cstNo;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=7,label="Excsie No")
	public String excsieNo;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=8,label="Excsie Range ")
	public String excsieRange ;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=9,label="Excsie Division")
	public String excsieDivision;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=10,label="Excsie Commisionrate")
	public String excsieCommisionrate;
	
	@WizardCardUI(name="Banking And Satutary Information",step=10)
	@UIFields(order=11,label="Pan No")
	public String panNo;
	
	//-------------------------------------------------------------------------
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=1,label="Future Product")
	public String futureProduct;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=2,label="Benchmark")
	public String benchmark;

	@WizardCardUI(name="Others",step=11)
	@UIFields(order=3,label="Competitor No 1")
	public String competitorNo1;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=4,label="Competitor No 2")
	public String competitorNo2;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=5,label="Competitor No 3")
	public String competitorNo3;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=6,label="Organazation Chart")
	public String organazationChart;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=7,label="Average age of Employee")
	public String averageageofEmployee;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=8,label="Average stay with Company")
	public String averagestaywithCompanyArchiechture;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=9,label="Contact Person")
	public String contactPerson;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=10,label="Designation")
	public String designation;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=11,label="E-mail address")
	public String emailaddress;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=12,label="Tel.No.")
	public String telNo;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=13,label="Hp.No.")
	public String hpNo;
	
	@WizardCardUI(name="Others",step=11)
	@UIFields(order=14,label="Navision ID")
	public String navisionID;
	//Third Wizard
	
	
	
	
	
	@WizardCardUI(name="Flexi Attribute",step=12)
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
	
	
	

	public String getLandlinenosofoffice() {
		return landlinenosofoffice;
	}

	public void setLandlinenosofoffice(String landlinenosofoffice) {
		this.landlinenosofoffice = landlinenosofoffice;
	}

	public String getFactoryAddress() {
		return factoryAddress;
	}

	public void setFactoryAddress(String factoryAddress) {
		this.factoryAddress = factoryAddress;
	}
	public String getMailIdofDirector2head() {
		return mailIdofDirector2head;
	}

	public void setMailIdofDirector2head(String mailIdofDirector2head) {
		this.mailIdofDirector2head = mailIdofDirector2head;
	}


	/*public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}
*/
	
	public String getOverseassupplier3() {
		return overseassupplier3;
	}

	public void setOverseassupplier3(String overseassupplier3) {
		this.overseassupplier3 = overseassupplier3;
	}
	
	public String getOverseassupplier2() {
		return overseassupplier2;
	}

	public void setOverseassupplier2(String overseassupplier2) {
		this.overseassupplier2 = overseassupplier2;
	}
	
	public String getMailIdofSecondPurchaseHead() {
		return mailIdofSecondPurchaseHead;
	}

	public void setMailIdofSecondPurchaseHead(String mailIdofSecondPurchaseHead) {
		this.mailIdofSecondPurchaseHead = mailIdofSecondPurchaseHead;
	}
	
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





	

	
	
	


	
	
	/*public String getrAndDno() {
		return rAndDno;
	}

	public void setrAndDno(String rAndDno) {
		this.rAndDno = rAndDno;
	}*/

	//0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000



	


	public String getEmailaddress() {
		return emailaddress;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getHpNo() {
		return hpNo;
	}

	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}

	public String getNavisionID() {
		return navisionID;
	}

	public void setNavisionID(String navisionID) {
		this.navisionID = navisionID;
	}

	public String getExcsieNo() {
		return excsieNo;
	}

	public void setExcsieNo(String excsieNo) {
		this.excsieNo = excsieNo;
	}

	public String getExcsieRange() {
		return excsieRange;
	}

	public void setExcsieRange(String excsieRange) {
		this.excsieRange = excsieRange;
	}

	public String getExcsieDivision() {
		return excsieDivision;
	}

	public void setExcsieDivision(String excsieDivision) {
		this.excsieDivision = excsieDivision;
	}

	public String getExcsieCommisionrate() {
		return excsieCommisionrate;
	}

	public void setExcsieCommisionrate(String excsieCommisionrate) {
		this.excsieCommisionrate = excsieCommisionrate;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getBank1() {
		return bank1;
	}

	public void setBank1(String bank1) {
		this.bank1 = bank1;
	}

	public String getBank2() {
		return bank2;
	}

	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}

	public String getBank3() {
		return bank3;
	}

	public void setBank3(String bank3) {
		this.bank3 = bank3;
	}
	
	

	public String getTurnoverFy2012() {
		return turnoverFy2012;
	}

	public void setTurnoverFy2012(String turnoverFy2012) {
		this.turnoverFy2012 = turnoverFy2012;
	}

	public String getTurnoverFy2013() {
		return turnoverFy2013;
	}

	public void setTurnoverFy2013(String turnoverFy2013) {
		this.turnoverFy2013 = turnoverFy2013;
	}

	public String getTurnoverFy2014() {
		return turnoverFy2014;
	}

	public void setTurnoverFy2014(String turnoverFy2014) {
		this.turnoverFy2014 = turnoverFy2014;
	}

	public String getProjectedTurnoverforfy2015() {
		return projectedTurnoverforfy2015;
	}

	public void setProjectedTurnoverforfy2015(String projectedTurnoverforfy2015) {
		this.projectedTurnoverforfy2015 = projectedTurnoverforfy2015;
	}

	public String getProjectedTurnoverforfy2016() {
		return projectedTurnoverforfy2016;
	}

	public void setProjectedTurnoverforfy2016(String projectedTurnoverforfy2016) {
		this.projectedTurnoverforfy2016 = projectedTurnoverforfy2016;
	}

	public String getProjectedTurnoverforfy2017() {
		return projectedTurnoverforfy2017;
	}

	public void setProjectedTurnoverforfy2017(String projectedTurnoverforfy2017) {
		this.projectedTurnoverforfy2017 = projectedTurnoverforfy2017;
	}

	public String getDirector3cellnos() {
		return director3cellnos;
	}

	public String getPursuingApprovals() {
		return pursuingApprovals;
	}

	public void setPursuingApprovals(String pursuingApprovals) {
		this.pursuingApprovals = pursuingApprovals;
	}

	public String getAveragepriceofProduct() {
		return averagepriceofProduct;
	}

	public void setAveragepriceofProduct(String averagepriceofProduct) {
		this.averagepriceofProduct = averagepriceofProduct;
	}

	public String getSalesHeadName() {
		return salesHeadName;
	}

	public void setSalesHeadName(String salesHeadName) {
		this.salesHeadName = salesHeadName;
	}

	public String getprojectedTurnoverforfy2016() {
		return projectedTurnoverforfy2016;
	}

	public void setprojectedTurnoverforfy2016(String projectedTurnoverforfy2016) {
		this.projectedTurnoverforfy2016 = projectedTurnoverforfy2016;
	}

	public String getprojectedTurnoverforfy2017() {
		return projectedTurnoverforfy2017;
	}

	public void setprojectedTurnoverforfy2017(String projectedTurnoverforfy2017) {
		this.projectedTurnoverforfy2017 = projectedTurnoverforfy2017;
	}

	public String getprojectedTurnoverforfy2015() {
		return projectedTurnoverforfy2015;
	}

	public void setprojectedTurnoverforfy2015(String projectedTurnoverforfy2015) {
		this.projectedTurnoverforfy2015 = projectedTurnoverforfy2015;
	}

	public String getturnoverFy2012() {
		return turnoverFy2012;
	}

	public void setturnoverFy2012(String turnoverFy2012) {
		this.turnoverFy2012 = turnoverFy2012;
	}

	public String getturnoverFy2013() {
		return turnoverFy2013;
	}

	public void setturnoverFy2013(String turnoverFy2013) {
		this.turnoverFy2013 = turnoverFy2013;
	}

	public String getturnoverFy2014() {
		return turnoverFy2014;
	}

	public void setturnoverFy2014(String turnoverFy2014) {
		this.turnoverFy2014 = turnoverFy2014;
	}

	public String getAgeofDirector3() {
		return ageofDirector3;
	}

	public void setAgeofDirector3(String ageofDirector3) {
		this.ageofDirector3 = ageofDirector3;
	}

	public String getProduct1() {
		return product1;
	}

	public void setProduct1(String product1) {
		this.product1 = product1;
	}

	public String getProduct2() {
		return product2;
	}

	public void setProduct2(String product2) {
		this.product2 = product2;
	}

	public String getProduct3() {
		return product3;
	}

	public void setProduct3(String product3) {
		this.product3 = product3;
	}

	public String getProduct4() {
		return product4;
	}

	public void setProduct4(String product4) {
		this.product4 = product4;
	}

	public String getProduct5() {
		return product5;
	}

	public void setProduct5(String product5) {
		this.product5 = product5;
	}

	public String getProduct6() {
		return product6;
	}

	public void setProduct6(String product6) {
		this.product6 = product6;
	}

	public String getProduct7() {
		return product7;
	}

	public void setProduct7(String product7) {
		this.product7 = product7;
	}

	public String getProduct8() {
		return product8;
	}

	public void setProduct8(String product8) {
		this.product8 = product8;
	}

	public String getProduct9() {
		return product9;
	}

	public void setProduct9(String product9) {
		this.product9 = product9;
	}

	public String getProduct10() {
		return product10;
	}

	public void setProduct10(String product10) {
		this.product10 = product10;
	}

	public String getQualification3() {
		return qualification3;
	}

	public void setQualification3(String qualification3) {
		this.qualification3 = qualification3;
	}

	public String getExpert1() {
		return expert1;
	}

	public void setExpert1(String expert1) {
		this.expert1 = expert1;
	}

	public String getExpert2() {
		return expert2;
	}

	public void setExpert2(String expert2) {
		this.expert2 = expert2;
	}

	public String getExpert3() {
		return expert3;
	}

	public void setExpert3(String expert3) {
		this.expert3 = expert3;
	}

	public String getMailIdofDirector3head() {
		return mailIdofDirector3head;
	}

	public void setMailIdofDirector3head(String mailIdofDirector3head) {
		this.mailIdofDirector3head = mailIdofDirector3head;
	}

	public String getDirector3Cellnos() {
		return director3cellnos;
	}

	public void setDirector3cellnos(String director3cellnos) {
		this.director3cellnos = director3cellnos;
	}

	public String getDirector3name() {
		return director3name;
	}

	public void setDirector3name(String director3name) {
		this.director3name = director3name;
	}

	public String getAgeofDirector2() {
		return ageofDirector2;
	}

	public void setAgeofDirector2(String ageofDirector2) {
		this.ageofDirector2 = ageofDirector2;
	}

	public String getQualification2() {
		return qualification2;
	}

	public void setQualification2(String qualification2) {
		this.qualification2 = qualification2;
	}

	

	public String getDirector2cellnos() {
		return director2cellnos;
	}

	public void setDirector2cellnos(String director2cellnos) {
		this.director2cellnos = director2cellnos;
	}

	public String getDirector2name() {
		return director2name;
	}

	public void setDirector2name(String director2name) {
		this.director2name = director2name;
	}

	public String getDirectorworkwithbeforecoestabilshment1() {
		return directorworkwithbeforecoestabilshment1;
	}

	public void setDirectorworkwithbeforecoestabilshment1(
			String directorworkwithbeforecoestabilshment1) {
		this.directorworkwithbeforecoestabilshment1 = directorworkwithbeforecoestabilshment1;
	}

	public String getAgeofDirector1() {
		return ageofDirector1;
	}

	public void setAgeofDirector1(String ageofDirector1) {
		this.ageofDirector1 = ageofDirector1;
	}

	public String getQualification1() {
		return qualification1;
	}

	public void setQualification1(String qualification1) {
		this.qualification1 = qualification1;
	}

	

	public String getMailIdofDirector1head() {
		return mailIdofDirector1head;
	}

	public void setMailIdofDirector1head(String mailIdofDirector1head) {
		this.mailIdofDirector1head = mailIdofDirector1head;
	}

	public String getDirector1cellnos() {
		return director1cellnos;
	}

	public void setDirector1cellnos(String director1cellnos) {
		this.director1cellnos = director1cellnos;
	}

	public String getDirector1name() {
		return director1name;
	}

	public void setDirector1name(String director1name) {
		this.director1name = director1name;
	}

	public String getLocalsupplier2() {
		return localsupplier2;
	}

	public void setLocalsupplier2(String localsupplier2) {
		this.localsupplier2 = localsupplier2;
	}

	public String getLocalsupplier3() {
		return localsupplier3;
	}

	public void setLocalsupplier3(String localsupplier3) {
		this.localsupplier3 = localsupplier3;
	}

	public String getLocalsupplier4() {
		return localsupplier4;
	}

	public void setLocalsupplier4(String localsupplier4) {
		this.localsupplier4 = localsupplier4;
	}

	public String getLocalsupplier5() {
		return localsupplier5;
	}

	public void setLocalsupplier5(String localsupplier5) {
		this.localsupplier5 = localsupplier5;
	}

	public String getLocalsupplier1() {
		return localsupplier1;
	}

	public void setLocalsupplier1(String localsupplier1) {
		this.localsupplier1 = localsupplier1;
	}

	public String getOverseassupplier4() {
		return overseassupplier4;
	}

	public void setOverseassupplier4(String overseassupplier4) {
		this.overseassupplier4 = overseassupplier4;
	}

	public String getOverseassupplier5() {
		return overseassupplier5;
	}

	public void setOverseassupplier5(String overseassupplier5) {
		this.overseassupplier5 = overseassupplier5;
	}

	public String getOverseassupplier1() {
		return overseassupplier1;
	}

	public void setOverseassupplier1(String overseassupplier1) {
		this.overseassupplier1 = overseassupplier1;
	}

	public String getAveragestaywithCompanyBuyerSecond() {
		return averagestaywithCompanyBuyerSecond;
	}

	public void setAveragestaywithCompanyBuyerSecond(
			String averagestaywithCompanyBuyerSecond) {
		this.averagestaywithCompanyBuyerSecond = averagestaywithCompanyBuyerSecond;
	}

	public String getPurchaseSecondHeadCellnos() {
		return purchaseSecondHeadCellnos;
	}

	public void setPurchaseSecondHeadCellnos(String purchaseSecondHeadCellnos) {
		this.purchaseSecondHeadCellnos = purchaseSecondHeadCellnos;
	}

	public String getPurchaseSecondHeadName() {
		return purchaseSecondHeadName;
	}

	public void setPurchaseSecondHeadName(String purchaseSecondHeadName) {
		this.purchaseSecondHeadName = purchaseSecondHeadName;
	}

	public String getAveragestaywithCompanyBuyerFirst() {
		return averagestaywithCompanyBuyerFirst;
	}

	public void setAveragestaywithCompanyBuyerFirst(
			String averagestaywithCompanyBuyerFirst) {
		this.averagestaywithCompanyBuyerFirst = averagestaywithCompanyBuyerFirst;
	}

	public String getMailIdofPurchaseHead() {
		return mailIdofPurchaseHead;
	}

	public void setMailIdofPurchaseHead(String mailIdofPurchaseHead) {
		this.mailIdofPurchaseHead = mailIdofPurchaseHead;
	}

	public String getPurchaseHeadCellnos() {
		return purchaseHeadCellnos;
	}

	public void setPurchaseHeadCellnos(String purchaseHeadCellnos) {
		this.purchaseHeadCellnos = purchaseHeadCellnos;
	}

	public String getPurchaseHeadName() {
		return purchaseHeadName;
	}

	public void setPurchaseHeadName(String purchaseHeadName) {
		this.purchaseHeadName = purchaseHeadName;
	}

	public String getMailIdofAccountsSecondHead() {
		return mailIdofAccountsSecondHead;
	}

	public void setMailIdofAccountsSecondHead(String mailIdofAccountsSecondHead) {
		this.mailIdofAccountsSecondHead = mailIdofAccountsSecondHead;
	}

	public String getAccountsSecondCellnos() {
		return accountsSecondCellnos;
	}

	public void setAccountsSecondCellnos(String accountsSecondCellnos) {
		this.accountsSecondCellnos = accountsSecondCellnos;
	}

	public String getAccountsSecondName() {
		return accountsSecondName;
	}

	public void setAccountsSecondName(String accountsSecondName) {
		this.accountsSecondName = accountsSecondName;
	}

	public String getMailIdofAccountsHead() {
		return mailIdofAccountsHead;
	}

	public void setMailIdofAccountsHead(String mailIdofAccountsHead) {
		this.mailIdofAccountsHead = mailIdofAccountsHead;
	}

	public String getAccountsHeadCellnos() {
		return accountsHeadCellnos;
	}

	public void setAccountsHeadCellnos(String accountsHeadCellnos) {
		this.accountsHeadCellnos = accountsHeadCellnos;
	}

	public String getAccountsHeadName() {
		return accountsHeadName;
	}

	public void setAccountsHeadName(String accountsHeadName) {
		this.accountsHeadName = accountsHeadName;
	}

	public String getAveragestaywithcompanysecond() {
		return averagestaywithcompanysecond;
	}

	public void setAveragestaywithcompanysecond(String averagestaywithcompanysecond) {
		this.averagestaywithcompanysecond = averagestaywithcompanysecond;
	}

	public String getMailIdofRandDsecondHead() {
		return mailIdofRandDsecondHead;
	}

	public void setMailIdofRandDsecondHead(String mailIdofRandDsecondHead) {
		this.mailIdofRandDsecondHead = mailIdofRandDsecondHead;
	}

	public String getRandDsecondCellnos() {
		return randDsecondCellnos;
	}

	public void setRandDsecondCellnos(String randDsecondCellnos) {
		this.randDsecondCellnos = randDsecondCellnos;
	}

	public String getRandDsecondName() {
		return randDsecondName;
	}

	public void setRandDsecondName(String randDsecondName) {
		this.randDsecondName = randDsecondName;
	}

	public String getAveragestaywithCompanyRandDfirst() {
		return averagestaywithCompanyRandDfirst;
	}

	public void setAveragestaywithCompanyRandDfirst(
			String averagestaywithCompanyRandDfirst) {
		this.averagestaywithCompanyRandDfirst = averagestaywithCompanyRandDfirst;
	}

	public String getMailIdofRandDhead() {
		return mailIdofRandDhead;
	}

	public void setMailIdofRandDhead(String mailIdofRandDhead) {
		this.mailIdofRandDhead = mailIdofRandDhead;
	}

	public String getRandDheadCellnos() {
		return randDheadCellnos;
	}

	public void setRandDheadCellnos(String randDheadCellnos) {
		this.randDheadCellnos = randDheadCellnos;
	}

	public String getRandDheadName() {
		return randDheadName;
	}

	public void setRandDheadName(String randDheadName) {
		this.randDheadName = randDheadName;
	}

	public String getMailIdofProductionHead() {
		return mailIdofProductionHead;
	}

	public void setMailIdofProductionHead(String mailIdofProductionHead) {
		this.mailIdofProductionHead = mailIdofProductionHead;
	}

	public String getAveragestaywithCompanyFactory() {
		return averagestaywithCompanyFactory;
	}

	public void setAveragestaywithCompanyFactory(
			String averagestaywithCompanyFactory) {
		this.averagestaywithCompanyFactory = averagestaywithCompanyFactory;
	}

	public String getProductionHeadCellnos() {
		return productionHeadCellnos;
	}

	public void setProductionHeadCellnos(String productionHeadCellnos) {
		this.productionHeadCellnos = productionHeadCellnos;
	}

	
	public String getProductionHeadName() {
		return productionHeadName;
	}

	public void setProductionHeadName(String productionHeadName) {
		this.productionHeadName = productionHeadName;
	}

	

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	


	//==00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000



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

	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public List<ClientFlexi> getFlexiAttributes() {
		return flexiAttributes;
	}

	public void setFlexiAttributes(List<ClientFlexi> flexiAttributes) {
		this.flexiAttributes = flexiAttributes;
	}

	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public Locality getLocality() {
		return locality;
	}
	public void setLocality(Locality locality) {
		this.locality = locality;
	}
	
	
	
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	

	


	


	public String getSubLocation() {
		return subLocation;
	}

	public void setSubLocation(String subLocation) {
		this.subLocation = subLocation;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	

	public String getFutureProduct() {
		return futureProduct;
	}

	public void setFutureProduct(String futureProduct) {
		this.futureProduct = futureProduct;
	}

	
	
	
	
	
	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getTypeofcompany() {
		return typeofcompany;
	}

	public void setTypeofcompany(String typeofcompany) {
		this.typeofcompany = typeofcompany;
	}

	public String getBenchmark() {
		return benchmark;
	}

	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}

	public String getCompetitorNo1() {
		return competitorNo1;
	}

	public void setCompetitorNo1(String competitorNo1) {
		this.competitorNo1 = competitorNo1;
	}

	public String getCompetitorNo2() {
		return competitorNo2;
	}

	public void setCompetitorNo2(String competitorNo2) {
		this.competitorNo2 = competitorNo2;
	}

	public String getCompetitorNo3() {
		return competitorNo3;
	}

	public void setCompetitorNo3(String competitorNo3) {
		this.competitorNo3 = competitorNo3;
	}

	public String getOrganazationChart() {
		return organazationChart;
	}

	public void setOrganazationChart(String organazationChart) {
		this.organazationChart = organazationChart;
	}

	public String getAverageageofEmployee() {
		return averageageofEmployee;
	}

	public void setAverageageofEmployee(String averageageofEmployee) {
		this.averageageofEmployee = averageageofEmployee;
	}

	public String getAveragestaywithCompanyArchiechture() {
		return averagestaywithCompanyArchiechture;
	}

	public void setAveragestaywithCompanyArchiechture(
			String averagestaywithCompanyArchiechture) {
		this.averagestaywithCompanyArchiechture = averagestaywithCompanyArchiechture;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}








	static {
		autoCompleteAction.put(COUNTRY, routes.Suppliers.findCountry.url);
		autoCompleteAction.put(STATE, routes.Suppliers.findStateByCountry.url);
		autoCompleteAction.put(CITY, routes.Suppliers.findCityByState.url);
	}
	
}
