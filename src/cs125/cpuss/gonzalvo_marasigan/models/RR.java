package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;

public class RR extends SchedulingAlgorithm {
	
	private int iQuantum;

	public RR(ArrayList<Process> processes, int iQuantum) {
		super(processes);
		this.iQuantum = iQuantum;
	}
	
	/**
	 * Performs scheduling using the Round Robin scheduling algorithm.
	 * @return 
	 */
	@Override
	public ArrayList<Process> performScheduling() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getQuantum() {
		return iQuantum;
	}

}
