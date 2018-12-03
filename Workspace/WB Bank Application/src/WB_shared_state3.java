import java.net.*;
import java.io.*;

public class WB_shared_state3 {
	
	private WB_shared_state mySharedObj;
	private String myThreadName;
	private double mySharedVariable3;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers

// Constructor	
	
	WB_shared_state3(double SharedVariable3) {
		mySharedVariable3 = SharedVariable3;
	}

//Attempt to aquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

		  public synchronized String processInput(String myThreadName, String theInput) {
	    		System.out.println(myThreadName + " received "+ theInput);
	    		String theOutput = null;
	    		// Check what the client said
	    		if (theInput.equalsIgnoreCase("Action")) {
	    			//Correct request
	    			if (myThreadName.equals("WB_server_Threading1")) {
	    				/*  Add 20 to the variable
	    					
	    				 */
	    				mySharedVariable3  = mySharedVariable3  + 23;
	   				System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable3 );
	    				theOutput = "Do WB completed.  Shared Variable now = " + mySharedVariable3 ;
	    			}
	    			else if (myThreadName.equals("WBServerThread2")) {
	    				/*	Subtract 5 from the variable
	    					
	    					*/
	       				mySharedVariable3  = mySharedVariable3  - 5;
	    					
	    				System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable3 );
	    				theOutput = "Do WB completed.  Shared Variable now = " + mySharedVariable3 ;

	    			}
	       			else {System.out.println("Error - thread call not recognised.");}
	    		}
	    		else { //incorrect request
	    			theOutput = myThreadName + " received incorrect request - only understand \"Action\"";
			
	    		}
	 
	     		//Return the output message to the WBServer
	    		System.out.println(theOutput);
	    		return theOutput;
	    	}	
}
