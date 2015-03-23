package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.mnt.core.domain.DomainEnum;
import com.mnt.core.ui.annotation.UIFields;
import com.mnt.core.ui.annotation.Validation;
import com.mnt.core.ui.annotation.WizardCardUI;

import play.db.ebean.Model;


@Entity
public class LeaveBalance extends Model {

	@Id
	public Long id;
	
	@OneToOne
	@Column(name="employee_id")
	public User employee;
	
	@OneToOne
	@Column(name="leave_level_id")
	public LeaveLevel leaveLevel;

	@Column(nullable=false)
	public Float balance;
	
	/*@Transient
	@WizardCardUI(name="Other Info",step=2)
	@UIFields(order=5,label="leaves")
	@Validation(required = true)
	public static List<DomainEnum> rolex;*/
	
	
	
	public static Model.Finder<Long, LeaveBalance> find = new Model.Finder<Long,LeaveBalance>(Long.class, LeaveBalance.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public LeaveLevel getLeaveLevel() {
		return leaveLevel;
	}

	public void setLeaveLevel(LeaveLevel leaveLevel) {
		this.leaveLevel = leaveLevel;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	/*public int getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}
	*/
	
}
