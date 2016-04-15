package com.arnavsankaran.training;

import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.preprocessing.Preprocessor;

public class Trainer {
	
	String filePath;
	TrainingFileParser fileParser;
	
	TrainingItem[] trainingData;
	
	NeuralNetwork network;
	
	public Trainer(String filePath, NeuralNetwork network) {
		this.filePath = filePath;
		fileParser = new TrainingFileParser(filePath);
		this.network = network;
		trainingData = fileParser.getTrainingSamples();
	}
	
	public void trainNetwork() {
		for(int i = 0; i < trainingData.length; i++) {
			Preprocessor preproc = new Preprocessor(fileParser.getFileDirectory() + "\\" + trainingData[i].getFileName());
			network.addTrainingNetworkData(trainingData[i].getConfigPosition(), preproc.getImageAsBooleanMap());
		}
	}
	
}
