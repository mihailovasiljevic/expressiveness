package listeners;

import graph.models.GraphModel;
import gui.MainFrame;
import gui.Tab;
import gui.Treeoutline;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;

import components.ConfigSource;
import components.Source;
import components.Visualizer;
import dialogs.GFormField;
import dialogs.GenericDialog;

public class ConfirmConfiguration implements Listener {

	private GenericDialog dialog;
	private Source source;
	
	public ConfirmConfiguration(GenericDialog dialog, Source source) {
		this.dialog = dialog;
		this.source = source;
	}
	
	@Override
	public void handleEvent(Event arg0) {
		ArrayList<GFormField> fields = dialog.getFields();
		ArrayList<String> errors = new ArrayList<String>();
		for(int i = 0; i < fields.size(); i++) {
			if(fields.get(i).getText().equals("")) {
				errors.add("Field " + fields.get(i).getName() + " can't be blank");
			}
		}
		if(dialog.getCombo() != null) {
			String text = dialog.getCombo().getText();
			if(text.equals("")) {
				errors.add("Visualisator choise can't be blank");
			}
		}
		String error = "";
		for(int i = 0; i < errors.size(); i++) {
			error += errors.get(i) +"\n";
		}
		if(errors.size() != 0) {
			MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
		    messageBox.setMessage(error);
		    messageBox.open();
		    return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < fields.size(); i++) {
			map.put(fields.get(i).getName(), fields.get(i).getText());
		}
		ConfigSource conf = new ConfigSource();
		Cursor busyCursor = new Cursor(dialog.getShell().getDisplay(), SWT.CURSOR_WAIT);
		dialog.getShell().setCursor(busyCursor);
		conf.setConfiguration(map);
		errors = source.setConfig(conf);
		Cursor normalCursor = new Cursor(dialog.getShell().getDisplay(), SWT.CURSOR_ARROW);
		dialog.getShell().setCursor(normalCursor);
		GraphModel graphModel = null;
		if(errors.size() != 0) {
			for(int i = 0; i < errors.size(); i++) {
				error += errors.get(i) +"\n";
			}
			MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
		    messageBox.setMessage(error);
		    messageBox.open();
		    return;
		} else {
			graphModel = source.getGraph();
		}
		if(dialog.getCombo() != null) {
			String visualizer_text = dialog.getCombo().getText();
			if(dialog.getFrame().getVisualizers().containsKey(visualizer_text)) {
				Visualizer visualizer = dialog.getFrame().getVisualizers().get(visualizer_text);
				if(graphModel != null) {
					Tab tab = new Tab(dialog.getFrame().getTabFolder(),SWT.NONE);
					tab.setText(dialog.getValueOfMain());
					//Composite composite = new Composite(dialog.getFrame().getTabFolder(), SWT.NONE);
					
					//composite.setLayout(new FillLayout());
					SashForm form = new SashForm(dialog.getFrame().getTabFolder(),SWT.HORIZONTAL);
					Composite composite = new Composite(form, SWT.NONE);
					composite.setLayout(new FillLayout());
					Treeoutline treea = new Treeoutline(form,SWT.NONE,graphModel,visualizer);
					treea.setBackground(new Color(Display.getDefault(),230,230,230));
					form.setWeights(new int[] {3,1});
					tab.setControl(form);
					tab.setItem(dialog.getFrame().getTree().addView(dialog.getShell().getText(), dialog.getValueOfMain(), tab, graphModel, visualizer));
					//postavljanje slike tabu
					ImageData img = new ImageData(getClass().getResourceAsStream("/images/closeTab.png"));
				    img = img.scaledTo(20, 20);
			    	Image image = new Image(Display.getDefault(), img);
				    tab.setImage(image);
				    //zatvaranje prozora
				    dialog.getShell().close();
				    visualizer.draw(composite, graphModel);
				    dialog.getFrame().getTabFolder().setSelection(tab);
				    composite.layout(false, true);
				}
			} else {
				MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
			    messageBox.setMessage("Visualizer " + visualizer_text + " doesn't exsits");
			    messageBox.open();
			}
		}

	}

}