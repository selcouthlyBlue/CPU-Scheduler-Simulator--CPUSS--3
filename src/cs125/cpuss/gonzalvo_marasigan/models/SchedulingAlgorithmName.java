package cs125.cpuss.gonzalvo_marasigan.models;

public enum SchedulingAlgorithmName {
	FCFS, SJF_NP, SJF_P, PRIO_NP, PRIOSCHED, RR;
	
	public String toString(){
		switch(this){
		case FCFS: return "First Come, First Serve Scheduling";
		case SJF_NP: return "Non-preemptive Shortest Job First Scheduling";
		case SJF_P: return "Preemptive Shortest Job First Scheduling";
		case PRIO_NP: return "Non-preemptive Priority Scheduling";
		case PRIOSCHED: return "Priority Scheduling";
		case RR: return "Round Robin Scheduling";
		}
		return null;
	}
}
