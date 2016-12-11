package kmu.itsp.score.core.process;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kmu.itsp.score.scoring.temp.CompileResultBean;

public interface IProcessService {
	public static final int EXEC_SUCCESS = 101;
	public static final int EXEC_TIMEOUT = 102;
	public static final int EXEC_ERROR = 103;
	
	public static final int COMPILE_ERROR = 201;
	public static final int COMPILE_SUCCESS = 202;
	
	public static final int NOINPUT_ERROR = 301;
	
	
	int excuteCommand(List<String> command,int limitTime);
	List<String> createCommand(String... args);
	String getError();
	String getSuccessResult();
	int getReturnValue();
	void setReturnValue(int returnValue);
	CompileResultBean complie(File complieFile) throws IOException;
	int excuteCommand(List<String> command, File inputFiile, int limitTime);
	int excuteCommand(List<String> command, String input, int limitTime);
	String getExcuteDirPath();
	String getInputDirPath();
	String getUploadCodeDirPath();
	String getAnswerFileDirPath();
	String getAnswerInputDirPath();
	String getAnswerSolveDirPath();
	int runExcuteFile(File inputFile, String fileName, String directory);
	int runExcuteFile(String input, String fileName, String directory);
	int runComplier(File compileFile, String uuid) throws IOException;
	
}
