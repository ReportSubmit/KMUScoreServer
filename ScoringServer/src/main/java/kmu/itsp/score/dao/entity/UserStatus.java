package kmu.itsp.score.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_user_stat")
public class UserStatus implements Serializable{
	
	
	private static final long serialVersionUID = 4833842087426604902L;
	public static final int PERMISSION_ADMIN = 111;
	public static final int PERMISSION_USER = 222;
	
	@Id
	@Column(name="uid",unique=true ,nullable=false)
	private String userId;
	@Id
	@Column(name="project", nullable=false)
	private String project;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="permission", nullable=false)
	private int permission; // 0 admin, 1 student
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == this) return true;
		if(obj == null || !(obj instanceof UserStatus))
		        return false;
		
		final UserStatus userStatus = (UserStatus) obj;
		if(userStatus.getUserId() != getUserId()){
			return false;
		}
		if(!userStatus.getProject().equals(getProject())){
			return false;
		}
		if(userStatus.getPermission() != getPermission()){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getUserId().hashCode() + getProject().hashCode() + getPermission()*19;
	}
	
}
