package org.agentspace.console;

public interface Dispatchable {
	public String read (long id);
	public void write (long id, String str);
}