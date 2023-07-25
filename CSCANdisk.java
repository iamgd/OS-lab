import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CSCANdisk {

    static int n;
    static int diskSize = 100;

    static void CSCAN(int[] arr, int head) {
        float seekCount = 0;
        int distance, curTrack;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> seekSequence = new ArrayList<>();

        // appending end values
        // which has to be visited
        // before reversing the direction
        left.add(0);
        right.add(diskSize - 1);

        // tracks on the left of the
        // head will be serviced when
        // once the head comes back
        // to the beginning (left end).
        for (int i = 0; i < n; i++) {
            if (arr[i] < head) {
                left.add(arr[i]);
            }
            if (arr[i] > head) {
                right.add(arr[i]);
            }
        }

        // sorting left and right ArrayLists
        Collections.sort(left);
        Collections.sort(right);

        // first service the requests
        // on the right side of the
        // head.
        for (int i = 0; i < right.size(); i++) {
            curTrack = right.get(i);
            // appending current track to seek sequence
            seekSequence.add(curTrack);

            // calculate absolute distance
            distance = Math.abs(curTrack - head);

            // increase the total count
            seekCount += distance;

            // accessed track is now new head
            head = curTrack;
        }

        // once reached the right end
        // jump to the beginning.
        head = 0;

        // adding seek count for head returning from 99 to 0
        seekCount += (diskSize - 1);

        // Now service the requests again
        // which are left.
        for (int i = 0; i < left.size(); i++) {
            curTrack = left.get(i);

            // appending current track to seek sequence
            seekSequence.add(curTrack);

            // calculate absolute distance
            distance = Math.abs(curTrack - head);

            // increase the total count
            seekCount += distance;

            // accessed track is now the new head
            head = curTrack;
        }

        System.out.println("Total number of seek operations = " + seekCount);
        System.out.println("Average seek time = " + seekCount / n);
        System.out.println("Throughput = " + n / seekCount);
        System.out.print("Seek Sequence is: ");

        // Print the sequence
    for (int i = 0; i < seekSequence.size(); i++) {
       if (i < seekSequence.size() - 1) { // Check if it's not the last element
        System.out.print(seekSequence.get(i) + "->");
       } else {
        System.out.print(seekSequence.get(i));
      }
}
    }

    // Driver code
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int h;
        System.out.print("Enter number of requests: ");
        n = sc.nextInt();

        int[] proc = new int[n];
        System.out.print("Enter request sequence: \n");
        for (int i = 0; i < n; i++) {
            proc[i] = sc.nextInt();
        }

        System.out.print("Enter initial head position: ");
        h = sc.nextInt();

        CSCAN(proc, h);
    }
}
