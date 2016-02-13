package cs125.cpuss.gonzalvo_marasigan.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs125.cpuss.gonzalvo_marasigan.models.FCFS;
import cs125.cpuss.gonzalvo_marasigan.models.Process;
import cs125.cpuss.gonzalvo_marasigan.models.SJF_P;
import cs125.cpuss.gonzalvo_marasigan.models.SchedulingAlgorithm;

public class SchedulerServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");
	}

	public static void main(String[] args){
		String sCsvFile = args[0];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(sCsvFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(br != null){
				try {
					String line = null;
					String splitter = ",";
					ArrayList<Process> processes = new ArrayList<Process>();
					while((line = br.readLine()) != null){
						String[] row = line.split(splitter);
						Process process = new Process(Integer.parseInt(row[0]), 
								Integer.parseInt(row[1]), 
								Integer.parseInt(row[2]), 
								Integer.parseInt(row[3]));
						processes.add(process);
					}
					SchedulingAlgorithm fcfs = new FCFS(processes);
					fcfs.performScheduling();
					fcfs.generateResult("fcfs_" + sCsvFile);
					reset(processes);
					SchedulingAlgorithm sjfp = new SJF_P(processes);
					sjfp.performScheduling();
					sjfp.generateResult("sjfp_" + sCsvFile);
					/*SchedulingAlgorithm fcfs = new FCFS(processes);
					fcfs.performScheduling();
					fcfs.generateResult("fcfs_" + sCsvFile);
					SchedulingAlgorithm sjfnp = new SJF_NP(processes);
					sjfnp.performScheduling();
					SchedulingAlgorithm sjfp = new SJF_P(processes);
					sjfp.performScheduling();
					SchedulingAlgorithm prioSched = new PrioSched(processes);
					prioSched.performScheduling();
					RR rr = new RR(processes);
					rr.setQuantum(Integer.parseInt(args[1]));
					rr.performScheduling();
					 */
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void reset(ArrayList<Process> processes) {
		for(Process process : processes){
			process.reset();
		}
	}
}
