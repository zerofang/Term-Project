package sort;
import java.lang.Comparable;

public class QuickBentleyMcllroy{
	
	public static void sort(Comparable[] a){
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo)    return;
        Comparable v = a[lo];
        int p=lo, q=hi+1;
        int i=lo, j=hi+1;

        while(true){
            while(less(a[++i], v)){
                if(i == hi) break;
            }
            while(less(v, a[--j]))
                if(j == lo) break;

            if(i == j && eq(a[i], v))
                exch(a, ++p, i);
            if(i>=j)    break;

            exch(a, i, j);

            if(eq(a[i], v)) exch(a, ++p, i);
            if(eq(a[j], v)) exch(a, --q, j);
        }

        i = j + 1;
        for(int k=lo; k<=p; k++)
            exch(a, k, j--);
        for(int k=hi; k>=q; k--)
            exch(a, k, i++);
        sort(a, lo, j);
        sort(a, i, hi);
    }

    private static boolean eq(Comparable v, Comparable w){
        return v.compareTo(w) == 0;
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
        QuickBentleyMcllroy.sort(a);
        assert isSorted(a);
        show(a);
    }
}
