package kmu.itsp.score.core.process;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IProcessService {
	public static final int EXEC_SUCCESS = 101;
	public static final int EXEC_TIMEOUT = 102;
	public static final int EXEC_ERROR = 103;
	public static final int COMPILE_ERROR = 201;
	public static final int COMPILE_SUCCESS = 202;

	List<String> createCommand(String... args);
	String getError();
	String getSuccessResult();
	int getReturnValue();
	void setReturnValue(int returnValue);
	CompileResultBean complie(File complieFile) throws IOException;
	int excuteCommand(List<String> command, int limitTime, File inputFiile);
	int excuteCommand(List<String> command, String input, int limitTime);
	int runComplier(File compileFile, String uuid) throws IOException;
	int runExcuteFile(File inputFile, String filePath);
	int runExcuteFile(String input, String filePath);
	
}
