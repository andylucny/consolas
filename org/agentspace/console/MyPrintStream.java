package org.agentspace.console;

import java.io.*;

public class MyPrintStream extends PrintStream {
	
	public static final String stx = "\02$$$Andrej$Lucny$$$";
	public static final String etx = "\03$$$Andrej$Lucny$$$";
	public static String newline = "\n";
	
	public MyPrintStream (OutputStream out, boolean autoFlush) {
		super(out,autoFlush);
	}
	
	public void println() {
		print(newline);
	}

	public void println (String str) {
		print(str+newline);
	}

	public void print (String str) {
		super.print(stx+Thread.currentThread().getId()+"#"+str+etx);
		super.flush();
		Thread.yield();
	}

}
