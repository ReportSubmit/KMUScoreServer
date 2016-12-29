package kmu.itsp.score.export;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.scoring.entity.ScoringResultEntity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * 출력할 정보를 DB로부터 얻어오는 Repository 클래스
 * @author WJ
 *
 */
@Repository
public class ExportFileDAOImpl extends CommonDAOImpl implements ExportFileDAO {

	/**
	 * 사용자번호에 대응하는 출력할 정보를 얻어오는 메소드
	 * @param userIdx - 사용자 번호
	 * @return List - 사용자 번호에 해당하는 성적 정보를 반환
	 */
	@Override
	public List<ScoringResultEntity> findExportInfoListByUser(Integer userIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(
				ScoringResultEntity.class);

		criteria.add(Restrictions.eq("userIdx", userIdx));
		criteria.add(Restrictions.eq("scoreNo", 0));
		// 0 == problem's total score idx;
		criteria.addOrder(Order.asc("problemIdx"));
		List<ScoringResultEntity> scoringEntitys = (List<ScoringResultEntity>) criteria
				.list();

		for (int i = 0; i < scoringEntitys.size(); i++) {
			System.out.println(scoringEntitys.get(i).getProblemIdx());
		}

		return scoringEntitys;

	}

}
