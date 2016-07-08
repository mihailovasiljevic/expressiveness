package listeners;
 
import gui.MainFrame;
import gui.Tab;
 
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TreeItem;
 
import tree.MainTree;
 
import components.Source;
import components.Visualizer;
 
import dialogs.GenericDialog;
 
public class ZoomListener implements SelectionListener  {
 
    private MainFrame frame;
    private int type;
   
    public ZoomListener(MainFrame frame, int type) {
        this.frame = frame;
        this.type = type;
    }
 
 
    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
        // TODO Auto-generated method stub
       
    }
 
    @Override
    public void widgetSelected(SelectionEvent arg0) {
        System.out.println("usao");
       
        Tab tab = frame.getActualTab();
        if(tab == null) {
            MessageBox messageBox = new MessageBox(frame.getShell(), SWT.ICON_ERROR);
            messageBox.setMessage("Zoom isn't possible");
            messageBox.open();
            return;
        } else {
            MainTree.ViewItem item = tab.getItem();
            Visualizer visualizer = item.getVisualizer();
            System.out.println("zoom");
            visualizer.zoom(type);
        }
    }
   
}