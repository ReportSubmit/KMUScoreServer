package kmu.itsp.score.scoring;

import org.springframework.web.multipart.MultipartFile;
/**
 * 채점에 필요한 정보를 담고 있는 클래스 
 * @author WJ
 *
 */
public class ScoringRequestInfoBean {

	private Integer problemIdx;
	private Integer compilerIdx;
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

	public Integer getCompilerIdx() {
		return compilerIdx;
	}

	public void setCompilerIdx(Integer complierIdx) {
		this.compilerIdx = complierIdx;
	}
}
