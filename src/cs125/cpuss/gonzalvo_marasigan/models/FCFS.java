package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class FCFS extends SchedulingAlgorithm{

	public FCFS(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.FCFS;
	}
	
	/**
	 * Performs scheduling using the First Come First Serve scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		Collections.sort(processes, arrivalOrder);
		int t = processes.get(0).getArrivalTime();
		for(Process process: processes){
			process.start(t);
			process.destroy(t);
			timeline.add(new Process(process));
			finished.add(process);
			t = process.getTurnaroundTime() + process.getArrivalTime();
		}
	}
}
