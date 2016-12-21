package kmu.itsp.score.export;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.scoring.entity.ScoringResultEntity;
import kmu.itsp.score.scoring.entity.ScoringTotalEntity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ExportFileDAOImpl extends CommonDAOImpl implements ExportFileDAO {

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
