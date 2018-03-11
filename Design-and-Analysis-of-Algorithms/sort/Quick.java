package sort;
import java.lang.Comparable;

public class Quick{
    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)    return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi){
        int i=lo, j=hi+1;
        Comparable v = a[lo];

		while(true){
        	while(less(a[++i], v))  if(i==hi)   break;
        	while(less(v, a[--j]))  if(j==lo)   break;
        	if(i>=j)    break;
        	exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static boolean isSorted(Comparable[] a){
        int n = a.length;
        for(int i=1; i<n; i++)
            if(less(a[i], a[i-1]))  return false;
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
        Quick.sort(a);
        assert isSorted(a);
        show(a);
    }
}
