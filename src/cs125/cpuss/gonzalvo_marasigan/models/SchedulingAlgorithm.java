package cs125.cpuss.gonzalvo_marasigan.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Gonzalvo, J.; Marasigan, M.
 *
 */
public abstract class SchedulingAlgorithm {
	protected ArrayList<Process> processes;
	protected ArrayList<Process> timeline;
	protected double dAverageWaitingTime;
	protected double dAverageTurnaroundTime;
	protected String sName;
	protected int iQuantum;
	
	/**
	 * Gets the Average Waiting Time and Average Turnaround Time 
	 */
	protected void getAverage(){
		Collections.sort(processes);
		for(Process process: processes){
			dAverageWaitingTime += process.getWaitingTime();
			dAverageTurnaroundTime += process.getTurnaroundTime();
		}
		dAverageWaitingTime = dAverageWaitingTime/processes.size();
		dAverageTurnaroundTime = dAverageTurnaroundTime/processes.size();
	}

	public abstract void performScheduling();

	/**
	 * Constructor of the Scheduling Algorithm class
	 * @param processes
	 */
	public SchedulingAlgorithm(ArrayList<Process> processes){
		this.processes = new ArrayList<Process>();
		for(Process process : processes){
			this.processes.add(new Process(process));
		}
		this.timeline = new ArrayList<Process>();
	}
	
	public void generateResult(String sOutputFile) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(sOutputFile, false));
		PrintWriter pw = new PrintWriter(bw);
		for(Process process : processes){
			StringBuilder sb = new StringBuilder();
			sb.append(process.getProcessId());
			sb.append(",");
			sb.append(process.getArrivalTime());
			sb.append(",");
			sb.append(process.getBurstTime());
			sb.append(",");
			sb.append(process.getPriority());
			sb.append(",");
			sb.append(process.getWaitingTime());
			sb.append(",");
			sb.append(process.getTurnaroundTime());
			pw.println(sb.toString());
		}
		pw.println(dAverageWaitingTime);
		pw.println(dAverageTurnaroundTime);
		bw.close();
	}
	
	public ArrayList<Process> getTimeline() {
		return timeline;
	}

	public ArrayList<Process> getProcesses() {
		return processes;
	}

	public String getName() {
		return sName;
	}

	public double getAverageWaitingTime() {
		return dAverageWaitingTime;
	}

	public double getAverageTurnaroundTime() {
		return dAverageTurnaroundTime;
	}
	

	public int getQuantum() {
		return iQuantum;
	}
}
