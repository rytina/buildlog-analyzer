package org.buildloganalyzer;

import java.io.File;

public interface IReportRowProvider {

	public String getRowName(File splittedLogItem);
	
	public String getRowValue(File splittedLogItem);
}
