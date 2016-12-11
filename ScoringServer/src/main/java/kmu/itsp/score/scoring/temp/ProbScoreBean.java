package kmu.itsp.score.scoring.temp;

public class ProbScoreBean {
	String probNum;
	int score;
	
	public ProbScoreBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ProbScoreBean(String probNum, int score) {
		// TODO Auto-generated constructor stub
		setProbNum(probNum);
		setScore(score);
	}

	public String getProbNum() {
		return probNum;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setProbNum(String probNum) {
		this.probNum = probNum;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
