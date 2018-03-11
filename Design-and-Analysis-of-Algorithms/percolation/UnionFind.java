package percolation;

public class UnionFind {
	private int[] id;
	private int[] sz;
	private int count;
	public UnionFind(int N) {
		count = N;
		id = new int[N];
		for(int i = 0; i < N; i++) id[i] = i;
		sz = new int[N];
		for(int i = 0; i < N; i++) sz[i] = i;
	}
	public int count() {
		return count;
	}
	public boolean connected(int p,int q) {
		return find(p) == find(q);
	}
	public int find(int p) {
		while(p != id[p]) p = id[p];
		return p;
	}
	public void union(int p,int q) {
		int i = find(p);
		int j = find(q);
		if(i == j) return;
		if(sz[i] < sz[i]) {
			id[i] = j;
			sz[i] += sz[i];
		}
		else {
			id[j] = i;
			sz[i] += sz[j];
		}
		count--;
	}
}
