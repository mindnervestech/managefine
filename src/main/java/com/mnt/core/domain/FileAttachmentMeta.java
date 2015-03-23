package com.mnt.core.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

public  class FileAttachmentMeta implements Attachment{
	public String n;
	public long s;
	public String ct;
	public FileAttachmentMeta(){}
	public FileAttachmentMeta(String name,long size,String contenttype ) {
		n = name;
		s = size;
		ct = contenttype;
	}
	@Override
	@JsonIgnore
	public String name() {
		return n;
	}
	@Override
	@JsonIgnore
	public long size() {
		return s;
	}
	@Override
	@JsonIgnore
	public String contentType() {
		return ct;
	}
}