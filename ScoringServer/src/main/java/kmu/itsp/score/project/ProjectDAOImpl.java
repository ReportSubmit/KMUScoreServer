package kmu.itsp.score.project;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDAOImpl extends CommonDAOImpl implements ProjectDAO{

	@Override
	public List<ProjectEntity> findAllProject() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(ProjectEntity.class);
		
		return criteria.list();
	}

	
	
}
