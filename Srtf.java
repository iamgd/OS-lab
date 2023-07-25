import java.util.Scanner;
class Process {
    int pid; // Process ID
    int bt; // Burst Time
    int art; // Arrival Time    
    Process(int pid, int bt, int art) {
        this.pid = pid;
        this.bt = bt;
        this.art = art;
    }
}
public class Srtf {
    // Method to find the waiting time for all processes
    static void findWaitingTime(Process[] processes, int n, int[] wt, int[] ct) {
        int[] rt = new int[n];        
        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++)
            rt[i] = processes[i].bt;   
        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = -1, finishTime;
        boolean check = false;
        // Process until all processes get completed
        while (complete != n) {
            // Find the process with the minimum remaining time among the processes that have arrived till the current time
            for (int j = 0; j < n; j++) {
                if (processes[j].art <= t && rt[j] < minm && rt[j] > 0) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }   
            if (!check) {
                t++;
                continue;
            }          
            // Reduce the remaining time by one
            rt[shortest]--;  
            // Update the minimum remaining time
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;      
            // If a process gets completely executed
            if (rt[shortest] == 0) {
                complete++;
                check = false;                
                // Find the finish time of the current process
                finishTime = t + 1;             
                // Calculate completion time
                ct[shortest] = finishTime;               
                // Calculate waiting time
                wt[shortest] = finishTime - processes[shortest].bt - processes[shortest].art;
                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }        
            // Increment time
            t++;
        }
    }
    // Method to calculate turnaround time
    static void findTurnaroundTime(Process[] processes, int n, int[] wt, int[] tat) {
        // Calculate turnaround time by adding burst time and waiting time
        for (int i = 0; i < n; i++)
            tat[i] = processes[i].bt + wt[i];
    }
    // Method to calculate average time
    static void findAverageTime(Process[] processes, int n) {
        int[] wt = new int[n];
        int[] tat = new int[n];
        int total_wt = 0, total_tat = 0;
        int[] ct = new int[n];        
        // Find waiting time of all processes
        findWaitingTime(processes, n, wt, ct);    
        // Find turnaround time for all processes
        findTurnaroundTime(processes, n, wt, tat);        
        // Display process details
        System.out.println("Process\tArrival\tBurst\tCompletion\tWaiting\tTurnaround");     
        int minArrivalTime = Integer.MAX_VALUE;
        int maxCompletionTime = Integer.MIN_VALUE;        
        for (int i = 0; i < n; i++) {
            minArrivalTime = Math.min(minArrivalTime, processes[i].art);            
            System.out.println(processes[i].pid + "\t" +
                    processes[i].art + "\t" + processes[i].bt + "\t" +
                    ct[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);          
            total_wt += wt[i];
            total_tat += tat[i];            
            maxCompletionTime = Math.max(maxCompletionTime, ct[i]);
        }       
        // Calculate average waiting time and average turnaround time
        float avg_wt = (float) total_wt / n;
        float avg_tat = (float) total_tat / n;        
        System.out.println("\nAverage Waiting Time: " + avg_wt);
        System.out.println("Average Turnaround Time: " + avg_tat);      
        float throughput = (float) n / (maxCompletionTime - minArrivalTime);
        System.out.println("Throughput: " + throughput + " processes/ms");
    }    
    // Driver Method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);        
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();        
        Process[] processes = new Process[n];     
        System.out.println("Enter Arrival Time and Burst Time:");
        for (int i = 0; i < n; i++) {
            System.out.println("For process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();           
            processes[i] = new Process(i + 1, burstTime, arrivalTime);
        }        
        findAverageTime(processes, processes.length);        
        scanner.close();
    }
}


