package com.arnavsankaran.preprocessing;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
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
		processImage();
		Mat scaled = new Mat(Configuration.sizeX, Configuration.sizeY, source.type());
		Imgproc.resize(source, scaled, new Size(Configuration.sizeX, Configuration.sizeY));
		source = scaled;
	}
	
	public void processImage() {
		Mat greyscale = new Mat(source.rows(), source.cols(), source.type());
		Mat binary = new Mat(source.rows(), source.cols(), source.type());
		Imgproc.cvtColor(source, greyscale, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(greyscale, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(binary, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		int largestContour = 0;
		for(int i = 0; i < contours.size(); i++){
	        if (Imgproc.contourArea(contours.get(i)) > Imgproc.contourArea(contours.get(largestContour))){
	            largestContour = i;
	        }
	    }
		Rect rect = Imgproc.boundingRect(contours.get(largestContour));
        Imgproc.rectangle(source, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255));
        source = source.submat(rect);
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
