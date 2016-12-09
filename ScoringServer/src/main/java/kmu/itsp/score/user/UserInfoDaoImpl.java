package kmu.itsp.score.user;

import java.util.List;

import javax.inject.Named;

import kmu.itsp.score.core.dao.CommonDAOImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
@Named("UserInfoDao")
public class UserInfoDaoImpl extends CommonDAOImpl implements UserInfoDao{

	@Override
	public UserStatus findUserStatusByIdandPwd(String userId, String password) {
		// TODO Auto-generated method stub
		Criteria criteria =getSession().createCriteria(UserStatus.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("password", password));
		
		UserStatus userStatus=(UserStatus)criteria.uniqueResult();
		
		return userStatus;
	}

	@Override
	public UserStatus findUserStatusById(String userId) {
		// TODO Auto-generated method stub
		Criteria criteria =getSession().createCriteria(UserStatus.class);
		criteria.add(Restrictions.eq("userId", userId));
		
		UserStatus userStatus=(UserStatus)criteria.uniqueResult();
		
		return userStatus;
	}

	@Override
	public List<UserStatus> findAllUserStatus() {
		// TODO Auto-generated method stub
		Criteria criteria =getSession().createCriteria(UserStatus.class);
		List<UserStatus> userStatusList=(List<UserStatus>)criteria.list();
		
		return userStatusList;
	}



	
	
}
