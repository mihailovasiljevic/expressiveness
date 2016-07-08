package listeners;

import gui.MainFrame;
import gui.Tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import components.Source;
import components.Visualizer;
import tree.MainTree;
import dialogs.GenericDialog;

public class SFListener implements SelectionListener {

	private final MainFrame frame;
	private String name;
	
	public SFListener(final MainFrame frame, String name) {
		this.frame = frame;
		this.name = name;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		
		
	}
	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		Tab tab = frame.getActualTab();
		if(tab == null) {
			MessageBox messageBox = new MessageBox(frame.getShell(), SWT.ICON_ERROR);
		    messageBox.setMessage(name + " isn't possible");
		    messageBox.open();
			return;
		} else {
			MainTree.ViewItem item = tab.getItem();
			Visualizer visualizer = item.getVisualizer();
			TreeItem sourceItem = item.getParentItem();
			Source source = null;
			if(sourceItem instanceof MainTree.SourceItem) {
				source = ((MainTree.SourceItem)sourceItem).getSource();
			}
			if(visualizer != null && source != null) {
				GenericDialog dialog = new GenericDialog(frame,name,source,visualizer);
				dialog.filsear();
				dialog.waiting();
			}
			
		}
		
	}
}
