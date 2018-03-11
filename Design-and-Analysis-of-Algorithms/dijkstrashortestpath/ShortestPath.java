package dijkstrashortestpath;

public class ShortestPath{
	
	public static void main(String[] args){
		Graph G=new Graph();
		String GraphPath="usa.txt";
		G.ReadGraph(GraphPath);
		
		Path Path=Dijkstra.solve(G,345,789);
		
		Path.ShowDist();
		Path.ShowPath();
	}
}