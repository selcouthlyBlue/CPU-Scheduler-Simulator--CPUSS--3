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
	protected ArrayList<Process> finished;
	protected double dAverageWaitingTime;
	protected double dAverageTurnaroundTime;
	protected SchedulingAlgorithmName name;
	protected int iQuantum;
	
	/**
	 * Gets the Average Waiting Time and Average Turnaround Time 
	 */
	private void getAverage(){
		Collections.sort(finished);
		for(Process process: finished){
			dAverageWaitingTime += process.getWaitingTime();
			dAverageTurnaroundTime += process.getTurnaroundTime();
		}
		dAverageWaitingTime = dAverageWaitingTime/finished.size();
		dAverageTurnaroundTime = dAverageTurnaroundTime/finished.size();
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
		this.finished = new ArrayList<Process>();
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
	
	public ArrayList<Process> getProcessTimeline() {
		return timeline;
	}

	public ArrayList<Process> getResults() {
		getAverage();
		return finished;
	}

	public String getName() {
		return name.toString();
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
