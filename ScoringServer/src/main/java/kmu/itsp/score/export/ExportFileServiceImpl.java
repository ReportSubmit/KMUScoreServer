package kmu.itsp.score.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kmu.itsp.score.scoring.entity.ScoringResultEntity;
import kmu.itsp.score.user.UserInfoDAO;
import kmu.itsp.score.user.entity.UserIDEntity;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExportFileServiceImpl implements ExportFileService{

	@Autowired
	UserInfoDAO userDao;
	
	@Autowired
	ExportFileDAO exportDao;
	
	
	@Override
	@Transactional(rollbackFor=HibernateException.class)
	public List<ExportInfoBean> getExportInfoList(Integer projectIdx) {
		// TODO Auto-generated method stub
		List<ExportInfoBean> infoBeans = new ArrayList<ExportInfoBean>();
		List<UserIDEntity> users=userDao.findUserIDList(projectIdx);
		for(UserIDEntity user: users){
			
			List<ScoringResultEntity> infos=exportDao.findExportInfoListByUser(user.getUserIdx());
			
			ExportInfoBean infoBean = new ExportInfoBean();
			
			infoBean.setId(user.getUserID());
			
			Map<Integer, Integer> infoMap = infoBean.getInfoMap();
			for(ScoringResultEntity info : infos){
				int problemIdx = info.getProblemIdx();
				int score = info.getScore();
				infoMap.put(problemIdx, score);
				
			}
			infoBean.setInfoMap(infoMap);
		
			infoBeans.add(infoBean);
		}
		
		return infoBeans;
	}
	
	
}
