package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class FCFS extends SchedulingAlgorithm{

	public FCFS(ArrayList<Process> processes) {
		super(processes);
		this.sName = "First Come, First Serve Scheduling";
	}
	
	/**
	 * Performs scheduling using the First Come First Serve scheduling algorithm.
	 * @return 
	 */
	@Override
	public void performScheduling() {
		Collections.sort(processes, new Process());
		int prevTurnaroundTime = 0;
		for(Process process: processes){
			process.start(prevTurnaroundTime);
			process.destroy(prevTurnaroundTime);
			prevTurnaroundTime = process.getTurnaroundTime() + process.getArrivalTime();
		}
		getAverage();
	}
}
