package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.problem.entity.ProblemEntity;

public interface ProblemService {

	boolean registProblem(ProblemInfoBean probInfo);

	List<ProblemEntity> getProblemList(int projectIdx, int pageIdx, int entitySize);

}
