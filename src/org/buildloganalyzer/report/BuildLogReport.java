package org.buildloganalyzer.report;

import java.io.File;
import java.io.IOException;

import org.buildloganalyzer.IReportRowProvider;
import org.buildloganalyzer.splitter.BuildLogSplitter;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class BuildLogReport {
	
	private File splitDir;

	public BuildLogReport(File logFile){
		if(!logFile.exists()){
			throw new RuntimeException("file does not exist!");
		}
		BuildLogSplitter splitter = new BuildLogSplitter(logFile);
		this.splitDir = splitter.split();
	}
	
	public void createReport(String reportName, IReportRowProvider rowProvider){
		if(!this.splitDir.isDirectory()){
			throw new RuntimeException("No Dir!");
		}
		StringBuilder sb = new StringBuilder();
		BuildLogAnalyzer analyzer = new BuildLogAnalyzer(rowProvider);
		for (File splitItem : this.splitDir.listFiles()) {
			String result = analyzer.analyze(splitItem);
			sb.append(result);
			sb.append('\n');
		}
		File report = new File(reportName+".txt");
		try {
			Files.write(sb.toString(), report, Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
