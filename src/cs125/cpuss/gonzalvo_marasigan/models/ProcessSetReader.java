package cs125.cpuss.gonzalvo_marasigan.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessSetReader {

	private ProcessSetReader() {
		
	}
	
	public static ArrayList<Process> getProcesses(String test_filename) throws NumberFormatException, IOException{
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(test_filename));
		String line = null;
		String splitter = ",";
		ArrayList<Process> processes = new ArrayList<Process>();
		while((line = br.readLine()) != null){
			String[] row = line.split(splitter);
			Process process = new Process(Integer.parseInt(row[0]), 
						Integer.parseInt(row[1]), 
						Integer.parseInt(row[2]), 
						(row.length == 3 ? 0 : Integer.parseInt(row[3])));
			processes.add(process);
		}
		br.close();
		return processes;
	}

}
