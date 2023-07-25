import java.util.Scanner;
import java.util.ArrayList;

public class FCFSdisk {

    public static void main(String[] args) {

        int i, n, m, x, h;
        float sum = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of disk:");
        m = sc.nextInt();
        System.out.println("Enter number of requests:");
        n = sc.nextInt();

        // Creating arrays to store request and arrival time
        ArrayList<Integer> requests = new ArrayList<>();
        ArrayList<Integer> arrivalTimes = new ArrayList<>();

        System.out.println("Enter the requests and their arrival times (separated by a space):");
        for (i = 0; i < n; i++) {
            x = sc.nextInt();
            int arrivalTime = sc.nextInt();
            requests.add(x);
            arrivalTimes.add(arrivalTime);
            if (x > m) {
                System.out.println("Error, Unknown position " + x);
                return;
            }
        }

        System.out.println("Enter the head position:");
        h = sc.nextInt();

        // Head will be at h at the starting
        int temp = h;
        System.out.print(temp);

        for (i = 0; i < n; i++) {
            System.out.print(" -> " + requests.get(i) + " ");

            // Calculating the difference for the head movement
            sum += Math.abs(requests.get(i) - temp);

            // Head movement due to waiting time (arrival time difference)
            if (i > 0)
                sum += (arrivalTimes.get(i) - arrivalTimes.get(i - 1));

            // Head is now at the next I/O request
            temp = requests.get(i);
        }

        System.out.println();
        System.out.println("Total head movements = " + sum);
        System.out.println("Average seek time = " + sum / n);
        System.out.println("Throughput = " + n / sum);
    }
}
