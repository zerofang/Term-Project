package dijkstrashortestpath;
import java.util.ArrayList;

public class Path{
	int ncount;
	double dist;
	ArrayList<Integer> path;

	public Path(int _ncount,double _dist,ArrayList<Integer> _path){
		ncount=_ncount;
		dist=_dist;
		path=_path;
	}
	
	public int getNcount(){
		return ncount;
	}
	
	public double getDist(){
		return dist;
	}
	
	public ArrayList<Integer> getPath(){
		return path;
	}
	
	public void ShowPath(){
		System.out.println(ncount+" points in total!");
		for(int i=0;i<ncount;++i){
			if(i!=ncount-1) System.out.print(path.get(i)+" --> ");
			else System.out.println(path.get(i));
		}

	}
	public void ShowDist(){
		System.out.println("\n"+dist+" kilometes in total!");
	}
}