package kmu.itsp.score.core.inter;

import java.util.Map;

import kmu.itsp.score.core.CodeCheckList;

public interface ICodeChecker {
	
	Map<String, String> getBanWordMap();
	void doCheck(String text);
	CodeCheckList getCodeCheckList();
}
