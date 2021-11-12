import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static int timeSlice = 1;
	static int noOfProcesses = 0;
	static ArrayList<ProcessModel> processes = new ArrayList<ProcessModel>();
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.print("Enter time slice: ");
		timeSlice = input.nextInt();
		
		System.out.print("Want to load Testbeds (y/n)? ");
		if (input.next().contains("y")) {
			processes = getTestBeds();
			noOfProcesses = processes.size();
			printInputValues();
		}
		else {
			System.out.print("Enter no. of Processes: ");
			noOfProcesses = input.nextInt();
			collectInput();
		}

		Scheduler scheduler = new Scheduler(timeSlice, processes);
		scheduler.start();
	}

	static ArrayList<ProcessModel> getTestBeds() {
		ArrayList<ProcessModel> testBeds = new ArrayList<ProcessModel>();
		
		// P1
		testBeds.add(new ProcessModel(1, 0, 5, 3, false));
		// P2
		testBeds.add(new ProcessModel(2, 0, 7, 1, false));
		// P3
		testBeds.add(new ProcessModel(3, 0, 3, 8, false));
		// P4
		testBeds.add(new ProcessModel(4, 2, 8, 4, false));
		// P5
		testBeds.add(new ProcessModel(5, 2, 2, 2, false));
		// P6
		testBeds.add(new ProcessModel(6, 3, 7, 5, false));
		// P7
		testBeds.add(new ProcessModel(7, 3, 6, 6, false));
		// P8
		testBeds.add(new ProcessModel(8, 5, 4, 8, false));
		// P9
		testBeds.add(new ProcessModel(9, 5, 1, 3, false));
		// P10
		testBeds.add(new ProcessModel(10, 6, 9, 7, false));
		// P11
		testBeds.add(new ProcessModel(11, 7, 5, 8, false));
		// P12
		testBeds.add(new ProcessModel(12, 7, 7, 5, false));
		// P13
		testBeds.add(new ProcessModel(13, 8, 3, 1, false));
		// P14
		testBeds.add(new ProcessModel(14, 9, 4, 4, false));
		// P15
		testBeds.add(new ProcessModel(15, 9, 6, 2, false));
		return testBeds;
	}

	static void collectInput() {

		for (int i = 1; i <= noOfProcesses; i++) {

			System.out.println("\nProcesss " + i + " (P" + i + ") ");
			System.out.println("=================\n");
			ProcessModel process = new ProcessModel();

			process.setId(i);

			System.out.print("Arrival Time: ");
			process.arrivalTime = input.nextInt();

			System.out.print("Burst Time: ");
			process.burstTime = input.nextInt();

			System.out.print("Priority: ");
			process.priority = input.nextInt();

			processes.add(process);

		}
	}

	static void printInputValues() {
		
		System.out.println("no. of Processes: " + noOfProcesses);
		for (ProcessModel process : processes) {
			System.out.println("\nProcesss " + process.id + " (P" + process.id + ") ");
			System.out.println("=================\n");
			System.out.println("Arrival Time: " + process.arrivalTime);
			System.out.println("Burst Time: " + process.burstTime);;
			System.out.println("Priority: " + process.priority);
		}
	}
}
