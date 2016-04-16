package com.arnavsankaran.neuralnetwork;

import com.arnavsankaran.configuration.Configuration;

public class NeuralNetwork {
	
	private Neuron[][] neuralnetwork;
	
	public NeuralNetwork() {
		neuralnetwork = new Neuron[Configuration.sizeX][Configuration.sizeY];
		for(int i = 0; i < neuralnetwork.length; i++) {
			for(int j = 0; j < neuralnetwork[i].length; j++) {
				neuralnetwork[i][j] = new Neuron();
			}
		}
	}
	
	public void resetNetworkValues() {
		for(int i = 0; i < neuralnetwork.length; i++) {
			for(int j = 0; j < neuralnetwork[i].length; j++) {
				neuralnetwork[i][j].resetNeuronValue();
			}
		}
	}
	
	public void addTrainingNetworkData(int position, boolean[][] pixelValue) {
		for(int i = 0; i < neuralnetwork.length; i++) {
			for(int j = 0; j < neuralnetwork[i].length; j++) {
				neuralnetwork[i][j].addTrainingData(position, pixelValue[i][j]);
			}
		}
	}
	
	public Prediction getNetworkPrediction(boolean[][] pixelValues) {
		if(pixelValues.length != Configuration.sizeY || pixelValues[0].length != Configuration.sizeX) {
			return null;
		}
		int[] weights = new int[Configuration.inputCharacters.length];
		for(int i = 0; i < neuralnetwork.length; i++) {
			for(int j = 0; j < neuralnetwork[i].length; j++) {
				Prediction neuronPrediction = neuralnetwork[i][j].getPrediction(pixelValues[i][j]);
				weights[neuronPrediction.position] += neuronPrediction.weight;
			}
		}
		int predictedPosition = 0;
		int predictedWeight = 0;
		for(int i = 0; i < weights.length; i++) {
			if(weights[i] > predictedWeight) {
				predictedWeight = weights[i];
				predictedPosition = i;
			}
		}
		return new Prediction(predictedPosition, predictedWeight);
	}
	
	public Neuron[][] getNeurons() {
		return neuralnetwork;
	}

}
