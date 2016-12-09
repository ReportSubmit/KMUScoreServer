package kmu.itsp.score.core.checker;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CCodeChecker extends AbstractCodeChecker {

	public CCodeChecker() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void doCheck(String text) {
		// TODO Auto-generated method stub
		Map<String, String> banWordMap = getBanWordMap();

		Set<String> banKeys = banWordMap.keySet();
		for (Iterator<String> iterator = banKeys.iterator(); iterator.hasNext();) {
			String banKey = iterator.next();

			if (text.matches(banWordMap.get(banKey))) {
				setCheckListElement(banKey);
			}

		}
	}
}
