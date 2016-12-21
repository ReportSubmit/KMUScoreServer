package kmu.itsp.score.user;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.user.entity.UserInfoEntity;
import kmu.itsp.score.user.entity.UserIDEntity;

public interface UserInfoDAO extends CommonDAO{

	UserInfoEntity findUserById(String userID);

	boolean registUser(Integer projectIdx, String userID, String userPwd);

	int getLastUserIdx();

	List<UserIDEntity> findUserIDList(Integer projectIdx);


	//insert
}
