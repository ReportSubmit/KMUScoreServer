package kmu.itsp.score.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Named;

import kmu.itsp.score.dao.entity.UserStatus;
import kmu.itsp.score.dao.inter.UserInfoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Named("UserService")
public class UserService implements UserDetailsService{
	@Autowired
	UserInfoDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		

		UserStatus userStatus = userDao.findUserStatusById(username);

		if(userStatus == null){
			throw new UsernameNotFoundException("Username not found");
		}
		Collection<SimpleGrantedAuthority> authorities= new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority role = null;
		switch (userStatus.getPermission()) {
		
		case UserStatus.PERMISSION_USER:
			role = new SimpleGrantedAuthority("ROLE_USER");
			authorities.add(role);
			role = null;
			if(userStatus.getProject().equals("prog")){
				role = new SimpleGrantedAuthority("ROLE_PROG");
			}else if(userStatus.getProject().equals("java")){
				role = new SimpleGrantedAuthority("ROLE_JAVA");
			}else if(userStatus.getProject().equals("coms")){
				role = new SimpleGrantedAuthority("ROLE_COMS");
			}
			if(role != null){
				authorities.add(role);
			}
			break;
		case UserStatus.PERMISSION_ADMIN:
			role = new SimpleGrantedAuthority("ROLE_ADMIN");
			authorities.add(role);
			break;

		default:
			break;
			
		}
		
		UserDetails userDetails = new User(username, userStatus.getPassword(), authorities);
		
		return userDetails;
	}
	

}
