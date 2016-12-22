package kmu.itsp.score.core.process;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service(value = "GccProcessService")
public class GccProcessServiceImpl extends AbstractProcessService {
	

	public GccProcessServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompileResultBean complie(File compileFile) throws IOException {
		// TODO Auto-generated method stub
		
		String excuteFileName = UUID.randomUUID().toString();
		int status=runComplier(compileFile, excuteFileName);
		
		if (!checkStatus(status)) {
			status = IProcessService.COMPILE_ERROR;
		}else{
			status = IProcessService.COMPILE_SUCCESS;
		}
		
		String filePath=compileFile.getCanonicalPath().replace(compileFile.getName(), "");
	
		return new CompileResultBean(status, excuteFileName, filePath);

	}
	@Override
	public int runExcuteFile(File inputFile, String filePath){
		int status;
		// status == AbstractComplier.SUCCESS
		List<String> execCommands = createCommand(filePath);
		
		status = excuteCommand(execCommands, 2, inputFile);
		
		return status;
	}
	
	@Override
	public int runExcuteFile(String input, String filePath) {
		// TODO Auto-generated method stub
		int status;
		List<String> execCommands = createCommand(filePath);
		
		status = excuteCommand(execCommands, input, 2);
		
		return status;
	}
	
	@Override
	public int runComplier(File compileFile, String fileName) throws IOException{
		String filePath = compileFile.getCanonicalPath().replace(compileFile.getName(), "");
		List<String> complieCommands = createCommand("gcc", compileFile.getCanonicalPath(),
				"-o", filePath+fileName, "-lm", "--static");

		int status = excuteCommand(complieCommands, 2, null);
		
		return status;
		
	}
	

}
