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
	 */
	@Override
	public void performScheduling() {
		Collections.sort(processes, new Process());
		int prevTurnaroundTime = processes.get(0).getArrivalTime();
		for(Process process: processes){
			if(prevTurnaroundTime < process.getArrivalTime()){
				Process nullProcess = new Process();
				nullProcess.start(prevTurnaroundTime);
				while (prevTurnaroundTime != process.getArrivalTime()){
					prevTurnaroundTime++;
				}
				nullProcess.stop(prevTurnaroundTime);
				if(nullProcess.getLength() != 0){
					timeline.add(nullProcess);
				}
			}
			process.start(prevTurnaroundTime);
			process.destroy(prevTurnaroundTime);
			timeline.add(new Process(process.getProcessId(), 
							process.getStartTime(), process.getEndTime()));
			prevTurnaroundTime = process.getTurnaroundTime() + process.getArrivalTime();
		}
		getAverage();
	}
}
