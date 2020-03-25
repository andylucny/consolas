package org.agentspace.console;

import com.eleet.dragonconsole.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Console
 * @author 
 */
public class ConsoleFrame extends DragonConsoleFrame implements ActionListener {

		public static final long serialVersionUID = 0;
		private static int con = 0;
		private static int opc = 0;
		
     /** Construction
     */
    public ConsoleFrame() {
	    	super();

	    	setTitle("Console 3.0");
	    	
	    	JLabel leftLabel = new JLabel("Console - more consoles for Java    ");
	    	JLabel rightLabel = new JLabel("    developed by Andrej Lucny, based on jsh and Dragonconsole");
	    	
				JButton but = new JButton("Add");
				but.addActionListener(this);
				JButton but2 = new JButton("Close");
				but2.addActionListener(this);

	    	JPanel menu = new JPanel();
				menu.setLayout(new FlowLayout());
				menu.add(leftLabel);
				menu.add(but);
				menu.add(but2);
				menu.add(rightLabel);

				JPanel panel=new JPanel();
				panel.setLayout(new BorderLayout());
				panel.add(getConsole(),BorderLayout.CENTER);
				panel.add(menu,BorderLayout.NORTH);
				add(panel);
        pack();
        setLocationRelativeTo(null);
        
        con++; opc++;
        getConsole().setCommandProcessor(new Processor(con));
        getConsole().append(con+"> ");
        setVisible(true);
        
        Point lh = getLocationOnScreen();
        int x = getX() + getWidth()/2;
        int y = getY() + getHeight()/2;
        try {        
	        Robot r = new Robot();
  	      r.mouseMove(x,y);
    	    r.mousePress(InputEvent.BUTTON1_MASK);
					r.mouseRelease(InputEvent.BUTTON1_MASK);
				} catch (AWTException ee) {
				}
    }
    
    public void actionPerformed(ActionEvent e) {
	    String name = ((JButton) e.getSource()).getActionCommand();
	    if ("Add".equals(name)) {
        try {
          new ConsoleFrame();
        } catch (Exception ee) {
        }
      }
      else if ("Close".equals(name)) {
	      close();
      }
		}

		public void close() {
			setVisible(false);
			dispose();
			opc--;
			if (opc == 0) System.exit(0);
		}	
}
