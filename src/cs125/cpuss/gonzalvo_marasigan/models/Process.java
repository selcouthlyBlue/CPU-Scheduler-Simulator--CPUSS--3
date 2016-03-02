package cs125.cpuss.gonzalvo_marasigan.models;


public class Process implements Comparable<Process>{
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
	 * Copy constructor of the Process class
	 * @param process
	 */
	public Process(Process process) {
		this.iProcessId = process.iProcessId;
		this.iArrivalTime = process.iArrivalTime;
		this.iBurstTime = process.iBurstTime;
		this.iStartTime = process.iStartTime;
		this.iEndTime = process.iEndTime;
		this.iWaitingTime = process.iWaitingTime;
		this.iTurnaroundTime = process.iTurnaroundTime;
		this.iRemainingBTime = process.iRemainingBTime;
		this.iPriority = process.iPriority;
		this.iCurrentPriority = process.iCurrentPriority;
	}

	/**
	 * Increases the priority number of the process by 1.
	 */
	public void age() {
		this.iCurrentPriority++;
	}

	/**
	 * Subtracts 1 from the remaining burst time.
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
		iStartTime = time;
		iWaitingTime += time - iEndTime;
	}

	/**
	 * Stops the process and updates the time it ended.
	 * @param time
	 */
	public void stop(int time){
		iEndTime = time;
	}

	/**
	 * Stops the process and updates the waiting time, the end time,
	 * and the turnaround time.
	 * @param time
	 */
	public void destroy(int time){
		iEndTime = time + iRemainingBTime;
		iWaitingTime -= iArrivalTime;
		iTurnaroundTime = iWaitingTime + iBurstTime;
	}

	/**
	 * Gets the running time of a process from the time it last started
	 * to the time it stopped.
	 * @return time
	 */
	public int getLength(){
		return (iEndTime - iStartTime);
	}

	/**
	 * Checks if the process' remaining burst time has reached 0
	 * @return true if the process' remaining burst time has reached 0; false otherwise
	 */
	public boolean isFinished(){
		return iRemainingBTime == 0;
	}

	/**
	 * Checks if the process has a higher burst time compared to
	 * that of the other process
	 * @param process
	 * @return true if the process has a higher burst time than that of the
	 * other process; false otherwise
	 */
	public boolean hasHigherBurstTime(Process process){
		if(this.iRemainingBTime == process.iRemainingBTime)
			return this.iProcessId > process.iProcessId;
		return this.iRemainingBTime > process.iRemainingBTime;
	}

	/**
	 * Checks if the process has a lower priority compared to
	 * that of the other process
	 * @param process
	 * @return true if the process has a higher burst time than that of the
	 * other process; false otherwise
	 */
	public boolean hasLowerPriority(Process process){
		if(this.iCurrentPriority == process.iCurrentPriority)
			return this.iProcessId > process.iProcessId;
		return this.iCurrentPriority > process.iCurrentPriority;
	}

	/**
	 * Checks if the process' arrival time matches the given
	 * @param time
	 * @return true if the process' arrival time matches given time; false otherwise
	 */
	public boolean hasArrived(int time) {
		return time >= iArrivalTime;
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

	public int getRemainingBurstTime() {
		return iRemainingBTime;
	}
	
	public int getCurrentPriority() {
		return iCurrentPriority;
	}

	@Override
	public int compareTo(Process process) {
		return Integer.valueOf(this.iProcessId).compareTo(process.iProcessId);
	}
}
