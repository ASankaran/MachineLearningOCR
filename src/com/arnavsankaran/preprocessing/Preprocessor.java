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
	
	protected String fileLocation;
	private Mat source;
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public Preprocessor(String fileLocation) {
		this.fileLocation = fileLocation;
		source =  Imgcodecs.imread(fileLocation);
		processImage();
		source = scaleWithAspectRatio(source, Configuration.sizeX, Configuration.sizeY);
	}
	
	public Preprocessor(Mat source) {
		this.source = source;
		processImage();
		this.source = scaleWithAspectRatio(this.source, Configuration.sizeX, Configuration.sizeY);
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
        source = source.submat(rect);
	}
	
	public static Mat scaleWithAspectRatio(Mat source, int sizeX, int sizeY) {
		Mat scaled = new Mat(sizeX, sizeY, source.type());
		for(int i = 0; i < scaled.rows(); i++) {
			for (int j = 0; j <scaled.cols(); j++) {
				scaled.put(i, j, new double[] {255, 255, 255} );
			}
		}
		
		int largerSize = Integer.max(source.cols(), source.rows());
		double scaleFactor = (double) sizeX / (double)largerSize;
		Rect area = new Rect();
		
		if(source.cols() > source.rows()) {
			area.width = sizeX;
			area.x = 0;
			area.height = (int) (source.rows() * scaleFactor);
			area.y = (sizeX - area.height) / 2;
		} else {
			area.y = 0;
			area.height = sizeY;
			area.width = (int) (source.cols() * scaleFactor);
			area.x = (sizeY - area.width) / 2;
		}
		
		Imgproc.resize(source, scaled.submat(area), new Size(area.width, area.height));
		return scaled;
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
