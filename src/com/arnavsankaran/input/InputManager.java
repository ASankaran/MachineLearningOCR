package com.arnavsankaran.input;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import com.arnavsankaran.configuration.Configuration;
import com.arnavsankaran.neuralnetwork.NeuralNetwork;
import com.arnavsankaran.neuralnetwork.Prediction;
import com.arnavsankaran.preprocessing.MultiPreprocessor;
import com.arnavsankaran.preprocessing.Preprocessor;

public class InputManager {
	
	private String filepath;
	private NeuralNetwork network;
	private ArrayList<Rectangle> characterLocations;
	
	private String networkPrediction;
	
	public InputManager(String filepath, NeuralNetwork network) {
		this.filepath  = filepath;
		this.network = network;
		networkPrediction = "";
		findCharacterLocations();
	}
	
	public void findCharacterLocations() {
		MultiPreprocessor multiProc = new MultiPreprocessor(filepath);
		characterLocations = multiProc.getAllCharacters();
	}
	
	public String getNetworkPrediction() {
		for(int i = 0; i < characterLocations.size(); i++) {
			if( i > 0 && characterLocations.get(i).x - characterLocations.get(i - 1).x > 22) {
				networkPrediction += ' ';
			}
			if(i > 0 && characterLocations.get(i).y - characterLocations.get(i - 1).y > 20) {
				networkPrediction += ' ';
			}
			Rect rect = new Rect(characterLocations.get(i).x - 1, characterLocations.get(i).y - 1, characterLocations.get(i).width + 1, characterLocations.get(i).height + 1);
			Mat source = Imgcodecs.imread(filepath);
			Preprocessor preProc = new Preprocessor(source.submat(rect));
			Prediction prediction = network.getNetworkPrediction(preProc.getImageAsBooleanMap());
			networkPrediction += Configuration.inputCharacters[prediction.position];
		}
		return networkPrediction;
	}
}
