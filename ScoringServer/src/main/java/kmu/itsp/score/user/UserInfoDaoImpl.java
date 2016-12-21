package kmu.itsp.score.user;

import java.util.List;

import javax.inject.Named;

import kmu.itsp.score.core.dao.CommonDAOImpl;
import kmu.itsp.score.user.entity.UserInfoEntity;
import kmu.itsp.score.user.entity.UserIDEntity;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Named("UserInfoDao")
public class UserInfoDaoImpl extends CommonDAOImpl implements UserInfoDAO {

	@Override
	@Transactional
	public UserInfoEntity findUserById(String userID) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(UserInfoEntity.class);
		criteria.add(Restrictions.eq("userID", userID));

		UserInfoEntity entity = (UserInfoEntity) criteria.uniqueResult();

		return entity;
	}

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
