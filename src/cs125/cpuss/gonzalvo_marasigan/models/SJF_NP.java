package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;

public class SJF_NP extends SchedulingAlgorithm {

	public SJF_NP(ArrayList<Process> processes) {
		super(processes);
		this.sName = "Non-preemptive Shortest Job First Scheduling";
	}
	
	/**
	 * Performs scheduling using the Non-preemptive Shortest
	 * Job First Algorithm.
	 * @return 
	 */
	@Override
	public void performScheduling() {
		// TODO Auto-generated method stub
	}

}
