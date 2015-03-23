package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import play.data.format.Formats;
import play.db.ebean.Model;

import com.custom.domain.Rating;
import com.mnt.core.ui.annotation.SearchColumnOnUI;
import com.mnt.core.ui.annotation.SearchFilterOnUI;
import com.mnt.core.ui.annotation.Validation;

@Entity
public class Feedback extends Model{

	public static final String ENTITY = "Feedback";
	
	@Id
	public Long id;
	
	
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date feedbackDate = new Date();
	
	@Transient
	@SearchColumnOnUI(rank=1,colName="Feedback Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public String feedbackDateDisplay;
	
	@Transient
	@SearchColumnOnUI(rank=2,colName="Feedback From")
	public String feedbackFromManager;
	
	@Lob
    @Validation(required=true)
	public String feedback;
	
	@SearchColumnOnUI(rank=3,colName="Rating")
	public Rating rating;
	
	@ManyToOne
	public User user;
	
	@Transient
	@SearchFilterOnUI(label="From Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date fromDateWindow;
	
	@OneToOne
	public User manager;
	
	@Transient
	@SearchFilterOnUI(label="To Date")
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date toDateWindow;
	
	public static Model.Finder<Long,Feedback> find = new Model.Finder<Long,Feedback>(Long.class, Feedback.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public String getFeedbackDateDisplay() {
		return feedbackDateDisplay;
	}

	public void setFeedbackDateDisplay(String feedbackDateDisplay) {
		this.feedbackDateDisplay = feedbackDateDisplay;
	}

	public String getFeedbackFromManager() {
		return feedbackFromManager;
	}

	public void setFeedbackFromManager(String feedbackFromManager) {
		this.feedbackFromManager = feedbackFromManager;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFromDateWindow() {
		return fromDateWindow;
	}

	public void setFromDateWindow(Date fromDateWindow) {
		this.fromDateWindow = fromDateWindow;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Date getToDateWindow() {
		return toDateWindow;
	}

	public void setToDateWindow(Date toDateWindow) {
		this.toDateWindow = toDateWindow;
	}

	public static Model.Finder<Long, Feedback> getFind() {
		return find;
	}

	public static void setFind(Model.Finder<Long, Feedback> find) {
		Feedback.find = find;
	}
	
}
