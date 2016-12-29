package kmu.itsp.score.scoring;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.scoring.entity.ScoringResultEntity;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * 채점에 대한 정보가 담긴 DB에 access하는 Repository 클래스
 * @author WJ
 *
 */
@Repository
public class ScoringDAOImpl extends CommonDAOImpl implements ScoringDAO {

	/**
	 * 문제 번호에 해당하는 각 테스트 입력 값의 채점 결과를 저장하거나 업데이트 한다.
	 * @param userIdx - 사용자 번호
	 * @param problemIdx - 문제 번호
	 * @param scoringResults - List 채점결과
	 * @return true or excepetion
	 */
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

	/**
	 * 해당 문제의 전체 점수를 저장한다.
	 * @param userIdx 유저 번호
	 * @param problemIdx 문제 번호
	 * @param totalScore 해당 문제의 종합 점수
	 * @return true or exception
	 */
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

	/**
	 * 문제 번호에 해당하는 채점 결과를 찾는다.
	 * @param userIdx 유저 번호
	 * @param problemIdx 문제 번호
	 * @return {@link List}:{@link ScoringTotalEntity}
	 */
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

}
