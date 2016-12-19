package kmu.itsp.score.scoring;

import org.springframework.web.multipart.MultipartFile;

public class ScoringRequestInfoBean {

	private Integer problemIdx;
	private Integer complierIdx;
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

	public Integer getComplierIdx() {
		return complierIdx;
	}

	public void setComplierIdx(Integer complierIdx) {
		this.complierIdx = complierIdx;
	}
}
