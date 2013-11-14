import java.util.Random;


public class Banker {

	private Object lock = new Object();
	private int btNum, rNum;
	private boolean safe;
	private int[][] need, alloc, max;
	private int[] available;
	
	//Banker constructor
	public Banker(int btNum, int[] resources){
		this.btNum = btNum;
		this.rNum = resources.length;
		this.available = resources;
		init();
	}

	public void init(){
		need = new int[btNum][rNum];
		alloc = new int[btNum][rNum];
		max = new int[btNum][rNum];

		for(int k = 0; k < btNum; k++){
			for(int m = 0; m < rNum; m++){
				max[k][m] = randInt(0,available[m]);
				alloc[k][m] = 0;
			}
		}

		need = matrixSubtract(max,alloc);
	}

	//Request resources
	public void request(int[] r, int i) throws InterruptedException {
		boolean granted = true;
		safe = false;

		synchronized(lock){
			//Make sure request is necessary and sane
			if(!arrayGreaterThan(r,need[i])){
				//Wait for resources to become available
				while(arrayGreaterThan(r,available)){
					lock.wait();
				}

				//Go ahead and allocate
				available = arraySubtract(available, r);
				alloc[i] = arrayAdd(alloc[i], r);
				need[i] = arraySubtract(need[i], r);

				//Check for unsafe state
				safe = safety();
				System.out.println(safe);

				//If unsafe state found, revert allocation
				if(!safe){
					available = arrayAdd(available, r);
					alloc[i] = arraySubtract(alloc[i], r);
					need[i] = arrayAdd(need[i], r);
					granted = false;
				}
			}
			else{
				granted = false;
			}

			System.out.println("Request from Thread #" + i);
			System.out.println("Request:");
			for(int n = 0; n < rNum; n++){
				System.out.print("R" + n + ": " + r[n] + "  ");
			}
			System.out.println("\nGranted: " + granted);
			System.out.println("Available: ");
			for(int n = 0; n < rNum; n++){
				System.out.print("R" + n + ": " + available[n] + "  ");
			}
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}

	//Return resources to the system
	public void release(int[] r, int i) throws InterruptedException{
		synchronized(lock){

			available = arrayAdd(available, r);
			alloc[i] = arraySubtract(alloc[i], r);
			need[i] = arrayAdd(need[i], r);

			System.out.println("Release from Thread #" + i);
			System.out.println("Release:");
			for(int n = 0; n < rNum; n++){
				System.out.print("R" + n + ": " + r[n] + "  ");
			}
			System.out.println("\nAvailable: ");
			for(int n = 0; n < rNum; n++){
				System.out.print("R" + n + ": " + available[n] + "  ");
			}
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();

			lock.notifyAll();
		}
	}
	
	public int[] generateRel(int ident) { //FINISH THIS
		int[] val = new int[rNum];
		
		synchronized(lock){
			for(int m = 0; m < rNum; m++){
				val[m] = randInt(0,alloc[ident][m]);
			}
		}
		
		return val;
	}

	public int[] generateReq(int ident) { //FINISH THIS
		int[] val = new int[rNum];
		
		synchronized(lock){
			for(int m = 0; m < rNum; m++){
				val[m] = randInt(0,need[ident][m]);
			}
		}
		
		return val;
	}

	private boolean arrayGreaterThan(int[] req, int[] compare){
		boolean val = false;
		if((req != null) && (compare != null)){
			if(req.length == compare.length){
				for(int i = 0; i < req.length; i++){
					if(req[i] > compare[i]){
						val = true;
					}
				}
			}
		}
		return val;
	}

	private static int randInt(int min, int max) {

    	Random rand = new Random();
    	int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/*
	/*
	/*
	/*
	/*   NOTE: Methods below require mutex lock
	/*
	/*
	/*
	*/

	private int[] arraySubtract(int[] a, int[] b){
		int[] c = null;
		if((a != null) && (b != null)){
			if(a.length == b.length){

				c = new int[a.length];

				for(int i = 0; i < a.length; i++){
					c[i] = a[i] - b[i];
				}
			}
		}
		return c;
	}

	private int[] arrayAdd(int[] a, int[] b){
		int[] c = null;
		if((a != null) && (b != null)){
			if(a.length == b.length){

				c = new int[a.length];

				for(int i = 0; i < a.length; i++){
					c[i] = a[i] + b[i];
				}
			}
		}
		return c;
	}

	private int[][] matrixSubtract(int[][] a, int[][] b){
		int[][] c = null;
		if((a != null) && (b != null)){
			if(a.length == b.length){

				c = new int[a.length][a[1].length]; //FIX THIS

				for(int l = 0; l < a.length; l++){
					c[l] = arraySubtract(a[l],b[l]);
				}
			}
		}
		return c;
	}

	//Test for unsafe state
	private boolean safety() {
		int[] work = available;
		boolean[] finish = new boolean[btNum];

		for(int j = 0; j < btNum; j++){
			finish[j] = false;
		}

		for(int n = 0; n < btNum; n++){
			for(int i = 0; i < btNum; i++){
				if((finish[i] == false) && !arrayGreaterThan(need[i],work)){
					work = arrayAdd(work,alloc[i]);
					finish[i] = true;
					i = btNum + 1;
				}
			}
		}

		for(int k = 0; k < btNum; k++){
			if (finish[k] == false) {
				return false;
			}
		}

		return true;
	}
}
