package models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.Expr;

import play.db.ebean.Model;

@Entity
public class LeaveLevel extends Model{
	
	public static final long serialVersionUID = 6992202576999577853L;
	@Id
	public Long id;
	
	public String leave_type;
		
	public String carry_forward;

	@Version
	public Timestamp lastUpdate;
	
	@ManyToOne
	public LeaveX leaveX;
	
	@OneToOne
	public RoleLeave roleLeave;

	public RoleLeave getRoleLeave() {
		return roleLeave;
	}

	public void setRoleLeave(RoleLeave roleLeave) {
		this.roleLeave = roleLeave;
	}

	public LeaveX getLeaveX() {
		return leaveX;
	}

	public void setLeaveX(LeaveX leaveX) {
		this.leaveX = leaveX;
	}
	public static Model.Finder<Long, LeaveLevel> find = new Model.Finder<Long,LeaveLevel>(Long.class, LeaveLevel.class);
    
	public static LeaveLevel findById(Long id){
		return LeaveLevel.find.byId(id);
	}
	
	public static LeaveLevel findByUser(Long id, String pm) {
        return find.where().add(Expr.eq("leaveX", LeaveX.findByCompany(User.findById(id).getCompanyobject().getId()))).findUnique();
    }
	
	public static List<LeaveLevel> findListByCompany(Long id) {
		return find.where().add(Expr.eq("leaveX",LeaveX.find.where().eq("company", Company.find.byId(id)).findUnique())).findList();
    }
	
	public static LeaveLevel findByCompany(Long id) {
		return find.where().add(Expr.eq("leaveX",LeaveX.find.where().eq("company", Company.find.byId(id)).findUnique())).findUnique();
    }

	public String getLeave_type() {
		return leave_type;
	}

	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}

	public String getCarry_forward() {
		return carry_forward;
	}

	public void setCarry_forward(String carry_forward) {
		this.carry_forward = carry_forward;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public LeaveLevel(){}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
