package txt;

import java.util.*;

public class Txt {
	private LinkedList<Object> recoder;
	
	public Txt(LinkedList<Object> recoder) {
		super();
		this.recoder = recoder;
	}	
	
	public boolean addALine(Object t){
		return recoder.add(t);
	}
	
	public int getAmount(){
		return recoder.size();
	}
	
	public void printALine(int x){
		int i = 1;
		for (Object string : recoder) {
			if(i == x) System.out.println(i+":"+string);
			i++;
		}
	}
	
	public void printAll(){
		int i = 1;
		for (Object string : recoder) {
			System.out.println(i+":"+string);
			i++;
		}
	}
	
	public void deleteALine(int i){
		recoder.remove(i-1);
	}
	
	public boolean deleteAll(){
		while(recoder.size()>0) recoder.remove(0);
		if(getAmount() == 0) return true;
		return false;
	}
}
