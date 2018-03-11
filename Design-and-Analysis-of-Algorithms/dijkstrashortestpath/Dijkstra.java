package dijkstrashortestpath;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections; 
import java.util.PriorityQueue; 

public class Dijkstra{
	public static Path solve(Graph G,int S,int T){
		
		int V=G.getV();
		int E=G.getE();
		
		Queue<PQNode> PQ = new PriorityQueue<PQNode>(100,cmp);
		int[] prenode=new int[V];
		double[] dist=new double[V];
		
		for(int i=0;i<V;++i)
			dist[i]=Double.MAX_VALUE;
		dist[S]=0;prenode[S]=-1;
		
		PQ.add(new PQNode(S,0.0));
		
		PQNode Now;
		Point nowPoint;
		double todist;
		int nowIndex,toIndex;
		while(PQ.size()!=0){
			Now=PQ.poll();
			nowIndex=Now.getIndex();
			if(Now.getDist()>dist[nowIndex])
				continue;
			
			//System.out.println(nowIndex);
			
			if(nowIndex==T)
				break;
			nowPoint=G.getPoint(nowIndex);
			for(int i=0;i<nowPoint.getCount();++i){
				toIndex=nowPoint.getNindex(i);
				todist=nowPoint.getNdist(i);
				if(dist[toIndex]>dist[nowIndex]+todist){
					prenode[toIndex]=nowIndex;
					dist[toIndex]=dist[nowIndex]+todist;
					PQ.add(new PQNode(toIndex,dist[toIndex]));
				}
			}
		}
		
		ArrayList<Integer> path=new ArrayList<Integer>();
		int tnow=T,ncount=0;
		while(prenode[tnow]!=-1){
			ncount++;
			path.add(tnow);
			tnow=prenode[tnow];
		}
		ncount++;
		path.add(S);
		Collections.reverse(path); 
		
		return new Path(ncount,dist[T],path);
	}
	
	
/*	public static void solve(Graph G,ArrayList<Path> Result){
		
		int V=G.getV();
		int E=G.getE();
		int Q=G.getQ();
		int S,T;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		Queue<PQNode> PQ = new PriorityQueue<PQNode>(20,cmp);
		int[] prenode=new int[V];
		double[] dist=new double[V];
		
		for(int i=0;i<V;++i)
			dist[i]=Double.MAX_VALUE;
		
		
		for(int cas=0;cas<Q;++cas){
			while(PQ.size()!=0)
				PQ.poll();
	
			for(int i=0;i<temp.size();++i)
				dist[temp.get(i)]=Double.MAX_VALUE;
			temp.clear();
			
			S=G.getQS(cas);
			T=G.getQE(cas);
			//System.out.println(cas+"    "+S+"    "+T);
			dist[S]=0;prenode[S]=-1;
			
			PQ.add(new PQNode(S,0.0));
			
			PQNode Now;
			Point nowPoint;
			double todist;
			int nowIndex,toIndex;
			while(PQ.size()!=0){
				Now=PQ.poll();
				nowIndex=Now.getIndex();
				if(Now.getDist()>dist[nowIndex])
					continue;
				temp.add(nowIndex);
				//System.out.println(nowIndex);
				
				if(nowIndex==T){
					while(PQ.size()!=0){
						Now=PQ.poll();
						nowIndex=Now.getIndex();
						if(Now.getDist()>dist[nowIndex])
							continue;
						temp.add(nowIndex);
					}
					break;
				}
				
				nowPoint=G.getPoint(nowIndex);
				for(int i=0;i<nowPoint.getCount();++i){
					toIndex=nowPoint.getNindex(i);
					todist=nowPoint.getNdist(i);
					if(dist[toIndex]>dist[nowIndex]+todist){
						prenode[toIndex]=nowIndex;
						dist[toIndex]=dist[nowIndex]+todist;
						PQ.add(new PQNode(toIndex,dist[toIndex]));
					}
				}
			}
			
			ArrayList<Integer> path=new ArrayList<Integer>();
			int tnow=T,ncount=0;
			while(prenode[tnow]!=-1){
				ncount++;
				path.add(tnow);
				tnow=prenode[tnow];
			}
			ncount++;
			path.add(S);
			Collections.reverse(path); 
			Result.add(new Path(ncount,dist[T],path));
		}
	}
	*/
	public static Comparator<PQNode> cmp= new Comparator<PQNode>(){
		public int compare(PQNode a,PQNode b){
			double dista=a.getDist();
			double distb=b.getDist();
			if(dista<distb) return -1;
			else if(dista==distb) return 0;
			else return 1;
		}
	};

}