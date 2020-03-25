package org.agentspace.shell;

import java.io.File;

public class Pwd {

    public static void main(String args[]) {
        String wd = System.getProperty("user.dir"+Thread.currentThread().getName());        
        if(wd == null || !(new File(wd)).exists()) return;
        System.out.println(wd);
    }

}
