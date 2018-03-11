package dijkstrashortestpath;
public class PQNode{
	int index;
	double dist;
	
	public PQNode(int _index,double _dist){
		index=_index;dist=_dist;
	}
	
	public int getIndex(){
		return index;
	}
	
	public double getDist(){
		return dist;
	}
}