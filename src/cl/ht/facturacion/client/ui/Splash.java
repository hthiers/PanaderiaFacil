package cl.ht.facturacion.client.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import cl.ht.facturacion.client.MainClient;
import cl.ht.facturacion.client.util.Utils;

public class Splash {

	static Properties props = new Properties();
	static boolean splashEnabled = false;
	
	public static void main(String[] args) {
		
	    final Display display = new Display();
	    final int[] count = new int[] { 4 };
	    final Image image = new Image(display, 450, 388);
	    
	    final Shell splash = new Shell(SWT.ON_TOP);
	    final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
	    bar.setMaximum(4);
	    
	    try {
			props.load(new FileInputStream("panaderia.properties"));
			
			if(props.getProperty("splash").equals("1")) {
				splashEnabled = true;
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if(splashEnabled) {
	    
		    Label label = new Label(splash, SWT.NONE);
		    label.setSize(450, 388);
		    label.setBackgroundImage(new Image(display, "media/splash_screen.jpg"));
		    label.setText("Desarrollado por Hernán Thiers, 2011");
		    label.setFont(new Font(display, Utils.changeFont(label, 10, true)));
		    label.setForeground(new Color(display, new RGB(255, 255, 255)));
		    
		    FormLayout layout = new FormLayout();
		    splash.setLayout(layout);
		    
		    GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
			gridData.widthHint = SWT.DEFAULT;
			gridData.heightHint = SWT.DEFAULT;
			label.setLayoutData(gridData);
			
		    FormData labelData = new FormData();
		    labelData.right = new FormAttachment(100, 0);
		    labelData.bottom = new FormAttachment(100, 0);
		    labelData.width = 450;
		    labelData.height = 388;
		    label.setLayoutData(labelData);
		    
		    FormData progressData = new FormData();
		    progressData.left = new FormAttachment(0, 5);
		    progressData.right = new FormAttachment(100, -5);
		    progressData.bottom = new FormAttachment(100, -5);
		    bar.setLayoutData(progressData);
		    
		    splash.pack();
		    
		    Rectangle splashRect = splash.getBounds();
		    Rectangle displayRect = display.getBounds();
		    int x = (displayRect.width - splashRect.width) / 2;
		    int y = (displayRect.height - splashRect.height) / 2;
		    splash.setLocation(x, y);
		    
		    splash.open();
		    
		    display.asyncExec(new Runnable() {
		    	public void run() {
		    	  
		    		Shell shellMain = new MainClient().createShell(display);
		    		shellMain.addListener(SWT.Close, new Listener() {
		    			public void handleEvent(Event e) {
		    				--count[0];
				        }
		    		});
				  
		    		for(int x=1; x<count[0]; x++) {
					    bar.setSelection(x + 1);
					      
					    try {
					    	Thread.sleep(1000);
					    } catch (Throwable e) {
					    }
		    		}
		    	  
		    		splash.close();
		    		image.dispose();
			    
		    		shellMain.pack();
		    		shellMain.open();
		    	}
		    });
		    
		}
		else {
			Shell shellMain = new MainClient().createShell(display);
			
			splash.close();
    		image.dispose();
	    
    		shellMain.pack();
    		shellMain.open();
		}
	    
	    while (count[0] != 0) {
		      if (!display.readAndDispatch())
		        display.sleep();
		}
		    
		display.dispose();
	    
	}//Main
}
