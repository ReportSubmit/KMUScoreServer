package kmu.itsp.score.export;

public class ScoreExportBean {
	String probNum;
	int score;
	
	public ScoreExportBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ScoreExportBean(String probNum, int score) {
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
