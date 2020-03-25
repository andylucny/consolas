package org.agentspace.console;

import java.io.*;

public class MyInputStream extends InputStream {
	
	byte[] myByte = new byte[1];
	
	public MyInputStream () {
		super();
	}
	
	public int read() throws IOException {
		if (read(myByte) != 1) return -1;
		else return myByte[0];
	}

	public int read(byte[] b) throws IOException {
		return read(b,0,b.length);
	}
	
	private StringBuffer buffer = new StringBuffer();
	
	public int read(byte[] b, int off, int len) throws IOException {
//		System.err.println("MyInputStream read");
		int n = buffer.length();
		if (n == 0) {
			Dispatcher dispatcher = Dispatcher.getInstance();
//		  System.err.println("MyInputStream before dispatch");
			String inputLine = dispatcher.read(Thread.currentThread().getId());
//		  System.err.println("MyInputStream after dispatch");
			buffer.append(inputLine.subSequence(0,inputLine.length()));
			n = buffer.length();
		}
		if (n < len) len = n;
		byte[] bytes = buffer.substring(0,len).getBytes();
//		System.err.println("MyInputStream read n="+n+" dlzka="+bytes.length);
		System.arraycopy(bytes,0,b,off,bytes.length);
		buffer.delete(0,len);
//	  System.err.println("MyInputStream return"+len);
		return len;
	}

}
