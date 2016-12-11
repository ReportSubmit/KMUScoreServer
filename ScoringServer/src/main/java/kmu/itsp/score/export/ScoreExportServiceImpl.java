package kmu.itsp.score.export;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import kmu.itsp.score.scoring.ScoringService;
import kmu.itsp.score.scoring.temp.ProbScoreBean;
import kmu.itsp.score.scoring.temp.ScoreResultBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "ScoreExportService")
public class ScoreExportServiceImpl {
	@Autowired
	@Qualifier(value = "CScoreService")
	ScoringService cScoreService;

	void findFiles(File targetDir, List<File> fileList) {
		File[] files = targetDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				if (checkSuffix(files[i].getName(), ".c", ".cpp")) {
					// System.out.println("File:"+files[i].getName());
					fileList.add(files[i]);
				}
				// work
			} else if (files[i].isDirectory()) {
				// System.out.println("Dir:"+files[i].getName());
				findFiles(files[i], fileList);
			}
		}
	}

	boolean checkSuffix(String name, String... suffixs) {
		for (int i = 0; i < suffixs.length; i++) {
			if (name.lastIndexOf(suffixs[i]) > 8) {
				return true;
			}
		}
		return false;
	}

	public HashMap<String, List<ProbScoreBean>> scoringFiles(
			ArrayList<File> fileList, String partName) {

		HashMap<String, List<ProbScoreBean>> scoreMap = new HashMap<String, List<ProbScoreBean>>();

		for (int i = 0; i < fileList.size(); i++) {
			Entry<String, String> entry = findStudentAndProbNum(fileList.get(i));
			if (entry != null) {

				String studentNum = entry.getKey();
				String probNum = entry.getValue();
				System.out.println(studentNum + ":" + probNum);
				
				
				ScoreResultBean scoreResult = cScoreService.scoringUploadFile(
						fileList.get(i), partName + "-" + probNum);

				List<ProbScoreBean> probScoreList = scoreMap.get(studentNum);

				if (probScoreList == null) {
					probScoreList = new ArrayList<ProbScoreBean>();
					scoreMap.put(studentNum, probScoreList);
				}
				probScoreList.add(new ProbScoreBean(probNum, scoreResult
						.getScore()));
			} else {
				System.out.println(fileList.get(i));
			}
		}
		return scoreMap;
	}

	private Entry<String, String> findStudentAndProbNum(File file) {
		// TODO Auto-generated method stub
		Entry<String, String> entry = null;
		String studentNum = null;
		String probNum = null;

		StringTokenizer tokenizer = new StringTokenizer(file.getName(), "_ -");

		// student number
		if (tokenizer.hasMoreTokens()) {
			studentNum = tokenizer.nextToken();
			// System.out.println(studentNum);
		} else {
			return entry;
		}
		// prob number
		if (tokenizer.hasMoreTokens()) {
			probNum = tokenizer.nextToken();
			probNum = probNum.replaceAll("[^0-9]", "");
			// System.out.println(probNum);
		} else {
			return entry;
		}

		entry = new AbstractMap.SimpleEntry<String, String>(studentNum, probNum);

		return entry;

	}

}
