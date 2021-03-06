package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

public interface ProblemService {

	boolean registProblem(ProblemInfoBean probInfo, int compilerIdx);

	List<ProblemEntity> getProblemList(int projectIdx);
	List<ProblemEntity> getProblemList(int projectIdx, int pageIdx, int entitySize);
	List<AnswerEntity> getAnswerList(int problemIdx);

	List<ProblemInputEntity> getInputList(int problemIdx);

	boolean removeProblem(int problemIdx);

	int getNumberOfProblems(int projectIdx);
	


}
