package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF_P extends SchedulingAlgorithm{
	
	protected Comparator<Process> burstOrder = new Comparator<Process>(){
		@Override
		public int compare(Process p1, Process p2) {
			return p1.getRemainingBurstTime() - p2.getRemainingBurstTime();
		}
	};
	
	public SJF_P(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.SJF_P;
	}
	
	/**
	 * Performs scheduling using the Preemptive Shortest Job First Algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, arrivalOrder);
		Process currentProcess = null;
		int time = 0;
		for(Process process : processes){
			if(!queue.isEmpty()){
				if(currentProcess.hasHigherBurstTime(Collections.min(queue, burstOrder))){
					currentProcess.stop(time);
					if(currentProcess.getLength() != 0){
						timeline.add(new Process(currentProcess));
					}
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(time);
				}
			} else if(currentProcess == null) {
				currentProcess = process;
				time = currentProcess.getArrivalTime();
				continue;
			}
			while(!process.hasArrived(time)){
				currentProcess.run();
				time++;
				if(currentProcess.isFinished()){
					currentProcess.destroy(time);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
			}
			if(currentProcess != null && currentProcess.hasHigherBurstTime(process)){
				currentProcess.stop(time);
				if(currentProcess.getLength() != 0){
					timeline.add(new Process(currentProcess));
				}
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(time);
			} else if(currentProcess == null) {
				if(!process.hasArrived(time) && !queue.isEmpty()){
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(time);
					while(!process.hasArrived(time)){
						currentProcess.run();
						time++;
						if (currentProcess.isFinished()) {
							currentProcess.destroy(time);
							timeline.add(new Process(currentProcess));
							finished.add(currentProcess);
							currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
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
		while(!queue.isEmpty()){
			while(!queue.isEmpty() && !currentProcess.hasHigherBurstTime(Collections.min(queue, burstOrder))){
				currentProcess.run();
				time++;
				if(currentProcess.isFinished()){
					currentProcess.destroy(time);
					timeline.add(new Process(currentProcess));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(time);
				}
			}
			if(currentProcess.getEndTime() != 0 && !queue.isEmpty()){
				currentProcess.stop(time);
				timeline.add(new Process(currentProcess));
			}
			if(!queue.isEmpty()){
				queue.add(currentProcess);
				currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
				currentProcess.start(time);
			} else {
				currentProcess.destroy(time);
				timeline.add(new Process(currentProcess));
				finished.add(currentProcess);
			}
		}
	}
}
