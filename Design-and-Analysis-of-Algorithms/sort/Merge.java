package sort;
import java.lang.Comparable;

public class Merge{
    public static void sort(Comparable[] a){
        int N = a.length;
        Comparable[] aux = new Comparable[N];

		sort(a, aux, 0, N-1);
	}

	public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi){
		if(hi <= lo)
			return;

        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
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
        Merge.sort(a);
        assert isSorted(a);
        show(a);
    }
}
