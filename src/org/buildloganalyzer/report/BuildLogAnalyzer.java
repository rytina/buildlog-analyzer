package org.buildloganalyzer.report;
import java.io.File;


public class BuildLogAnalyzer {
	
	public BuildLogAnalyzer(File dir){
		if(!dir.isDirectory()){
			throw new RuntimeException("no Dir!");
		}
	}

}
