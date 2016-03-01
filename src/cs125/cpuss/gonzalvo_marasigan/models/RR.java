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
		Collections.sort(processes, new Process());
		int t = 0;
		while(!processes.isEmpty()) {
			Process currentProcess = processes.remove(0);
			currentProcess.start(t);
			int q = 0;
			while (q != iQuantum && !currentProcess.isFinished()) {
				currentProcess.run();
				q++;
				t++;
			}
			if (currentProcess.isFinished() || processes.isEmpty()) {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			} else {
				currentProcess.stop(t);
				processes.add(processes.size(), new Process(currentProcess));
				timeline.add(new Process(currentProcess));
			}
		}
	}
}
