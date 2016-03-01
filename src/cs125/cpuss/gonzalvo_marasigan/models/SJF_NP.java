package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class SJF_NP extends SJF_P {
	
	public SJF_NP(ArrayList<Process> processes) {
		super(processes);
		this.sName = "Non-preemptive Shortest Job First Scheduling";
	}
	
	/**
	 * Performs scheduling using the Non-preemptive Shortest
	 * Job First Algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> finished = new ArrayList<Process>();
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
				if (!process.hasHigherBurstTime(Collections.min(queue, burstOrder))) {
					currentProcess = process;
					currentProcess.start(t);
					t++;
				}
				else {
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(t);
					t++;
				}
			}
			else { // case where t == process.getArrivalTime()
				queue.add(process);
				
			}
		}
		while (!currentProcess.isFinished()) {
			currentProcess.run();
			t++;
		}
		currentProcess.destroy(t);
		timeline.add(new Process(currentProcess));
		finished.add(currentProcess);
		while (!queue.isEmpty()) {
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
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
