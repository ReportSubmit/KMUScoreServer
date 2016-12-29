package kmu.itsp.score.problem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProblemEntity
 * DB table tb_input의 entity이다.
 * @author WJ
 *
 */

@Entity
@Table(name="tb_input")
public class ProblemInputEntity implements Serializable{
	
	private static final long serialVersionUID = -8011115418749166701L;
	
	@Id
	@Column(name = "problem_idx", nullable=false, unique=true)
	private int problemIdx;
	@Id
	@Column(name = "no", nullable=false, unique=true)
	private int inputNo;
	@Column(name = "input", nullable=false)
	private String input;
	
	public int getProblemIdx() {
		return problemIdx;
	}
	public void setProblemIdx(int problemIdx) {
		this.problemIdx = problemIdx;
	}
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
	public int getInputNo() {
		return inputNo;
	}
	public void setInputNo(int inputNo) {
		this.inputNo = inputNo;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return problemIdx*10+inputNo*100+input.hashCode();
	}
}
