package kmu.itsp.score.admin.ctrl;

import org.springframework.web.multipart.MultipartFile;

public class ProblemInfoBean {
	private int projectIdx;
	private int problemIdx;
	private String problemName;
	private String problemContents;
	private String[] inputValue;
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
}
