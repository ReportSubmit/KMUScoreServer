package kmu.itsp.score.ctrl.bean;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Named("ScoringInfoBean")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScoringInfoBean {
	public static final int GCC_COMPLIER = 101;
	public static final int JAVA_COMPLIER = 102;
	
	private String userId;
	private int probNo;
	private String project;
	private String fileName;
	private int complieLanguage;
	private MultipartFile uploadFile;
	private int limitTime = 3;
	
	public int getComplieLanguage() {
		return complieLanguage;
	}
	public void setComplieLanguage(int complieLanguage) {
		this.complieLanguage = complieLanguage;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	
	public int getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}

	public int getProbNo() {
		return probNo;
	}

	public void setProbNo(int probNo) {
		this.probNo = probNo;
	}
}
