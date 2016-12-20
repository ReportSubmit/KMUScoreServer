package kmu.itsp.score.project;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.project.entity.ProjectEntity;

public interface ProjectDAO extends CommonDAO{

	List<ProjectEntity> findAllProject();

}
