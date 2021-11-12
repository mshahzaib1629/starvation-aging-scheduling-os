import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Scheduler {

	int timeSlice;
	int currentTime = 1;
	ArrayList<ProcessModel> processes = new ArrayList<ProcessModel>();
	ArrayList<ProcessModel> readyQueue = new ArrayList<ProcessModel>();

	String processingQueueString = "";
	String readyQueueString = "";

	public Scheduler(int timeSlice, ArrayList<ProcessModel> processes) {
		super();
		this.timeSlice = timeSlice;
		this.processes = processes;
	}

	void start() {
		try {
			displayLegends();
			schedulerAlgo();
			finalResults();
		} catch (Exception error) {
			System.out.println(error.toString());
		}
	}
	
	void displayLegends() {
		System.out.println("\n\n==================== Legends ====================\n");
		System.out.println("sec = Seconds (time unit)");
		System.out.println("RBT = Remaining Burst Time");
		System.out.println("WT = Waiting Time");
		System.out.println("PR = Current Priority");
	}

	void finalResults() {
		System.out.println("\n\n==================== Final Results ====================\n");
		for (ProcessModel process : processes) {
			System.out.println(process.toString());
			System.out.println("---------------------------------------");
		}
		System.out.println("\nAverage Waiting Time = " + calculateAvgWaitingTime());
		System.out.println("Average TurnAround Time = " + calculateAvgTurnAroundTime());
	}

	double calculateAvgWaitingTime() {
		int totalWaitingTime = 0;
		for (ProcessModel p : processes) {
			totalWaitingTime = totalWaitingTime + p.waitingTime;
		}
		return totalWaitingTime / processes.size();
	}

	double calculateAvgTurnAroundTime() {
		int totalTurnAroundTime = 0;
		for (ProcessModel p : processes) {
			totalTurnAroundTime = totalTurnAroundTime + p.turnArroundTime;
		}
		return totalTurnAroundTime / processes.size();
	}

	private boolean shouldContinue() {
		for (ProcessModel process : processes) {
			if (process.completionTime == 0) {
				return true;
			}
		}
		return false;
	}

	private void loadNewProcesses() {
		for (ProcessModel process : processes) {
			if (process.isLoaded == false && process.arrivalTime <= currentTime) {
				process.isLoaded = true;
				ProcessModel newProcess = new ProcessModel(process.id, process.arrivalTime, process.burstTime,
						process.priority, process.isLoaded);
				addToReadyQueue(newProcess);
			}
		}
	}

	private void addToReadyQueue(ProcessModel process) {
		readyQueue.add(process);
	}

	private void removeFromReadyQueue(ProcessModel process) {
		ProcessModel toRemove = null;
		for (ProcessModel p : readyQueue) {
			if (p.id == process.id)
				toRemove = p;
		}

		if (toRemove != null)
			readyQueue.remove(toRemove);
	}

	private ProcessModel getMaxPriorityProcess() {
		ProcessModel target = readyQueue.get(0);
		for (ProcessModel process : readyQueue) {
//			larger the priority value means lower the priority
			if (target.priority > process.priority) {
				target = process;
			}
		}
		return target;
	}

	private void addWaitingToProcesses(ProcessModel currentProcess) {
		// waiting time of all processes instead of currentProcess in readyQueue will be
		// updated with +1;
		// Note: ReadyQueue don't contain those processes which have been completed or
		// are not loaded yet

		for (ProcessModel process : readyQueue) {
			if (process.id != currentProcess.id) {
				process.waitingTime++;
			}
		}
	}

	private void handleStarvation() {
		ProcessModel target = readyQueue.get(0);
		for (ProcessModel process : readyQueue) {
			if (process.waitingTime > target.waitingTime) {
				target = process;
			} else if (process.waitingTime == target.waitingTime && process.priority > target.priority) {
				target = process;
			}
		}
		if (target.priority > 1)
			target.priority--;
		// updating the priority of target in readyQueue
		// get the index of targetedProcess in readyQueue
		// overwrite the process with targetProcess object (which has incremented
		// priority
		for (ProcessModel p : readyQueue) {
			if (p.id == target.id) {
				p = target;
			}
		}
	}

	private void markProcessComplete(ProcessModel currentProcess) {
		// these attributes should be updated in main process (present in processes
		// list)
		ProcessModel targetProcess = null;
		for (ProcessModel process : processes) {
			if (process.id == currentProcess.id)
				targetProcess = process;
		}
		targetProcess.completionTime = currentTime;
		targetProcess.waitingTime = currentProcess.waitingTime;
		targetProcess.turnArroundTime = currentTime - currentProcess.arrivalTime;
		// updating the targetProcess in processes list by overwriting with new
		// values
		for (ProcessModel p : processes) {
			if (p.id == targetProcess.id)
				p = targetProcess;

		}
	}

	void updateReadyQueueString() {
		readyQueueString = "";
		for (ProcessModel process : readyQueue) {
			readyQueueString = readyQueueString + "  P" + process.id + " (RBT: " + process.burstTime + ", WT: "
					+ process.waitingTime + ", PR: " + process.priority + ")  |";
		}
	}

	void updateProcessingQueueString(ProcessModel process) {
		processingQueueString = processingQueueString + "  P" + process.id + "  |";

	}

	private void schedulerAlgo() throws InterruptedException {
		System.out.println("\n\n==================== Executing Starvation & Aging Algorithm ====================\n");
		ProcessModel currentProcess = null;
		while (shouldContinue() == true) {
			loadNewProcesses();

			if ((currentTime % timeSlice == 0 || currentProcess == null) && !readyQueue.isEmpty()) {
				currentProcess = getMaxPriorityProcess();
				removeFromReadyQueue(currentProcess);
				updateProcessingQueueString(currentProcess);

			}
			System.out.println("\n---------------------------------------\n");
			if (currentProcess != null) {
				System.out.println(
						currentTime + " sec - executing P" + currentProcess.id + " (RBT: " + currentProcess.burstTime
								+ ", WT: " + currentProcess.waitingTime + ", PR: " + currentProcess.priority + ")");
//				System.out.println(currentProcess.toString());
				currentProcess.burstTime--;

			} else {
				System.out.println(currentTime + " sec");
			}

			// at end of every iteration:
			if (!readyQueue.isEmpty()) {
				addWaitingToProcesses(currentProcess);
				if (currentTime % timeSlice == 0)
					handleStarvation();
			}
			if (currentProcess != null) {
				int nextItrTime = currentTime + 1;
				if ((currentTime > 0 && nextItrTime % timeSlice == 0) || currentProcess.burstTime == 0) {
					if (currentProcess.burstTime > 0) {
						addToReadyQueue(currentProcess);
					} else {
						markProcessComplete(currentProcess);
					}
					currentProcess = null;
				}
			}
			updateReadyQueueString();
			System.out.println("\nprocessingQueue after execution: \n" + processingQueueString);
			System.out.println("\nreadyQueue after execution: \n" + readyQueueString);

			TimeUnit.MILLISECONDS.sleep(500);
			currentTime++;
		}
	}

}
