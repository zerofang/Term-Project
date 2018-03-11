package percolation;

import java.util.Random;

public class Percolation {
	private int N;
	private boolean[][] grid;
	UnionFind unionFind;
	public Percolation(int N) { // create N-by-N grid, with all sites blocked
		this.N = N;
		grid = new boolean[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				grid[i][j] = false;
			}
		}
		unionFind = new UnionFind(N*N+2);
		for(int i = 0; i < N; i++) {
			unionFind.union(i, N*N);
			unionFind.union(N*(N-1)+i,N*N+1);
		}
	}
	public void open(int i, int j) {	// open site (row i, column j) if it is not already
		grid[i][j] = true;
		if(i > 0 && grid[i-1][j] == true) {
			unionFind.union(N*(i-1)+j,N*i+j);
		}
		if(i < N-1 && grid[i+1][j] == true) {
			unionFind.union(N*(i+1)+j,N*i+j);
		}
		if(j > 0 && grid[i][j-1] == true) {
			unionFind.union(N*i+j-1,N*i+j);
		}
		if(j < N-1 && grid[i][j+1] == true) {
			unionFind.union(N*i+j+1,N*i+j);
		}
	}
	public boolean isOpen(int i, int j) { // is site (row i, column j) open?
		return grid[i][j];
	}
	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		return unionFind.connected(N*N, N*i+j);
	}
	public boolean percolates() { // does the system percolate?
		return unionFind.connected(N*N, N*N+1);
	}
	public static void main(String[] args) { // test client, optional
		if(args.length != 2) {
			System.out.println("error");
			return;
		}
		
		int n = Integer.parseInt(args[0]);
		int c = Integer.parseInt(args[1]);
		
		int countOfPcl = 0;
		
		for(int k = 0; k < c; k++) {
			Percolation percolation = new Percolation(n);
			Random random = new Random();
			int i,j;
			while(true) {
				i = random.nextInt(n);
				j = random.nextInt(n);
				
				if(percolation.isOpen(i, j)) {
					continue;
				}
				else {
					percolation.open(i, j);
					countOfPcl++;
				}
				
				if(percolation.percolates()) {
					break;
				}
			}
			System.out.println(n+"-"+n+":"+(float)countOfPcl/((n*n)*c));
		}
	}
}
