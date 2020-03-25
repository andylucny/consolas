package org.agentspace.shell;

import java.io.*;

public class Ls {
	
	public static void main (String[] args) {
		File folder = new File(System.getProperty("user.dir"+Thread.currentThread().getName()));
		File[] listOfFiles = folder.listFiles();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println(" " + listOfFiles[i].getName());
      } else if (listOfFiles[i].isDirectory()) {
        System.out.println("+" + listOfFiles[i].getName());
      }
    }	
  }

}
