package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.mnt.core.ui.annotation.Validation;

@Entity
public class Delegate extends Model{
	
	@Id
	public Long id;
	
	@Validation(required=true)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromDate;
	
	@Validation(required=true)
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toDate;
	
	@OneToOne
	public User delegator;
	@OneToOne
	public User delegatedTo;
	
	
	public static Finder<Long,Delegate> find = new Finder<Long, Delegate>(Long.class, Delegate.class);


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public User getDelegator() {
		return delegator;
	}


	public void setDelegator(User delegator) {
		this.delegator = delegator;
	}


	public User getDelegatedTo() {
		return delegatedTo;
	}


	public void setDelegatedTo(User delegatedTo) {
		this.delegatedTo = delegatedTo;
	}
}
