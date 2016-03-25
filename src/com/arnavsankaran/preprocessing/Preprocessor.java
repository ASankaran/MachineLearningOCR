package com.arnavsankaran.preprocessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.arnavsankaran.configuration.Configuration;

public class Preprocessor {
	
	private String fileLocation;
	private Mat source;
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public Preprocessor(String fileLocation) {
		this.fileLocation = fileLocation;
		source =  Imgcodecs.imread(fileLocation);
		Mat scaled = new Mat(Configuration.sizeX, Configuration.sizeY, source.type());
		Imgproc.resize(source, scaled, new Size(Configuration.sizeX, Configuration.sizeY));
		source = scaled;
	}
	
	public boolean[][] getImageAsBooleanMap() {
		Mat greyscale = new Mat(source.rows(), source.cols(), source.type());
		Mat binary = new Mat(source.rows(), source.cols(), source.type());
		Imgproc.cvtColor(source, greyscale, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(greyscale, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
		boolean[][] imageMap = new boolean[binary.rows()][binary.cols()];
		for(int i = 0; i < imageMap.length; i++) {
			for(int j = 0; j < imageMap[i].length; j++) {
				double[] pixelValue = binary.get(i, j);
				imageMap[i][j] = pixelValue[0] == 255; 
			}
		}
		return imageMap;
	}
	
}
