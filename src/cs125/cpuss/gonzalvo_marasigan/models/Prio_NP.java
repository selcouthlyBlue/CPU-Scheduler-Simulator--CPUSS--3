package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class Prio_NP extends PrioSched {

	public Prio_NP(ArrayList<Process> processes) {
		super(processes);
		this.sName = "Priority Non-Preemptive Scheduling";
	}


	/**
	 * Performs scheduling using the Priority scheduling algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> finished = new ArrayList<Process>();
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		
		Process currentProcess = processes.remove(0);
		int t = 0;
		
		for (Process process : processes) {
			while(t != process.getArrivalTime() || currentProcess.getRemainingBurstTime() == 0){
				if(currentProcess.getRemainingBurstTime() == 0){
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}

				currentProcess.run();
				t++;
			}
			if (currentProcess == null) {
				if (process.getRemainingBurstTime() < Collections.min(queue, priorityOrder).getRemainingBurstTime()) {
					currentProcess = process;
					currentProcess.start(t);
					t++;
				}
				else {
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, priorityOrder)));
					currentProcess.start(t);
					t++;
				}
			}
			else { // case where t == process.getArrivalTime()
				queue.add(process);
			}
		}
		
		while (currentProcess.getRemainingBurstTime() != 0) {
			currentProcess.run();
			t++;
		}
		currentProcess.destroy(t);
		timeline.add(new Process(currentProcess));
		finished.add(currentProcess);
		
		while (!queue.isEmpty()) {
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, priorityOrder)));
			currentProcess.start(t);
			while (currentProcess.getRemainingBurstTime() != 0) {
				currentProcess.run();
				t++;
			}
			currentProcess.destroy(t);
			timeline.add(new Process(currentProcess));
			finished.add(currentProcess);
		}
		Collections.sort(finished);
		this.processes = new ArrayList<Process>(finished);
		getAverage();
	}
}
