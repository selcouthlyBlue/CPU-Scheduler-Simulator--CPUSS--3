package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrioSched extends SchedulingAlgorithm {

	private Comparator<Process> priorityOrder = new Comparator<Process>() {

		@Override
		public int compare(Process p1, Process p2) {
			return p1.getCurrentPriority() - p2.getCurrentPriority();
		}

	};
	
	private Comparator<Process> arrivalOrder = new Comparator<Process>(){

		@Override
		public int compare(Process p1, Process p2) {
			return p1.getArrivalTime() - p2.getArrivalTime();
		}
		
	};

	public PrioSched(ArrayList<Process> processes) {
		super(processes);
		this.sName = "Priority Scheduling";
	}

	/**
	 * Performs scheduling using the Priority scheduling algorithm.
	 */
	@Override
	public void performScheduling() {
		ArrayList<Process> finished = new ArrayList<Process>();
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		Process currentProcess = processes.remove(0);
		int t = 0;
		while (t < currentProcess.getArrivalTime()) {
			t++;
		}
		for (Process process : processes) {
			if (!queue.isEmpty()) {
				if (currentProcess.getCurrentPriority() > Collections.min(
						queue, priorityOrder).getCurrentPriority()) {
					currentProcess.stop(t);
					if (currentProcess.getRemainingBurstTime() != currentProcess
							.getBurstTime()) {
						timeline.add(new Process(currentProcess));
					}
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, priorityOrder)));
					currentProcess.start(t);
				} else {
					currentProcess.stop(t);
					if (currentProcess.getRemainingBurstTime() != currentProcess
							.getBurstTime()) {
						timeline.add(new Process(currentProcess));
					}
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, arrivalOrder)));
					currentProcess.start(t);
				}
			}
			while (t != process.getArrivalTime()
					|| currentProcess.getRemainingBurstTime() == 0) {
				if (currentProcess.getRemainingBurstTime() == 0) {
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
				currentProcess.run();
				t++;
			}
			if (currentProcess != null
					&& currentProcess.getCurrentPriority() > process
							.getCurrentPriority()) {
				currentProcess.stop(t);
				if (currentProcess.getRemainingBurstTime() != currentProcess
						.getBurstTime()) {
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(t);
			} else if (currentProcess == null) {
				currentProcess = process;
			} else {
				queue.add(process);
			}
		}
		while (!queue.isEmpty()) {
			while (!queue.isEmpty()
					&& currentProcess.getCurrentPriority() <= Collections.min(
							queue, priorityOrder).getCurrentPriority()) {
				if (currentProcess.getRemainingBurstTime() == 0) {
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections
							.min(queue, priorityOrder)));
					currentProcess.start(t);
				}
				currentProcess.run();
				t++;
			}
			if (currentProcess.getEndTime() != 0) {
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess));
			}
			queue.add(currentProcess);
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue,
					priorityOrder)));
			if (!queue.isEmpty()) {
				currentProcess.start(t);
			} else {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
				Collections.sort(finished);
				this.processes = new ArrayList<Process>(finished);
				getAverage();
			}
		}
	}
}
