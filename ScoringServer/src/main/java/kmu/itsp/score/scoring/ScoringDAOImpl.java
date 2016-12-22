package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.scoring.entity.ScoringResultEntity;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ScoringDAOImpl extends CommonDAOImpl implements ScoringDAO {

	@Override
	public boolean addScoringResults(int userIdx, int problemIdx,
			List<ScoringResultBean> scoringResults) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < scoringResults.size(); i++) {
			ScoringResultEntity entity = new ScoringResultEntity();
			entity.setProblemIdx(problemIdx);
			entity.setUserIdx(userIdx);
			entity.setScoreNo(scoringResults.get(i).getNo());
			entity.setScore(scoringResults.get(i).getScore());

			update(entity);
		}
		return true;

	}

	@Override
	public boolean addTotalScore(int userIdx, Integer problemIdx, int totalScore) {
		// TODO Auto-generated method stub
		ScoringResultEntity entity = new ScoringResultEntity();
		entity.setProblemIdx(problemIdx);
		entity.setUserIdx(userIdx);
		entity.setScoreNo(0);
		entity.setScore(totalScore);

		update(entity);
		return true;
	}

	@Override
	public List<ScoringTotalEntity> findScoringResult(int userIdx,
			int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(
				ScoringTotalEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));
		criteria.add(Restrictions.eq("userIdx", userIdx));
		criteria.addOrder(Order.asc("problemIdx"));
		criteria.addOrder(Order.asc("scoreNo"));
		List<ScoringTotalEntity> totalEntitys = (List<ScoringTotalEntity>) criteria
				.list();

//		for (int i = 0; i < totalEntitys.size(); i++) {
//			System.out.println(totalEntitys.get(i).getScoreNo());
//		}

		return totalEntitys;
	}

	@Override
	public List<ScoringTotalEntity> findScoringTotalResult(int userIdx,
			int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(
				ScoringTotalEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));
		criteria.add(Restrictions.eq("userIdx", userIdx));
		criteria.addOrder(Order.asc("problemIdx"));
		criteria.addOrder(Order.asc("scoreNo"));
		List<ScoringTotalEntity> scoringList = (List<ScoringTotalEntity>) criteria
				.list();

		if(scoringList.isEmpty()){
			return null;
		}
		
//		for (int i = 0; i < scoringList.size(); i++) {
//			System.out.println(problemIdx);
//			System.out.println(scoringList.get(i).getScoreNo());
//			System.out.println(scoringList.get(i).getScore());
//			
//		}
		
		return scoringList;
	}

}
