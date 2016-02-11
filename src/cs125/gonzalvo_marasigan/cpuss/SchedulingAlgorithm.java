package cs125.gonzalvo_marasigan.cpuss;

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
		this.processes = new ArrayList<Process>(processes);
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
}
