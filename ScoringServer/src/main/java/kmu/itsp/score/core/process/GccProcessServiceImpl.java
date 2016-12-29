package kmu.itsp.score.core.process;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * GCC를 이용하여 C언어 소스코드를 컴파일 및 실행시키는 클래스
 * @author WJ
 *
 */

@Service(value = "GccProcessService")
public class GccProcessServiceImpl extends AbstractProcessService {
	

	public GccProcessServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * C언어 소스코드를 컴파일한 결과를 정리하여 리턴 객체에 넣어 반환하는 메소드
	 * @param compileFile - c언어 소스코드
	 * @return CompileResultBean
	 */
	@Override
	public CompileResultBean compile(File compileFile) throws IOException {
		// TODO Auto-generated method stub
		
		String executableFileName = UUID.randomUUID().toString();
		int status=runCompiler(compileFile, executableFileName);
		
		if (!checkStatus(status)) {
			status = IProcessService.COMPILE_ERROR;
		}else{
			status = IProcessService.COMPILE_SUCCESS;
		}
		
		String filePath=compileFile.getCanonicalPath().replace(compileFile.getName(), "");
	
		return new CompileResultBean(status, executableFileName, filePath);

	}
	
	
	/**
	 * 실제 컴파일을 실행하는 메소드
	 * @param compileFile - c언어 소스코드
	 * @param fileName - 생성될 파일 이름
	 * @return status - 실행한 후 상태
	 */
	@Override
	public int runCompiler(File compileFile, String fileName) throws IOException{
		String filePath = compileFile.getCanonicalPath().replace(compileFile.getName(), "");
		List<String> complieCommands = createCommand("gcc", compileFile.getCanonicalPath(),
				"-o", filePath+fileName, "-lm", "--static");

		int status = executeCommand(complieCommands, 2, null);
		
		return status;
		
	}
	

}
