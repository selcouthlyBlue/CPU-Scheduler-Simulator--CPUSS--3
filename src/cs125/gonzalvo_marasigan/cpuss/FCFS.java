package cs125.gonzalvo_marasigan.cpuss;

import java.util.ArrayList;
import java.util.Collections;

public class FCFS extends SchedulingAlgorithm{

	public FCFS(ArrayList<Process> processes) {
		super(processes);
	}
	
	/**
	 * Performs scheduling using the First Come First Serve scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		Collections.sort(processes, new Process());
		int prevTurnaroundTime = 0;
		for(Process process: processes){
			process.start(prevTurnaroundTime);
			process.destroy(prevTurnaroundTime);
			prevTurnaroundTime = process.getTurnaroundTime();
		}
		getAverage();
	}
}
