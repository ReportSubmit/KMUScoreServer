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

@Service
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	ProblemDAO dao;
	@Autowired
	ProcessServiceFactory processFactory;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registProblem(ProblemInfoBean problemInfo, int compilerIdx) {
		// TODO Auto-generated method stub

		int nextProblemIdx = dao.getLastProblemIdx() + 1;

		dao.addProblemEntity(problemInfo.getProjectIdx(), nextProblemIdx,
				problemInfo.getProblemName(), problemInfo.getProblemContents());

//		System.out.println(nextProblemIdx);

		problemInfo.setProblemIdx(nextProblemIdx);
		// insert problem

		dao.addInputs(nextProblemIdx, problemInfo.getInputValue());
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
			compileResult = processService.complie(file);
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
	
					int status = processService.runExcuteFile(inputs[i],
							compileResult.getExcuteFile().getCanonicalPath());

//					System.out.println("status:" + status);

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
						dao.addAnswer(nextProblemIdx, answerEntity);

					} else {
						// excute error
						return false;
					}
				}
			} else {
				// compile error
				return false;
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

	@Override
	@Transactional
	public List<ProblemEntity> getProblemList(int projectIdx) {
		// TODO Auto-generated method stub
		List<ProblemEntity> problemList = dao.findProblemList(projectIdx);
		return problemList;
	}

	@Override
	@Transactional
	public List<ProblemEntity> getProblemList(int projectIdx, int pageIdx,
			int entitySize) {
		// TODO Auto-generated method stub
		List<ProblemEntity> problemList = dao.findProblemList(projectIdx,
				pageIdx, entitySize);
		return problemList;
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public List<ProblemInputEntity> getInputList(int problemIdx) {
		// TODO Auto-generated method stub
		List<ProblemInputEntity> inputList = dao.findInputList(problemIdx);
		return inputList;
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public List<AnswerEntity> getAnswerList(int problemIdx) {
		// TODO Auto-generated method stub
		List<AnswerEntity> answerList = dao.findAnswerList(problemIdx);
		return answerList;
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public boolean removeProblem(int problemIdx) {
		// TODO Auto-generated method stub
		if (!dao.deleteProblem(problemIdx)) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public int getNumberOfProblems(int projectIdx) {
		// TODO Auto-generated method stub

		return dao.getNumberOfProblems(projectIdx);
	}

}
