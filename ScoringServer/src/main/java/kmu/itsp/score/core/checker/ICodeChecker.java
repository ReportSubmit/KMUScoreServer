package kmu.itsp.score.core.checker;

import java.util.Map;

public interface ICodeChecker {
	
	Map<String, String> getBanWordMap();
	void doCheck(String text);
	CodeCheckList getCodeCheckList();
}
