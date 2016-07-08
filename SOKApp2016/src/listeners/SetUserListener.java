package listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import dialogs.GenericDialog;

public class SetUserListener implements Listener {

	Shell shell;
	
	public SetUserListener(Shell shell) {
		this.shell = shell;
	}
	
	@Override
	public void handleEvent(Event arg0) {
		//GenericDialog dialog = new GenericDialog(shell);
	}

}
