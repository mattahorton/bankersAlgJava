import java.text.SimpleDateFormat;
import java.util.Date;


public class BankerThread implements Runnable {
	
	Banker banker;
	int identifier;
	Boolean stopped = false;
	Thread t;
	
	//Constructor
	public BankerThread(Waiter waiter, int identifier){
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
				r = generateReq();
				banker.request(r, identifier);
				op();
				r = generateRel();
				banker.release(r, identifier);
				sleep();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}

	//Sleep
	private void sleep() throws InterruptedException {
		sleepTime(1000);
	}
	
	//Operate
	private void op() throws InterruptedException {
		sleepTime(500);
	}
	
	// //Obtain clock time in String in "hh:mm" format
	// private static String getClockTime() {
	// 	Date date = new Date();
	// 	//Must use a new SimpleDateFormat for each clock time request, as SimpleDateFormat is not thread-safe. 
	// 	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	// 	String stringDate = sdf.format(date);
	// 	sdf = null;
	// 	date = null;
	// 	return stringDate;
	// }
	
	//Sleep for seemingly random amount of time
	//Used in think() and eat() methods
	private void sleepTime(int time) throws InterruptedException {
		long ms = (long)(time*Math.random());
		Thread.sleep(ms);
	}
	
	// //Formats time data regarding thinking to be printed
	// private void printThink(int i, int count, long time) {
	// 	System.out.println(count + ": Philosopher " + i + " has been thinking for " + ((double)time)/1000 + " seconds.");
	// }
	
	// //Formats time data regarding eating to be printed
	// private void printEat(int i, int count, long time, String reqTime, String eatTime, String finishTime) {
	// 	System.out.println(count + ": Philosopher " + i + " requested to eat at " + reqTime + ", started eating at "
	// 			+ eatTime + ", finished eating at " + finishTime + ", and ate for " + ((double)time)/1000 + " seconds.");
	// }
	
    public Boolean getStop() {
        return stopped;
    }

    public void setStop(Boolean stop) {
        this.stopped = stop;
    }

	public Thread getT() {
		return t;
	}    
}
