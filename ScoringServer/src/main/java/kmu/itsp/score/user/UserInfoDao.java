package kmu.itsp.score.user;

import java.util.List;

public interface UserInfoDao {

	//select
	UserStatus findUserStatusByIdandPwd(String userId, String password);
	UserStatus findUserStatusById(String userId);
	List<UserStatus> findAllUserStatus();
	
	//insert
}
