package com.arnavsankaran.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.arnavsankaran.configuration.Configuration;

public class NetworkFileParser extends CSVFileParser {
	
	public NetworkFileParser(String filepath) {
		super(filepath);
	}
	
	public int[][] getNetworkData() {
		int[][] networkData = new int[Configuration.sizeX * Configuration.sizeY][Configuration.inputCharacters.length];
		int i = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				String[] lineElements = line.split(",");
				for(int j = 0; j < networkData[i].length; j++) {
					networkData[i][j] = Integer.parseInt(lineElements[j]);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return networkData;
	}

}
