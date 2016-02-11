package cs125.gonzalvo_marasigan.cpuss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SJF_P extends SchedulingAlgorithm{
	
	private Comparator<Process> burstOrder = new Comparator<Process>(){
		@Override
		public int compare(Process p1, Process p2) {
			return p1.getRemainingBTime() - p2.getRemainingBTime();
		}
	};
	
	public SJF_P(ArrayList<Process> processes) {
		super(processes);
	}
	
	/**
	 * Performs scheduling using the Preemptive Shortest Job First Algorithm.
	 */
	@Override
	public void performScheduling(){
		ArrayList<Process> finished = new ArrayList<Process>();
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, new Process());
		Process currentProcess = new Process();
		currentProcess = processes.remove(0);
		int t = 0;
		for(Process process : processes){
			if(!queue.isEmpty()){
				if(currentProcess == null){
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
				}
				else if(currentProcess.getRemainingBTime() > Collections.min(queue, burstOrder).getRemainingBTime()){
					currentProcess.stop(t);
					timeline.add(new Process(currentProcess.getProcessId(), 
							currentProcess.getStartTime(), currentProcess.getEndTime()));
					queue.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(t);
				}
			}
			while(t != process.getArrivalTime() || currentProcess.getRemainingBTime() == 0){
				if(currentProcess.getRemainingBTime() == 0){
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess.getProcessId(), 
							currentProcess.getStartTime(), currentProcess.getEndTime()));
					finished.add(currentProcess);
					currentProcess = null;
					break;
				}
				currentProcess.run();
				t++;
			}
			if(currentProcess != null && currentProcess.getRemainingBTime() > process.getBurstTime()){
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess.getProcessId(), 
						currentProcess.getStartTime(), currentProcess.getEndTime()));
				queue.add(currentProcess);
				currentProcess = process;
				currentProcess.start(t);
			} else if(currentProcess == null) {
				currentProcess = process;
			} else {
				queue.add(process);
			}
		}
		while(!queue.isEmpty()){
			while(!queue.isEmpty() && currentProcess.getRemainingBTime() <= Collections.min(queue, burstOrder).getRemainingBTime()){
				if(currentProcess.getRemainingBTime() == 0){
					currentProcess.destroy(t);
					timeline.add(new Process(currentProcess.getProcessId(), 
							currentProcess.getStartTime(), currentProcess.getEndTime()));
					finished.add(currentProcess);
					currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
					currentProcess.start(t);
				}
				currentProcess.run();
				t++;
			}
			if(currentProcess.getEndTime() != 0){
				currentProcess.stop(t);
				timeline.add(new Process(currentProcess.getProcessId(), 
						currentProcess.getStartTime(), currentProcess.getEndTime()));
			}
			queue.add(currentProcess);
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
			currentProcess.start(t);
		}
		currentProcess.start(t);
		currentProcess.destroy(t);
		timeline.add(new Process(currentProcess.getProcessId(), 
				currentProcess.getStartTime(), currentProcess.getEndTime()));
		finished.add(currentProcess);
		Collections.sort(finished);
		processes = new ArrayList<Process>(finished);
		getAverage();
	}
}
