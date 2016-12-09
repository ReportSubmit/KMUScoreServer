package kmu.itsp.score.export.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kmu.itsp.score.core.bean.ProbScoreBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScoreExportController {
	@Autowired
	@Qualifier(value = "ScoreExportService")
	ScoreExportServiceImpl exportService;

	/* 임시 사용 url */
	@RequestMapping(value = "/export/c/excel", method = RequestMethod.GET)
	public String viewAdminExportScoreInExcel(Model model) {
		ArrayList<File> fileList = new ArrayList<File>();
//		File directory = new File("C:\\Users\\WJ\\Desktop\\Test\\QuizTestFile");
//		File directory = new File("C:\\Users\\WJ\\Desktop\\Test\\Quiz2TestFile");
//		File directory = new File("C:\\Users\\WJ\\Desktop\\Test\\Mid");
		File directory = new File("C:\\Users\\WJ\\Desktop\\Test\\Final");
		
		exportService.findFiles(directory, fileList);
		// System.out.println(fileList);
		HashMap<String, List<ProbScoreBean>> scoreMap = exportService
				.scoringFiles(fileList, "final");

		model.addAttribute("scoreMap", scoreMap);
		
		return "admin/ExportExcelPage";
	}
}
