package kmu.itsp.score.scoring;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScoringResultBean{
	private int no;
	private boolean isRight;
	private String input;
	private int score;
	private String msg;
	private List<String> answerList;
	private List<String> resultList;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public boolean isRight() {
		return isRight;
	}
	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public List<String> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
		

	
	
}
