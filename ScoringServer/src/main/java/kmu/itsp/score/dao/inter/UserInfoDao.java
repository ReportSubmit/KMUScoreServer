package kmu.itsp.score.dao.inter;

import java.util.List;

import kmu.itsp.score.dao.entity.UserStatus;

public interface UserInfoDao {

	//select
	UserStatus findUserStatusByIdandPwd(String userId, String password);
	UserStatus findUserStatusById(String userId);
	List<UserStatus> findAllUserStatus();
	
	//insert
}
