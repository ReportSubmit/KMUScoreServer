package kmu.itsp.score.problem;

import java.sql.Timestamp;
import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

public interface ProblemDAO extends CommonDAO{
	
	boolean addProblemEntity(int projectIdx, int problemIdx, String problemName, String problemContents, Timestamp limitTime);
	boolean addInputs(int problemIdx, String[] inputValues);
	boolean addAnswer(int problemIdx, AnswerEntity answerEntity);
	List<ProblemEntity> findProblemList(int projectIdx, int firstIdx, int entitySize);
	ProblemEntity findProblem(int problemIdx);
	boolean deleteProblem(int problemIdx);
	boolean deleteAllProblemInProject(int projectIdx);
	int getLastProblemIdx();
	List<AnswerEntity> findAnswerList(int problemIdx);
	List<ProblemInputEntity> findInputList(int problemIdx);
	List<ProblemEntity> findAllProblemListByProject(int projectIdx,boolean reverse);
	int getNumberOfProblems(int projectIdx);
	
	
}

	
