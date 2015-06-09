package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Supplier;

import play.db.ebean.Model;
@Entity

public class Pits extends Model{
	
	@Id
	private Long id;
	private String pitsName;
	
	
	public static Finder<Long,Pits> find = new Finder<Long,Pits>(Long.class,Pits.class);
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPitsName() {
		return pitsName;
	}


	public void setPitsName(String pitsName) {
		this.pitsName = pitsName;
	}


	public static Pits getById(Long id) {
		return find.byId(id);
	}
	
	
	
	public static List<Pits> getAllPits() {
		return find.all();
	}
	
}
