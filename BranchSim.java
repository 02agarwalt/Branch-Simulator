/*
Author: Tanay Agarwal
JHED: tagarwa2
Class: CSF
Assignment: 6
*/

import java.util.Scanner;
import java.util.BitSet;
import java.util.ArrayList;

/**
BranchSim class for problem 2.
*/
public final class BranchSim {
    
    /** Hexadecimal constant. */
    static final int HEX = 16;
    
    /** Percentage constant. */
    static final long PERCENT = 100;
    
    /** Sixteen. */
    static final int STEPS_UPPER = 16;
    
    /** Four. */
    static final int SLOTS_LOWER = 4;
    
    /** 65536. */
    static final int SLOTS_UPPER = 65536;
    
    /** Three. */
    static final int THREE = 3;
    
    /** Five. */
    static final int FIVE = 5;
    
    /** Constructor for checkstyle compliance. */
    private BranchSim() {
        
    }
        
    /** 
    Main. 
    
    @param args command line arguments.
    */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Not enough command-line arguments.");
            return;
        }
        
        try {
            if (args[0].equals("at")) {
                at();
            } else if (args[0].equals("nt")) {
                nt();
            } else if (args[0].equals("btfn")) {
                btfn();
            } else if (args[0].equals("bimodal") && args.length >= THREE) {
                int arg1 = Integer.parseInt(args[1]);
                int arg2 = Integer.parseInt(args[2]);
                    
                bimodal(arg1, arg2);   
            } else if (args[0].equals("twolevel") && args.length >= FIVE) {
                //need variable counter otherwise checkstyle complains
                //about magic numbers......
                int x = 1;
                
                int arg1 = Integer.parseInt(args[x++]);
                int arg2 = Integer.parseInt(args[x++]);
                x++;
                int arg4 = Integer.parseInt(args[x--]);
                    
                twoLevel(arg1, arg2, args[x], arg4);
            } else {
                System.out.println("Invalid command-line arguments.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid command-line arguments.");
        }
    }
    
    /**
    Decides on which two-level predictor to use.
    
    @param slots size of history table.
    @param hist number of possible history patterns.
    @param type local or global.
    @param steps saturation counter.
    */
    public static void twoLevel(int slots, int hist, String type, int steps) {
        if (type.equals("local")) {
            twoLevelLocal(slots, hist, steps);
        } else if (type.equals("global")) {
            twoLevelGlobal(slots, hist, steps);
        } else {
            System.out.println("Invalid command-line arguments.");
        }
    }
    
    /**
    Always taken.
    */
    public static void at() {
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
            
            if (taken.equals("T")) {
                good++;
            } else if (taken.equals("N")) {
                bad++;
            }
        }
        userIn.close();
        
        float goodPercent = 0;
        float badPercent = 0;
        if (total > 0) {
            goodPercent = (float) good * PERCENT / total;
            badPercent = (float) bad * PERCENT / total;
        }
        long size = 0;
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Never taken.
    */
    public static void nt() {
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
            
            if (taken.equals("T")) {
                bad++;
            } else if (taken.equals("N")) {
                good++;
            }
        }
        userIn.close();
        
        float goodPercent = 0;
        float badPercent = 0;
        if (total > 0) {
            goodPercent = (float) good * PERCENT / total;
            badPercent = (float) bad * PERCENT / total;
        }
        long size = 0;
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Backward taken, forward not taken.
    */
    public static void btfn() {
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
            
            if (origin < destination) { //forward not taken
                if (taken.equals("T")) {
                    bad++;
                } else if (taken.equals("N")) {
                    good++;
                }
            } else { //backward taken
                if (taken.equals("T")) {
                    good++;
                } else if (taken.equals("N")) {
                    bad++;
                }
            }
        }
        userIn.close();
        
        float goodPercent = 0;
        float badPercent = 0;
        if (total > 0) {
            goodPercent = (float) good * PERCENT / total;
            badPercent = (float) bad * PERCENT / total;
        }
        long size = 0;
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Bimodal.
    
    @param slots size of prediction table.
    @param steps saturation counter.
    */
    public static void bimodal(int slots, int steps) {
        if (!paramsValid(slots, SLOTS_LOWER, steps)) {
            return;
        }
        
        byte[] predTable = new byte[slots];
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
            
            int mask = slots - 1;
            int index = (int) origin & mask;
            
            if (taken.equals("T")) {
                if (predTable[index] < (steps / 2)) { //predict
                    bad++;
                } else {
                    good++;
                }
                
                if (predTable[index] < steps - 1) {
                    predTable[index]++;
                }
                
            } else if (taken.equals("N")) {
                if (predTable[index] < (steps / 2)) {
                    good++;
                } else {
                    bad++;
                }
                
                if (predTable[index] > 0) {
                    predTable[index]--;
                }   
            }
        }
        userIn.close();
        
        float goodPercent = 0;
        float badPercent = 0;
        if (total > 0) {
            goodPercent = (float) good * PERCENT / total;
            badPercent = (float) bad * PERCENT / total;
        }
        
        long size = slots * (long) (Math.log(steps) / Math.log(2));
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Two-level PAg.
    
    @param slots size of history table.
    @param hist number of possible history patterns.
    @param steps saturation counter.
    */
    public static void twoLevelGlobal(int slots, int hist, int steps) {
        if (!paramsValid(slots, hist, steps)) {
            return;
        }
        
        int[] histTable = new int[slots];
        byte[] predTable = new byte[hist];
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
            
            int mask = slots - 1;
            int histIndex = (int) origin & mask;
            int predIndex = histTable[histIndex];
            
            if (taken.equals("T")) {
                if (predTable[predIndex] < (steps / 2)) {
                    bad++;
                } else {
                    good++;
                }
                
                if (predTable[predIndex] < steps - 1) {
                    predTable[predIndex]++;
                }
                predIndex = predIndex << 1;
                predIndex = predIndex + 1;
                predIndex = predIndex & (hist - 1);
                histTable[histIndex] = predIndex;
                
            } else if (taken.equals("N")) {
                if (predTable[predIndex] < (steps / 2)) {
                    good++;
                } else {
                    bad++;
                }
                
                if (predTable[predIndex] > 0) {
                    predTable[predIndex]--;
                }
                
                predIndex = predIndex << 1;
                predIndex = predIndex & (hist - 1);
                histTable[histIndex] = predIndex;
            }
        }
        userIn.close();
        
        float goodPercent = 0;
        float badPercent = 0;
        if (total > 0) {
            goodPercent = (float) good * PERCENT / total;
            badPercent = (float) bad * PERCENT / total;
        }
        
        long size = slots * (long) (Math.log(hist) / Math.log(2))
            + hist * (long) (Math.log(steps) / Math.log(2));
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Two-level PAp.
    
    @param slots size of history table.
    @param hist number of possible history patterns.
    @param steps saturation counter.
    */
    public static void twoLevelLocal(int slots, int hist, int steps) {
        if (!paramsValid(slots, hist, steps)) {
            return;
        }
        
        //keeps track of histories
        ArrayList<Integer> histTable = new ArrayList<Integer>(); 
        //keeps track of branches
        ArrayList<Integer> histOrder = new ArrayList<Integer>(); 
        //sat-counters
        ArrayList<BitSet> predTable = new ArrayList<BitSet>(); 
        
        Scanner userIn = new Scanner(System.in);
        
        long total = 0;
        long good = 0;
        long bad = 0;
        
        while (userIn.hasNext()) {
            long origin = userIn.nextLong(HEX);
            long destination = userIn.nextLong(HEX);
            String taken = userIn.next();
            
            total++;
           
            int branch = (int) origin & (slots - 1); //histIndex = branch
            int histIndex = histOrder.indexOf(branch);
            if (histIndex == -1) {
                histIndex = histOrder.size();
                histOrder.add(branch);
                histTable.add(0);
                BitSet temp = new BitSet(hist * (2 + 2));
                predTable.add(temp);
            }
            int predIndex = (int) histTable.get(histIndex);
            
            if (taken.equals("T")) {
                if (value(predTable.get(histIndex).get(predIndex * 2 * 2, 
                    predIndex * 2 * 2 + 2 + 2)) < (steps / 2)) {
                    bad++;
                } else {
                    good++;
                }
                
                if (value(predTable.get(histIndex).get(predIndex * 2 * 2, 
                    predIndex * 2 * 2 + 2 + 2)) < steps - 1) {
                    
                    predTable.set(histIndex, 
                        changeValue(predTable.get(histIndex), 1, 
                            predIndex * 2 * 2, predIndex * 2 * 2 + 2 + 2));
                }
                predIndex = predIndex << 1;
                predIndex = predIndex + 1;
                predIndex = predIndex & (hist - 1);
                histTable.set(histIndex, predIndex);
                
            } else if (taken.equals("N")) {
                if (value(predTable.get(histIndex).get(predIndex * 2 * 2, 
                    predIndex * 2 * 2 + 2 + 2)) < (steps / 2)) {
                    good++;
                } else {
                    bad++;
                }
                
                if (value(predTable.get(histIndex).get(predIndex * 2 * 2, 
                    predIndex * 2 * 2 + 2 + 2)) > 0) {
                    
                    predTable.set(histIndex, 
                        changeValue(predTable.get(histIndex), -1, 
                            predIndex * 2 * 2, predIndex * 2 * 2 + 2 + 2));
                }
                
                predIndex = predIndex << 1;
                predIndex = predIndex & (hist - 1);
                histTable.set(histIndex, predIndex);
            }
        }
        userIn.close();
        
        float goodPercent = percentage(good, total);
        float badPercent = percentage(bad, total);
        
        long size = (long) slots * (long) (Math.log(hist) / Math.log(2))
            + (long) slots * (long) hist 
            * (long) (Math.log(steps) / Math.log(2));
        
        System.out.println("Total " + total);
        System.out.println("Good " + good);
        System.out.println("Bad " + bad);
        System.out.print("Good% ");
        System.out.format("%.2f\n", goodPercent);
        System.out.print("Bad% ");
        System.out.format("%.2f\n", badPercent);
        System.out.println("Size " + size);
    }
    
    /**
    Calculates percentage.
    
    @param numer the numerator.
    @param denom the denominator.
    @return the percentage.
    */
    public static float percentage(long numer, long denom) {
        if (denom > 0) {
            return (float) numer * PERCENT / denom;
        }
        return 0;
    }
    
    /**
    Checks validity of some command-line arguments.
    Made a new function for checkstyle compliance.
    
    @param slots size of history table.
    @param hist number of possible history patterns.
    @param steps saturation counter.
    @return true if value, false if invalid.
    */
    public static boolean paramsValid(int slots, int hist, int steps) {
        if (slots >= SLOTS_LOWER && slots <= SLOTS_UPPER
            && (slots & (slots - 1)) == 0
            && hist >= SLOTS_LOWER && hist <= SLOTS_UPPER
            && (hist & (hist - 1)) == 0
            && steps >= 2 && steps <= STEPS_UPPER
            && (steps & (steps - 1)) == 0) {
            return true;   
        } else {
            System.out.println("Invalid command-line arguments.");
            return false;
        }
    }
    
    /**
    Converts BitSet to int.
    
    @param set the BitSet to convert.
    @return the byte equivalent of set.
    */
    public static byte value(BitSet set) {
        byte output = 0;
        if (set.get(2 + 1)) {
            output += 1;
        }
        if (set.get(2)) {
            output += 2;
        }
        if (set.get(1)) {
            output += 2 + 2;
        }
        if (set.get(0)) {
            output += 2 + 2 + 2 + 2;
        }
        return output;
    }
    
    /**
    Changes value of BitSet.
    
    @param set the BitSet to change.
    @param x the amount to change by.
    @param from starting index (inclusive).
    @param to ending index (exclusive).
    @return the modified BitSet
    */
    public static BitSet changeValue(BitSet set, int x, int from, int to) {
        byte newVal = (byte) (value(set.get(from, to)) + x);
        
        set.clear(from, to);
        
        if ((newVal & (2 + 2 + 2 + 2)) == (2 + 2 + 2 + 2)) {
            set.set(from);
        }
        if ((newVal & (2 + 2)) == (2 + 2)) {
            set.set(from + 1);
        }
        if ((newVal & 2) == 2) {
            set.set(from + 2);
        }
        if ((newVal & 1) == 1) {
            set.set(from + 2 + 1);
        }
        
        return set;
    }
}
