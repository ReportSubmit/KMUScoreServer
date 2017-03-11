package kmu.itsp.score.problem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import kmu.itsp.score.core.process.CompileResultBean;
import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.process.ProcessServiceFactory;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 문제에 대한 정보를 처리하는 서비스 클래스
 * @author WJ
 *
 */
@Service
public class ProblemServiceImpl implements ProblemService {
	/**
	 * 문제 정보가 담긴 DB와 access 할 수 있는 객체
	 */
	@Autowired
	ProblemDAO problemDao;
	/**
	 * ProcessService를 얻올 수 있는 factory 객체
	 */
	@Autowired
	ProcessServiceFactory processFactory;

	/**
	 * 문제 등록 서비스 메소드
	 * 문제 정보, 각 테스트 입력 값,정답을 저장한다 
	 * @param problemInfo 등록할 문제 정보가 담긴 bean
	 * @param compilerIdx 컴파일러 결정할 idx
	 * @return true or exception
	 * @exception HibernateException 데이터베이스 문제시 발생
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registProblem(ProblemInfoBean problemInfo, int compilerIdx) {
		// TODO Auto-generated method stub

		int nextProblemIdx = problemDao.getLastProblemIdx() + 1;

		problemDao.addProblemEntity(problemInfo.getProjectIdx(), nextProblemIdx,
				problemInfo.getProblemName(), problemInfo.getProblemContents(), problemInfo.getLimitTime());

//		System.out.println(nextProblemIdx);

		problemInfo.setProblemIdx(nextProblemIdx);
		// insert problem

		problemDao.addInputs(nextProblemIdx, problemInfo.getInputValue());
		// insert input

		// complie & excute source file
		File file = null;

		try {
			file = File.createTempFile("gcc_", ".c");
			problemInfo.getSourceFile().transferTo(file);
//			System.out.println(file.getCanonicalPath());
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			if (file != null && file.exists()) {
				file.delete();
			}
			throw new HibernateException("종료");
		}

		IProcessService processService = processFactory
				.getInstance(compilerIdx);

		// compile
		CompileResultBean compileResult = null;
		try {
			compileResult = processService.compile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HibernateException("종료");
		} finally {
			if (file != null && file.exists()) {
				file.delete();
			}
		}

		// excute
		try {
			if (compileResult.getStatus() == IProcessService.COMPILE_SUCCESS) {
				String inputs[] = problemInfo.getInputValue();
				if (inputs == null) {
					inputs = new String[1];
					inputs[0] = "";
				}
				for (int i = 0; i < inputs.length; i++) {
	
					int status = processService.runExecutableFile(inputs[i],
							compileResult.getExcuteFile().getCanonicalPath());

//					System.out.println("excute status:" + status);

					if (status == IProcessService.EXEC_SUCCESS) {

						AnswerEntity answerEntity = new AnswerEntity();

						String successResult = processService
								.getSuccessResult();

						successResult = successResult.replaceAll(" ", "\n");
//						System.out.println(successResult);
						// answer and result compare
						StringTokenizer tokenizer = new StringTokenizer(
								successResult, "(),\t\r\n");

						int tokenSize = tokenizer.countTokens();

//						System.out.println("TokenSize :" + tokenSize);

						// insert answer
						answerEntity.setAnswerLineNum(tokenSize);
						answerEntity.setAnswerNo(i + 1);
						answerEntity.setAnswer(successResult);
						problemDao.addAnswer(nextProblemIdx, answerEntity);

					} else {
						// excute error
						throw new HibernateException("종료");
						
					}
				}
			} else {
				// compile error
				throw new HibernateException("종료");
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new HibernateException("종료");
		} finally {
			if (compileResult.getExcuteFile() != null
					&& compileResult.getExcuteFile().exists()) {
				compileResult.getExcuteFile().delete();
			}
		}

		return true;
	}

	/**
	 * 과목 번호에 해당하는 모든 문제들을 반환한다.
	 * @param projectIdx 과목 번호
	 * @return List - {@link ProblemEntity}
	 */
	@Override
	@Transactional
	public List<ProblemEntity> getProblemList(int projectIdx) {
		// TODO Auto-generated method stub
		List<ProblemEntity> problemList = problemDao.findAllProblemListByProject(projectIdx, false);
		return problemList;
	}

	/**
	 * 과목 번호에 해당하는 문제들을 제한된 수만큼 반환한다.
	 * @param projectIdx 과목 번호
	 * @param pageIdx 문제들 중 처음 반환 시작할 idx
	 * @param entitySize 반환할 개수
	 * @return List - {@link ProblemEntity}
	 */
	@Override
	@Transactional
	public List<ProblemEntity> getProblemList(int projectIdx, int pageIdx,
			int entitySize) {
		// TODO Auto-generated method stub
		List<ProblemEntity> problemList = problemDao.findProblemList(projectIdx,
				pageIdx, entitySize);
		return problemList;
	}

	/**
	 * 문제 번호에 해당하는 테스트 입력값을 반환한다.
	 * @param problemIdx - 문제 번호
	 * @return List - {@link ProblemInputEntity}
	 * @exception HibernateException 데이터베이스 문제시 발생
	 */
	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public List<ProblemInputEntity> getInputList(int problemIdx) {
		// TODO Auto-generated method stub
		List<ProblemInputEntity> inputList = problemDao.findInputList(problemIdx);
		return inputList;
	}

	/**
	 * 문제 번호에 해당하는 정답을 반환한다.
	 * @param problemIdx - 문제 번호
	 * @return List - {@link AnswerEntity}
	 * 
	 */
	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public List<AnswerEntity> getAnswerList(int problemIdx) {
		// TODO Auto-generated method stub
		List<AnswerEntity> answerList = problemDao.findAnswerList(problemIdx);
		return answerList;
	}

	/**
	 * 문제 번호에 해당하는 문제를 제거한다.
	 * @param problemIdx - 문제 번호
	 * @return true or false
	 * 
	 */
	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public boolean removeProblem(int problemIdx) {
		// TODO Auto-generated method stub
		if (!problemDao.deleteProblem(problemIdx)) {
			return false;
		}
		return true;
	}
	/**
	 * 과목 번호에 해당하는 모든 문제의 수를 반환한다.
	 * @param projectIdx 과목 번호
	 * @return int
	 * 
	 */
	@Override
	@Transactional
	public int getNumberOfProblems(int projectIdx) {
		// TODO Auto-generated method stub

		return problemDao.getNumberOfProblems(projectIdx);
	}

}
