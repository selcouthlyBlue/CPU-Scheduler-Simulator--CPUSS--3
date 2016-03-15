package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class SJF_NP extends SJF_P {
	
	public SJF_NP(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.SJF_NP;
	}
	
	/**
	 * Performs scheduling using the Non-preemptive Shortest
	 * Job First Algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, arrivalOrder);
		Process currentProcess = null;
		int time = 0;
		for (Process process : processes) {
			if(currentProcess == null){
				currentProcess = process;
				time = currentProcess.getArrivalTime();
				continue;
			}
			while(!process.hasArrived(time)){
				currentProcess.run();
				time++;
				if(currentProcess.isFinished()){
					currentProcess.destroy(time);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
			}
			if (currentProcess == null) {
				if(!process.hasArrived(time) && !queue.isEmpty()){
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(time);
					while(!process.hasArrived(time)){
						currentProcess.run();
						time++;
						if (currentProcess.isFinished()) {
							currentProcess.destroy(time);
							timeline.add(new Process(currentProcess));
							finished.add(currentProcess);
							currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
							currentProcess.start(time);
						}
					}
					queue.add(process);
				} else {
					currentProcess = new Process(process);
					currentProcess.start(time);
				}
			}
			else { // case where t == process.getArrivalTime()
				queue.add(process);
			}
		}
		if(currentProcess != null){
			while (!currentProcess.isFinished()) {
				currentProcess.run();
				time++;
			}
			currentProcess.destroy(time);
			timeline.add(new Process(currentProcess));
			finished.add(currentProcess);
		}
		while (!queue.isEmpty()) {
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
			currentProcess.start(time);
			while (!currentProcess.isFinished()) {
				currentProcess.run();
				time++;
			}
			currentProcess.destroy(time);
			timeline.add(new Process(currentProcess));
			finished.add(currentProcess);
		}
	}

}
