package org.agentspace.shell;

import java.util.*;
import java.lang.reflect.*;
import org.agentspace.console.Backend;

public class Kill {
   public static void main(String args[]){
	    if (args.length == 0) {
		    System.out.println("kill <pid>");
		    return;
	    }
	    long id = Long.parseLong(args[0]);
	   	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
	   	Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
	   	for (int i=0; i<threadArray.length; i++) {
		   	Thread t = threadArray[i];
		   	long l = t.getId();
		   	if (l == id) {
		   		String process = Backend.get(l);
		   		if (process != null) {
			   		try {
				   		Method m = Thread.class.getDeclaredMethod( "stop0" , new Class[]{Object.class} );
							m.setAccessible( true );
							m.invoke( t , new ThreadDeath() );
						} catch (IllegalAccessException ee) {
						} catch (InvocationTargetException ee) {
						} catch (NoSuchMethodException ee) {
						}
		   			System.out.println(l+" killed");
	   			}
   			}
	   	}
   }
}