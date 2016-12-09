package kmu.itsp.score.core.inter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import kmu.itsp.score.core.CCodeChecker;
import kmu.itsp.score.core.CodeCheckList;

public abstract class AbstractCodeChecker implements ICodeChecker {
	private Map<String, String> banWordMap;
	private CodeCheckList checkList;

	public AbstractCodeChecker() {
		banWordMap = new HashMap<String, String>();
		checkList = new CodeCheckList();
	}

	public Map<String, String> getBanWordMap() {
		return banWordMap;
	}

	public CodeCheckList getCodeCheckList() {
		return checkList;
	}

	
	protected boolean setCheckListElement(String key) {
		// TODO Auto-generated method stub
		Class<? extends CodeCheckList> checkClass = getCodeCheckList()
				.getClass();
		Method[] methods = checkClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (!key.isEmpty() && method.getName().startsWith("set")) {
				if (method.getName().contains(
						key.replaceFirst(key.substring(0, 1),
								key.substring(0, 1).toUpperCase())))
					try {
						method.invoke(getCodeCheckList(), true);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		AbstractCodeChecker checker = new CCodeChecker();
		if (checker.setCheckListElement("systemCall")) {
			System.out.println(true);
			System.out.println(checker.getCodeCheckList().isSystemCall());
		} else {
			System.out.println(false);
		}
	}
}
