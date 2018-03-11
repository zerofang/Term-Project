package dijkstrashortestpath;
import java.io.*;
import java.util.ArrayList;

public class Graph{
	Point[] point;
	int V,E,Q;
	ArrayList<Integer> SP;
	ArrayList<Integer> EP;
	
	public void ReadGraph(String GraphPath){
		
		File file = new File(GraphPath);
		BufferedReader reader = null;
		
		try{	
			reader = new BufferedReader(new FileReader(file));
			String str=null;

			str=reader.readLine();
			String s[]=str.split(" +");
			System.out.println(s[0]+" "+s[1]);
			V=Integer.parseInt(s[0]);
			E=Integer.parseInt(s[1]);
			
			point=new Point[V];

			for(int i=0;i<V;++i){
				str=reader.readLine();
				s=str.split(" +");
				point[i]=new Point(i,Integer.parseInt(s[1]),Integer.parseInt(s[2]));
			}
			
			for(int i=0;i<E;++i){
				str=reader.readLine();
				s=str.split(" +");
				int u=Integer.parseInt(s[1]);
				int v=Integer.parseInt(s[2]);
				point[u].addEdge(point[v]);
				point[v].addEdge(point[u]);
			}

			SP=new ArrayList<Integer>();
			EP=new ArrayList<Integer>();
			
			while((str=reader.readLine())!=null){
				s=str.split(" +");
				SP.add(Integer.parseInt(s[0]));
				EP.add(Integer.parseInt(s[1]));
				//System.out.println(s[0]+"    "+s[1]);
			}
			Q=SP.size();
			//System.out.println("V  =  "+V+"    E  =  "+E+"    Q  =  "+Q);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public int getV(){
		return V;
	}
	public int getE(){
		return E;
	}
	public int getQ(){
		return Q;
	}
	public int getQS(int i){
		return SP.get(i);
	}
	public int getQE(int i){
		return EP.get(i);
	}
	public Point getPoint(int index){
		return point[index];
	}
}