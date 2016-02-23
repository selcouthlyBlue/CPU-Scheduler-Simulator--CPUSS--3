package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class RR extends SchedulingAlgorithm {

	public RR(ArrayList<Process> processes, int iQuantum) {
		super(processes);
		this.sName = "Round Robin Scheduling";
		this.iQuantum = iQuantum;
	}

	/**
	 * Performs scheduling using the Round Robin scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		ArrayList<Process> finished = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		int t = 0;
		while(!processes.isEmpty()) {
			Process currentProcess = processes.remove(0);
			currentProcess.start(t);
			int q = 1;
			while (q != iQuantum + 1 && currentProcess.getRemainingBurstTime() > 0) {
				currentProcess.run();
				q++;
				t++;
			}
			if (currentProcess.getRemainingBurstTime() == 0 || processes.isEmpty()) {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			} else {
				currentProcess.stop(t);
				processes.add(processes.size(), new Process(currentProcess));
				timeline.add(new Process(currentProcess));
			}
		}
		Collections.sort(finished);
		this.processes = new ArrayList<Process>(finished);
		getAverage();
	}
}
