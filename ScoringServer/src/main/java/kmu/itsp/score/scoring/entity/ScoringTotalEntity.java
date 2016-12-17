package kmu.itsp.score.scoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

@Entity
@Table(name="tb_result")
@SecondaryTables(value = {
		@SecondaryTable(name = "tb_answer"),
		@SecondaryTable(name = "tb_input")})
public class ScoringTotalEntity implements Serializable {

	private static final long serialVersionUID = 8440667824568220990L;

	
	@Column(name = "user_idx", nullable = false)
	private Integer userIdx;
	
	@Id
	@Column(name = "problem_idx", nullable = false)
	private Integer problemIdx;
	
	@Id
	@Column(name = "no", nullable = false)
	private Integer scoreNo;
	
	@Column(name = "score", nullable = false)
	private Integer score;

	@Column(table = "tb_input", name = "input", nullable = false)
	private String input;
	
	@Column(table = "tb_answer", name = "answer", nullable = false)
	private String answer;

	public Integer getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(Integer userIdx) {
		this.userIdx = userIdx;
	}

	public Integer getProblemIdx() {
		return problemIdx;
	}

	public void setProblemIdx(Integer problemIdx) {
		this.problemIdx = problemIdx;
	}

	public Integer getScoreNo() {
		return scoreNo;
	}

	public void setScoreNo(Integer scoreNo) {
		this.scoreNo = scoreNo;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof ScoringTotalEntity))
			return false;

		final ScoringTotalEntity entity = (ScoringTotalEntity) obj;
		if (entity.getProblemIdx() != getProblemIdx()) {
			return false;
		}
		if (entity.getScoreNo() != getScoreNo()) {
			return false;
		}
		if (entity.getUserIdx() != getUserIdx()) {
			return false;
		}
		
		return true;
	}

	
}
