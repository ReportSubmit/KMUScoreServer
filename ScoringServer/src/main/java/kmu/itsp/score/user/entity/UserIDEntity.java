package kmu.itsp.score.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
public class UserIDEntity implements Serializable {

	private static final long serialVersionUID = -2394649283803004618L;

	
	@Column(name = "project_idx", nullable = false)
	private Integer projectIdx;
	
	@Id
	@Column(name = "user_idx", nullable = false)
	private Integer userIdx;
	
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	private String userID;
	
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
	public Integer getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(Integer userIdx) {
		this.userIdx = userIdx;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == this)
			return true;
		if (obj == null || !(obj instanceof UserIDEntity))
			return false;

		final UserIDEntity entity = (UserIDEntity) obj;

		if (entity.getUserID() != getUserID()) {
			return false;
		}

		return true;
	}

}
