import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class RoundRobinScheduler {
    private Process3[] processes;
    private int timeQuantum;

    public RoundRobinScheduler(int numProcesses, int timeQuantum) {
        processes = new Process3[numProcesses];
        this.timeQuantum = timeQuantum;
    }

    public void inputProcessDetails(Scanner scanner) {
        for (int i = 0; i < processes.length; i++) {
            System.out.println("Enter the arrival time and burst time for Process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            processes[i] = new Process3(i + 1, arrivalTime, burstTime);
        }

        // Sort processes based on arrival time
        Arrays.sort(processes, Comparator.comparing(Process3::getArrivalTime));
    }

    public void runScheduler() {
        int[] remainingTime = new int[processes.length];
        int currentTime = 0;
        boolean allProcessesCompleted = false;

        for (int i = 0; i < processes.length; i++) {
            remainingTime[i] = processes[i].getBurstTime();
        }

        while (!allProcessesCompleted) {
            allProcessesCompleted = true;

            for (Process3 process : processes) {
                int index = process.getId() - 1;

                if (remainingTime[index] > 0) {
                    allProcessesCompleted = false;

                    if (remainingTime[index] <= timeQuantum) {
                        currentTime += remainingTime[index];
                        remainingTime[index] = 0;
                        process.setCompletionTime(currentTime);
                    } else {
                        currentTime += timeQuantum;
                        remainingTime[index] -= timeQuantum;
                    }
                }
            }
        }
    }

    public void calculateTimes() {
        for (Process3 process : processes) {
            int waitingTime = process.getCompletionTime() - process.getArrivalTime() - process.getBurstTime();
            process.setWaitingTime(waitingTime);

            int turnAroundTime = process.getCompletionTime() - process.getArrivalTime();
            process.setTurnAroundTime(turnAroundTime);
        }
    }

    public void printProcessDetails() {
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\t\tWaiting Time\tTurnaround Time");
        for (Process3 process : processes) {
            System.out.println(process.getId() + "\t\t" + process.getArrivalTime() + "\t\t" + process.getBurstTime()
                    + "\t\t" + process.getCompletionTime() + "\t\t\t" + process.getWaitingTime()
                    + "\t\t" + process.getTurnAroundTime());
        }
    }

    public void printAverageTimes() {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;

        for (Process3 process : processes) {
            totalWaitingTime += process.getWaitingTime();
            totalTurnAroundTime += process.getTurnAroundTime();
        }

        double averageWaitingTime = (double) totalWaitingTime / processes.length;
        double averageTurnAroundTime = (double) totalTurnAroundTime / processes.length;

        System.out.println("\nAverage Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turnaround Time: " + averageTurnAroundTime);

        // Calculate throughput
        double throughput = (double) processes.length / processes[processes.length - 1].getCompletionTime();
        System.out.println("Throughput: " + throughput + " per time unit");
    }
}

class Process3 {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int completionTime;
    private int waitingTime;
    private int turnAroundTime;

    public Process3(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the total number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        RoundRobinScheduler scheduler = new RoundRobinScheduler(numProcesses, timeQuantum);
        scheduler.inputProcessDetails(scanner);
        scheduler.runScheduler();
        scheduler.calculateTimes();
        scheduler.printProcessDetails();
        scheduler.printAverageTimes();
    }
}
