package sort;

public class Insertion {

    public static void sort(Comparable[] a) {
        int N = a.length;

        // put smallest element in position to serve as sentinel
        for (int i = N-1; i > 0; i--)
            if (less(a[i], a[i-1])) exch(a, i, i-1);

        // insertion sort with half-exchanges
        for (int i = 2; i < N; i++) {
            Comparable v = a[i];
            int j = i;
            while (less(v, a[j-1])) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = v;
        }

        assert isSorted(a);
    }


   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
        	System.out.println(a[i]);
        }
    }

    // Read strings from standard input, sort them, and print.
    public static void main(String[] args) {
        String[] a = In.readStrings();
        Insertion.sort(a);
        show(a);
    }

}
