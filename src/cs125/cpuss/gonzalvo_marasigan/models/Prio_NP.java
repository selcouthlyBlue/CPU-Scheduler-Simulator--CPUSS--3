package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class Prio_NP extends PrioSched {

	public Prio_NP(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.PRIO_NP;
	}


	/**
	 * Performs scheduling using the Priority scheduling algorithm.
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
				if (!process.hasArrived(time) && !queue.isEmpty()) {
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(time);
					while (!process.hasArrived(time)) {
						currentProcess.run();
						time++;
						if (currentProcess.isFinished()) {
							currentProcess.destroy(time);
							timeline.add(new Process(currentProcess));
							finished.add(currentProcess);
							currentProcess = queue.remove(queue
									.indexOf(Collections.min(queue,
											priorityOrder)));
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
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, priorityOrder)));
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
