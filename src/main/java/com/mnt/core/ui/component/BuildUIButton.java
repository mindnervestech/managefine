package com.mnt.core.ui.component;

public class BuildUIButton implements UIButton{

	private String url;
	private boolean visibility = false;
	private ButtonActionType target = ButtonActionType.ACTION;
	private String label = "Save";
	private String id = "SaveID";
	
	private BuildUIButton(){}
	
	
	@Override
	public String url() {
		return url;
	}

	@Override
	public boolean visibility() {
		return visibility;
	}

	@Override
	public ButtonActionType target() {
		return getTarget();
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String id() {
		return id;
	}
	
	public static BuildUIButton me(){
		return new BuildUIButton();
	}
	
	public BuildUIButton withUrl(String url){
		this.setUrl(url);
		return this;
	}
	public BuildUIButton withVisibility(boolean visibility){
		this.setVisibility(visibility);
		return this;
	}
	
	public BuildUIButton withVisibilityTrue(){
		this.setVisibility(true);
		return this;
	}
	public BuildUIButton withTarget(ButtonActionType target){
		this.setTarget(target);
		return this;
	}
	
	public BuildUIButton withTargetModal(){
		this.setTarget(ButtonActionType.MODAL);
		return this;
	}
	public BuildUIButton withLabel(String label){
		this.setLabel(label);
		return this;
	}
	public BuildUIButton withID(String id){
		this.setId(id);
		return this;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public boolean isVisibility() {
		return visibility;
	}


	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}


	public ButtonActionType getTarget() {
		return target;
	}


	public void setTarget(ButtonActionType target) {
		this.target = target;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
}
