package kmu.itsp.score.export;

import java.util.List;

import kmu.itsp.score.core.dao.CommonDAO;
import kmu.itsp.score.scoring.entity.ScoringResultEntity;

public interface ExportFileDAO extends CommonDAO{

	List<ScoringResultEntity> findExportInfoListByUser(Integer userIdx);

}
