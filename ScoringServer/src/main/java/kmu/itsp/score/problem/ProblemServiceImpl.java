package kmu.itsp.score.problem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import kmu.itsp.score.core.process.IProcessService;
import kmu.itsp.score.core.util.FileManager;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.scoring.CompileResultBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	ProblemDAO dao;

	private @Value("${path.temp_dir_path}") String tempDirPath;

	@Autowired
	@Qualifier(value = "GccProcessService")
	IProcessService processService;

	@Override
	@Transactional
	public boolean registProblem(ProblemInfoBean problemInfo) {
		// TODO Auto-generated method stub
		int nextProblemIdx = dao.getLastProblemIdx() + 1;

		if (!dao.addProblemEntity(problemInfo.getProjectIdx(),
				problemInfo.getProblemName(), problemInfo.getProblemContents())) {
			return false;
		}

		System.out.println(nextProblemIdx);

		problemInfo.setProblemIdx(nextProblemIdx);
		// insert problem

		dao.addInputs(nextProblemIdx, problemInfo.getInputValue());
		// insert input

		// complie & excute source file
		String suffix = ".c";
		File file = FileManager.createStringToFile(tempDirPath, "", suffix);

		System.out.println(file.getAbsolutePath());

		try {
			problemInfo.getSourceFile().transferTo(file);
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			CompileResultBean compileResult = processService.complie(file);

			if (compileResult.getStatus() == IProcessService.COMPILE_SUCCESS) {
				String inputs[] = problemInfo.getInputValue();
				for (int i = 0; i < inputs.length; i++) {
					int status = processService.runExcuteFile(inputs[i],
							compileResult.getUuid(),
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
		}

		return true;
	}

	@Override
	public List<ProblemEntity> getProblemList(int projectIdx, int pageIdx,
			int entitySize) {
		// TODO Auto-generated method stub
		List<ProblemEntity> problemList = dao.findProblemList(projectIdx,
				pageIdx, entitySize);
		return problemList;
	}

}
