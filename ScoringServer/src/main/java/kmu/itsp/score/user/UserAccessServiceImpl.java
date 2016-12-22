package kmu.itsp.score.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Named;

import kmu.itsp.score.user.entity.UserInfoEntity;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Named("UserAccessService")
public class UserAccessServiceImpl implements UserDetailsService,
		UserAccessService {

	final static int ROLE_USER = 2;
	final static int ROLE_ADMIN = 1;

	@Autowired
	UserInfoDAO dao;
	
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		UserInfoEntity userEntity = dao.findUserById(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority role = null;

		switch (userEntity.getUserGrade()) {

		case ROLE_USER:
			role = new SimpleGrantedAuthority("ROLE_USER");
			authorities.add(role);
			break;
		case ROLE_ADMIN:
			role = new SimpleGrantedAuthority("ROLE_ADMIN");
			authorities.add(role);
			break;

		default:
			return null;
		}

		ScoreUser userDetails = new ScoreUser(userEntity.getUserID(),
				userEntity.getUserPwd(), authorities,userEntity.getProjectIdx(), userEntity.getUserIdx());

		
		 
		return userDetails;
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public boolean registUser(Integer projectIdx, String userID, String userPwd) {
		// TODO Auto-generated method stub
		if(projectIdx==100){
			throw new HibernateException("관리자");
		}
		
		return dao.registUser(projectIdx, userID, userPwd);
	}

}
