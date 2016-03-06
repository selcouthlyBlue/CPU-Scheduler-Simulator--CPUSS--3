package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RR_Plus extends RR {
	
	protected Comparator<Process> burstOrder = new Comparator<Process>(){
		@Override
		public int compare(Process p1, Process p2) {
			return p1.getRemainingBurstTime() - p2.getRemainingBurstTime();
		}
	};

	public RR_Plus(ArrayList<Process> processes, int iQuantum) {
		super(processes, iQuantum);
		this.name = SchedulingAlgorithmName.RR_PLUS;
	}
	
	public void performScheduling(){
		ArrayList<Process> queue = new ArrayList<Process>();
		Collections.sort(processes, arrivalOrder);
		int time = processes.get(0).getArrivalTime();
		Process currentProcess = null;
		while(!processes.isEmpty()) {
			currentProcess = processes.remove(0);
			currentProcess.start(time);
			int q = 0;
			while (q < iQuantum && !currentProcess.isFinished()) {
				currentProcess.run();
				q++;
				time++;
			}
			if (currentProcess.isFinished() || processes.isEmpty()) {
				currentProcess.destroy(time);
				finished.add(currentProcess);
			} else {
				currentProcess.stop(time);
				if(!processes.isEmpty() && !processes.get(0).hasArrived(time)){
					processes.add(0, new Process(currentProcess));
				} else {
					queue.add(new Process(currentProcess));
				}
			}
			timeline.add(new Process(currentProcess));
		}
		if(!queue.isEmpty()){
			currentProcess = queue.remove(queue.indexOf(Collections.min(queue, burstOrder)));
			currentProcess.start(time);
		}
		if(queue.isEmpty()){
			currentProcess.start(time);
			time += currentProcess.getRemainingBurstTime();
			currentProcess.destroy(time);
		} else {
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
}
