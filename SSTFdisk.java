import java.util.Scanner;

public class SSTFdisk {

    // Calculates difference of each
    // track number with the head position
    static void calculatedifference(int request[], int head,
                                    int diff[][], int n) {
        for (int i = 0; i < n; i++) {
            diff[i][0] = Math.abs(head - request[i]);
        }
    }

    // Find unaccessed track which is
    // at minimum distance from head
    static int findMIN(int diff[][], int n) {
        int index = -1;
        int minimum = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (diff[i][1] == 0 && minimum > diff[i][0]) {
                minimum = diff[i][0];
                index = i;
            }
        }
        return index;
    }

    static void shortestSeekTimeFirst(int request[], int head, int n) {
        if (n == 0) {
            return;
        }

        // Create array of objects of class node
        int[][] diff = new int[n][2];

        // Count total number of seek operation
        float seekcount = 0;

        // Stores sequence in which disk access is done
        int[] seeksequence = new int[n + 1];

        for (int i = 0; i < n; i++) {
            seeksequence[i] = head;
            calculatedifference(request, head, diff, n);
            int index = findMIN(diff, n);
            diff[index][1] = 1;

            // Increase the total count
            seekcount += diff[index][0];

            // Accessed track is now new head
            head = request[index];
        }
        seeksequence[n] = head;

        System.out.println("Total number of seek operations = " + seekcount);
        System.out.println("Average seek time = " + seekcount / n);
        System.out.println("Throughput = " + n / seekcount);
        System.out.print("Seek sequence is: ");

        // Print the sequence
        for (int i = 0; i <= n; i++) {
            if (i < n) {
                System.out.print(seeksequence[i] + "->");
            } else {
                System.out.print(seeksequence[i]);
            }
        }
    }

    // Driver code
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n, h;
        System.out.print("Enter number of requests: ");
        n = sc.nextInt();

        int[] proc = new int[n];
        System.out.print("Enter request sequence: \n");
        for (int i = 0; i < n; i++) {
            proc[i] = sc.nextInt();
        }

        System.out.print("Enter initial head position: ");
        h = sc.nextInt();

        shortestSeekTimeFirst(proc, h, n);
    }
}
