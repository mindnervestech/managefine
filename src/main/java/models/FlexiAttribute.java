package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class FlexiAttribute extends Model{

	private static Model.Finder<Long, FlexiAttribute> find = new Model.Finder<Long,FlexiAttribute>(Long.class, FlexiAttribute.class);

	@Id
	private Long id;
	
	private String model;
	
	private String name;
	
	private String type;
	
	private Long uniqueid;
	
	@Transient
	private Object value;
	
	@Transient
	private Long modelId;
	
	@JsonIgnore
	public static List<FlexiAttribute> getFieldsByModel(Class model) {
		return find.where().eq("model", model.getName()).findList();
	}

	public static List<FlexiAttribute> getFieldsByUniqueId(Long uniqueid) {
		return find.where().eq("uniqueid", uniqueid).findList();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public static FlexiAttribute getById(Long flexiId) {
		return find.byId(flexiId);
		
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}


	public Long getModelId() {
		return modelId;
	}


	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	
	public String getValueAsJson() {
		if(value == null) return "";
		return Json.toJson(value).toString();
	}


	public Long getUniqueid() {
		return uniqueid;
	}


	public void setUniqueid(Long uniqueid) {
		this.uniqueid = uniqueid;
	}
}
