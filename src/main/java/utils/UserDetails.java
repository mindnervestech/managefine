package utils;

public class UserDetails {
	public String empName;
	public Integer hrsSpent;
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getHrsSpent() {
		return hrsSpent;
	}

	public void setHrsSpent(Integer hrsSpent) {
		this.hrsSpent = hrsSpent;
	}

	public Double getMoneyBilled() {
		return moneyBilled;
	}

	public void setMoneyBilled(Double moneyBilled) {
		this.moneyBilled = moneyBilled;
	}

	public Double moneyBilled;
	
	public UserDetails(String empName, Integer hrsSpent, Double moneyBilled) {
		this.empName = empName;
		this.hrsSpent = hrsSpent;
		this.moneyBilled = moneyBilled;
	}
}
