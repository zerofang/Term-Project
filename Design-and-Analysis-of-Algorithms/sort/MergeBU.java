package sort;
import java.lang.Comparable;

public class MergeBU{
    public static void sort(Comparable[] a){
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        for(int n=1; n<N; n *= 2)
            for(int i=0; i < N-n; i += n*2){
                int lo = i;
                int mid = i + n - 1;
                int hi = Math.min(i+n*2-1, N-1);

                merge(a, aux, lo, mid, hi);
            }
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
        int i = lo, j = mid+1;

        for(int k=lo; k<=hi; k++)
            aux[k] = a[k];

        for(int k=lo; k<=hi; k++)
            if(i > mid)                   a[k] = aux[j++];
            else if(j > hi)               a[k] = aux[i++];
            else if(less(aux[j], aux[i])) a[k] = aux[j++];
            else                          a[k] = aux[i++];
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
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
        MergeBU.sort(a);
        assert isSorted(a);
        show(a);
    }
}
