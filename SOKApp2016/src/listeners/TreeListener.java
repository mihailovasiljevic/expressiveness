package listeners;

import java.awt.GridLayout;

import gui.MainFrame;
import gui.Tab;
import gui.Treeoutline;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TreeItem;

import tree.MainTree;
import tree.MainTree.ViewItem;

public class TreeListener implements MouseListener {

	private MainFrame frame;
	
	public TreeListener(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		MainTree tree = (MainTree) arg0.getSource();
        TreeItem[] selection = tree.getSelection();
        for (int i = 0; i < selection.length; i++) {
        	if(selection[i] instanceof MainTree.ViewItem) {
        		Tab tab = ((MainTree.ViewItem)selection[i]).getTab();
        		TabFolder tabFolder = frame.getTabFolder();
        		boolean selected = false;
        		for(int j = 0; j < tabFolder.getItemCount(); j++) {
        			if(tabFolder.getItem(j) == tab) {
        				tabFolder.setSelection(j);
        				selected = true;
        			}
        		}
        		if(selected == false) {
        			Tab new_tab = new Tab(tabFolder,SWT.NONE);
        			new_tab.setText(((MainTree.ViewItem)selection[i]).getText());
					ImageData img = new ImageData(getClass().getResourceAsStream("/images/closeTab.png"));
				    img = img.scaledTo(20, 20);
			    	Image image = new Image(Display.getDefault(), img);
			    	new_tab.setImage(image);
			    	((MainTree.ViewItem)selection[i]).setTab(new_tab);
			    	
			    	SashForm form = new SashForm(frame.getTabFolder(),SWT.HORIZONTAL);
			    	new_tab.setControl(form);
			    	new_tab.setItem(((MainTree.ViewItem)selection[i]));
			    	new_tab.getItem().setVisualizer(((MainTree.ViewItem)selection[i]).getVisualizer());
					Composite composite = new Composite(form, SWT.NONE);
					composite.setLayout(new FillLayout());
					Treeoutline treea = new Treeoutline(form,SWT.NONE,((MainTree.ViewItem)selection[i]).getGraph(),((MainTree.ViewItem)selection[i]).getVisualizer());
					treea.setBackground(new Color(Display.getDefault(),230,230,230));
					form.setWeights(new int[] {3,1});

			    	((MainTree.ViewItem)selection[i]).getVisualizer().draw(composite, 
			    			((MainTree.ViewItem)selection[i]).getGraph());
			    	composite.layout(false, true);
        			
        		}
        	}
        }
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
