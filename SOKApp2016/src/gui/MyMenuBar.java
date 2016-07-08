package gui;

import java.util.ArrayList;

import listeners.SFListener;
import listeners.ZoomListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import components.ConfigSourceReq;
import components.Source;
import sokapp2016.Activator;
import dialogs.GenericDialog;

public class MyMenuBar extends Menu {

	private Menu menu1;
	private Shell shell;
	private MainFrame frame;
	
	public MyMenuBar(MainFrame frame, int style) {
		super(frame.getShell(), style);
		this.shell = frame.getShell();
		this.frame = frame;
		
		MenuItem item1 = new MenuItem(this, SWT.CASCADE);
		item1.setText("Graph");
		menu1 = new Menu(shell,SWT.DROP_DOWN);
		item1.setMenu(menu1);
		
		MenuItem item2 = new MenuItem(this, SWT.CASCADE);
		item2.setText("Filtering & Searching");
		Menu menu2 = new Menu(shell,SWT.DROP_DOWN);
		item2.setMenu(menu2);
		
		MenuItem item21 = new MenuItem(menu2, SWT.PUSH);
		item21.setText("Filtering");
		
		MenuItem item22 = new MenuItem(menu2, SWT.PUSH);
		item22.setText("Searching");
		
		item21.addSelectionListener(new SFListener(frame, "Filtering"));
		item22.addSelectionListener(new SFListener(frame, "Searching"));
		
		
		MenuItem item3 = new MenuItem(this, SWT.CASCADE);
		item3.setText("Zoom");
		Menu menu3 = new Menu(shell,SWT.DROP_DOWN);
		item3.setMenu(menu3);
		
		MenuItem item31 = new MenuItem(menu3, SWT.PUSH);
		item31.setText("Zoom in");
		
		MenuItem item32 = new MenuItem(menu3, SWT.PUSH);
		item32.setText("Zoom out");
		
		MenuItem item33 = new MenuItem(menu3, SWT.PUSH);
		item33.setText("Bird view");
		
		item31.addSelectionListener(new ZoomListener(frame,1));
		item32.addSelectionListener(new ZoomListener(frame,2));
		item33.addSelectionListener(new ZoomListener(frame,3));
		
		
	}
	
	public void addMenuItem(final String name, final Source source) {

		MenuItem item11 = new MenuItem(menu1, SWT.PUSH);
		item11.setText(name);
		final Shell sh = shell;
		final MainFrame fr = frame;
		
		item11.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenericDialog dialog = new GenericDialog(fr,name);
				ArrayList<String> parameters = new ArrayList<String>();
				dialog.makeFields(source);
				dialog.waiting();
			}
		});
	}
	public void removeMenuItem(final String name, final Source source) {
		MenuItem[] items = menu1.getItems();

		for (MenuItem item : items) {
		    if(item.getText().equalsIgnoreCase(name))
		    	item.dispose();
		};
	}	
	@Override
	protected void checkSubclass() {
		
	}
	
}