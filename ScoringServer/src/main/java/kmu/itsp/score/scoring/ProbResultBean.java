package kmu.itsp.score.scoring;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.util.ScoreUtil;

public class ProbResultBean {
	private int status;
	private int isRight;
	private String input;
	private List<String> answerList;
	private String message;
	private String successMsg;

	public ProbResultBean() {
		// TODO Auto-generated constructor stub
		setIsRight(0);
		input = "";
		setAnswerList(new ArrayList<String>());
		message = "";
		successMsg = "";

	}

	public boolean compareResultToAnswer(String result, int correctLine) {
		// answer and result compare
		StringTokenizer tokenizer = new StringTokenizer(result, "(), \t\r\n");

		int matchCount = 0;

		int tokenSize = tokenizer.countTokens();

		//System.out.println("TokenSize :"+ tokenSize);
		// System.out.println("AnswerListSize :"+probBean.getAnswerList().size());

		for (int j = 0; j < tokenSize; j++) {
			if (this.getAnswerList().size() <= j) {
				matchCount = -1;
				break;
			}
			String token = tokenizer.nextToken();
			// if String value is number
			if (ScoreUtil.isStringDouble(token.trim())) {
				token = ScoreUtil.cutDigitInDouble(token.trim());
			}

			//System.out.println("token : " + token);
			//System.out.println("prob : " + this.getAnswerList().get(j));

			if (token.equals(this.getAnswerList().get(j))) {
				matchCount++;
			} else {
				break;
			}
		}
		//
		if (matchCount == correctLine) {
			return true;
		} else {
			return false;
		}
	}

	public int setSubListToAnswerList(int currentLine, List<String> answerList) {
		StringTokenizer ansTokenizer = new StringTokenizer(
				answerList.get(currentLine), " \r\n");
		int ansNum = Integer.valueOf(ansTokenizer.nextToken());
		int nextLine = Integer.valueOf(ansTokenizer.nextToken());
		int start = currentLine + 1;
		int end = start + nextLine;
		//System.out.println(start + ": "+ end );
		this.setAnswerList(answerList.subList(start, end));

		return end;
	}

	public void setProbResultByStatus(String successMsg, String errorMsg) {
		switch (this.getStatus()) {
		case IProcessService.EXEC_SUCCESS:
			this.setMessage("실행됐어요(Success)");
			this.setSuccessMsg(successMsg);
			break;
		case IProcessService.EXEC_ERROR:
			this.setMessage("실행에 실패했어요(Excution Fail)");
			break;
		case IProcessService.EXEC_TIMEOUT:
			this.setMessage("너무 오래걸려요(Time Out)");
			break;
		case IProcessService.NOINPUT_ERROR:
			this.setMessage("입력란에 값이 없어요(NO INPUT)");
			break;

		default:
			break;
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}

	public int getIsRight() {
		return isRight;
	}

	public void setIsRight(int isRight) {
		this.isRight = isRight;
	}

}
