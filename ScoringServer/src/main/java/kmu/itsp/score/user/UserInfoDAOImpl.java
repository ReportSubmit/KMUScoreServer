package kmu.itsp.score.user;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.user.entity.UserIDEntity;
import kmu.itsp.score.user.entity.UserInfoEntity;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자에 대한 정보가 담긴 DB에 access하는 Repository 클래스
 * @author WJ
 *
 */
@Repository
public class UserInfoDAOImpl extends CommonDAOImpl implements UserInfoDAO {

	/**
	 * 사용자아이디에 해당하는 사용자정보를 반환하는 메소드
	 * @param userID 사용자 아이디
	 * @return {@link UserInfoEntity}
	 */
	@Override
	@Transactional
	public UserInfoEntity findUserById(String userID) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(UserInfoEntity.class);
		criteria.add(Restrictions.eq("userID", userID));

		UserInfoEntity entity = (UserInfoEntity) criteria.uniqueResult();

		return entity;
	}

	/**
	 * 사용자 정보를 DB에 저장하는 메소드
	 * @param projectIdx 과목 번호
	 * @param userID 사용자 아이디
	 * @param userPwd 사용자 비밀번호
	 * @return true or exception
	 */
	@Override
	public boolean registUser(Integer projectIdx, String userID, String userPwd) {
		// TODO Auto-generated method stub

		UserInfoEntity entity = new UserInfoEntity();
		entity.setProjectIdx(projectIdx);
		entity.setUserID(userID);
		entity.setUserPwd(userPwd);

		persist(entity);

		return true;
	}

	/**
	 * 등록된 사용자 번호 중 가장 마지막 번호를 반환한다.
	 * @return int - 등록된 사용자의 마지막 사용자 번호 없으면 0을 리턴
	 */
	@Override
	public int getLastUserIdx() {
		SQLQuery query = getSession()
				.createSQLQuery(
						"SELECT user_idx FROM tb_user ORDER BY user_idx DESC LIMIT 1;");
		try {
			return (int) query.uniqueResult();
		} catch (HibernateException he) {
			// TODO: handle exception
			return 0;
		}
	}

	/**
	 * 과목 번호에 해당하는 사용자의 아이디, 사용자 번호를 DB에서 찾아 반환한다.
	 * @param projectIdx 쿼리문 조건에 사용 될 과목 번호
	 * @return {@link List} {@link UserIDEntity}
	 */
	@Override
	public List<UserIDEntity> findUserIDList(Integer projectIdx) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(UserIDEntity.class);
		criteria.add(Restrictions.eq("projectIdx", projectIdx));
		criteria.addOrder(Order.asc("userIdx"));
		
		List<UserIDEntity> entitys = (List<UserIDEntity>) criteria.list();

		return entitys;
	}
	
}
