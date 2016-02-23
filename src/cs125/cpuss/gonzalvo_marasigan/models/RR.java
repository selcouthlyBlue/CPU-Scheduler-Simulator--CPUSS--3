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
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		int t = 0;
		for (Process currentProcess : processes) {
			currentProcess.start(t);
			int q = 1;
			while (currentProcess.getRemainingBurstTime() != 0) {
				if (q % (iQuantum + 1) != 0) {
					currentProcess.run();
					q++;
					t++;
				} else {
					break;
				}
			}
			if (currentProcess.getRemainingBurstTime() == 0) {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			} else {
				queue.add(currentProcess);
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess));
			}
		}

		while (!queue.isEmpty()) {
			for (int i = 0; i < queue.size(); i++) {
				Process currentProcess = queue.get(i);
				currentProcess.start(t);
				int q = 1;
				while (currentProcess.getRemainingBurstTime() != 0) {
					if (q % (iQuantum + 1) != 0) {
						currentProcess.run();
						q++;
						t++;
					} else {
						break;
					}
				}
				if (currentProcess.getRemainingBurstTime() == 0) {
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					queue.remove(currentProcess);
				} else {
					currentProcess.stop(t);
					timeline.add(new Process(currentProcess));
				}
			}
		}
		Collections.sort(finished);
		this.processes = new ArrayList<Process>(finished);
		getAverage();
	}
}
