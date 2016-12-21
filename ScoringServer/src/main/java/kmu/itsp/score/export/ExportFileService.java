package kmu.itsp.score.export;

import java.util.List;

public interface ExportFileService {

	List<ExportInfoBean> getExportInfoList(Integer projectIdx);

}
