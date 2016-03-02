package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class RR extends SchedulingAlgorithm {

	public RR(ArrayList<Process> processes, int iQuantum) {
		super(processes);
		this.name = SchedulingAlgorithmName.RR;
		this.iQuantum = iQuantum;
	}

	/**
	 * Performs scheduling using the Round Robin scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		Collections.sort(processes, arrivalOrder);
		int time = 0;
		while(!processes.isEmpty()) {
			Process currentProcess = processes.remove(0);
			currentProcess.start(time);
			int q = 0;
			while (q < iQuantum && !currentProcess.isFinished()) {
				currentProcess.run();
				q++;
				time++;
			}
			if (currentProcess.isFinished() || processes.isEmpty()) {
				currentProcess.destroy(time);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			} else {
				while(!processes.isEmpty() && !processes.get(0).hasArrived(time)){
					currentProcess.run();
					time++;
				}
				currentProcess.stop(time);
				processes.add(processes.size(), new Process(currentProcess));
				timeline.add(new Process(currentProcess));
			}
		}
	}
}
