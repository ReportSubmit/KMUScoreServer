package kmu.itsp.score.export;

import java.util.HashMap;
import java.util.Map;

public class ExportInfoBean {

	private String id;
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
