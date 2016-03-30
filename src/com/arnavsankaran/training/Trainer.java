package com.arnavsankaran.training;

import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.preprocessing.Preprocessor;
import com.arnavsankaran.util.CSVFileParser;

public class Trainer {
	
	String filePath;
	CSVFileParser fileParser;
	
	TrainingItem[] trainingData;
	
	NeuralNetwork network;
	
	public Trainer(String filePath, NeuralNetwork network) {
		this.filePath = filePath;
		fileParser = new CSVFileParser(filePath);
		this.network = network;
		String[][] trainingSamples = fileParser.getParsedFile();
		trainingData = new TrainingItem[trainingSamples.length];
		for(int i = 0; i < trainingSamples.length; i++) {
			trainingData[i] = new TrainingItem(trainingSamples[i][0], Integer.parseInt(trainingSamples[i][1]));
		}
	}
	
	public void trainNetwork() {
		for(int i = 0; i < trainingData.length; i++) {
			Preprocessor preproc = new Preprocessor(fileParser.getFileDirectory() + "\\" + trainingData[i].getFileName());
			network.addTrainingNetworkData(trainingData[i].getConfigPosition(), preproc.getImageAsBooleanMap());
		}
	}
	
}
