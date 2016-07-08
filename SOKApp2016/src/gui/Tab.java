package gui;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import tree.MainTree;

public class Tab extends TabItem {

	private MainTree.ViewItem item;
	
	public Tab(TabFolder parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected void checkSubclass() {
		
	}
	
	public void setItem(MainTree.ViewItem item) {
		this.item = item;
	}

	public MainTree.ViewItem getItem() {
		return item;
	}

	
}
