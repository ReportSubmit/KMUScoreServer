package kmu.itsp.score.core.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 원하는 프로그램을 실행시키는 클래스
 * 
 * @author WJ
 *
 */

public abstract class AbstractProcessService implements IProcessService {

	/**
	 * 실행 성공시 결과값을 담는 필드
	 */
	protected String successResult;

	/**
	 * 실행 실패시 결과값을 담는 필드
	 */
	protected String errorResult;

	/**
	 * 실행 성공시 return 값을 담는 필드
	 */
	protected int returnValue;

	public AbstractProcessService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 실행 실패시 결과를 담고있는 erroyCopy에서 그 결과를 문자열로 반환하는 메소드
	 * 
	 * @return {@link String} or null
	 * 
	 */
	@Override
	public String getError() {
		// TODO Auto-generated method stub

		return errorResult;

	}

	/**
	 * 실행 성공시 return 값이 저장된 필드 returnValue의 값을 반환하는 메소드
	 * 
	 * @return returnValue
	 */
	@Override
	public int getReturnValue() {
		// TODO Auto-generated method stub
		return returnValue;
	}

	/**
	 * 실행 성공시 얻은 return 값을 클래스의 returnValue 필드에 저장한다.
	 * 
	 * @param returnValue
	 *            : {@link Integer}- 실행 성공시 return 값
	 */
	@Override
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	/**
	 * 실행 성공시 결과를 담고있는 successCopy에서 그 결과를 문자열로 반환하는 메소드
	 * 
	 * @return {@link String} or null
	 * 
	 */
	@Override
	public String getSuccessResult() {
		// TODO Auto-generated method stub

		return successResult;

	}

	/**
	 * 실행시 성공인지 실패인지 확인하는 메소드
	 * 
	 * @param status
	 *            - 프로그램을 실행 후 상태값
	 * @return true or false
	 */
	public boolean checkStatus(int status) {
		// TODO Auto-generated method stub
		if (status == EXEC_SUCCESS) {
			return true;
		}
		return false;
	}

	/**
	 * 프로그램을 실행하기 위한 명령문을 만드는 메소드
	 * 
	 * @param args
	 *            - 수가 정해지지 않은 문자열들
	 * @return commands : {@link List} - 명령문
	 */
	@Override
	public List<String> createCommand(String... args) {
		// TODO Auto-generated method stub
		List<String> commands = Arrays.asList(args);
		return commands;
	}

	/**
	 * 프로그램을 실행하기전에 인풋이나 제한시간 명령문을 세팅하는 메소드
	 * 
	 * @param command
	 *            : {@link List} - 명령문
	 * @param input
	 *            : {@link String} - 리다이렉션 input 문자열
	 * @param limitTime
	 *            : {@link Integer} - 프로그램의 최대 실행 시간
	 * @return status - 실행 후 상태 값
	 * 
	 */
	@Override
	public int executeCommand(List<String> command, String input, int limitTime) {
		// TODO Auto-generated method stub
		File file = null;
		ProcessBuilder pb = new ProcessBuilder(command);

		if (input != null && !(input.trim().equals(""))) {
			try {
				file = File.createTempFile("temp", UUID.randomUUID() + "");
				FileWriter writer = new FileWriter(file);
				System.out.println(input);
				writer.write(input);
				writer.flush();
				pb.redirectInput(file);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				if (file != null && file.exists()) {
					file.delete();
				}
			}
		}

		int status = runProcess(pb, limitTime);

		return status;

	}

	/**
	 * 프로그램을 실행하기전에 인풋이나 제한시간 명령문을 세팅하는 메소드
	 * 
	 * @param command
	 *            : {@link List} - 명령문
	 * @param inputFile
	 *            : {@link File} - 리다이렉션 input 문자열
	 * @param limitTime
	 *            : {@link Integer} - 프로그램의 최대 실행 시간
	 * @return status - 실행 후 상태 값
	 */
	@Override
	public int executeCommand(List<String> command, int limitTime,
			File inputFile) {
		// TODO Auto-generated method stub
		ProcessBuilder pb = new ProcessBuilder(command);
		if (inputFile != null && inputFile.exists()) {
			pb.redirectInput(inputFile);
		}

		return runProcess(pb, limitTime);
	}

	/**
	 * 프로그램을 실행하기전에 인풋이나 제한시간 명령문을 세팅하는 메소드
	 * 
	 * @param pb
	 *            : {@link ProcessBuilder} - 프로그램을 실행하고 process 객체를 반환하는 클래스
	 * @param limitTime
	 *            : {@link Integer} - 프로그램의 최대 실행 시간
	 * 
	 * @return status - 실행 후 상태 값 / EXEC_SUCCESS,EXEC_TIMEOUT,EXEC_ERROR
	 * @exception InterruptedException
	 *                ,IOException
	 * 
	 */
	private int runProcess(ProcessBuilder pb, int limitTime) {
		Process processObj;

		try {
			processObj = pb.start();

			try {
				if (!processObj.waitFor(limitTime, TimeUnit.SECONDS)) {
					processObj.getInputStream().close();
					processObj.getErrorStream().close();
					processObj.getOutputStream().close();

					return EXEC_TIMEOUT;
				} else {
					successResult = getStreamString(processObj.getInputStream());
					errorResult = getStreamString(processObj.getErrorStream());
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return EXEC_ERROR;
			} finally {
				// setReturnValue(processObj.exitValue());

				if (processObj.isAlive()) {
					processObj.destroyForcibly();
				}
				if (getError() != null && getError().length() > 0) {
					return EXEC_ERROR;
				}
			}

			return EXEC_SUCCESS;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			return EXEC_ERROR;
		}
	}

	/**
	 * 
	 * @param inputFile
	 *            - 실행파일에 입력될 inputFile
	 * @param filePath
	 *            - 실행 파일 위치
	 * 
	 */
	@Override
	public int runExecutableFile(File inputFile, String filePath) {
		int status;
		// status == AbstractComplier.SUCCESS
		List<String> execCommands = createCommand(filePath);

		status = executeCommand(execCommands, 2, inputFile);

		return status;
	}

	/**
	 * 
	 */
	@Override
	public int runExecutableFile(String input, String filePath) {
		// TODO Auto-generated method stub
		int status;
		List<String> execCommands = createCommand(filePath);

		status = executeCommand(execCommands, input, 2);

		return status;
	}

	/**
	 * 프로그램을 실행하기전에 인풋이나 제한시간 명령문을 세팅하는 메소드
	 * 
	 * @param is
	 *            : {@link InputStream}
	 * @return {@link String} - InputStream로부터 읽은 값을 문자열로 반환
	 * @throws IOException InputStream 사용에 문제 될 시 에러 발생
	 */
	public static String getStreamString(InputStream is) throws IOException {

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new InputStreamReader(is));

			StringBuffer out = new StringBuffer();

			int stdLine;

			while ((stdLine = reader.read()) != -1) {
				out.append((char) stdLine);
			}

			if (out.toString() == null) {
				return "";
			}

			return out.toString();

		} finally {

			if (reader != null) {

				try {

					reader.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

	}

}
