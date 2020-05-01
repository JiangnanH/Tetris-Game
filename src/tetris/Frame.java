package tetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

public class Frame extends JFrame{
	 private static final long serialVersionUID = 1L;  	  
	 private Tetris panel;
	 private Container contentPane; 
	 private JMenuItem startMI = new JMenuItem("Start");  
	  
     private JMenuItem pauseMI = new JMenuItem("Pause");  
     
     private JMenuItem continueMI = new JMenuItem("Continue"); 
  
     private JMenu speedMenu = new JMenu("Speed");  
  
     private JMenuItem exitMI = new JMenuItem("Exit");  
  
     private JMenuItem aboutMI = new JMenuItem("About");  
  
     private JRadioButtonMenuItem speedMI1 = new JRadioButtonMenuItem("Speed1",  
            true);  
  
     private JRadioButtonMenuItem speedMI2 = new JRadioButtonMenuItem("Speed2",  
            false);
  
     private JRadioButtonMenuItem speedMI3 = new JRadioButtonMenuItem("Speed3",  
            false);  
  
     private JRadioButtonMenuItem speedMI4 = new JRadioButtonMenuItem("Speed4",  
            false);  
  
	
	public Frame() {  
	    
	    JMenuBar menuBar = new JMenuBar();  
        setJMenuBar(menuBar);  
  
        JMenu setMenu = new JMenu("Set");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(setMenu);
        menuBar.add(helpMenu);
  
        setMenu.add(startMI);  
        setMenu.add(pauseMI);  
        setMenu.add(continueMI);
        setMenu.addSeparator();  
  
        setMenu.addSeparator();  
        setMenu.add(speedMenu);  
        setMenu.addSeparator();  
        setMenu.add(exitMI);  
  
        ButtonGroup group = new ButtonGroup();  
        group.add(speedMI1);  
        group.add(speedMI2);  
        group.add(speedMI3);  
        group.add(speedMI4);   
  
        speedMenu.add(speedMI1);  
        speedMenu.add(speedMI2);  
        speedMenu.add(speedMI3);  
        speedMenu.add(speedMI4);  
  
        startMI.addActionListener(new StartAction());  
        pauseMI.addActionListener(new PauseAction());
        continueMI.addActionListener(new ContinueAction());
        exitMI.addActionListener(new ExitAction());  
        speedMI1.addActionListener(new SpeedAction());  
        speedMI2.addActionListener(new SpeedAction());  
        speedMI3.addActionListener(new SpeedAction());  
        speedMI4.addActionListener(new SpeedAction());  
  
        helpMenu.add(aboutMI);  
        aboutMI.addActionListener(new AboutAction());  
        
        contentPane = getContentPane();   
        panel = new Tetris();
        contentPane.add(panel);
        
	    setTitle("Tetris");  
	    setSize(525, 610);  
	    setResizable(false);  
		setUndecorated(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
        
        startMI.setEnabled(true);  
        pauseMI.setEnabled(true); 
        continueMI.setEnabled(false); 

	}
	
    private class StartAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
        	startMI.setEnabled(true);  
            pauseMI.setEnabled(true); 
            continueMI.setEnabled(false);  
            panel.startAction();
        }  
    }  
    
    private class PauseAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
            pauseMI.setEnabled(false);  
            continueMI.setEnabled(true);  
            panel.pauseAction(); 
        }   
    }
    
    private class ContinueAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
            pauseMI.setEnabled(true);  
            continueMI.setEnabled(false);  
            panel.continueAction(); 
        }   
    }
    
    private class ExitAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
            int result = JOptionPane.showConfirmDialog(Frame.this, "Are you sure to quit?", "Quit",JOptionPane.YES_NO_OPTION);  
            if (result == JOptionPane.YES_OPTION) {  
                System.exit(0);  
            }  
        }  
    }
    
    private class SpeedAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
            Object speed = event.getSource();  
            if (speed == speedMI1) {  
                  panel.setLevel(1); 
                  panel.setScore(0);
            } else if (speed == speedMI2) {  
            	  panel.setLevel(2);
            	  panel.setScore(21);
            } else if (speed == speedMI3) {  
            	  panel.setLevel(3);
            	  panel.setScore(61);
            } else if (speed == speedMI4) {
            	  panel.setLevel(4);
            	  panel.setScore(151);
            }
            panel.pauseAction();
            panel.continueAction();
  
        }  
    } 
    
    private class AboutAction implements ActionListener {  
        public void actionPerformed(ActionEvent event) {  
            String string = Tetris.help;  
            JOptionPane.showMessageDialog(Frame.this, string);  
        }  
    }  
    
    
	public static void main(String[] args){
		
		Frame frame = new Frame();
		frame.panel.action();
		frame.repaint();
    	System.out.println("-----------------------action-------------------------");
    	
	}
    
}