package kmu.itsp.score.scoring.temp;

import java.util.ArrayList;

import javax.inject.Named;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Named("ScoreResultBean")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScoreResultBean extends CompileResultBean{
	private int score;
	private int numOfProbSize;
	private ArrayList<ProbResultBean> probList;
	
	public ScoreResultBean() {
		// TODO Auto-generated constructor stub
		probList = new ArrayList<ProbResultBean>();
	}
	
	public ArrayList<ProbResultBean> getProbList() {
		return probList;
	}
	
	public int getNumOfProbSize() {
		return numOfProbSize;
	}
	
	public void setNumOfProbSize(int numOfProbSize) {
		this.numOfProbSize = numOfProbSize;
	}
	
	public int getScore() {
		return score;
	}
	//calculate
	public void calculateScore(){
		int score = 0;
		for (int i = 0; i < this.getNumOfProbSize(); i++) {
			if (this.getProbList().get(i).getIsRight() == 1) {
				score++;
			}
		}
		this.setScore(score * 100 / this.getNumOfProbSize());
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	

}
