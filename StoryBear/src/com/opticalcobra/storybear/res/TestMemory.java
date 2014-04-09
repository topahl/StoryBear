package com.opticalcobra.storybear.res;

public class TestMemory {
    
    public static void print() {
        if(Ressources.DEBUG){
        	Runtime runtime = Runtime.getRuntime();
        	int mb = 1024*1024;
	        String result = "Memory ";
	        long mem = runtime.totalMemory();
	        long used = mem - runtime.freeMemory();
	        int percent = (int)((100*used)/mem);
	        //Getting the runtime reference from system
	        
	        for(int i=0;i<40;i++){
	        	if(i*4<percent)
	        		result+='#';
	        	else
	        		result+='=';
	        	
	        }
	        
	        System.out.println(result);
	        System.out.println("Cores "+runtime.availableProcessors());
	        
//	        System.out.println("##### Heap utilization statistics [MB] #####");
//	         
//	        //Print used memory
//	        System.out.println("Used Memory:"
//	            + (runtime.totalMemory() - runtime.freeMemory()) / mb);
//	 
//	        //Print free memory
//	        System.out.println("Free Memory:"
//	            + runtime.freeMemory() / mb);
//	         
//	        //Print total available memory
//	        System.out.println("Total Memory:" + runtime.totalMemory() / mb);
//	 
//	        //Print Maximum available memory
//	        System.out.println("Max Memory:" + runtime.maxMemory() / mb);
        }
    }
}