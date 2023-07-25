package priority;

import java.util.*;

class Process2 {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int tempPriority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    public Process2(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.tempPriority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

public class PriorityAging {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process2> processes = new ArrayList<>();
        List<Process2> completedProcesses = new ArrayList<>(); // Store completed processes

        System.out.println("Enter Arrival, Burst, Priority:");
        for (int i = 0; i < n; i++) {
            System.out.println("For process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process2 process = new Process2(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt((Process2 p) -> p.arrivalTime));

        int currentTime = 0;
        int completedProcessesCount = 0;

        while (completedProcessesCount < n) {
            Process2 selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process2 process : processes) {
                if (process.arrivalTime <= currentTime && process.priority < highestPriority && !process.completed) {
                    highestPriority = process.priority;
                    selectedProcess = process;
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            selectedProcess.startTime = currentTime;
            selectedProcess.completionTime = selectedProcess.startTime + selectedProcess.burstTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;
            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;
            completedProcessesCount++;

            // Aging: Increase the priority of waiting processes
            for (Process2 process : processes) {
                if (process.arrivalTime <= currentTime && process != selectedProcess) {
                    process.priority--;
                }
            }

            completedProcesses.add(selectedProcess); // Add completed process to the list
        }

        float averageWaitingTime = 0f, averageTurnaroundTime = 0f;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tPriority\tCompletion\tWaiting\t\tTurnaround");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        for (Process2 process : completedProcesses) { // Iterate over completed processes
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;

            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" + process.tempPriority +
                    "\t\t" + process.completionTime + "\t\t" + process.waitingTime + "\t\t" + process.turnaroundTime);
        }

        averageWaitingTime = (float) totalWaitingTime / n;
        averageTurnaroundTime = (float) totalTurnaroundTime / n;
        float throughput = (float) n / currentTime;

        System.out.println("\nAverage Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Throughput: " + throughput);

        scanner.close();
    }
}
