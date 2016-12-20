package kmu.itsp.score.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_project")
public class ProjectEntity implements Serializable {

	private static final long serialVersionUID = 4651201456006408419L;

	@Id
	@Column(name = "project_idx", nullable=false)
	private int projectIdx;
	
	@Column(name = "project_name", nullable=false)
	private String projectName;

	public int getProjectIdx() {
		return projectIdx;
	}

	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}

	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == this) return true;
		if(obj == null || !(obj instanceof ProjectEntity))
		        return false;
		
		final ProjectEntity entity = (ProjectEntity) obj;
		
		if(entity.getProjectIdx() != getProjectIdx()){
			return false;
		}
	
		return true;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (int) (projectIdx * 10 + projectName.hashCode());
	}
}
