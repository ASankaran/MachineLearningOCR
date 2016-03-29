package com.arnavsankaran.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVFileParser {
	
	String filePath;
	
	public CSVFileParser(String filePath) {
		this.filePath = filePath;
	}
	
	public String[][] getParsedFile() {
		String rawFileContents;
		try {
			rawFileContents = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			rawFileContents = "";
		}
		String[] fileLines = rawFileContents.split("\r\n");
		String[][] parsedFileContents = new String[fileLines.length][];
		for(int i = 0; i < fileLines.length; i++) {
			String[] fileLineElements = fileLines[i].split(",");
			parsedFileContents[i] = fileLineElements;
		}
		return parsedFileContents;
	}

}
