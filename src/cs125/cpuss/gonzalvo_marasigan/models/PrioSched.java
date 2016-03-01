package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrioSched extends SchedulingAlgorithm {

	protected Comparator<Process> priorityOrder = new Comparator<Process>() {
		@Override
		public int compare(Process p1, Process p2) {
			return p1.getCurrentPriority() - p2.getCurrentPriority();
		}
	};

	public PrioSched(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.PRIOSCHED;
	}

	/**
	 * Performs scheduling using the Priority scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		Process currentProcess = processes.remove(0);
		int t = currentProcess.getArrivalTime();
		for (Process process : processes) {
			if (!queue.isEmpty()) {
				if (currentProcess.hasLowerPriority(Collections.min(queue,
						priorityOrder))) {
					currentProcess.stop(t);
					if (currentProcess.getLength() != 0) {
						timeline.add(new Process(currentProcess));
					}
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(t);
				}
			}
			while (t != process.getArrivalTime()) {
				currentProcess.run();
				t++;
				if (currentProcess.isFinished()) {
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
			}
			if (currentProcess != null
					&& currentProcess.hasLowerPriority(process)) {
				currentProcess.stop(t);
				if (currentProcess.getLength() != 0) {
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(t);
			} else if (currentProcess == null) {
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
			} else {
				queue.add(process);
			}
		}
		while (!queue.isEmpty()) {
			while (!queue.isEmpty()
					&& !currentProcess.hasLowerPriority(Collections.min(queue,
							priorityOrder))) {
				currentProcess.run();
				t++;
				if (currentProcess.isFinished()) {
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(t);
				}
			}
			if (currentProcess.getEndTime() != 0 && !queue.isEmpty()) {
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess));
			}
			if (!queue.isEmpty()) {
				queue.add(currentProcess);
				currentProcess = queue.remove(queue.indexOf(Collections.min(queue,
						priorityOrder)));
				currentProcess.start(t);
			} else {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			}
		}
	}
}
