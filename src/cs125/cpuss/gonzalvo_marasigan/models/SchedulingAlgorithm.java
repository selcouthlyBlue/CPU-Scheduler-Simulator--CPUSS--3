package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Implementation of the CPU scheduling algorithms namely, First Come, First Serve Scheduling;
 * Non-preemptive Shortest Job First Scheduling; Preemptive Shortest Job First Scheduling; Non-preemptive
 * Priority Scheduling; Preemptive priority Scheduling, and; Round-Robin Scheduling.
 * @see http://www.cs.columbia.edu/~junfeng/10sp-w4118/lectures/l13-sched.pdf
 * @author Gonzalvo, J.; Marasigan, M.
 */
public abstract class SchedulingAlgorithm {
	protected ArrayList<Process> processes;
	protected ArrayList<Process> timeline;
	protected ArrayList<Process> finished;
	protected SchedulingAlgorithmName name;
	protected int iQuantum;
	
	/**
	 * Compares processes with respect to their arrival times
	 */
	protected Comparator<Process> arrivalOrder = new Comparator<Process>(){
		@Override
		public int compare(Process process1, Process process2) {
			return process1.getArrivalTime() - process2.getArrivalTime();
		}
	};

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
	
	public ArrayList<Process> getProcessTimeline() {
		return timeline;
	}

	public ArrayList<Process> getResults() {
		Collections.sort(finished);
		return finished;
	}

	public String getName() {
		return name.toString();
	}

	public double getAverageWaitingTime() {
		double dAverageWaitingTime = 0;
		for(Process process: finished){
			dAverageWaitingTime += process.getWaitingTime();
		}
		return dAverageWaitingTime/finished.size();
	}

	public double getAverageTurnaroundTime() {
		double dAverageTurnaroundTime = 0;
		for(Process process: finished){
			dAverageTurnaroundTime += process.getTurnaroundTime();
		}
		return dAverageTurnaroundTime/finished.size();
	}
	
	public int getQuantum() {
		return iQuantum;
	}
}
