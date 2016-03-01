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
		Collections.sort(processes, new Process());
		
		Process currentProcess = processes.remove(0);
		int t = 0;
		
		for (Process process : processes) {
			while(t != process.getArrivalTime()){
				currentProcess.run();
				t++;
				if(currentProcess.isFinished()){
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
			}
			if (currentProcess == null) {
				if (t < process.getArrivalTime() && !queue.isEmpty()) {
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(t);
					while (t < process.getArrivalTime()) {
						currentProcess.run();
						t++;
						if (currentProcess.isFinished()) {
							currentProcess.destroy(t);
							timeline.add(new Process(currentProcess));
							finished.add(currentProcess);
							currentProcess = queue.remove(queue
									.indexOf(Collections.min(queue,
											priorityOrder)));
							currentProcess.start(t);
						}
					}
					queue.add(process);
				} else {
					currentProcess = process;
				}
			}
			else { // case where t == process.getArrivalTime()
				queue.add(process);
			}
		}
		if(currentProcess != null){
			while (!currentProcess.isFinished()) {
				currentProcess.run();
				t++;
			}
			currentProcess.destroy(t);
			timeline.add(new Process(currentProcess));
			finished.add(currentProcess);
		}
		while (!queue.isEmpty()) {
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, priorityOrder)));
			currentProcess.start(t);
			while (!currentProcess.isFinished()) {
				currentProcess.run();
				t++;
			}
			currentProcess.destroy(t);
			timeline.add(new Process(currentProcess));
			finished.add(currentProcess);
		}
	}
}
