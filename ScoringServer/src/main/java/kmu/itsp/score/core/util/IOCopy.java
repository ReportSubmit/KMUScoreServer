package kmu.itsp.score.core.util;

import javax.inject.Named;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Named("IOCopy")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IOCopy {

	private String result;

	public void setResult(String result) {
		this.result = result;
	}

	public IOCopy() {
		// TODO Auto-generated constructor stub
	}

	public String getResult() {
		return result.toString();
	}
}
