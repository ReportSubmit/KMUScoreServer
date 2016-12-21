package kmu.itsp.score.project;

import java.util.List;

import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDAO projectDao;
	
	@Override
	@Transactional
	public List<ProjectEntity> getProjectList() {
		// TODO Auto-generated method stub
		List<ProjectEntity> entitys=projectDao.findAllProject();
		return entitys;
	}

}
