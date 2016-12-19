package kmu.itsp.score.scoring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import kmu.itsp.score.core.process.CompileResultBean;
import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.process.ProcessServiceFactory;
import kmu.itsp.score.core.util.ScoreUtil;
import kmu.itsp.score.problem.ProblemDAO;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;
import kmu.itsp.score.user.UserInfoDAO;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ScoringService")
public class ScoringServiceImpl implements ScoringService {

	@Autowired
	ProblemDAO problemDao;

	@Autowired
	UserInfoDAO userDao;

	@Autowired
	ScoringDAO scoringDao;

	@Autowired
	ProcessServiceFactory processfactory;

	private int currentProgress;

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	private @Value("${path.temp_dir_path}") String tempDirPath;

	@Override
	@Transactional
	public List<ScoringResultBean> scoringSourceFile(
			ScoringRequestInfoBean requestInfo) {
		// TODO Auto-generated method stub

		IProcessService processService = processfactory.getInstance(requestInfo.getComplierIdx());

		File file = null;
		List<ScoringResultBean> scoringResultBeanList = new ArrayList<ScoringResultBean>();

		try {

			file = File.createTempFile(UUID.randomUUID().toString(), null);
			requestInfo.getSourceFile().transferTo(file);
			System.out.println(file.getAbsolutePath());
			CompileResultBean compileResult = processService.complie(file);

			if (compileResult.getStatus() == IProcessService.COMPILE_SUCCESS) {

				List<ProblemInputEntity> inputList = problemDao
						.findInputList(requestInfo.getProblemIdx());
				List<AnswerEntity> answerEntityList = problemDao
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

					ScoringResultBean scoringResultBean = new ScoringResultBean();
					scoringResultBean.setNo(i + 1);
					scoringResultBean.setInput(input);

					List<String> answerList = splitMsgToList(answerEntityList
							.get(i).getAnswer());
					scoringResultBean.setAnswerList(answerList);

					if (status == IProcessService.EXEC_SUCCESS) {

						List<String> resultList = splitMsgToList(processService
								.getSuccessResult());

						scoringResultBean.setResultList(resultList);
						int eachScore = compareResultToAnswer(resultList,
								answerList);

						if (eachScore == 100) {
							scoringResultBean.setRight(true);
							scoringResultBean.setMsg("Correct:excute");
						} else {
							scoringResultBean.setRight(false);
							scoringResultBean.setMsg("Incorrect:excute");
						}

						scoringResultBean.setScore(eachScore);
						scoringResultBeanList.add(scoringResultBean);

						// progress
						setCurrentProgress((i + 1) * 100
								/ answerEntityList.size());
					} else {
						scoringResultBean.setRight(false);
						scoringResultBean.setScore(0);
						scoringResultBean.setResultList(null);
						scoringResultBeanList.add(scoringResultBean);
						scoringResultBean.setMsg("Error:do not excute");
						// progress
						setCurrentProgress(0);
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

	public int compareResultToAnswer(List<String> resultList,
			List<String> answerList) {
		// answer and result compare

		int matchCount = 0;
		int score;
		for (int i = 0; i < resultList.size(); i++) {
			if (resultList.get(i).equals(answerList.get(i))) {
				matchCount++;
			}
		}
		score = matchCount * 100 / answerList.size();
		if (score > 100) {
			score = 0;
		}
		return score;
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public boolean registResult(int userIdx,
			ScoringRequestInfoBean requestInfo,
			List<ScoringResultBean> scoringResults) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < scoringResults.size(); i++) {
			if (scoringResults.get(i).isRight()) {
				count++;
			}
		}
		int totalScore = -1;
		if (scoringResults.size() > 0) {
			totalScore = count * 100 / scoringResults.size();
			System.out.println(totalScore);
		}
		scoringDao.addTotalScore(userIdx, requestInfo.getProblemIdx(),
				totalScore);
		scoringDao.addScoringResults(userIdx, requestInfo.getProblemIdx(),
				scoringResults);

		return true;
	}

	@Override
	@Transactional
	public ScoringReadResponseBean readResult(int userIdx, int problemIdx) {
		// TODO Auto-generated method stub
		ScoringReadResponseBean scoreReadBean = new ScoringReadResponseBean();
		scoreReadBean.setProblemIdx(problemIdx);
		scoreReadBean.setInfos(scoringDao.findScoringResult(userIdx, problemIdx));

		return scoreReadBean;
	}

	@Override
	@Transactional
	public List<ScoringReadResponseBean> readResults(int userIdx) {
		// TODO Auto-generated method stub
		int endProblemIdx = problemDao.getLastProblemIdx();
		List<ScoringReadResponseBean> scoreReadBeanList = new ArrayList<ScoringReadResponseBean>();
		System.out.println(endProblemIdx);

		for (int i = 0; i <= endProblemIdx; i++) {

			List<ScoringTotalEntity> entitys = scoringDao.findScoringTotalResult(
					userIdx, i);
			if(entitys != null && !entitys.isEmpty()){
				ScoringReadResponseBean scoreReadBean = new ScoringReadResponseBean();
				scoreReadBean.setProblemIdx(i);
				scoreReadBean.setInfos(entitys);
				scoreReadBeanList.add(scoreReadBean);
			}
		}

		return scoreReadBeanList;
	}

}
