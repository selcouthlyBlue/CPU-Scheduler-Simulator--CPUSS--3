package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.ArrayList;

public class SchedulingAlgorithmFactory {
	public SchedulingAlgorithm getSchedulingAlgorithm(
			SchedulingAlgorithmName name, ArrayList<Process> processes) {
		SchedulingAlgorithm schedulingAlgorithm = null;
		switch (name) {
		case FCFS:
			schedulingAlgorithm = new FCFS(processes);
			break;
		case SJF_NP:
			schedulingAlgorithm = new SJF_NP(processes);
			break;
		case SJF_P:
			schedulingAlgorithm = new SJF_P(processes);
			break;
		case PRIOSCHED:
			schedulingAlgorithm = new PrioSched(processes);
			break;
		case PRIO_NP:
			schedulingAlgorithm = new Prio_NP(processes);
			break;
		default:
			break;
		}
		return schedulingAlgorithm;
	}

	public SchedulingAlgorithm getSchedulingAlgorithm(
			SchedulingAlgorithmName name, ArrayList<Process> processes,
			int additionalParameter) {
		SchedulingAlgorithm schedulingAlgorithm = null;
		switch (name) {
		case RR:
			schedulingAlgorithm = new RR(processes, additionalParameter);
			break;
		default:
			break;
		}
		return schedulingAlgorithm;
	}
}
