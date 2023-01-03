
/*
 * Written by Mostafa Mohamed Ali
 * CSCE 311
 */
import java.io.*;
import java.util.*;

public class ReadingFiles {
    public static void main(String[] args) throws Exception {
        // Scanner to get the User absolute file path
        Scanner scan = new Scanner(System.in);

        // Prompting the user to enter file
        System.out.println("Enter Alloc text file path: ");
        String allocScan = scan.nextLine();

        // Prompting the user to enter file
        System.out.println("Enter Req text file path: ");
        String reqScan = scan.nextLine();

        // Prompting the user to enter file
        System.out.println("Enter Avail text file path: ");
        String availScan = scan.nextLine();

        BufferedReader bufAlloc = new BufferedReader(
                new FileReader(allocScan));
        BufferedReader bufReq = new BufferedReader(
                new FileReader(reqScan));
        BufferedReader bufAvail = new BufferedReader(
                new FileReader(availScan));

        ArrayList<String> listAlloc = new ArrayList<>();
        ArrayList<String> listReq = new ArrayList<>();
        ArrayList<String> listAvail = new ArrayList<>();

        String lineAlloc = bufAlloc.readLine();
        String lineReq = bufReq.readLine();
        String lineAvail = bufAvail.readLine();
        String seq = "";
        char answer = 'y';
        boolean flag = true;
        int q = 0;

        // initialize a boolean array
        Boolean[] boolArray = new Boolean[5];
        // set all to false
        Arrays.fill(boolArray, Boolean.FALSE);

        // set user input to upper case

        while (lineAlloc != null) {
            listAlloc.add(lineAlloc);
            lineAlloc = bufAlloc.readLine();
        }
        while (lineReq != null) {
            listReq.add(lineReq);
            lineReq = bufReq.readLine();
        }
        while (lineAvail != null) {
            listAvail.add(lineAvail);
            lineAvail = bufAvail.readLine();
        }

        // to handle leaks
        bufAlloc.close();
        bufReq.close();
        bufAvail.close();

        // declare arrays
        int[][] alloc = new int[5][3];
        int[][] req = new int[5][3];
        int[][] avail = new int[1][3];
        int[][] work = new int[5][3];

        for (int i = 2; i < listAlloc.size(); i++) {
            int k = 0;
            for (int j = 0; j < listAlloc.get(i).length(); j++) {
                if (listAlloc.get(i).charAt(j) != '	') {
                    alloc[i - 2][k] = (int) (listAlloc.get(i).charAt(j) - 48);
                    k++;
                }
            }
        }

        for (int i = 2; i < listReq.size(); i++) {
            int k = 0;
            for (int j = 0; j < listReq.get(i).length(); j++) {
                // reads chars between the tabs
                if (listReq.get(i).charAt(j) != '	') {
                    req[i - 2][k] = (int) (listReq.get(i).charAt(j) - 48);
                    k++;
                }
            }
        }

        for (int i = 1; i < listAvail.size(); i++) {
            int k = 0;
            for (int j = 0; j < listAvail.get(i).length(); j++) {
                // reads chars between the spaces
                if (listAvail.get(i).charAt(j) != '	') {
                    avail[i - 1][k] = (int) (listAvail.get(i).charAt(j) - 48);
                    k++;
                }
            }
        }

        while (Character.toUpperCase(answer) == 'Y' && flag) {
            flag = false;
            for (int s = 0; s < boolArray.length; s++) {
                if (boolArray[s] == false) {
                    flag = true;

                    // set user input to upper case
                    if (Character.toUpperCase(answer) == 'Y') {
                        for (int i = 0; i < alloc.length; i++)
                            for (int j = 0; j < alloc[i].length; j++)
                                work[i][j] = req[i][j];

                        for (int i = 0; i < req.length; i++) {
                            int k = 0;

                            for (int j = 0; j < req[i].length; j++) {
                                if (req[i][j] <= avail[0][j]) {
                                    k++;
                                }
                            }
                            if (k == 3 && boolArray[i] == false) {
                                for (int j = 0; j < req[i].length; j++)
                                    avail[0][j] = avail[0][j] + alloc[i][j]; // adds alloc to avail
                                boolArray[i] = true; // sets to true if the processor is passed

                                System.out.println(Arrays.toString(boolArray));
                                System.out.println(("P" + i)); // a deadlock does not exist so the sequence is shown to
                                                               // the user
                                                               // step by step
                                seq += "P" + String.valueOf(i) + ",";
                            }

                            for (int l = 0; l < avail.length; l++) {
                                for (int j = 0; j < avail[l].length; j++) {
                                    System.out.print(avail[l][j] + "	");
                                }
                                System.out.println();
                            }

                            System.out.print("\n Press y to continue, Press n to terminate and show the sequence: ");
                            answer = scan.next().charAt(0);

                            // terminate program if answer = n
                            if (Character.toUpperCase(answer) == 'N') {
                                System.out.println(seq); // to print the sequence
                                System.exit(0);
                            }

                            // to terminate after all processors are true
                            for (int n = 0; n < boolArray.length; n++) {
                                if (boolArray[n] == false) {
                                    flag = true;
                                }
                            }
                            System.out.println("Sequence: " + seq); // to print the sequence
                        }
                    }
                }

                if (!flag) {
                    System.exit(0);
                }

            }
        }

        // prompts the user if the system is deadlocked
        for (int n = 0; n < boolArray.length; n++) {
            if (boolArray[n] == false) {
                System.out.println("There is a Deadlock!");

            }
        }
    }
}