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

/**
 * 사용자에 대한 정보를 처리하는 서비스 클래스
 * @author WJ
 *
 */
@Service
@Named("UserAccessService")
public class UserAccessServiceImpl implements UserDetailsService,
		UserAccessService {

	/**
	 * 사용자 등급
	 */
	final static int ROLE_USER = 2;
	final static int ROLE_ADMIN = 1;

	/**
	 * 사용자 정보를 처리하는 DB와 연결가능한 객체
	 */
	@Autowired
	UserInfoDAO dao;
	
	/**
	 * 로그인 시 사용자 번호와 과제 번호, 사용자의 역할을 session에 등록하는 메소드
	 * @param username 사용자 아이디
	 * @return {@link ScoreUser}
	 */
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

	/**
	 * 회원 가입 서비스
	 * @param projectIdx 과목 번호
	 * @param userID 사용자 아이디
	 * @param userPwd 사용자 비밀번호
	 * @return true or exception
	 */
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
