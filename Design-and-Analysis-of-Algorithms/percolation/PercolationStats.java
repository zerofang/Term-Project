package percolation;

import java.lang.Math;
import java.util.Random;

public class PercolationStats {
	
	private int N;
	private int cycles;
	private double[] results;
	
	public PercolationStats(int N, int C) { // perform T independent computational experiments on an N-by-N grid
		
		this.N = N;
		this.cycles = C;
		
		results = new double[C];
		for(int k = 0; k <C; k++) {
			
			Percolation percolation = new Percolation(this.N);
			Random random = new Random();
			
			int count = 0;
			
			while(true) {
				
				int i = random.nextInt(this.N);
				int j = random.nextInt(this.N);
				
				if(percolation.isOpen(i, j)) {
					continue;
				}
				else {
					percolation.open(i, j);
					count++;
				}
				if(percolation.percolates()) {
					break;
				}
			}
			results[k] = (double)count/(this.N*this.N);
		}
	}
	public double mean() { // sample mean of percolation threshold
		double sum = 0;
		for(int i = 0; i < cycles; i++) {
			sum += results[i];
		}
		
		return sum/cycles;
	}
	public double stddev() { // sample standard deviation of percolation threshold
        double mean = this.mean();
        double sum=0;
        for(int i=0; i<cycles; i++) {
            sum += Math.pow(results[i]-mean, 2);
        }
        
        return sum/(cycles-1);		
	}
	public double confidenceLo() { // returns lower bound of the 95% confidence interval
        double mean = this.mean();
        double s = this.stddev();

        return mean-1.96*s/Math.pow(cycles, 1/2);
	}
	public double confidenceHi() {// returns upper bound of the 95% confidence interval
        double mean = this.mean();
        double s = this.stddev();

        return mean+1.96*s/Math.pow(cycles, 1/2);
	}
	public static void main(String[] args) { // test client, described below
		if(args.length != 2) {
			System.out.println("error");
			return;
		}
		int N = Integer.parseInt(args[0]);
		int C = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(N,C);
		System.out.println("mean = "+percolationStats.mean());
		System.out.println("stddv = "+percolationStats.stddev());
		System.out.println("confidenceLow = "+percolationStats.confidenceLo());
		System.out.println("confidenceHigh = "+percolationStats.confidenceHi());
	}
}
