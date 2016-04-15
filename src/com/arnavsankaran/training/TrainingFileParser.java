package com.arnavsankaran.training;

import com.arnavsankaran.configuration.ConfigurationUtils;
import com.arnavsankaran.util.CSVFileParser;

public class TrainingFileParser extends CSVFileParser {
	
	public TrainingFileParser(String filepath) {
		super(filepath);
	}
	
	public TrainingItem[] getTrainingSamples() {
		String[][] rawTrainingSamples = getParsedFile();
		TrainingItem[] trianingSamples = new TrainingItem[rawTrainingSamples.length];
		for(int i = 0; i < rawTrainingSamples.length; i++) {
			trianingSamples[i] = new TrainingItem(rawTrainingSamples[i][0], ConfigurationUtils.getConfigPosition(rawTrainingSamples[i][1].charAt(0)));
		}
		return trianingSamples;
	}

}
