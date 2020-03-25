package org.agentspace.console;

import java.lang.reflect.*;
import java.util.StringTokenizer;

public class Command {
    
	public static void perform (String command) {
		StringTokenizer st = new StringTokenizer(command, " 	");
		if (!st.hasMoreTokens()) return;
		String className = st.nextToken();
		int argc = 0;
    while(st.hasMoreTokens()) { st.nextToken(); argc++; }
    String[] args  = new String[argc];
		st = new StringTokenizer(command, " 	");
		st.nextToken();
		argc = 0;
    while(st.hasMoreTokens()) args[argc++] = st.nextToken();

    try {
	    Class<?> newClass = Class.forName("org.agentspace.shell."+className.substring(0,1).toUpperCase()+className.substring(1));
			Method mainMethod = newClass.getMethod("main", new Class<?>[]{ String[].class });
			Object instance = null;
			if (!Modifier.isStatic(mainMethod.getModifiers())) {
				newClass.getConstructor(new Class<?>[0]);
				instance = newClass.newInstance();
			}
			mainMethod.invoke(instance, new Object[] { args });
			return;
		} catch (ClassNotFoundException ee) {
		} catch (NoSuchMethodException ee) {
		} catch (InstantiationException ee) {
		} catch (IllegalAccessException ee) {
		} catch (InvocationTargetException ee) {
		} catch (Throwable ee) {
		}
    
    try {
        Reloader reloader = new Reloader(className);
//			System.err.println("launching reloader for "+className);			
	    Class<?> newClass = reloader.loadClass(className);
	    if (newClass == null) {
//		    System.err.println("Class "+className+" not found");
		    System.out.println(className+" not found");
		    throw new ClassNotFoundException();
	    }
			Method mainMethod = newClass.getMethod("main", new Class<?>[]{ String[].class });
			Object instance = null;
			if (!Modifier.isStatic(mainMethod.getModifiers())) {
				newClass.getConstructor(new Class<?>[0]);
				instance = newClass.newInstance();
			}
			mainMethod.invoke(instance, new Object[] { args });
  		System.gc();
		} catch (ClassNotFoundException ee) {
		} catch (NoSuchMethodException ee) {
		} catch (InstantiationException ee) {
		} catch (IllegalAccessException ee) {
		} catch (InvocationTargetException ee) {
		} catch (Throwable ee) {
//			System.err.println(ee);
//			ee.printStackTrace();
		}
	}
	
}