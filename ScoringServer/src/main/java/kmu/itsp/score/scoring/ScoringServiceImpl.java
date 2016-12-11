package kmu.itsp.score.scoring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.process.ProcessServiceFactory;
import kmu.itsp.score.core.util.ScoreUtil;
import kmu.itsp.score.problem.ProblemDAO;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;
import kmu.itsp.score.scoring.temp.CompileResultBean;
import kmu.itsp.score.scoring.temp.ScoreResultBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="ScoringService")
public class ScoringServiceImpl implements ScoringService {

	@Autowired
	ProblemDAO dao;

	private int currentProgress;
	
	public int getCurrentProgress() {
		return currentProgress;
	}
	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}
	
	private @Value("${path.temp_dir_path}") String tempDirPath;

	@Override
	public ScoreResultBean scoringUploadFile(File file, String probName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<ScoringResultBean> scoringSourceFile(
			ScoringRequestInfoBean requestInfo) {
		// TODO Auto-generated method stub

		IProcessService processService = ProcessServiceFactory
				.getInstance(requestInfo.getProjectIdx());
		
		File file = null;
		List<ScoringResultBean> scoringResultBeanList = new ArrayList<ScoringResultBean>();
		
		try {

			file = File.createTempFile(UUID.randomUUID().toString(), null);
			requestInfo.getSourceFile().transferTo(file);

			CompileResultBean compileResult = processService.complie(file);

			if (compileResult.getStatus() == IProcessService.COMPILE_SUCCESS) {

				List<ProblemInputEntity> inputList = dao
						.findInputList(requestInfo.getProblemIdx());
				List<AnswerEntity> answerEntityList = dao
						.findAnswerList(requestInfo.getProblemIdx());

				for (int i = 0; i < answerEntityList.size(); i++) {
					int status;
					String input = "";
					if (i < inputList.size()) {
						input = inputList.get(i).getInput();
					}

					status = processService.runExcuteFile(input,
							compileResult.getFileName(),
							processService.getExcuteDirPath());

					if (status == IProcessService.EXEC_SUCCESS) {
						ScoringResultBean scoringResultBean = new ScoringResultBean();

						List<String> resultList = splitMsgToList(processService
								.getSuccessResult());
						List<String> answerList = splitMsgToList(answerEntityList
								.get(i).getAnswer());

						scoringResultBean.setNo(i + 1);
						scoringResultBean.setAnswerList(answerList);
						scoringResultBean.setResultList(resultList);
						scoringResultBean.setInput(input);

						if (compareResultToAnswer(resultList, answerList)) {
							scoringResultBean.setRight(true);
						} else {
							scoringResultBean.setRight(false);
						}
						setCurrentProgress((i+1)*100/answerEntityList.size());
						scoringResultBeanList.add(scoringResultBean);
					}else{
						setCurrentProgress(0);
						return null;
					}
				}

			}

		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			setCurrentProgress(0);
			return null;
		} finally {
			if (file != null && file.exists()) {
				file.delete();
			}
		}

		return scoringResultBeanList;
	}

	public List<String> splitMsgToList(String msg) {

		StringTokenizer tokenizer = new StringTokenizer(msg, "(), \t\r\n");

		int tokenSize = tokenizer.countTokens();

		// System.out.println("TokenSize :"+ tokenSize);
		List<String> stringList = new ArrayList<String>();
		for (int j = 0; j < tokenSize; j++) {
			String token = tokenizer.nextToken();
			// if String value is number
			if (ScoreUtil.isStringDouble(token.trim())) {
				token = ScoreUtil.cutDigitInDouble(token.trim());
			}

			stringList.add(token);
		}

		return stringList;

	}

	public boolean compareResultToAnswer(List<String> resultList,
			List<String> answerList) {
		// answer and result compare

		if (resultList.size() != answerList.size()) {
			return false;
		}

		int matchCount = 0;

		for (int i = 0; i < resultList.size(); i++) {
			if (!resultList.get(i).equals(answerList.get(i))) {
				return false;
			}
		}

		return true;
	}
	
	@Override
	@Transactional
	public boolean registResult(ScoringRequestInfoBean requestInfo, List<ScoringResultBean> scoringResults) {
		// TODO Auto-generated method stub
		int count=0;
		for(int i=0;i<scoringResults.size();i++){
			if(!scoringResults.get(i).isRight()){
				count++;
			}
		}
		int score = -1;
		if(scoringResults.size()>0){
			score = count*100/scoringResults.size();
			System.out.println(score);
		}
		
		//dao.addResult(userid,problemIdx,score);
		
		return true;
	}

}
