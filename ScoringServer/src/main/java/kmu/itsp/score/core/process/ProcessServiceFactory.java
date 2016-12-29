package kmu.itsp.score.core.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ProcessService 들의 factory 클래스
 * @author WJ
 *
 */
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProcessServiceFactory {
	
	@Autowired
	@Qualifier(value = "GccProcessService")
	private IProcessService gccProcessServiceInstance;
	
	
	public ProcessServiceFactory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 초기에 생성된 컴파일러를 반환한다.
	 * @param compilerIdx - 컴파일러를 결정하는 idx 
	 * @return processService
	 */
	public IProcessService getInstance(Integer compilerIdx){
		
		switch (compilerIdx) {
		case 1:
			return gccProcessServiceInstance;

		default:
			return null;
		}
		
	}
}
