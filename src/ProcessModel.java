
public class ProcessModel {
	int id;
	int arrivalTime;
	int burstTime;
	boolean isLoaded;
	int priority;
	int waitingTime;
	int turnArroundTime;
	int completionTime;

	public ProcessModel(int id, int arrivalTime, int burstTime, int priority, boolean isLoaded) {
		super();
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.isLoaded = isLoaded;
		this.priority = priority;
	}

	// to be called when new process is created
	public ProcessModel() {
	}

	@Override
	public String toString() {
		return "ProcessModel [id=" + id + ", arrivalTime=" + arrivalTime + ", burstTime=" + burstTime + ", isLoaded="
				+ isLoaded + ", priority=" + priority + ", waitingTime=" + waitingTime + ", turnArroundTime="
				+ turnArroundTime + ", completionTime=" + completionTime + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTurnArroundTime() {
		return turnArroundTime;
	}

	public void setTurnArroundTime(int turnArroundTime) {
		this.turnArroundTime = turnArroundTime;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

}
