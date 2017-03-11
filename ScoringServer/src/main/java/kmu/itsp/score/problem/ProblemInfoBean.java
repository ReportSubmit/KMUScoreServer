package kmu.itsp.score.problem;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;
/**
 * 문제에 대한 정보를 담고 있는 bean 클래스
 * @author WJ
 *
 */
public class ProblemInfoBean {
	/**
	 * 과목 번호
	 */
	private int projectIdx;
	/**
	 * 문제 번호
	 */
	private int problemIdx;
	/**
	 * 문제 제목
	 */
	private String problemName;
	/**
	 * 문제 내용
	 */
	private String problemContents;
	/**
	 * 제한 시간
	 */
	private Timestamp limitTime;
	/**
	 * 각 테스트 케이스들
	 */
	private String[] inputValue;
	/**
	 * 문제 소스코드 파일
	 */
	private MultipartFile sourceFile;
	
	public String[] getInputValue() {
		return inputValue;
	}
	
	public void setInputValue(String[] inputValue) {
		this.inputValue = inputValue;
	}
	
	public String getProblemName() {
		return problemName;
	}
	
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
	
	public int getProjectIdx() {
		return projectIdx;
	}
	
	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}
	
	public MultipartFile getSourceFile() {
		return sourceFile;
	}
	
	public void setSourceFile(MultipartFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	public int getProblemIdx() {
		return problemIdx;
	}

	public void setProblemIdx(int problemIdx) {
		this.problemIdx = problemIdx;
	}

	public String getProblemContents() {
		return problemContents;
	}

	public void setProblemContents(String problemContents) {
		this.problemContents = problemContents;
	}

	public Timestamp getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Timestamp limitTime) {
		this.limitTime = limitTime;
	}
}
