import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SCANdisk {

    static int diskSize = 100;
    static int n;

    static void SCAN(int[] arr, int head, String direction) {
        float seekCount = 0;
        int distance, curTrack;
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> seekSequence = new ArrayList<>();

        // appending end values
        // which has to be visited
        // before reversing the direction
        if (direction.equals("left")) {
            left.add(0);
        } else if (direction.equals("right")) {
            right.add(diskSize - 1);
        }

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

        // run the while loop two times.
        // one by one scanning right
        // and left of the head
        int run = 2;
        while (run-- > 0) {
            if (direction.equals("left")) {
                for (int i = left.size() - 1; i >= 0; i--) {
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
                direction = "right";
            } else if (direction.equals("right")) {
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
                direction = "left";
            }
        }

        System.out.println("Total number of seek operations = " + seekCount);
        System.out.println("Average seek time = " + seekCount / n);
        System.out.println("Throughput = " + n / seekCount);
        System.out.print("Seek Sequence is: ");

        // Print the sequence
        for (int i = 0; i < seekSequence.size(); i++) {
            if (i < n) {
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

        System.out.print("Enter direction: (left or right)\n");
        String direction = sc.next();

        SCAN(proc, h, direction);
    }
}
