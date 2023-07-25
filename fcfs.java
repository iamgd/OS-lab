import java.util.Scanner;
class Process {
    int id, bt, at, ct, tat, wt, st;
}
public class fcfs {
    public static void input(Process[] p, int n) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            p[i].at = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            p[i].bt = scanner.nextInt();
            p[i].id = i + 1;
        }
    }
    public static void calc(Process[] p, int n) {
        int sum = 0;
        sum = sum + p[0].at;
        for (int i = 0; i < n; i++) {
            sum = (sum > p[i].at) ? sum : p[i].at;
            p[i].st = sum;
            sum = sum + p[i].bt;
            p[i].ct = sum;
            p[i].tat = p[i].ct - p[i].at;
            p[i].wt = p[i].tat - p[i].bt;
        }
    }
    public static void sort(Process[] p, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (p[j].at > p[j + 1].at) {
                    int temp;
                    temp = p[j].bt;
                    p[j].bt = p[j + 1].bt;
                    p[j + 1].bt = temp;
                    temp = p[j].at;
                    p[j].at = p[j + 1].at;
                    p[j + 1].at = temp;
                    temp = p[j].id;
                    p[j].id = p[j + 1].id;
                    p[j + 1].id = temp;
                }
            }
        }
    }
    public static void show(Process[] p, int n) {
        int total_wt = 0;
        int total_tat = 0;
        int completion_time = p[n - 1].ct;
        System.out.println("Process\tArrival\tBurst\tStart\tCompletion\tWaiting\tTurnaround");
        for (int i = 0; i < n; i++) {
            System.out.printf("P[%d]\t%d\t%d\t%d\t%d\t\t%d\t%d\n", p[i].id, p[i].at, p[i].bt, p[i].st, p[i].ct,
                    p[i].wt, p[i].tat);
            total_wt += p[i].wt;
            total_tat += p[i].tat;
        }
        float avg_wt = (float) total_wt / n;
        float avg_tat = (float) total_tat / n;
        float throughput = (float) n / completion_time;
        System.out.println("Average Waiting Time = " + avg_wt);
        System.out.println("Average Turnaround Time = " + avg_tat);
        System.out.println("Throughput = " + throughput + " processes per unit time");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
        }
        input(p, n);
        sort(p, n);
        calc(p, n);
        show(p, n);
    }
}
