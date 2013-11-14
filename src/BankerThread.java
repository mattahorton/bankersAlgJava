public class BankerThread implements Runnable {
	
	Banker banker;
	int identifier;
	boolean stopped = false;
	Thread t;
	
	//Constructor
	public BankerThread(Banker banker, int identifier){
		this.banker = banker;
		this.identifier = identifier;
		t = new Thread(this);
	}

	//Thread operations while not interrupted
	@Override
	public void run() {	
		int r[];

		while(!stopped){
			try {
				r = generateReq(identifier);
				banker.request(r, identifier);
				op();
				r = generateRel(identifier);
				banker.release(r, identifier);
				sleep();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

	private int[] generateRel(int ident) { //FINISH THIS
		return banker.generateRel(ident);
	}

	private int[] generateReq(int ident) { //FINISH THIS
		return banker.generateReq(ident);
	}

	//Sleep
	private void sleep() throws InterruptedException {
		sleepTime(1000);
	}
	
	//Operate
	private void op() throws InterruptedException {
		sleepTime(500);
	}
	
	//Sleep for seemingly random amount of time
	//Used in op() and sleep() methods
	private void sleepTime(int time) throws InterruptedException {
		long ms = (long)(time*Math.random());
		Thread.sleep(ms);
	}
	
    public boolean getStop() {
        return stopped;
    }

    public void setStop(boolean stop) {
        this.stopped = stop;
    }

	public Thread getT() {
		return t;
	}    
}
