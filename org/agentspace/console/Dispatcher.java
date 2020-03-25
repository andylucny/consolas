package org.agentspace.console;

import java.io.*;
import java.util.*;

public class Dispatcher implements Runnable {

	static private Dispatcher instance = create();

	static private Dispatcher create() {
		Dispatcher myinstance = null;
		try {
			myinstance = new Dispatcher();
//			System.err.println("Dispatcher created");
		} catch (java.io.IOException ee) {
		}
		return myinstance;
	}
	
	static public Dispatcher getInstance () {
		return instance;
	}
	
	private PipedInputStream pin; 
	private boolean quit = false;
	
	private Dispatcher () throws java.io.IOException {
		pin=new PipedInputStream(); 
		PipedOutputStream pout=new PipedOutputStream(pin);
		System.setOut(new MyPrintStream(pout,false)); 
//		System.err.println("out initialized");
		System.setIn(new MyInputStream()); 
//		System.err.println("in initialized");
		new Thread(this).start();
//		System.err.println("dispatcher started");
	}
	
	private String buffer = "";

	String readLine() throws java.io.IOException {
		String input="";
		do {
			int available=pin.available();
			if (available==0) break;
			byte b[]=new byte[available];
			pin.read(b);
			input=input+new String(b,0,b.length);														
		} while( !input.endsWith(MyPrintStream.newline) && !quit);
		return input;
	}	

	public void run () {
		for (; !quit; ) {
			try {
				String line = readLine();
				if (line.length() > 0) write(line);
				try { 
					Thread.sleep(50);
				} catch(InterruptedException ie) {
				}
			} catch (java.io.IOException ee) {
			}
		}
	}
	
	public void quit () {
		quit = true;
	}
	
	private HashMap<Long,Dispatchable> hash = new HashMap<Long,Dispatchable>();
	
	public void addDispatchable (Thread thread, Dispatchable device) {
		hash.put(thread.getId(),device);
//		System.err.println("process "+thread.getId()+" registered");
	}
	
	public void removeDispatchable (Thread thread, Dispatchable device) {
		hash.remove(thread.getId());
//		System.err.println("process "+thread.getId()+" removed");
	}

	public void write (String line) {
//		System.err.println("to be write: "+line);
		line = buffer + line;
		buffer = "";
		int j = 0;
		for (;;) {
			j = line.indexOf(MyPrintStream.stx,j);
			if (j == -1) break;
			int k = line.indexOf(MyPrintStream.etx,j+MyPrintStream.stx.length());
			if (k == -1) {
				buffer = line.substring(j);
//				System.err.println("stx-etx synchronization lost");
				break;
			}
			String part = line.substring(j+MyPrintStream.stx.length(),k);
//			System.err.println("part: "+part);
			int ind = part.indexOf("#",0);
			long id = new Long(part.substring(0,ind));
//			System.err.println("identified: "+id);
			Dispatchable device = hash.get(id);
//			System.err.println("written data: "+part.substring(ind+1));
			device.write(id,part.substring(ind+1));
			j = k + MyPrintStream.etx.length();
		}
	}
	
	public String read (long id) {
//		System.err.println("to be read");
		Dispatchable device = hash.get(id);
		if (device != null) return device.read(id);		
		else return "";
	}
	
}