package sort;
import java.lang.Comparable;

public class QuickDijkstra{
    public static void sort(Comparable[] a){
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)    return;
        int lt = lo, i = lo, gt = hi;
        Comparable v = a[lo];
        while(i <= gt){
            int cmp = a[i].compareTo(v);
            if(cmp < 0) exch(a, lt++, i++);
            else if(cmp > 0) exch(a, i, gt--);
            else        i++;
        }

        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static boolean isSorted(Comparable[] a){
        int n = a.length;
        for(int i=1; i<n; i++)
            if(less(a[i], a[i-1]))  return false;
        return true;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }


    private static void show(Comparable[] a){
        int n = a.length;
        for(int i=0; i<n; i++)
            System.out.println(a[i]);
    }

    public static Integer[] conventToInteger(String[] a){
        int n = a.length;
        Integer[] b = new Integer[n];
        for(int i=0; i<n; i++)
            b[i] = Integer.valueOf(a[i]);
        return b;
    }

    public static void main(String[] args){
        String[] a = In.readStrings();
        QuickDijkstra.sort(a);
        assert isSorted(a);
        show(a);
    }
}
