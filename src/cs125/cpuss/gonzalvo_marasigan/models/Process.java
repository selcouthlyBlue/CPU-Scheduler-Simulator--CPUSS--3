package cs125.cpuss.gonzalvo_marasigan.models;

import java.util.Comparator;

public class Process implements Comparable<Process>, Comparator<Process>{
	private int iProcessId;
	private int iArrivalTime;
	private int iBurstTime;
	private int iPriority;
	private int iWaitingTime;
	private int iTurnaroundTime;
	private int iRemainingBTime;
	private int iCurrentPriority;
	private int iStartTime;
	private int iEndTime;
	
	Process(){
		
	}
	
	/**
	 * Primary constructor of the Process class
	 * @param iProcessId
	 * @param iArrivalTime
	 * @param iBurstTime
	 * @param iPriority
	 */
	public Process(int iProcessId, int iArrivalTime, int iBurstTime, int iPriority){
		this.iProcessId = iProcessId;
		this.iArrivalTime = iArrivalTime;
		this.iBurstTime = iBurstTime;
		this.iPriority = iPriority;
		this.iRemainingBTime = this.iBurstTime;
		this.iCurrentPriority = this.iPriority;
	}
	
	/**
	 * Secondary constructor of the Process class with known startTime
	 * and endTime.
	 * @param processId
	 * @param startTime
	 * @param endTime
	 */
	public Process(int processId, int startTime, int endTime) {
		this.iProcessId = processId;
		this.iStartTime = startTime;
		this.iEndTime = endTime;
	}

	public int getBurstTime() {
		return iBurstTime;
	}

	public int getArrivalTime() {
		return iArrivalTime;
	}

	public int getPriority() {
		return iPriority;
	}

	public int getProcessId() {
		return iProcessId;
	}
	
	public int getWaitingTime() {
		return iWaitingTime;
	}

	public int getTurnaroundTime() {
		return iTurnaroundTime;
	}

	public int getStartTime() {
		return iStartTime;
	}

	public int getEndTime() {
		return iEndTime;
	}

	public int getRemainingBTime() {
		return iRemainingBTime;
	}
	
	public int getCurrentPriority() {
		return iCurrentPriority;
	}

	/**
	 * Increases the priority number of the process by 1.
	 */
	public void age() {
		this.iCurrentPriority++;
	}
	
	/**
	 * Substracts 1 from the remaining burst time.
	 */
	public void run(){
		this.iRemainingBTime--;
	}
	
	/**
	 * Starts the process and updates the waiting time
	 * based on the time it started and the time it last stopped.
	 * @param time
	 */
	public void start(int time){
		this.iStartTime = time;
		this.iWaitingTime += time - iEndTime;
	}
	
	/**
	 * Stops the process and updates the time it ended.
	 * @param time
	 */
	public void stop(int time){
		this.iEndTime = time;
	}
	
	/**
	 * Stops the process and updates the waiting time, the end time,
	 * and the turnaround time.
	 * @param time
	 */
	public void destroy(int time){
		this.iEndTime = time + this.iRemainingBTime;
		this.iWaitingTime -= this.iArrivalTime;
		this.iTurnaroundTime = this.iWaitingTime + this.iBurstTime;
	}
	
	public void reset(){
		this.iRemainingBTime = this.iBurstTime;
		this.iCurrentPriority = this.iPriority;
		this.iWaitingTime = 0;
		this.iTurnaroundTime = 0;
		this.iStartTime = 0;
		this.iEndTime = 0;
	}

	@Override
	public int compareTo(Process process) {
		return Integer.valueOf(this.iProcessId).compareTo(process.iProcessId);
	}

	@Override
	public int compare(Process p1, Process p2) {
		return Integer.valueOf(p1.iArrivalTime).compareTo(p2.iArrivalTime);
	}
}
