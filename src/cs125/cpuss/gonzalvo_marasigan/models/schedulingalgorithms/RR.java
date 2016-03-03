package cs125.cpuss.gonzalvo_marasigan.models.schedulingalgorithms;

import java.util.ArrayList;
import java.util.Collections;

import cs125.cpuss.gonzalvo_marasigan.models.Process;

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
				currentProcess.stop(time);
				if(!processes.isEmpty() && !processes.get(0).hasArrived(time)){
					processes.add(0, new Process(currentProcess));
				} else {
					processes.add(new Process(currentProcess));
				}
				timeline.add(new Process(currentProcess));
			}
		}
	}
}
