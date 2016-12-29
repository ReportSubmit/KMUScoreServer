package kmu.itsp.score.project;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
/**
 * 과목에 대한 정보가 담긴 DB에 access하는 Repository 클래스
 * @author WJ
 *
 */
@Repository
public class ProjectDAOImpl extends CommonDAOImpl implements ProjectDAO{

	/**
	 * 모든 과목 정보를 반환한다.
	 * @return {@link List} - {@link ProjectEntity}
	 */
	@Override
	public List<ProjectEntity> findAllProject() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(ProjectEntity.class);
		
		return criteria.list();
	}

	
	
}
