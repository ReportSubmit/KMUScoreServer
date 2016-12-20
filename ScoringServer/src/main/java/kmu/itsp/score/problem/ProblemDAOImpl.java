package kmu.itsp.score.problem;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class ProblemDAOImpl extends CommonDAOImpl implements ProblemDAO {

	@Override
	public boolean addProblemEntity(int projectIdx, String problemName,
			String problemContents) {

		ProblemEntity entity = new ProblemEntity();
		entity.setProjectIdx(projectIdx);
		entity.setProblemName(problemName);
		entity.setProblemContents(problemContents);
		persist(entity);
		// insert problem entity

		return true;

	}

	@Override
	public int getLastProblemIdx() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"SELECT problem_idx FROM tb_problem ORDER BY problem_idx DESC LIMIT 1;");
		try {
			return (int) query.uniqueResult();
		} catch (HibernateException he) {
			// TODO: handle exception
			return 0;
		}
	}

	@Override
	public boolean addInputs(int problemIdx, String[] inputValues) {
		if (inputValues != null) {
			for (int i = 0; i < inputValues.length; i++) {
				ProblemInputEntity entity = new ProblemInputEntity();
				entity.setProblemIdx(problemIdx);
				entity.setInputNo(i + 1);
				entity.setInput(inputValues[i]);
				persist(entity);
			}
		}
		return true;
	}

	@Override
	public boolean addAnswer(int problemIdx, AnswerEntity entity) {
		// TODO Auto-generated method stub

		entity.setProblemIdx(problemIdx);

		persist(entity);
		// insert problem entity

		return true;
	}

	@Override
	public List<ProblemEntity> findProblemList(int projectIdx, int pageIdx,
			int entitySize) {
		// TODO Auto-generated method stub

		System.out.println(projectIdx + ":" + pageIdx + ":" + entitySize + ":"
				+ pageIdx * (entitySize - 1) + ":" + pageIdx * (entitySize));

		Criteria criteria = getSession().createCriteria(ProblemEntity.class);

		// compare id
		if(projectIdx != 100){
			criteria.add(Restrictions.eq("projectIdx", projectIdx));
		}
		criteria.addOrder(Order.desc("problemIdx"));

		// limit
		criteria.setFirstResult((pageIdx - 1) * entitySize);
		criteria.setMaxResults(pageIdx * (entitySize));

		return criteria.list();
	}
	
	@Override
	public List<ProblemEntity> findAllProblemListByProject(int projectIdx) {
		// TODO Auto-generated method stub

		Criteria criteria = getSession().createCriteria(ProblemEntity.class);

		// compare id
		if(projectIdx != 100){
			criteria.add(Restrictions.eq("projectIdx", projectIdx));
		}
		criteria.addOrder(Order.desc("problemIdx"));

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
	public List<AnswerEntity> findAnswerList(int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AnswerEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));

		List<AnswerEntity> answerList = (List<AnswerEntity>) criteria.list();

		for (int i = 0; i < answerList.size(); i++) {
			System.out.println(answerList.get(i).getAnswer());
		}

		return answerList;
	}

	@Override
	public List<ProblemInputEntity> findInputList(int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(
				ProblemInputEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));

		List<ProblemInputEntity> inputList = (List<ProblemInputEntity>) criteria
				.list();

		for (int i = 0; i < inputList.size(); i++) {
			System.out.println(inputList.get(i).getInput());
		}
		return inputList;
	}

}
