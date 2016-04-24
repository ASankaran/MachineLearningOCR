package com.arnavsankaran.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.neuralnetwork.Neuron;

public class NetworkFileSaver {
	
	NeuralNetwork network;
	
	public NetworkFileSaver(NeuralNetwork network) {
		this.network = network;
	}
	
	public void writeToFile(String filepath) {
		Neuron[][] neurons = network.getNeurons();
		for(int i = 0; i < neurons.length; i++) {
			for(int j = 0; j < neurons[i].length; j++) {
				String contents = "";
				int[] characterProbabilities = neurons[i][j].getCharacterProbabilities();
				for(int k = 0; k < characterProbabilities.length; k++) {
					contents += characterProbabilities[k] + ",";
				}
				contents = contents.substring(0, contents.length() - 1);
				contents += "\n";
				try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
					writer.print(contents);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
