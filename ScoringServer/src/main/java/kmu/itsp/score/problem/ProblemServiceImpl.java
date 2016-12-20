package kmu.itsp.score.problem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import kmu.itsp.score.core.process.CompileResultBean;
import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.process.ProcessServiceFactory;
import kmu.itsp.score.core.util.FileManager;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	ProblemDAO dao;
	@Autowired
	ProcessServiceFactory processFactory;

	private @Value("${path.temp_dir_path}") String tempDirPath;

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public boolean registProblem(ProblemInfoBean problemInfo, int compilerIdx) {
		// TODO Auto-generated method stub

		int nextProblemIdx = dao.getLastProblemIdx() + 1;

		dao.addProblemEntity(problemInfo.getProjectIdx(),nextProblemIdx,
				problemInfo.getProblemName(), problemInfo.getProblemContents());

		System.out.println(nextProblemIdx);

		problemInfo.setProblemIdx(nextProblemIdx);
		// insert problem

		dao.addInputs(nextProblemIdx, problemInfo.getInputValue());
		// insert input

		// complie & excute source file
		File file = FileManager.createStringToFile(tempDirPath, "", "");

		System.out.println(file.getAbsolutePath());

		try {
			problemInfo.getSourceFile().transferTo(file);
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		try {
			IProcessService processService = processFactory
					.getInstance(compilerIdx);

			CompileResultBean compileResult = processService.complie(file);

			if (compileResult.getStatus() == IProcessService.COMPILE_SUCCESS) {
				String inputs[] = problemInfo.getInputValue();
				if(inputs == null){
					inputs = new String[1];
					inputs[0] = "";
				}
				for (int i = 0; i < inputs.length; i++) {
					int status = processService.runExcuteFile(inputs[i],
							compileResult.getFileName(),
							processService.getExcuteDirPath());
					System.out.println("status:" + status);

					if (status == IProcessService.EXEC_SUCCESS) {

						AnswerEntity answerEntity = new AnswerEntity();

						String successResult = processService
								.getSuccessResult();

						successResult = successResult.replaceAll("(),\t\r\n",
								"\n");
						successResult = successResult.replaceAll(" ", "\n");
						System.out.println(successResult);
						// answer and result compare
						StringTokenizer tokenizer = new StringTokenizer(
								successResult, "\n");

						int tokenSize = tokenizer.countTokens();

						System.out.println("TokenSize :" + tokenSize);

						// insert answer
						answerEntity.setAnswerLineNum(tokenSize);
						answerEntity.setAnswerNo(i + 1);
						answerEntity.setAnswer(successResult);
						dao.addAnswer(nextProblemIdx, answerEntity);

					} else {
						return false;
					}
				}

			} else {
				return false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
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
		if(!dao.deleteProblem(problemIdx)){
			return false;
		}
		return true;
	}

}
