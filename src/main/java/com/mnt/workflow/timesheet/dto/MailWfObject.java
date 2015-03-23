package com.mnt.workflow.timesheet.dto;

import java.io.Serializable;

public class MailWfObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public String to;
	public String from;
	public String cc;
	public String subject;
	public String body;
	public String bcc;
	
	public String getTo() {
		return to;
	}
	public String getFrom() {
		return from;
	}
	public String getCc() {
		return cc;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public String getBcc() {
		return bcc;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	
}
