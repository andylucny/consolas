package org.agentspace.shell;

import java.io.File;

public class Cd {

    public static void main(String args[]) {
	    if (args.length == 0) {
		    System.out.println("cd <dir>");
		    return;
	    }
	    String wd = args[0];
	    if (wd.endsWith("\\")) wd = wd.substring(0,wd.length()-1);
	    String ws;
	    if (wd.equals("..")) {
		    ws = System.getProperty("user.dir"+Thread.currentThread().getName());
		    while (!ws.endsWith("\\")) ws = ws.substring(0,ws.length()-1);
		    ws = ws.substring(0,ws.length()-1);
	    }
	    else ws = System.getProperty("user.dir"+Thread.currentThread().getName())+"\\"+wd;
      if((new File(ws)).exists()) wd = ws;
	    if(!(new File(wd)).exists()) return;
	    System.setProperty("user.dir"+Thread.currentThread().getName(),wd);
    }

}
