package kmu.itsp.score.dao;

import java.util.List;

import kmu.itsp.score.dao.entity.AnswerEntity;
import kmu.itsp.score.dao.entity.ProblemEntity;
import kmu.itsp.score.dao.entity.ProblemInputEntity;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class ProblemDAOImpl implements ProblemDAO {
	@Autowired
	CommonDAO dao;

	@Override
	public boolean addProblemEntity(int projectIdx, String problemName,
			String problemContents) {
		try {
			ProblemEntity entity = new ProblemEntity();
			entity.setProjectIdx(projectIdx);
			entity.setProblemName(problemName);
			entity.setProblemContents(problemContents);
			dao.persist(entity);
			// insert problem entity

			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			dao.getSession().getTransaction().rollback();
		}

		return false;
	}

	@Override
	public int getLastProblemIdx() {
		SQLQuery query = dao
				.getSession()
				.createSQLQuery(
						"SELECT problem_idx FROM tb_problem ORDER BY problem_idx DESC LIMIT 1;");
		return (int) query.uniqueResult();

	}

	@Override
	public boolean addInputs(int problemIdx, String[] inputValues) {
		try {
			for (int i = 0; i < inputValues.length; i++) {
				ProblemInputEntity entity = new ProblemInputEntity();
				entity.setProblemIdx(problemIdx);
				entity.setInputNo(i + 1);
				entity.setInput(inputValues[i]);
				dao.persist(entity);
			}
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			dao.getSession().getTransaction().rollback();
		}
		return false;

	}

	@Override
	public boolean addAnswer(int problemIdx, AnswerEntity entity) {
		// TODO Auto-generated method stub
		try {

			entity.setProblemIdx(problemIdx);

			dao.persist(entity);
			// insert problem entity

			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			dao.getSession().getTransaction().rollback();
		}
		return false;
	}

	@Override
	public List<ProblemEntity> findProblemList(int projectIdx, int pageIdx,
			int entitySize) {
		// TODO Auto-generated method stub

		System.out.println(projectIdx + ":" + pageIdx + ":" + entitySize + ":"
				+ pageIdx * (entitySize - 1) + ":" + pageIdx * (entitySize));

		Criteria criteria = dao.getSession()
				.createCriteria(ProblemEntity.class);

		// compare id
		criteria.add(Restrictions.eq("projectIdx", projectIdx));
		criteria.addOrder(Order.desc("problemIdx"));

		// limit
		criteria.setFirstResult((pageIdx - 1) * entitySize);
		criteria.setMaxResults(pageIdx * (entitySize));

		return criteria.list();
	}

	@Override
	public ProblemEntity findProblem(int problemIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProblem(int problemIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllProblemInProject(int projectIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AnswerEntity> getAnswerList(int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = dao.getSession().createCriteria(AnswerEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));

		List<AnswerEntity> answerList = (List<AnswerEntity>) criteria.list();

		for (int i = 0; i < answerList.size(); i++) {
			System.out.println(answerList.get(i).getAnswer());
		}
		return answerList;
	}

}
