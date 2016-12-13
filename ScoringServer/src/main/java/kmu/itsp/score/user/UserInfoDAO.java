package kmu.itsp.score.user;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.user.entity.UserInfoEntity;

public interface UserInfoDAO extends CommonDAO{

	UserInfoEntity findUserById(String userID);

	boolean registUser(Integer projectIdx, String userID, String userPwd);


	//insert
}
