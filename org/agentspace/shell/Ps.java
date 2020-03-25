package org.agentspace.shell;

import java.util.*;
import org.agentspace.console.Backend;

public class Ps {
   public static void main(String args[]){
	   	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	   	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
	   	for (int i=0; i<threadArray.length; i++) {
		   	long l = threadArray[i].getId();
		   	String process = Backend.get(l);
		   	if (process != null)
		   		System.out.println(l+" "+process);
	   	}
   }
}