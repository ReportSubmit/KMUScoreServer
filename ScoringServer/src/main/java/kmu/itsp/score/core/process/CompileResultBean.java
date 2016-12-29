package kmu.itsp.score.core.process;

import java.io.File;


/**
 * Compile 시 결과값을 저장하는 클래스
 * @author WJ
 *
 */
public class CompileResultBean {
	private int status;
	private File excuteFile;
	private String errorMsg;
	private String successMsg;
	
	public CompileResultBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CompileResultBean(int status, String fileName, String filePath) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.excuteFile = new File(filePath+fileName);
		
	}
	
	public File getExcuteFile() {
		return excuteFile;
	}
	public void setExcuteFile(File excuteFile) {
		this.excuteFile = excuteFile;
	}
		
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSuccessMsg() {
		return successMsg;
	}
	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	/**
	 * 실행 프로그램을 GC가 실행될 때 컴퓨터 내부에서 제거
	 * 
	 */
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		if(this.excuteFile != null && this.excuteFile.exists()){
			this.excuteFile.delete();
		}
	}
	
}
