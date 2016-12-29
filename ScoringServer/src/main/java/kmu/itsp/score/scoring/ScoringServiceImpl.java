package kmu.itsp.score.scoring;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import kmu.itsp.score.core.process.CompileResultBean;
import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.process.ProcessServiceFactory;
import kmu.itsp.score.core.util.NumberConvertUtil;
import kmu.itsp.score.problem.ProblemDAO;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;
import kmu.itsp.score.user.UserInfoDAO;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 채점 서비스 클래스
 * @author WJ
 *
 */
@Service(value = "ScoringService")
public class ScoringServiceImpl implements ScoringService {

	/**
	 * 문제 정보가 담긴 DB와 access 할 수 있는 객체
	 */
	@Autowired
	ProblemDAO problemDao;

	/**
	 * 사용자 정보가 담긴 DB와 access 할 수 있는 객체
	 */
	@Autowired
	UserInfoDAO userDao;

	/**
	 * 채점 정보가 담긴 DB와 access 할 수 있는 객체
	 */
	@Autowired
	ScoringDAO scoringDao;

	/**
	 * ProcessService를 얻올 수 있는 factory 객체
	 */
	@Autowired
	ProcessServiceFactory processfactory;

	/**
	 * 미구현
	 */
	private int currentProgress;

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	/**
	 * 소스코드를 채점하는 메소드
	 * @param requestInfo 채점에 필요한 정보를 담고 있다 {@link ScoringRequestInfoBean}
	 * @return {@link List} {@link ScoringResultBean} 각 테스트에 대한 채점결과
	 */
	@Override
	@Transactional
	public List<ScoringResultBean> scoringSourceFile(
			ScoringRequestInfoBean requestInfo) {
		// TODO Auto-generated method stub

		IProcessService processService = processfactory.getInstance(requestInfo
				.getCompilerIdx());

		File file = null;
		List<ScoringResultBean> scoringResultBeanList = new ArrayList<ScoringResultBean>();

		try {
			file = File.createTempFile("gcc_", ".c");
			requestInfo.getSourceFile().transferTo(file);
//			System.out.println(file.getCanonicalPath());
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if (file != null && file.exists()) {
				file.delete();
			}
			return null;
		}

		CompileResultBean compileResult = null;
		try {
			compileResult = processService.compile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (file != null && file.exists()) {
				file.delete();
			}
		}

		try {
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

					status = processService.runExecutableFile(input, compileResult
							.getExcuteFile().getCanonicalPath());

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
			if (compileResult.getExcuteFile() != null
					&& compileResult.getExcuteFile().exists()) {
				compileResult.getExcuteFile().delete();
			}
		}

		return scoringResultBeanList;
	}

	/**
	 * msg를 (),\t\r\n으로 나눠 list로 반환하는 메소드
	 * @param str 문자열
	 * @return {@link List} {@link String}
	 */
	public List<String> splitMsgToList(String str) {

		str=str.replaceAll(" ", "\n");
		StringTokenizer tokenizer = new StringTokenizer(str, "(),\t\r\n");

		int tokenSize = tokenizer.countTokens();

//		System.out.println("TokenSize :"+ tokenSize);
		List<String> stringList = new ArrayList<String>();
		for (int j = 0; j < tokenSize; j++) {
			String token = tokenizer.nextToken();
			// if String value is number
			if (NumberConvertUtil.isStringDouble(token.trim())) {
				token = NumberConvertUtil.cutDigitInDouble(token.trim());
			}
//			System.out.println(token);
			stringList.add(token);
		}

		return stringList;

	}

	/**
	 * 소스코드 결과와 정답을 비교하여 점수를 반환
	 * @param resultList 소스코드 결과
	 * @param answerList 정답
	 * @return int - 점수
	 */
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

	/**
	 * 채점 결과를 등록 서비스
	 * @param userIdx 사용자 번호
	 * @param requestInfo 채점에 필요한 정보
	 * @param scoringResults 채점 결과
	 * @return true or exception
	 */
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
		int totalScore = 0;
		if (scoringResults.size() > 0) {
			totalScore = count * 100 / scoringResults.size();
//			System.out.println(totalScore);
		}
		scoringDao.addTotalScore(userIdx, requestInfo.getProblemIdx(),
				totalScore);
		scoringDao.addScoringResults(userIdx, requestInfo.getProblemIdx(),
				scoringResults);

		return true;
	}

	/**
	 * 사용자 번호와 문제 번호에 해당하는 채점결과를 DB로부터 얻어 반환
	 * @param userIdx 사용자 번호
	 * @param problemIdx 문제 번호
	 * @return {@link ScoringReadResponseBean}
	 */
	@Override
	@Transactional
	public ScoringReadResponseBean readResult(int userIdx, int problemIdx) {
		// TODO Auto-generated method stub
		ScoringReadResponseBean scoreReadBean = new ScoringReadResponseBean();
		scoreReadBean.setProblemIdx(problemIdx);
		scoreReadBean.setInfos(scoringDao
				.findScoringResult(userIdx, problemIdx));

		return scoreReadBean;
	}

	/**
	 * 사용자 번호와 과제 번호에 해당하는 각 문제에 해당하는 채점 결과를 DB로부터 얻어 반환
	 * @param userIdx 사용자 번호
	 * @param projectIdx 과목 번호
	 * @return {@link List} {@link ScoringReadResponseBean}
	 */
	@Override
	@Transactional
	public List<ScoringReadResponseBean> readResults(int projectIdx, int userIdx,List<ProblemEntity> problems) {
		// TODO Auto-generated method stub
		
		List<ScoringReadResponseBean> scoreReadBeanList = new ArrayList<ScoringReadResponseBean>();

		for (int i = 0; i < problems.size(); i++) {

			List<ScoringTotalEntity> entitys = scoringDao
					.findScoringResult(userIdx, problems.get(i)
							.getProblemIdx());
			if (entitys != null && !entitys.isEmpty()) {
				ScoringReadResponseBean scoreReadBean = new ScoringReadResponseBean();
				scoreReadBean.setProblemIdx(problems.get(i).getProblemIdx());
				scoreReadBean.setInfos(entitys);
				scoreReadBeanList.add(scoreReadBean);
			}
		}

		return scoreReadBeanList;
	}

}
