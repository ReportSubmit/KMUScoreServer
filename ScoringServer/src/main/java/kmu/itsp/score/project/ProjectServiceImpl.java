package kmu.itsp.score.project;

import java.util.List;

import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 과제에 대한 정보를 처리하는 서비스 클래스
 * @author WJ
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	/**
	 * 과제 정보가 담긴 DB와 access 할 수 있는 객체
	 */
	@Autowired
	ProjectDAO projectDao;
	
	/**
	 * 모든 과목 정보를 List로 반환한다.
	 */
	@Override
	@Transactional
	public List<ProjectEntity> getProjectList() {
		// TODO Auto-generated method stub
		List<ProjectEntity> entitys=projectDao.findAllProject();
		return entitys;
	}

}
