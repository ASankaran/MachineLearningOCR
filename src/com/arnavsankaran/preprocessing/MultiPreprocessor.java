package com.arnavsankaran.preprocessing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MultiPreprocessor extends Preprocessor {

	public MultiPreprocessor(String fileLocation) {
		super(fileLocation);
	}
	
	public ArrayList<Rectangle> getAllCharacters() {
		Mat source = Imgcodecs.imread(fileLocation);
		Mat destination = new Mat(source.rows(), source.cols(), source.type());
		Imgproc.cvtColor(source, destination, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(destination, destination, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		ArrayList<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(destination, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		ArrayList<Rectangle> letters = new ArrayList<>();
		for(MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			letters.add(new Rectangle(rect.x, rect.y, rect.width, rect.height));
		}
		Collections.sort(letters, new Comparator<Rectangle>() {
			public int compare(Rectangle r1, Rectangle r2) {
				if(r1.y - r2.y > 20) {
					return 1 * (r1.y - r2.y);
				} else if(r2.y - r1.y > 20) {
					return -1 * (r2.y - r1.y);
				}
				return Integer.compare(r1.x, r2.x);
			}
		});
		
		for(int i  = letters.size() - 1; i > -1; i--) {
			if(letters.get(i).width * letters.get(i).height <= 15) {
				letters.remove(i);
			}
		}
		
		return letters;
	}

}
