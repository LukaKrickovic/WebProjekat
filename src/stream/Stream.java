package stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Stream{

	public void writeToFile(String toWrite, File file) {
		ArrayList<String> lines = (ArrayList)readFromFile(file);
		lines.add(toWrite);
		String buffer = listToString(lines);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			bw.write(buffer);
			bw.close();
		} catch (IOException e) {
			System.out.println("BufferedWriter Error!");
		}
	}
	
	public void blankOutFile(File file) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
			bw.write("");
			bw.close();
		} catch (IOException e) {
			System.out.println("Could not clear out file!");
		}
		
	}
	
	private String listToString(Iterable<String> lines) {
		StringBuilder buffer = new StringBuilder();
		for(String temp : lines) {
			buffer.append(temp);
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	public Iterable<String> readFromFile(File file){
		checkIfFileExists(file);
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Could not read file!");
		}
		return lines;
	}
	
	private void checkIfFileExists(File file) {
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Could not create a new file!");
			}
	}

}
