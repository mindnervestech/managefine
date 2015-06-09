package com.mnt.createProject.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.Supplier;

import play.db.ebean.Model;
@Entity

public class DefinePart extends Model{
	
	@Id
	private Long id;
	@OneToOne
	private ProjectPart partNo;
	private Double annualQty;
	private Double costPrice;
	private Double suggestedResale;
	private Double estimatedRevenue;
	private String claimStatus;
	private String leadTime;
	@OneToOne
	private Pits pits;
	@OneToOne
	private Supplier supplier;
	@OneToOne
	private Projectinstance Projectinstance;
	
	public static Finder<Long,DefinePart> find = new Finder<Long,DefinePart>(Long.class,DefinePart.class);
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public ProjectPart getPartNo() {
		return partNo;
	}


	public void setPartNo(ProjectPart partNo) {
		this.partNo = partNo;
	}


	public Double getAnnualQty() {
		return annualQty;
	}


	public void setAnnualQty(Double annualQty) {
		this.annualQty = annualQty;
	}


	public Double getCostPrice() {
		return costPrice;
	}


	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}


	public Double getSuggestedResale() {
		return suggestedResale;
	}


	public void setSuggestedResale(Double suggestedResale) {
		this.suggestedResale = suggestedResale;
	}




	public Double getEstimatedRevenue() {
		return estimatedRevenue;
	}


	public void setEstimatedRevenue(Double estimatedRevenue) {
		this.estimatedRevenue = estimatedRevenue;
	}


	public Projectinstance getProjectinstance() {
		return Projectinstance;
	}


	public void setProjectinstance(Projectinstance projectinstance) {
		Projectinstance = projectinstance;
	}


	public static DefinePart getById(Long id) {
		return find.byId(id);
	}


	public String getClaimStatus() {
		return claimStatus;
	}


	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	
	public String getLeadTime() {
		return leadTime;
	}


	public void setLeadTime(String leadTime) {
		this.leadTime = leadTime;
	}


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	public Pits getPits() {
		return pits;
	}


	public void setPits(Pits pits) {
		this.pits = pits;
	}


	public static List<DefinePart> getPartByProject(Long projectId) {
		return find.where().eq("Projectinstance.id", projectId).findList();
	}
	
	
	
}
