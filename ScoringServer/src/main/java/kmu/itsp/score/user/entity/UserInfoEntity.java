package kmu.itsp.score.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserInfoEntity implements Serializable {

	private static final long serialVersionUID = -2394649283803004618L;

	
	@Column(name = "project_idx", nullable = false)
	private Integer projectIdx;
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;

	@Column(name = "user_pwd", nullable = false)
	private String userPwd;

	@Column(name = "user_grade", nullable = false, length=1)
	private Integer userGrade;

	public UserInfoEntity() {
		// TODO Auto-generated constructor stub
		userGrade = 2;
	}
	
	public Integer getProjectIdx() {
		return projectIdx;
	}

	public void setProjectIdx(Integer projectIdx) {
		this.projectIdx = projectIdx;
	}

	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getUserGrade() {
		return userGrade;
	}
	
	public void setUserGrade(Integer userGrade) {
		this.userGrade = userGrade;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof UserInfoEntity))
			return false;

		final UserInfoEntity entity = (UserInfoEntity) obj;

		if (entity.getUserID() != getUserID()) {
			return false;
		}

		return true;
	}

}
