package cs125.cpuss.gonzalvo_marasigan.models.schedulingalgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cs125.cpuss.gonzalvo_marasigan.models.Process;

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
		Collections.sort(processes, arrivalOrder);
		Process currentProcess = processes.remove(0);
		int time = currentProcess.getArrivalTime();
		for (Process process : processes) {
			if (!queue.isEmpty()) {
				if (currentProcess.hasLowerPriority(Collections.min(queue,
						priorityOrder))) {
					currentProcess.stop(time);
					if (currentProcess.getLength() != 0) {
						timeline.add(new Process(currentProcess));
					}
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(time);
				}
			} else if(currentProcess == null){
				currentProcess = process;
				continue;
			}
			while (!process.hasArrived(time)) {
				currentProcess.run();
				time++;
				if (currentProcess.isFinished()) {
					currentProcess.destroy(time);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
			}
			if (currentProcess != null
					&& currentProcess.hasLowerPriority(process)) {
				currentProcess.stop(time);
				if (currentProcess.getLength() != 0) {
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(time);
			} else if (currentProcess == null) {
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
				time++;
				if (currentProcess.isFinished()) {
					currentProcess.destroy(time);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(time);
				}
			}
			if (currentProcess.getEndTime() != 0 && !queue.isEmpty()) {
				currentProcess.stop(time);
				timeline.add(new Process(currentProcess));
			}
			if (!queue.isEmpty()) {
				queue.add(currentProcess);
				currentProcess = queue.remove(queue.indexOf(Collections.min(queue,
						priorityOrder)));
				currentProcess.start(time);
			} else {
				currentProcess.destroy(time);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			}
		}
	}
}
