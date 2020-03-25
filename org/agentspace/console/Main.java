package org.agentspace.console;

public class Main {
    public static void main(String[] args) {
	      Dispatcher.getInstance();
//	    	System.err.println("Main started");
        try {
          ConsoleFrame dcf = new ConsoleFrame();
        } catch (Exception exc) {
        }
    }
}
