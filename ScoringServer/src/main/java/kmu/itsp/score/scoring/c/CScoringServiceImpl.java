package kmu.itsp.score.scoring.c;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.util.FileManager;
import kmu.itsp.score.core.util.ScoreUtil;
import kmu.itsp.score.scoring.CompileResultBean;
import kmu.itsp.score.scoring.ProbResultBean;
import kmu.itsp.score.scoring.ScoreResultBean;
import kmu.itsp.score.scoring.ScoringService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value ="CScoreService")
public class CScoringServiceImpl implements ScoringService{
	@Autowired
	@Qualifier(value = "GccProcessService")
	IProcessService processService;
	private String inputSuffix = ".txt";
	
	public ScoreResultBean scoringUploadFile(File file, String probName){
		ScoreResultBean scoreResult = new ScoreResultBean();
		ArrayList<ProbResultBean> probResBean = null;
	
		// upload file start to compile
		if(file == null || !file.exists()){
			scoreResult.setErrorMsg("업로드된 파일을 찾을 수 없습니다.");
			scoreResult.setScore(0);;
			scoreResult.setMessage("Sever Error");;
			return scoreResult;
		}
		
		try {

			CompileResultBean result = processService.complie(file);
			scoreResult.setStatus(result.getStatus());
			System.out.println(result.getStatus());
			scoreResult.setComplieResultByStatus(processService
					.getSuccessResult(), ScoreUtil.deletePathInStr(
					processService.getError(),
					processService.getUploadCodeDirPath()));

			// I will add compile error status
			if (result.getErrorMsg() == null) {
				// complie success
				probResBean = scoreResult.getProbList();
				System.out.println(probName);
				// answer read in file, each line
				ArrayList<String> answerList = ScoreUtil.getAnswer(
						processService.getAnswerSolveDirPath(), probName,
						inputSuffix);
								
				if (answerList == null) {
					scoreResult.setErrorMsg("정답 파일을 찾을 수 없습니다.");
					return scoreResult;
				}

				// set problem Size
				try {
					scoreResult.setNumOfProbSize(Integer.valueOf(answerList
							.get(0)));
				} catch (NumberFormatException e) {
					scoreResult.setErrorMsg("정답 파일의 형식이 맞지 않습니다.");
					return scoreResult;
				}

				// number format error must catch
				// System.out.println(numOfProbSize);

				int currentLine = 1;

				for (int i = 0; i < scoreResult.getNumOfProbSize(); i++) {

					ProbResultBean probBean = new ProbResultBean();

					// get AnswerFile
					File ansInputFile = new File(
							processService.getAnswerInputDirPath() + probName
									+ "-" + (i + 1) + inputSuffix);
					// get inputFile
					probBean.setInput(FileManager.getFileText(ansInputFile));

					// run program
					int status = processService
							.runExcuteFile(ansInputFile, result.getUuid(),
									processService.getExcuteDirPath());
					// set program status
					
					probBean.setStatus(status);
					probBean.setProbResultByStatus(
							processService.getSuccessResult(), null);
			
					// a test's answer is set to each probBean's answerlist
					currentLine = probBean.setSubListToAnswerList(currentLine,
							answerList);

					// if excute file success
					if (probBean.getStatus() == IProcessService.EXEC_SUCCESS) {
						// answer and result compare
						if (probBean.compareResultToAnswer(probBean
								.getSuccessMsg(), probBean.getAnswerList()
								.size())) {
							probBean.setIsRight(1);
						} else {
							probBean.setIsRight(0);
						}
					}

					probResBean.add(probBean);
				}
			} else {
				// status check
				scoreResult.setScore(0);
				return scoreResult;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		// calculate Score
		scoreResult.calculateScore();
		
		return scoreResult;
	}
	
	
}
