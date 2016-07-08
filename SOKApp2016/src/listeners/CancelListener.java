package listeners;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class CancelListener implements Listener {

	private Shell shell;
	
	public CancelListener(Shell shell) {
		this.shell = shell;
	}

	@Override
	public void handleEvent(Event arg0) {
		shell.close();
	}
	
}
