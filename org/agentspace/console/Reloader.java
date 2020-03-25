package org.agentspace.console;

import java.io.*;

// http://stackoverflow.com/questions/3971534/how-to-force-java-to-reload-class-upon-instantiation

public class Reloader extends ClassLoader {
    
    private String reloadedClassName;
    
    public Reloader (String name) {
        reloadedClassName = name;
    }
	
    @Override
    public Class<?> loadClass(String s) {
        return findClass(s);
    }

    @Override
    public Class<?> findClass(String s) {
        if (s.equals(reloadedClassName)) {
            //System.out.println("loading reloadable class "+s);
            try {
                byte[] bytes = loadClassData(s);
                return defineClass(s, bytes, 0, bytes.length);
            } catch (IOException ee) {
                try {
                    return ClassLoader.getSystemClassLoader().loadClass(s);
                } catch (ClassNotFoundException ignore) { }
            }
        }
        else {
            //System.out.println("loading normal class "+s);
            try {
                return ClassLoader.getSystemClassLoader().loadClass(s);
            } catch (ClassNotFoundException ignore) { }
        }
        //System.out.println("loading class failed");
        return null;
    }

    private byte[] loadClassData(String className) throws IOException {
//        System.err.println("from "+Thread.currentThread().getName()+" loading "+className);
				String dir = System.getProperty("user.dir"+Thread.currentThread().getName());
				if (dir == null) dir = System.getProperty("user.dir");
//				System.err.println("directory "+dir);
        File f = new File(dir.replaceAll("\\.", "/") + "/" + className.replaceAll("\\.", "/") + ".class");
        int size = (int) f.length();
        byte buff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        dis.readFully(buff);
        dis.close();
//        System.err.println("loaded "+size+"B");
        return buff;
    }
}