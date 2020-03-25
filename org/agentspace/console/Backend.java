package org.agentspace.console;

import java.util.*;

public class Backend implements Dispatchable, Runnable {
	
	private static Map<Long,String> hash = Collections.synchronizedMap(new HashMap<Long,String>());
	
	public static String get (long id) {
		return hash.get(id);
	}
	
	private String command;
	private Processor processor;
	private Thread commander;
	private boolean blocking;
	
	public Backend (String command, Processor processor) {
//		System.err.println("Command: <"+command+">");
		this.command = command;
		this.command = this.command.trim();
		blocking = !this.command.endsWith("&");
		this.processor = processor;
		if (!command.equals("")) {
			commander = new Thread(this);
			commander.start();		
			hash.put(commander.getId(),this.command);
			commander.setName(""+processor.getId());
			Dispatcher.getInstance().addDispatchable(commander,this);
		}
		// tento sleep je tu na to, aby sa stihol zrusit prompt
		try { 
			Thread.sleep(100);
		} catch(InterruptedException ie) {
		} 
	}
	
	public void run() {
		// tu sa vykona command
		if (blocking) processor.block();
		else {
			command = command.substring(0,command.length()-1);
			command = command.trim();
		}
//		System.err.println("backend before command");
		Command.perform(command);
		try { 
			Thread.sleep(100);
		} catch(InterruptedException ie) {
		}
		if (blocking) processor.unblock();
//		System.err.println("backend after command");
		Dispatcher.getInstance().removeDispatchable(commander,this);
		hash.remove(commander.getId());
	}
	
	public String read (long id) {
		if (!blocking) return "";
		return processor.input();
	}
	
	public void write (long id, String str) {
		processor.output(str);
	}
	
}