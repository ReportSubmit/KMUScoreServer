package kmu.itsp.score.scoring;

import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.util.ScoreUtil;

public class CompileResultBean {
	private int status;
	private String uuid;
	private String message;
	private String errorMsg;
	private String successMsg;
	
	public CompileResultBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CompileResultBean(int status, String uuid) {
		// TODO Auto-generated constructor stub
		this.status = status;
		this.uuid = uuid;
	}
	
	
	public void setComplieResultByStatus(String successMsg,String errorMsg) {

		switch (this.getStatus()) {
		case IProcessService.COMPILE_SUCCESS:
			this.setSuccessMsg(successMsg);
			this.setMessage("실행됐어요(Success)");
			this.setErrorMsg("");

			break;
		case IProcessService.EXEC_ERROR:
			this.setSuccessMsg("");
			this.setMessage("실행에 실패했어요(Excution Fail)");
			this.setErrorMsg(errorMsg);
			break;
		case IProcessService.COMPILE_ERROR:
			this.setSuccessMsg("");
			this.setMessage("컴파일에 실패했어요(Complie Error)");
			this.setErrorMsg(errorMsg);
			break;
		case IProcessService.EXEC_TIMEOUT:
			this.setSuccessMsg("");
			this.setMessage("너무 오래걸려요(Time Out)");
			this.setErrorMsg("");

			break;
		case IProcessService.NOINPUT_ERROR:
			this.setSuccessMsg("");
			this.setMessage("입력란에 값이 없어요(NO INPUT)");
			this.setErrorMsg("");

			break;

		default:
			break;
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	
	
}
