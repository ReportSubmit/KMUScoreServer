package kmu.itsp.score.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class ScoreUser extends User implements UserDetails{
	private int projectIdx;
	private int userIdx;
	
	public ScoreUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities, int projectIdx, int userIdx) {
		super(username, password, authorities);
		this.projectIdx = projectIdx;
		this.userIdx= userIdx;
		// TODO Auto-generated constructor stub
	}

	public int getProjectIdx() {
		return projectIdx;
	}
	
	public void setProjectIdx(int projectIdx) {
		this.projectIdx = projectIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	
	
	
}
