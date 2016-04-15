package com.arnavsankaran.configuration;

public class ConfigurationUtils {
	
	public static int getConfigPosition(char key) {
		for(int i = 0; i < Configuration.inputCharacters.length; i++) {
			if(Configuration.inputCharacters[i] == key) {
				return i;
			}
		}
		return -1;
	}
	
}
