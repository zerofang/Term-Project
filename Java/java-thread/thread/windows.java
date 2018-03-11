package thread;

class windows implements Runnable{
	private int i;
	private titicks N;
	windows(int i,titicks N){
		this.i=i;
		this.N=N;
	}
	public void run() {

		while(true){
			synchronized (N){
			N.sell();
			System.out.println("窗口"+this.i+"售出第"+ (N.total - N.gettic()) + "张票");
			       }
			try {
				int n = (int) (Math.random() * 2000);
				Thread.sleep(n);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!N.have()){
				System.out.println("已售空！");
				System.exit(0);
			}
		}
	}
}
