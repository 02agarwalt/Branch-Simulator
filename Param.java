/*
Author: Tanay Agarwal
JHED: tagarwa2
Class: CSF
Assignment: 6
*/

import java.util.Scanner;

/**
Param class for problem 1.
*/
public final class Param {
    
    /** Hexadecimal constant. */
    static final int HEX = 16;
    
    /** Percentage constant. */
    static final long PERCENT = 100;
    
    /** Constructor for checkstyle compliance. */
    private Param() {
        
    }
    
    /** 
    Main. 
    
    @param args command line arguments.
    */
    public static void main(String[] args) {
        Scanner userIn = new Scanner(System.in);
        
        long numBranches = 0;
        long numForward = 0;
        long numBackward = 0;
        double forwardDistAvg = 0;
        double backwardDistAvg = 0;

        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            numBranches++;
            
            if (origin < destination) {
                numForward++;
                long newDist = destination - origin;
                forwardDistAvg = forwardDistAvg
                     + ((newDist - forwardDistAvg) / numForward);
            } else {
                numBackward++;
                long newDist = origin - destination;
                backwardDistAvg = backwardDistAvg
                     + ((newDist - backwardDistAvg) / numBackward);
            }
        }
        userIn.close();
        /*
        double forwardDistAvg = 0;
        double backwardDistAvg = 0;
        if (numForward > 0) {
            forwardDistAvg = forwardDistTot / numForward;
        }
        if (numBackward > 0) {
            backwardDistAvg = backwardDistTot / numBackward;
        }
        */
        double forwardPercent = 0;
        double backwardPercent = 0;
        if (numBranches > 0) {
            forwardPercent = (double) numForward * PERCENT / numBranches;
            backwardPercent = (double) numBackward * PERCENT / numBranches;
        }

        System.out.println("Total " + numBranches);
        System.out.println("Forward " + numForward);
        System.out.println("Backward " + numBackward);
        System.out.print("Forward% ");
        System.out.format("%.2f\n", forwardPercent);
        System.out.print("Backward% ");
        System.out.format("%.2f\n", backwardPercent);
        System.out.format("Forward-distance %.2f\n", forwardDistAvg);
        System.out.format("Backward-distance %.2f\n", backwardDistAvg);
    }
}
