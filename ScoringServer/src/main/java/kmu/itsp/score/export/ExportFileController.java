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

/**
 * 출력할 정보에 대한 요청을 처리하는 컨트롤러
 * 원하는 형식(excel, word etc)으로 파일을 출력.
 *  
 * @author WJ
 *
 */
@Controller
public class ExportFileController{
	/**
	 * 파일출력에 관련된 서비스
	 */
	@Autowired
	private ExportFileService exportService;

	/**
	 * 과목에 관련된 서비스
	 */
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 문제에 관련된 서비스
	 */
	@Autowired
	private ProblemService problemService;
	
	/**
	 * 파일 출력 페이지를 보여주는 메소드
	 * 
	 * @param model 모든 과목에 대한 정보를 담으며 jsp에 전송되는 객체
	 * @return String - jsp 파일 이름
	 */
	@RequestMapping(value = "/admin/choose/export_file", method = RequestMethod.GET)
	public String viewExportFilePage(Model model) {
		
		List<ProjectEntity> projects = projectService.getProjectList();

		model.addAttribute("projects", projects);
		
		return "export/ExportFilePage";
	}
	
	/**
	 * 성적을 excel로 출력하는 메소드
	 * 
	 * @param projectIdx - 과목 번호
	 * @param model 문제들과 성적을 담아 jsp에 전송
	 * @return String - 엑셀파일 출력 포멧 jsp 파일
	 */
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
