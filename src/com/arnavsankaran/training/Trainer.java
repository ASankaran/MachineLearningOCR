package com.arnavsankaran.training;

import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.preprocessing.Preprocessor;
import com.arnavsankaran.util.CSVFileParser;

public class Trainer {
	
	String filePath;
	CSVFileParser fileParser;
	
	NeuralNetwork network;
	
	public Trainer(String filePath, NeuralNetwork network) {
		this.filePath = filePath;
		fileParser = new CSVFileParser(filePath);
		this.network = network;
	}
	
	public void trainNetwork() {
		String[][] trainingSamples = fileParser.getParsedFile();
		for(int i = 0; i < trainingSamples.length; i++) {
			Preprocessor preproc = new Preprocessor(fileParser.getFileDirectory() + "\\" + trainingSamples[i][0]);
			network.addTrainingNetworkData(Integer.parseInt(trainingSamples[i][1]), preproc.getImageAsBooleanMap());
		}
	}
	
}
