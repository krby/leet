package myutil;

import java.util.*;

public final class MyUtil {
    
    public static void print2DArray(boolean[][] inputArray) {
        for (boolean[] row : inputArray) {
            System.out.println( Arrays.toString(row) );
        }
    }

    public static void print2DArray(int[][] inputArray) {
        for (int[] row : inputArray) {
            System.out.println( Arrays.toString(row) );
        }
    }
    
}
