package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;
import java.util.Collections;

public class RR_Plus extends SchedulingAlgorithm {
	
	private int iQuantum = 1;

	public RR_Plus(ArrayList<Process> processes) {
		super(processes);
		this.name = SchedulingAlgorithmName.RR_PLUS;
	}
	
	public void performScheduling(){
		Collections.sort(processes, arrivalOrder);
		int time = 0;
		while(!processes.isEmpty()) {
			Process currentProcess = processes.remove(0);
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
					processes.add(new Process(currentProcess));
				}
			}
			iQuantum++;
			timeline.add(new Process(currentProcess));
		}
	}
}
