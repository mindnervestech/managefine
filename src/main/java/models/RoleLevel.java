package models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import play.db.ebean.Model;

import com.avaje.ebean.Expr;
import com.custom.domain.RoleLevels;

@Entity
public class RoleLevel extends Model{

	public static final long serialVersionUID = 6992202576999577853L;

	@Id
	public Long id;
	
	@Version
	public Timestamp lastUpdate;
	
	@ManyToOne
	public RoleX roleX;
	
	@Enumerated(EnumType.ORDINAL)
	public RoleLevels role_level;
		
	public String role_name;
	
	public String reporting_to;
	
	public String final_approval;
	
	@Column(length=700)
	public String permissions;
	
	public String getPermissions(){
		return permissions;
	}
	
	public void setPermissions(String permissions){
		this.permissions = permissions;
	}
	
	public static Model.Finder<Long, RoleLevel> find = new Model.Finder<Long,RoleLevel>(Long.class, RoleLevel.class);
    
	public static RoleLevel findById(Long id){
		return RoleLevel.find.byId(id);
	}
	
	public static RoleLevel findByUser(Long id, String pm) {
        return find.where().add(Expr.eq("roleX", RoleX.findByCompany(User.findById(id).companyobject.id))).findUnique();
    }
	
	public static List<RoleLevel> findListByCompany(Long id) {
		return find.where().add(Expr.eq("roleX",RoleX.find.where().eq("company", Company.find.byId(id)).findUnique())).findList();
    }
	
	public static RoleLevel findByCompany(Long id) {
		return find.where().add(Expr.eq("roleX",RoleX.find.where().eq("company", Company.find.byId(id)).findUnique())).findUnique();
    }
	
	
	
	public static boolean checkUserLevel(Long id,RoleLevels currentLevel){
		RoleX roleX = RoleX.find.where(Expr.eq("company", User.findById(id).companyobject)).findUnique();
		for(RoleLevel level : roleX.getRoleLevels()){
			if(level.role_level.ordinal() >= currentLevel.ordinal()){
				return true;
			}
		}
		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public RoleX getRoleX() {
		return roleX;
	}

	public void setRoleX(RoleX roleX) {
		this.roleX = roleX;
	}

	public RoleLevels getRole_level() {
		return role_level;
	}

	public void setRole_level(RoleLevels role_level) {
		this.role_level = role_level;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getReporting_to() {
		return reporting_to;
	}

	public void setReporting_to(String reporting_to) {
		this.reporting_to = reporting_to;
	}

	public String getFinal_approval() {
		return final_approval;
	}

	public void setFinal_approval(String final_approval) {
		this.final_approval = final_approval;
	}
	public RoleLevel(){}
	
}
