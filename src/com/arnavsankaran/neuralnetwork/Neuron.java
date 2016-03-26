package com.arnavsankaran.neuralnetwork;

import com.arnavsankaran.configuration.Configuration;

public class Neuron {
	
	private int[] characterProbabilities;
	
	public Neuron() {
		characterProbabilities = new int[Configuration.inputCharacters.length];
	}
	
	public void resetNeuronValue() {
		for(int i = 0; i < characterProbabilities.length; i++) {
			characterProbabilities[i] = (int) Math.round(255 * Math.random());
		}
	}
	
	public void addTrainingData(int position, boolean pixelValue) {
		Prediction neuronPrediction = getPrediction(pixelValue);
		if(neuronPrediction.position != position) {
			characterProbabilities[position] += 2;
			for(int i = 0; i < characterProbabilities.length; i++) {
				characterProbabilities[i] -= 1;
				if(characterProbabilities[i] < 0) {
					characterProbabilities[i] = 0;
				}
			}
			if(characterProbabilities[position] > 255) {
				characterProbabilities[position] = 255;
			}
		}
	}
	
	public Prediction getPrediction(boolean pixelValue) {
		int positionOne = 0;
		int positionTwo = 0;
		if(pixelValue) {
			for (int i = 0; i < characterProbabilities.length; i++) {
				if(characterProbabilities[i] > characterProbabilities[positionOne]) {
					positionTwo = positionOne;
					positionOne = i;
				} else if (characterProbabilities[i] > characterProbabilities[positionTwo]) {
					positionTwo = i;
				}
			}
		} else {
			for (int i = 0; i < characterProbabilities.length; i++) {
				if(characterProbabilities[i] < characterProbabilities[positionOne]) {
					positionTwo = positionOne;
					positionOne = i;
				} else if (characterProbabilities[i] < characterProbabilities[positionTwo]) {
					positionTwo = i;
				}
			}
		}
		return new Prediction(positionOne, Math.abs(characterProbabilities[positionOne] - characterProbabilities[positionTwo]));
	}

}
