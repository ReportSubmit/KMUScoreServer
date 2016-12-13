package kmu.itsp.score.scoring;

import org.springframework.web.multipart.MultipartFile;

public class ScoringRequestInfoBean {

	private Integer problemIdx;
	private Integer projectIdx;
	private MultipartFile sourceFile;

	public Integer getProblemIdx() {
		return problemIdx;
	}

	public void setProblemIdx(Integer problemIdx) {
		this.problemIdx = problemIdx;
	}

	public MultipartFile getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(MultipartFile sourceFile) {
		this.sourceFile = sourceFile;
	}

	public Integer getProjectIdx() {
		return projectIdx;
	}

	public void setProjectIdx(Integer projectIdx) {
		this.projectIdx = projectIdx;
	}
}
