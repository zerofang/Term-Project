package dijkstrashortestpath;
import java.util.ArrayList;

public class Point{
	int x,y,index,count;
	ArrayList<Integer> nindex;
	ArrayList<Double> ndist;
	
	public Point(int _index,int _x,int _y){
		x=_x;y=_y;index=_index;
		ndist=new ArrayList<Double>();
		nindex=new ArrayList<Integer>();
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getNindex(int idx){
		return nindex.get(idx);
	}
	public double getNdist(int idx){
		return ndist.get(idx);
	}
	public double Edistance(Point a){
		int ax=a.getX();
		int ay=a.getY();
		return Math.sqrt((ax-x)*(ax-x)+(ay-y)*(ay-y));
	}
	
	public void addEdge(Point a){
		int aindex=a.getIndex();
		double dist=Edistance(a);
		nindex.add(aindex);
		ndist.add(dist);
		++count;
	}
	
	public static double Edistance(Point a,Point b){
		int ax=a.getX();
		int ay=a.getY();
		int bx=b.getX();
		int by=b.getY();
		return Math.sqrt((ax-bx)*(ax-bx)+(ay-by)*(ay-by));
	}
}