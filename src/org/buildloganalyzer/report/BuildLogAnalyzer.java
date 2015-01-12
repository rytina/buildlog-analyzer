package org.buildloganalyzer.report;

import java.io.File;

import org.buildloganalyzer.IReportRowProvider;

class BuildLogAnalyzer {

	private IReportRowProvider rowProvider;

	public BuildLogAnalyzer(IReportRowProvider rowProvider) {
		this.rowProvider = rowProvider;
	}

	public String analyze(File splitItem) {
		return String.format("%s %s", rowProvider.getRowName(splitItem),
				rowProvider.getRowValue(splitItem));
	}

}
