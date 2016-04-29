package com.arnavsankaran;

import com.arnavsankaran.input.InputManager;
import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.training.Trainer;
import com.arnavsankaran.util.NetworkFileSaver;

public class OCRTool {
	
	private NeuralNetwork network;
	
	public OCRTool() {
		network = new NeuralNetwork();
		network.resetNetworkValues();
	}
	
	public void trainNetworkFromDataset(String filepath) {
		Trainer trainer = new Trainer(filepath, network);
		trainer.trainNetwork();
	}
	
	public void loadNetworkStateFromFile(String filepath) {
		network.loadNeuronData(filepath);
	}
	
	public void saveNetworkStateToFile(String filepath) {
		NetworkFileSaver fileSaver = new NetworkFileSaver(network);
		fileSaver.writeToFile(filepath);
	}
	
	public String getNetworkOutput(String filepath) {
		InputManager inputmngr = new InputManager(filepath, network);
		return inputmngr.getNetworkPrediction();
	}

}
