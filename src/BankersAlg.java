import java.util.Arrays;


public class BankersAlg {
	
	//Default values for banker thread number and duration
	public static final int BANKERTHREADS = 5;
	public static final int DURATION = 5000;
	
	public static void main(String[] args) throws InterruptedException{
		
		int bankerthreads = BANKERTHREADS;
		int duration = DURATION;
		
		//Parse user-provided values
		try{
			bankerthreads = Integer.parseInt(args[0]); //Allows user to specify number of bankerthreads
			duration = Integer.parseInt(args[1])*1000; //Allows user to specify duration in seconds
		} catch (Exception e) {
			System.out.println("Please input at least two integer arguments. The first is the number of bankerthreads. " +
				"The second is the duration of the program run in seconds. " +
				"Any following arguments, argnum, denote the available number of resources of type arg[argnum - 1].");
		}

		//Obtain subset of args for resource availability and cast its members to ints
		String[] resourceStrings = Arrays.copyOfRange(args, 2, args.length);
		int[] resources = new int[resourceStrings.length];
		for(int j = 0; j < resources.length; j++){
			resources[j] = Integer.parseInt(resourceStrings[j]);
		}
		
		//Banker! Banker!
		Banker banker = new Banker(bankerthreads, resources);
		
		//Gimme some BankerThreads
		BankerThread[] bts = new BankerThread[bankerthreads];
		for(int i = 0; i < bankerthreads; i++){
			bts[i] = new BankerThread(banker,i);
			bts[i].getT().start();
		}
		
		//Wait for BankerThreads to operate
		try{
			Thread.sleep(duration);
		} catch (InterruptedException e){}
		
		//Stop all BankerThreads
		for(int i = 0; i < bankerthreads; i++){
			bts[i].setStop(true);
		}
		System.out.println("done");

		return;
	}

}
