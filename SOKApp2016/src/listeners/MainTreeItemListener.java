package listeners;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class MainTreeItemListener implements Listener {

	private Tree tree;
	
	public MainTreeItemListener(Tree tree) {
		this.tree = tree;
	}
	
	@Override
	public void handleEvent(Event arg0) {
        String string = "";
        TreeItem[] selection = tree.getSelection();
        for (int i = 0; i < selection.length; i++)
          string += selection[i] + " ";
        System.out.println("Selection={" + string + "}");
	}

}
