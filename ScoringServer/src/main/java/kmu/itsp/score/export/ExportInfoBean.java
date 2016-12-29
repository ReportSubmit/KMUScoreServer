package kmu.itsp.score.export;

import java.util.HashMap;
import java.util.Map;
/**
 * 출력정보를 담는 Bean 클래스
 * @author WJ
 *
 */
public class ExportInfoBean {

	/**
	 * 사용자 ID
	 */
	private String id;
	/**
	 * 출력 정보를 담은 map
	 */
	private Map<Integer,Integer> infoMap;
	
	public ExportInfoBean() {
		// TODO Auto-generated constructor stub
		infoMap = new HashMap<Integer, Integer>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Map<Integer, Integer> getInfoMap() {
		return infoMap;
	}
	public void setInfoMap(Map<Integer, Integer> infoMap) {
		this.infoMap = infoMap;
	}
	
}
