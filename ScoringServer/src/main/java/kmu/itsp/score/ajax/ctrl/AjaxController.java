package kmu.itsp.score.ajax.ctrl;

import java.io.File;
import java.io.IOException;

import kmu.itsp.score.core.bean.CompileResultBean;
import kmu.itsp.score.core.bean.ScoreResultBean;
import kmu.itsp.score.core.inter.IProcessService;
import kmu.itsp.score.core.inter.ScoreService;
import kmu.itsp.score.core.util.FileManager;
import kmu.itsp.score.core.util.ScoreUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AjaxController {
	@Autowired
	@Qualifier(value = "GccProcessService")
	IProcessService processService;
	
	@Autowired
	@Qualifier(value = "CScoreService")
	ScoreService cScoreService;

	private String inputSuffix = ".txt";
	
	@RequestMapping(value = "/ajax/C/test", method = RequestMethod.POST)
	public @ResponseBody CompileResultBean ajaxWriteCode(String code,
			String input, @Value("${path.temp_dir_path}") String tempDirPath) {

		CompileResultBean complieResult = new CompileResultBean();
		String suffix = ".c";
		

		File file = FileManager.createStringToFile(tempDirPath, code, suffix);

		if (file == null) {
			complieResult.setSuccessMsg("");
			complieResult.setMessage("Server Error!");
			complieResult.setErrorMsg("");
			return complieResult;
		}

		try {
			CompileResultBean result = processService.complie(file);
			complieResult.setStatus(result.getStatus());
			if (result.getStatus() != IProcessService.COMPILE_ERROR) {
				File inputFile = null;
				if (input != null && input.trim() != "") {
					inputFile = FileManager.createStringToFile(
							processService.getInputDirPath(), input,
							inputSuffix);
				}
				int status = processService.runExcuteFile(inputFile,
						result.getUuid(), processService.getExcuteDirPath());
				complieResult.setStatus(status);
				complieResult.setComplieResultByStatus(processService
						.getSuccessResult(), ScoreUtil.deletePathInStr(
						processService.getError(),
						processService.getExcuteDirPath()));

			} else {
				complieResult.setComplieResultByStatus(processService
						.getSuccessResult(), ScoreUtil.deletePathInStr(
						processService.getError(),
						processService.getUploadCodeDirPath()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return complieResult;

	}

	@RequestMapping(value = "/ajax/C/answer", method = RequestMethod.POST)
	public @ResponseBody CompileResultBean ajaxAnswer(String fileName,
			String input) {
		CompileResultBean complieResult = new CompileResultBean();

		File inputFile = null;
		if (input != null && input.trim() != "") {
			inputFile = FileManager.createStringToFile(
					processService.getInputDirPath(), input, inputSuffix);
		}

		int status = processService.runExcuteFile(inputFile, fileName,
				processService.getAnswerFileDirPath());

		complieResult.setStatus(status);

		complieResult.setComplieResultByStatus(processService
				.getSuccessResult(), ScoreUtil.deletePathInStr(
				processService.getError(), processService.getExcuteDirPath()));

		return complieResult;
	}

	@RequestMapping(value = "/ajax/C/scoring/file", method = RequestMethod.POST)
	public @ResponseBody ScoreResultBean ajaxUploadCodeFile(
			MultipartFile uploadFile, String probNum,
			@Value("${path.temp_dir_path}") String tempDirPath) {
		String suffix = ".c";
		File file = FileManager.createStringToFile(tempDirPath, "", suffix);

		// System.out.println(file.getAbsolutePath());

		try {
			uploadFile.transferTo(file);
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ScoreResultBean scoreResult=cScoreService.scoringUploadFile(file, probNum);

		//수정할 것
		return scoreResult;
	}

	@RequestMapping(value = "/test/test/test", method = RequestMethod.POST)
	public @ResponseBody String TestTest(TestBean testBean){
		
		System.out.println("name: " + testBean.getName());
		System.out.println("num: " + testBean.getNum());
		
		
		return "abc";
	}
}
