package kmu.itsp.score.core.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProcessServiceFactory {
	
	@Autowired
	@Qualifier(value = "GccProcessService")
	private IProcessService gccProcessServiceInstance;
	
	
	public ProcessServiceFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public IProcessService getInstance(Integer compilerIdx){
		
		switch (compilerIdx) {
		case 1:
			return gccProcessServiceInstance;

		default:
			return null;
		}
		
	}
}
