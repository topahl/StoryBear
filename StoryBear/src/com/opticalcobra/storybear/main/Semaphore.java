package com.opticalcobra.storybear.main;
/*
  File: Semaphore.java

  Originally written by Doug Lea and released into the public domain.
  This may be used for any purposes whatsoever without acknowledgment.
  Thanks for the assistance and support of Sun Microsystems Labs,
  and everyone contributing, testing, and using this code.

  History:
  Date       Who                What
  11Jun1998  dl               Create public version
   5Aug1998  dl               replaced int counters with longs
  24Aug1999  dl               release(n): screen arguments


  This is a slightly adapted and simplified version for the use in
  the operating systems lecture Dual Hochschule Baden-Württemberg.
  
  The method names were adjusted to the lecture
  instead of acquire() we use down()
  instead of release() we use up()
  
  Source of the original code: http://gee.cs.oswego.edu/dl/classes/
  EDU/oswego/cs/dl/util/concurrent/intro.html
  Thanks to University Siegen (Roland Wismueller) too. 
*/


/**
 * Base class for counting semaphores.
 * Conceptually, a semaphore maintains a set of permits.
 * Each up() blocks if necessary
 * until a permit is available, and then takes it. 
 * Each down() adds a permit. However, no actual permit objects
 * are used; the Semaphore just keeps a count of the number
 * available and acts accordingly.
 * <p>
 * A semaphore initialized to 1 can serve as a mutual exclusion
 * lock. 
 * <p>
 * This implementation makes NO 
 * guarantees about the order in which threads will 
 * acquire permits
**/


public class Semaphore 
{
	/** Set this variable to true for debugging output */
	public static boolean verbose = false;

	/** current number of available permits **/
	protected int permits_;
	
	/** The Semaphore name (for debugging) **/
	String name;
	static int seqno = 1;

	/** 
	 * Create a Semaphore with the given initial number of permits.
	 * Using a seed of one makes the semaphore act as a mutual exclusion lock.
	 * Negative seeds are also allowed, in which case no acquires will proceed
	 * until the number of releases has pushed the number of permits past 0.
	 * @param initialPermits initial value of the Semaphore
	 **/
	public Semaphore(int initialPermits) 
	{
		permits_ = initialPermits;
	}
	
	/** 
	 * Create a named Semaphore with the given initial number of permits.
     * the name is only used for debugging output.
	 * Using a seed of one makes the semaphore act as a mutual exclusion lock.
	 * Negative seeds are also allowed, in which case no acquires will proceed
	 * until the number of releases has pushed the number of permits past 0.
	 * @param initialPermits initial value of the Semaphore
	 * @param name name of the Semaphore
	 **/
	public Semaphore(int initialPermits, String name) 
	{
		permits_ = initialPermits;
		this.name = name;
	}
	
		
	/** Wait until a permit is available, and take one **/
	public synchronized void down() 
	{
		try {
			long oldPermits = permits_;
			if (verbose && permits_ <= 0) {
				System.out.println(Thread.currentThread().getName()
								   + ": " + name 
								   + ".down() : warte ...");
		  		System.out.flush();
			}

			while (permits_ <= 0) 
				wait();
			--permits_;

			if (verbose) {
				if (oldPermits <= 0) {
					System.out.println(Thread.currentThread().getName()
									   + ": " + name + ".down() : fertig");
				}
				else {
					System.out.println(Thread.currentThread().getName()
									   + ": " + name + ".down()");
				}
		  		System.out.flush();
			}
		}
		catch (InterruptedException ex) {
			System.err.println("Interrupt, exiting ...");
			System.exit(1);
		}
	}
	
	
	/** Release a permit **/
	public synchronized void up()
	{
		if (verbose) {
			System.out.println(Thread.currentThread().getName()
							   + ": " + name + ".up()");
		  	System.out.flush();
		}
		++permits_;
		notify();
	}
}

