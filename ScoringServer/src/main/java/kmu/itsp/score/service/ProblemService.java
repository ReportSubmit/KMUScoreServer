package kmu.itsp.score.service;

import java.util.List;

import kmu.itsp.score.admin.ctrl.ProblemInfoBean;
import kmu.itsp.score.dao.entity.ProblemEntity;

public interface ProblemService {

	boolean registProblem(ProblemInfoBean probInfo);

	List<ProblemEntity> getProblemList(int projectIdx, int pageIdx, int entitySize);

}
