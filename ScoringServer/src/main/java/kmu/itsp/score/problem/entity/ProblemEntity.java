package kmu.itsp.score.problem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_problem")
public class ProblemEntity implements Serializable {

	private static final long serialVersionUID = 4651201456006408419L;

	@Id
	@Column(name = "project_idx", nullable=false)
	private Integer projectIdx;

	@Id
	@Column(name = "problem_idx", nullable=false, unique=true)
	private Integer problemIdx ;
	
	@Column(name = "problem_name", nullable=false)
	private String problemName;
	
	@Column(name = "problem_contents", nullable=false)
	private String problemContents;

	public ProblemEntity() {
		// TODO Auto-generated constructor stub
		problemIdx = new Integer(0);
	}
	
	public Integer getProblemIdx() {
		return problemIdx;
	}
	
	public void setProblemIdx(Integer problemIdx) {
		this.problemIdx = problemIdx;
	}

	public int getProjectIdx() {
		return projectIdx;
	}

	public void setProjectIdx(Integer projectIdx) {
		this.projectIdx = projectIdx;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public String getProblemContents() {
		return problemContents;
	}
	public void setProblemContents(String problemContents) {
		this.problemContents = problemContents;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == this) return true;
		if(obj == null || !(obj instanceof ProblemEntity))
		        return false;
		
		final ProblemEntity entity = (ProblemEntity) obj;
		if(entity.getProblemIdx() != getProblemIdx()){
			return false;
		}
		if(entity.getProjectIdx() != getProjectIdx()){
			return false;
		}
	
		return true;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return projectIdx * 10 + problemIdx * 100 + problemName.hashCode();
	}
}
