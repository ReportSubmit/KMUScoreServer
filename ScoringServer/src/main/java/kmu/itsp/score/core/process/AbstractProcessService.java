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

import kmu.itsp.score.core.util.IOCopy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractProcessService implements IProcessService {
	@Autowired
	@Qualifier(value = "IOCopy")
	protected IOCopy successCopy;
	@Autowired
	@Qualifier(value = "IOCopy")
	protected IOCopy errorCopy;
	protected int returnValue;

	public AbstractProcessService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return errorCopy.getResult();
	}

	@Override
	public int getReturnValue() {
		// TODO Auto-generated method stub
		return returnValue;
	}

	@Override
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public String getSuccessResult() {
		// TODO Auto-generated method stub
		return successCopy.getResult();
	}

	public boolean checkStatus(int status) {
		// TODO Auto-generated method stub
		if (status == EXEC_SUCCESS) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> createCommand(String... args) {
		// TODO Auto-generated method stub
		List<String> commands = Arrays.asList(args);
		return commands;
	}

	@Override
	public int excuteCommand(List<String> command, int limitTime) {
		// TODO Auto-generated method stub
		ProcessBuilder pb = new ProcessBuilder(command);

		return runProcess(pb, limitTime);

	}

	@Override
	public int excuteCommand(List<String> command, String input, int limitTime) {
		// TODO Auto-generated method stub
		File file = null;
		ProcessBuilder pb = new ProcessBuilder(command);
		try {
			file = File.createTempFile("temp", UUID.randomUUID()+"");
			FileWriter writer = new FileWriter(file);
			System.out.println(input);
			writer.write(input);
			writer.flush();
			pb.redirectInput(file);
			System.out.println(file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		int status = runProcess(pb, limitTime);
		
		if(file.exists()){
			file.delete();
		}
		
		return status;

	}

	@Override
	public int excuteCommand(List<String> command, File inputFile, int limitTime) {
		// TODO Auto-generated method stub

		if (inputFile == null || !inputFile.exists()) {
			return IProcessService.NOINPUT_ERROR;
		}
		ProcessBuilder pb = new ProcessBuilder(command)
				.redirectInput(inputFile);

		return runProcess(pb, limitTime);
	}

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
					successCopy.setResult(getStreamString(processObj
							.getInputStream()));
					errorCopy.setResult(getStreamString(processObj
							.getErrorStream()));
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
				if (getError()!= null && getError().length() > 0) {
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

	
	private String getStreamString(InputStream is) throws IOException {

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
