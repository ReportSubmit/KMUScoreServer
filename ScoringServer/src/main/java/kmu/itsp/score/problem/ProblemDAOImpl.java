package kmu.itsp.score.problem;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.problem.entity.AnswerEntity;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.problem.entity.ProblemInputEntity;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * 문제에 대한 정보가 담긴 DB에 access하는 Repository 클래스
 * @author WJ
 *
 */
@Repository
public class ProblemDAOImpl extends CommonDAOImpl implements ProblemDAO {

	/**
	 * 새로운 문제를 저장하는 메소드
	 * @param projectIdx 과목 번호
	 * @param problemIdx 문제 번호
	 * @param problemName 문제 제목
	 * @param problemContents 문제 내용
	 * @return true or exception
	 */
	@Override
	public boolean addProblemEntity(int projectIdx,int problemIdx, String problemName,
			String problemContents, Timestamp limitTime) {

		ProblemEntity entity = new ProblemEntity();
		entity.setProjectIdx(projectIdx);
		entity.setProblemIdx(problemIdx);
		entity.setProblemName(problemName);
		entity.setProblemContents(problemContents);
		entity.setProblemLimitTime(limitTime);
		persist(entity);
		// insert problem entity

		return true;

	}

	/**
	 * 현재 저장된 문제들 중 마지막 번호를 DB로부터 얻어온다
	 * @return int 마지막번호 or 값이 없을시 0
	 */
	@Override
	public int getLastProblemIdx() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"SELECT problem_idx FROM tb_problem ORDER BY problem_idx DESC LIMIT 1;");
		try {
			return (int) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 새로운 문제에 대한 여러개의 테스트 입력 값을 저장한다.
	 * @param problemIdx 문제번호
	 * @param inputValues 테스트에 대한 여러 입력 값들
	 * @return true or exception
	 */
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

	/**
	 * 해당 문제의 여러개의 테스트 입력 값에 대한 정답을 저장한다.
	 * @param problemIdx 문제번호
	 * @param entity 문제에 대한 정답 정보를 담고 있다
	 * @return true or exception
	 */
	@Override
	public boolean addAnswer(int problemIdx, AnswerEntity entity) {
		// TODO Auto-generated method stub

		entity.setProblemIdx(problemIdx);

		persist(entity);
		// insert problem entity

		return true;
	}

	/**
	 * 해당 문제 번호에 대한 정보를 찾아온다
	 * @param problemIdx 문제 번호
	 * @return {@link ProblemEntity}
	 */
	@Override
	public ProblemEntity findProblem(int problemIdx){
		// TODO Auto-generated method stub

		Criteria criteria = getSession().createCriteria(ProblemEntity.class);

		// compare id
		
		criteria.add(Restrictions.eq("problemIdx", problemIdx));
		
		Object object = criteria.uniqueResult();
		if(object != null){
			return (ProblemEntity) criteria.uniqueResult();
		}else{
			return null;
		}
	}
	/**
	 * 해당 과목 번호에 대한 문제들을 찾아온다. 내림차순
	 * @param projectIdx 과목 번호
	 * @param firstIdx 전체 문제 들 중 최초 가져올 문제의 시작 index
	 * @param entitySize 가져올 문제 개수
	 * @return List - {@link ProblemEntity}
	 */
	@Override
	public List<ProblemEntity> findProblemList(int projectIdx, int firstIdx,
			int entitySize) {
		// TODO Auto-generated method stub

		Criteria criteria = getSession().createCriteria(ProblemEntity.class);

		// compare id
		if(projectIdx != 100){
			criteria.add(Restrictions.eq("projectIdx", projectIdx));
			Timestamp current = new Timestamp(System.currentTimeMillis());
			criteria.add(Restrictions.gt("problemLimitTime", current));
		}
		criteria.addOrder(Order.desc("problemIdx"));

		// limit
		criteria.setFirstResult((firstIdx - 1) * entitySize);
		criteria.setMaxResults(firstIdx * (entitySize));

		return criteria.list();
	}

	/**
	 * 해당 과목 번호에 대한 문제들을 찾아온다.
	 * @param projectIdx 과목 번호
	 * @param reverse - true: problemIdx desc , false: problemIdx asc 
	 * @return List - {@link ProblemEntity}
	 */
	@Override
	public List<ProblemEntity> findAllProblemListByProject(int projectIdx, boolean reverse) {
		// TODO Auto-generated method stub

		Criteria criteria = getSession().createCriteria(ProblemEntity.class);

		// compare id
		if(projectIdx != 100){
			criteria.add(Restrictions.eq("projectIdx", projectIdx));
		}
		if(reverse){
			criteria.addOrder(Order.desc("problemIdx"));
		}else{
			criteria.addOrder(Order.asc("problemIdx"));
		}
		return criteria.list();
	}

	/**
	 * 문제 번호에 해당하는 문제를 찾아 지운다.
	 * @param problemIdx 문제 번호 
	 * @return true or false
	 */
	@Override
	public boolean deleteProblem(int problemIdx) {
		// TODO Auto-generated method stub
		ProblemEntity entity = findProblem(problemIdx);
		if(entity != null){
			delete(entity);
		}else{
			return false;
		}
		return true;
	}

	/**
	 * 미구현
	 */
	@Override
	public boolean deleteAllProblemInProject(int projectIdx) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 문제 번호에 해당하는 정답들을 찾아온다.
	 * @param problemIdx 문제번호
	 * @return List - {@link AnswerEntity}
	 */
	@Override
	public List<AnswerEntity> findAnswerList(int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AnswerEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));

		List<AnswerEntity> answerList = (List<AnswerEntity>) criteria.list();

//		for (int i = 0; i < answerList.size(); i++) {
//			System.out.println(answerList.get(i).getAnswer());
//		}

		return answerList;
	}

	/**
	 * 문제 번호에 해당하는 테스트 입력들을 찾아온다.
	 * @param problemIdx 문제번호
	 * @return List - {@link ProblemInputEntity}
	 */
	@Override
	public List<ProblemInputEntity> findInputList(int problemIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(
				ProblemInputEntity.class);

		criteria.add(Restrictions.eq("problemIdx", problemIdx));

		List<ProblemInputEntity> inputList = (List<ProblemInputEntity>) criteria
				.list();

//		for (int i = 0; i < inputList.size(); i++) {
//			System.out.println(inputList.get(i).getInput());
//		}
		return inputList;
	}

	/**
	 * 현재 과목의 전체 문제 수를 찾아온다.
	 * @param projectIdx 과목 번호
	 * @return int
	 */
	@Override
	public int getNumberOfProblems(int projectIdx) {
		// TODO Auto-generated method stub
		String where = "WHERE project_idx="+projectIdx;
		if(projectIdx == 100){
			where ="";
		}
		SQLQuery query = getSession()
				.createSQLQuery(
						"SELECT Count(problem_idx) FROM tb_problem " +where);
		try {
			 
			return ((BigInteger)query.uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}


}
