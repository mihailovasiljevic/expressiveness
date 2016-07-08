package gui;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import listeners.SetUserListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class TwitterToolBar extends ToolBar {

	private Display display;
	
	public TwitterToolBar(Composite parent, int style, Display display, Shell shell) {
		super(parent,style);
		
		ToolItem item = new ToolItem(this, SWT.BUTTON1);
	    item.setText("Set User");
	    ImageData img = new ImageData(getClass().getResourceAsStream("/images/add_user.jpg"));
	    img = img.scaledTo(40, 40);
	    Image image = new Image(display,img);
	    item.setImage(image);
	    Color color = new Color(display,209,209,209);
	    
	    item.addListener(SWT.Selection, new SetUserListener(shell));
	    
	    this.setBackground(color);
	    this.pack();
	}
	
	@Override
	protected void checkSubclass() {
		
	}
	
}
