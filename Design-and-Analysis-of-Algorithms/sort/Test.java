package sort;

import java.util.Random;

public class Test extends Object implements Cloneable{
	
    Integer[] unordered = null;
    int size;

    public Test(int size){
        this.size = size;

        unordered = new Integer[this.size];
        Random random = new Random();

        for(int i=0; i<this.size; i++){
            unordered[i] = random.nextInt();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Test t = (Test)super.clone();
        t.unordered = unordered.clone();

        return t;
    }
    
    private static void output(int i,long[][] time, boolean[][] flag) {
    	long sum = 0;
    	float cycle = 10;
    	float temp = 1;
    	for(int j = 0; j<10; j++) {
    		if(flag[i][j]) {
    			System.out.format("%s ", "error");
    			cycle--;
    			continue;
    		}
    		sum+=time[i][j];
    		System.out.format("%.4f ",time[i][j]/temp);
    	}
		System.out.format("AvgTime: %.4f\n",sum/cycle);
    }
    
    public static void main(String[] args){
        int cycle = 10;
        
        long[][] time = new long[6][10];
        boolean[][] flag = new boolean[6][10];
        long startTime, endTime;

        for(int i=0; i<cycle; i++){
            Test data = new Test(Integer.valueOf(args[0]));
            try{
                Test test = (Test)data.clone();

                try{
                    startTime = System.currentTimeMillis(); 
                    Insertion.sort(test.unordered);
                    endTime = System.currentTimeMillis();
                    time[0][i] = endTime - startTime;
                } catch(StackOverflowError e){
                    flag[0][i] = true;
                }
            } catch(CloneNotSupportedException e){ e.printStackTrace();}


            try{
                Test test = (Test)data.clone();

                try{
                    startTime = System.currentTimeMillis();
                    Merge.sort(test.unordered);
                    endTime=System.currentTimeMillis();
                    time[1][i] = endTime - startTime;
                } catch(StackOverflowError e){
                    flag[1][i] = true;
                }
            } catch(CloneNotSupportedException e){}

            try{
                Test test = (Test)data.clone();
                try{
                    startTime = System.currentTimeMillis();
                    MergeBU.sort(test.unordered);
                    endTime=System.currentTimeMillis();
                    time[2][i] = endTime - startTime;
                } catch(StackOverflowError e){
                    flag[2][i] = true;
                }
            } catch(CloneNotSupportedException e){}

            try{
                Test test = (Test)data.clone();

                try{
                    startTime = System.currentTimeMillis();
                    Quick.sort(test.unordered);
                    endTime=System.currentTimeMillis();
                    time[3][i] = endTime - startTime;
                } catch(StackOverflowError e){
                    flag[3][i] = true;
                }
            } catch(CloneNotSupportedException e){}

            try{
                Test test = (Test)data.clone();

                try{
                    startTime = System.currentTimeMillis();
                    QuickDijkstra.sort(test.unordered);
                    endTime=System.currentTimeMillis();
                    time[4][i] = endTime - startTime;
                } catch(StackOverflowError e){
                    flag[4][i] = true;
                }
            } catch(CloneNotSupportedException e){}
        }
        System.out.println("Insertion:");
        output(0,time,flag);
        System.out.println("Merge:");
        output(1,time,flag);
        System.out.println("Merge BU:");
        output(2,time,flag);
        System.out.println("Random Quicksort:");
        output(3,time,flag);
        System.out.println("QuickDijkstra:");
        output(4,time,flag);
       
    }
}
