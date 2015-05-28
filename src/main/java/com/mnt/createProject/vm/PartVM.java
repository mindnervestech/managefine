package com.mnt.createProject.vm;



public class PartVM {
	
	public Long id;
	public Long partNo;
	public Double annualQty;
	public Double costPrice;
	public Double suggestedResale;
	public Double estimatedRevenue;
	public String claimStatus;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPartNo() {
		return partNo;
	}
	public void setPartNo(Long partNo) {
		this.partNo = partNo;
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
	public Double getAnnualQty() {
		return annualQty;
	}
	public void setAnnualQty(Double annualQty) {
		this.annualQty = annualQty;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	
	
	
	
	
	
}
