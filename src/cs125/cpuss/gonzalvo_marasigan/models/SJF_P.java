package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF_P extends SchedulingAlgorithm{
	
	private Comparator<Process> burstOrder = new Comparator<Process>(){
		@Override
		public int compare(Process p1, Process p2) {
			return p1.getRemainingBurstTime() - p2.getRemainingBurstTime();
		}
	};
	
	public SJF_P(ArrayList<Process> processes) {
		super(processes);
		this.sName = "Preemptive Shortest Job First Scheduling";
	}
	
	/**
	 * Performs scheduling using the Preemptive Shortest Job First Algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> finished = new ArrayList<Process>();
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		Process currentProcess = processes.remove(0);
		int t = currentProcess.getArrivalTime();
		for(Process process : processes){
			if(!queue.isEmpty() && currentProcess.hasHigherBurstTime(Collections.min(queue, burstOrder))){
				currentProcess.stop(t);
				if(currentProcess.getLength() != 0){
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
				currentProcess.start(t);
			}
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
			if(currentProcess != null && currentProcess.hasHigherBurstTime(process)){
				currentProcess.stop(t);
				if(currentProcess.getLength() != 0){
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(t);
			} else if(currentProcess == null) {
				if(t < process.getArrivalTime() && !queue.isEmpty()){
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(t);
					while(t < process.getArrivalTime()){
						currentProcess.run();
						t++;
						if (currentProcess.isFinished()) {
							currentProcess.destroy(t);
							timeline.add(new Process(currentProcess));
							finished.add(currentProcess);
							currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
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
		while(!queue.isEmpty()){
			while(!queue.isEmpty() && !currentProcess.hasHigherBurstTime(Collections.min(queue, burstOrder))){
				currentProcess.run();
				t++;
				if(currentProcess.isFinished()){
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(t);
				}
			}
			if(currentProcess.getEndTime() != 0 && !queue.isEmpty()){
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess));
			}
			queue.add(currentProcess);
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
			if(!queue.isEmpty()){
				currentProcess.start(t);
			} else {
				currentProcess.destroy(t);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
				Collections.sort(finished);
				processes = finished;
				getAverage();
			}
		}
	}
}
