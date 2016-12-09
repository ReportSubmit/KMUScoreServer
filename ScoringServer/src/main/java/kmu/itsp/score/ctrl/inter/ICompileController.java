package kmu.itsp.score.ctrl.inter;

import javax.servlet.http.HttpSession;

import kmu.itsp.score.core.bean.ScoreResultBean;
import kmu.itsp.score.ctrl.bean.ScoringInfoBean;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface ICompileController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String readScoreResult(ScoringInfoBean sInfoBean,
			ScoreResultBean sResultBean, Model model, HttpSession session);
	
	@RequestMapping(method = RequestMethod.POST)
	public String createScoreResult(ScoringInfoBean sInfoBean);
	
	@RequestMapping(method = RequestMethod.PUT)
	public String updateScoreResult();
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String deleteSocreResult();
}
