package listeners;

import gui.MainFrame;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class TabFolderListener implements MouseListener {

	private MainFrame frame;
	
	public TabFolderListener(MainFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDown(MouseEvent arg0) {
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		TabFolder curFolder = (TabFolder)arg0.widget;
        Point eventLocation = new Point(arg0.x, arg0.y);
        TabItem item = curFolder.getItem(eventLocation);
        if(item == null)
            return;

        Image image = item.getImage();

        // check if click is on image
        if(        eventLocation.x >= item.getBounds().x + image.getBounds().x && eventLocation.x <= item.getBounds().x + image.getBounds().x + image.getBounds().width
                && eventLocation.y >= item.getBounds().y + image.getBounds().y && eventLocation.y <= item.getBounds().y + image.getBounds().y + image.getBounds().height)
        {
            item.dispose();
        }
        else
        {
        }
		
	}

}
