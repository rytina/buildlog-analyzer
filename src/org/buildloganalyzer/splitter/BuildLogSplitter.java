package org.buildloganalyzer.splitter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class BuildLogSplitter {
	
	private static final String SUREFIRE_DECL = "tycho-surefire-plugin:0.22.0:test (default-test) @ ";

	private String fullLog;
	
	private List<Integer> indizes = new ArrayList<Integer>();

	public BuildLogSplitter(File logFile) {
		try{
			this.fullLog = readLog(logFile);
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

	public void split(){
		split(fullLog);
	}

	private void split(String fullLog) {
		int lastIndex = 0;
		while(lastIndex != -1){
			lastIndex = fullLog.indexOf("T E S T S", lastIndex + 1);
			if(lastIndex != -1){
				indizes.add(lastIndex);
			}
		}
		writeSplittedFiles();
	}

	private void writeSplittedFiles() {
		for (int i=0; i<indizes.size();i++) {
			int toIndex;
			if(i == indizes.size()-1){
				toIndex = fullLog.length();
			}else{
				toIndex = indizes.get(i+1);
			}
			writeFile(indizes.get(i), toIndex);
		}
	}

	private void writeFile(int fromIndex, int toIndex) {
		File file = new File(getFileName(fromIndex));
		try {
			Files.write(fullLog.substring(fromIndex, toIndex), file, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileName(int fromIndex) {
		int indexOfTestPlugin = fullLog.substring(0,fromIndex).lastIndexOf(SUREFIRE_DECL)+SUREFIRE_DECL.length();
		String testPluginName = fullLog.substring(indexOfTestPlugin, indexOfTestPlugin+100).split(" ")[0];
		return testPluginName + ".txt";
	}

	private String readLog(File logFile) throws IOException {
		return Files.toString(logFile, Charsets.UTF_8);
	}

	public List<Integer> getIndizes() {
		return indizes;
	}

}
