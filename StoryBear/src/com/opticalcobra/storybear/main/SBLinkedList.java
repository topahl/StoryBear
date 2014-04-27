package com.opticalcobra.storybear.main;

import java.util.LinkedList;

public class SBLinkedList<T> extends LinkedList<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4459818340789791528L;
	
	@Override
	public T get(int index){
		boolean done = false;
		T result=null;
		int i =0;
		while (!done && i< 100) {
			try {
				result=super.get(index);
				done=true;
			} catch (IndexOutOfBoundsException e) {
				Thread.currentThread().yield();
			}
			i++;
		}
		return result;
	}
}
