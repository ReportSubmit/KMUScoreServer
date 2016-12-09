package kmu.itsp.score.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_answer")
public class AnswerEntity implements Serializable {

	private static final long serialVersionUID = -3203098776014829699L;

	@Id
	@Column(name = "problem_idx", nullable = false, unique = true)
	private Integer problemIdx;
	@Id
	@Column(name = "no", nullable = false, unique = true)
	private Integer answerNo;
	@Column(name = "answer_line", nullable = false)
	private Integer answerLineNum;
	@Column(name = "answer", nullable = false)
	private String answer;

	public AnswerEntity() {
		// TODO Auto-generated constructor stub
	}

	public Integer getProblemIdx() {
		return problemIdx;
	}

	public void setProblemIdx(Integer problemIdx) {
		this.problemIdx = problemIdx;
	}

	public Integer getAnswerNo() {
		return answerNo;
	}

	public void setAnswerNo(Integer answerNo) {
		this.answerNo = answerNo;
	}

	public Integer getAnswerLineNum() {
		return answerLineNum;
	}

	public void setAnswerLineNum(Integer answerLineNum) {
		this.answerLineNum = answerLineNum;
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
		if (obj == null || !(obj instanceof AnswerEntity))
			return false;

		final AnswerEntity entity = (AnswerEntity) obj;
		if (entity.getProblemIdx() != getProblemIdx()) {
			return false;
		}
		if (entity.getAnswerNo() != getAnswerNo()) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return answerNo * 10 + problemIdx * 100 + answer.hashCode();
	}

}
