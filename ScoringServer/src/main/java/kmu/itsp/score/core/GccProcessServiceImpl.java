package kmu.itsp.score.core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import kmu.itsp.score.core.bean.CompileResultBean;
import kmu.itsp.score.core.inter.AbstractProcessService;
import kmu.itsp.score.core.inter.IProcessService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "GccProcessService")
public class GccProcessServiceImpl extends AbstractProcessService {
	private @Value("${path.gcc_dir_path}") String excuteDirPath;
	private @Value("${path.input_dir_path}") String inputDirPath;
	private @Value("${path.c_dir_path}") String uploadCodeDirPath;
	private @Value("${path.answer_file_dir_path}") String answerFileDirPath;
	private @Value("${path.answer_solve_dir_path}") String answerSolveDirPath;
	private @Value("${path.answer_input_dir_path}") String answerInputDirPath;
	
	
	@Override
	public String getExcuteDirPath() {
		return excuteDirPath;
	}
	@Override
	public String getInputDirPath() {
		return inputDirPath;
	}
	@Override
	public String getUploadCodeDirPath() {
		return uploadCodeDirPath;
	}
	@Override
	public String getAnswerFileDirPath() {
		return answerFileDirPath;
	}
	@Override
	public String getAnswerSolveDirPath() {
		return answerSolveDirPath;
	}
	@Override
	public String getAnswerInputDirPath() {
		// TODO Auto-generated method stub
		return answerInputDirPath;
	}
	
	

	public GccProcessServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompileResultBean complie(File compileFile) throws IOException {
		// TODO Auto-generated method stub
		
		String uuid = UUID.randomUUID().toString();
		int status=runComplier(compileFile, uuid);
		
		if (!checkStatus(status)) {
			status = IProcessService.COMPILE_ERROR;
		}else{
			status = IProcessService.COMPILE_SUCCESS;
		}
		
		return new CompileResultBean(status, uuid);

	}
	@Override
	public int runExcuteFile(File inputFile, String fileName, String directory){
		int status;
		// status == AbstractComplier.SUCCESS
		List<String> execCommands = createCommand(directory + fileName);
		if(inputFile != null && inputFile.exists()){
			status = excuteCommand(execCommands, inputFile, 2);
		}else{
			status = excuteCommand(execCommands, 2);
		}
		return status;
	}
	
	@Override
	public int runExcuteFile(String input, String fileName, String directory) {
		// TODO Auto-generated method stub
		int status;
		List<String> execCommands = createCommand(directory + fileName);
		if(input == null || input.trim().equals("")){
			status = excuteCommand(execCommands, 2);
		}else{
			status = excuteCommand(execCommands, input, 2);
		}
		return status;
	}
	@Override
	public int runComplier(File compileFile, String uuid) throws IOException{
		File directory = new File(uploadCodeDirPath);
		

		// 저장하는 파일에 random 추가할 것
		File destFile = File.createTempFile("temp_", ".c", directory);
		FileUtils.copyFile(compileFile, destFile);
		
//		List<String> complieCommands = createCommand("gcc", destFile.getPath(),
//				"-o", excuteDirPath + uuid,"-Wall", "-lm", "--static");
		
		List<String> complieCommands = createCommand("gcc", destFile.getPath(),
				"-o", excuteDirPath + uuid, "-lm", "--static");

		int status = excuteCommand(complieCommands, 2);
		
		int count =0;
		while(!destFile.delete()){
			count++;
			if(count == 10){
				break;
			}
		}
		
		return status;
		
	}
	

}
