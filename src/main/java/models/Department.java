package models;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Department extends Model {
	
	@Id
	private Long id;
	private String name;
	
	private static Model.Finder<Long, Department> find = new Model.Finder<Long,Department>(Long.class, Department.class);

	
	public static List<Department> findAll() {
        return find.all();
    }
	
	public static Department departmentById(Long id) {
        return find.where().eq("id", id).findUnique();
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
