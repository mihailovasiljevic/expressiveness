package listeners;

import gui.MainFrame;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import sokapp2016.Activator;
//import twitterparser.MainUser;
import dialogs.GenericDialog;

public class ConfirmUserDialog implements Listener {

	private GenericDialog dialog;
	
	public ConfirmUserDialog(GenericDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void handleEvent(Event arg0) {

		String expression = dialog.getTxt().getText().trim();
		if(expression.equals("")) {
			MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
		    messageBox.setMessage("Expression can't be blank");
		    messageBox.open();
		    return;
		}
		
		//MainUser user = Activator.twitterParser.twitter(expression);
		
		/*if(user != null) {
			System.out.println("ulaz");
			MainFrame.getTwitterTree().makeTree(user);
			dialog.getShell().close();
		} else {
			MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
		    messageBox.setMessage("User with name " + expression + " doesn't exsists");
		    messageBox.open();
		}*/
		
		
	}
	
}
