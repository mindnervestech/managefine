package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class TaskComment extends Model {

	@Id
	public Long id;
	public Date date;
	public String comment;
	public Long projectId;
	public Long taskId;
	@OneToOne
	public User user;
	
	public static Model.Finder<Long, TaskComment> find = new Model.Finder<Long,TaskComment>(Long.class, TaskComment.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public static List<TaskComment> getByUser(User user) {
		return find.where().eq("user", user).findList();
	}
	
	public static List<TaskComment> getByUserAndTask(User user,Long projectId,Long taskId) {
		return find.where().eq("user", user).eq("projectId", projectId).eq("taskId", taskId).findList();
	} 
	
}
