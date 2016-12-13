package kmu.itsp.score.core.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProcessServiceFactory {
	
	@Autowired
	@Qualifier(value = "GccProcessService")
	private static IProcessService gccProcessServiceInstance;
	
	
	public ProcessServiceFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static IProcessService getInstance(Integer projectIdx){
		
		switch (projectIdx) {
		case 1:case 2:
			if(gccProcessServiceInstance == null){
				gccProcessServiceInstance = new GccProcessServiceImpl();
			}
			return gccProcessServiceInstance;

		default:
			return null;
		}
		
	}
}
