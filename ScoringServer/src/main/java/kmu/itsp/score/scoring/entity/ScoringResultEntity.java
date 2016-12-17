package kmu.itsp.score.scoring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_result")
public class ScoringResultEntity implements Serializable {

	private static final long serialVersionUID = -3203098776014829699L;

	@Id
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

	public ScoringResultEntity() {
		// TODO Auto-generated constructor stub
	}

	
	
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

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof ScoringResultEntity))
			return false;

		final ScoringResultEntity entity = (ScoringResultEntity) obj;
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
