package org.agentspace.console;

import com.eleet.dragonconsole.*;

public class Processor extends CommandProcessor {
    private boolean inputDemo = false;
    private Object monitorInput = new Object();
    private String receivedInput = "";
    private boolean blocked = false;
    private int id;

    public Processor(int id) {
        super();
//	    	System.err.println("Processor created");
        this.id = id;
        System.setProperty("user.dir"+id,System.getProperty("user.dir"));
    }
    
    public int getId() {
	    return id;
    }
    
    @Override
    public void processCommand(String input) {
//	      System.err.println("input="+input+" blocked="+blocked+" inputDemo="+inputDemo);
        if (inputDemo) {
	          receivedInput = input;
            synchronized(monitorInput) {
	           	monitorInput.notify();
	            inputDemo = false;
            }
            output(MyPrintStream.newline);
//            System.err.println("inputDemo true->false");
        } 
        else if (!blocked) {
	        	input = input.trim();
            String cmd[] = input.split(" ");
            if (cmd.length == 0) {
		          //prompt();
            } 
            else if (cmd[0].equals("ansi")) {
                if (cmd.length > 1) {
                    if (cmd[1].equals("on")) {
                        getConsole().setUseANSIColorCodes(true);
                        outputSystem(MyPrintStream.newline+"ANSI Color Codes are now on."+MyPrintStream.newline);
                    } else if (cmd[1].equals("off")) {
                        getConsole().setUseANSIColorCodes(false);
                        outputSystem(MyPrintStream.newline+"ANSI Color Codes are now off."+MyPrintStream.newline);
                    }
                }
                prompt();
            } else if (cmd[0].equals("exit")) {
                System.exit(0);
            }
            else {
	            output(MyPrintStream.newline); 
	            new Backend(input,this);
//	            System.err.println("blocked: "+blocked);
	            if (!blocked) prompt();
            }
        }
    }
    
    public void prompt() {
	    output(id+"> ");
    }

    /**
     * Overrides the default output in CommandProcessor to determine if ANSI
     * Colors are processed or DCCC and converts accordingly.
     * @param s The String to output.
     */
    @Override
    public void output(String s) {
        if (getConsole().isUseANSIColorCodes())
            super.output(convertToANSIColors(s));
        else
            super.output(s);
    }
    
    /**
     */
		public String input () {
//      System.err.println("inputDemo "+inputDemo+"->true");
      synchronized(monitorInput) {
        try {
					inputDemo = true;
         	monitorInput.wait();
       	} catch (InterruptedException ee) {
       	}
     	}
			return receivedInput+MyPrintStream.newline; 
		}

		/**
     */
		public void block () {
			blocked = true;
			// getConsole().enabled(false);
		}
				
		/**
     */
		public void unblock () {
			if (blocked) {
				// getConsole().enabled(true);
				prompt();
			}
			blocked = false;
		}
				
}
