package kmu.itsp.score.export;

import java.util.List;

import kmu.itsp.score.problem.ProblemService;
import kmu.itsp.score.problem.entity.ProblemEntity;
import kmu.itsp.score.project.ProjectService;
import kmu.itsp.score.project.entity.ProjectEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExportFileController{
	
	@Autowired
	private ExportFileService exportService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(value = "/admin/choose/export_file", method = RequestMethod.GET)
	public String viewExportFilePage(Model model) {
		
		List<ProjectEntity> projects = projectService.getProjectList();

		model.addAttribute("projects", projects);
		
		return "export/ExportFilePage";
	}
	
	@RequestMapping(value = "/admin/export/export_file/excel", method = RequestMethod.GET)
	public String exportExcelFile(Integer projectIdx, Model model) {

		//problemidx list
		List<ProblemEntity> problems=problemService.getProblemList(projectIdx);
		//info list
		List<ExportInfoBean> exportBeans=exportService.getExportInfoList(projectIdx);
		
		model.addAttribute("problems", problems);
		model.addAttribute("exports", exportBeans);
		
		return "export/ExportExcelForm";
	}
}
