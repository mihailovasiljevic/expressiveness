package listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import dialogs.GFormField;
import dialogs.GenericDialog;
import graph.models.GraphModel;
import graph.models.GraphNode;

public class ConfirmCFilSear implements Listener {

	private GenericDialog dialog;

	public ConfirmCFilSear(GenericDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	public void handleEvent(Event arg0) {

		ArrayList<GFormField> fields = dialog.getFields();
		ArrayList<String> errors = new ArrayList<String>();
		System.out.println(fields.size());
		for (int i = 0; i < fields.size(); i++) {
			if (fields.get(i).getType().equalsIgnoreCase("Integer")) {
				if (isInteger(fields.get(i).getText()) == false) {
					errors.add("Field " + fields.get(i).getName() + " is not number");
				}
			}
		}
		String error = "";
		for (int i = 0; i < errors.size(); i++) {
			error += errors.get(i) + "\n";
		}
		if (errors.size() != 0) {
			MessageBox messageBox = new MessageBox(dialog.getShell(), SWT.ICON_ERROR);
			messageBox.setMessage(error);
			messageBox.open();
			return;
		}
		HashMap<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < fields.size(); i++) {
			map.put(fields.get(i).getName(), fields.get(i).getText());
		}
		System.out.println(dialog.getShell().getText());
		if (dialog.getShell().getText().equals("Searching")) {
			System.out.println("Searching");
			
			dialog.getVisualizer().search(getNodes(map));
			dialog.getShell().dispose();
			return;
		}


		if (dialog.getShell().getText().equals("Filtering")) {
			System.out.println("Filtering");
			
			dialog.getVisualizer().filter(getNodes(map));
			dialog.getShell().dispose();
			return;
		}

	}
	
	private ArrayList<GraphNode> getNodes(HashMap<String, String> map){
		// dialog.getSource().getGraph()

		// dialog.getVisualizert().filter(cvorovi);
		GraphModel model = dialog.getSource().getGraph();
		ArrayList<GraphNode> nodes = model.getNodes();
		ArrayList<GraphNode> forRemove = new ArrayList<>();

		for (String formKey : map.keySet()) {

			boolean containtsTo = formKey.contains(".to");
			boolean containsFrom = formKey.contains(".from");	
			if (containtsTo || containsFrom) {

				Integer value = null;
				try {
					value = Integer.parseInt(map.get(formKey));
					if (containtsTo) {
						String forCheck = formKey.replace(".to", "");
						for (int i = 0; i < nodes.size(); i++) {
							if (Integer.parseInt(nodes.get(i).getFeatures().get(forCheck)) > value){
								forRemove.add(nodes.get(i));
								
							}

						}
					} else {
						String forCheck = formKey.replace(".from", "");	
						for (int i = 0; i < nodes.size(); i++) {
							if(Integer.parseInt(nodes.get(i).getFeatures().get(forCheck)) < value){
								forRemove.add(nodes.get(i));
								
							}

						}
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				for (int i = 0; i < nodes.size(); i++) {
					if (!nodes.get(i).getFeatures().get(formKey).contains(map.get(formKey))){
						forRemove.add(nodes.get(i));

					}
				}
			}

		}
		System.out.println("CVOROVI KOJI ZADOVOLJAVAJU USLOV");
		for (GraphNode n : forRemove) {
			for(GraphNode node : model.getNodes()){
				if(model.getName(node).equalsIgnoreCase(model.getName(n))){
					nodes.remove(nodes.indexOf(node));
					break;
				}
			}
		}
		for(GraphNode n : nodes){
			System.out.println(model.getName(n));
		}
		
		return nodes;
	}
	
	private boolean isInteger(String expression) {
		for (int i = 0; i < expression.length(); i++) {
			if (Character.digit(expression.charAt(i), 10) < 0)
				return false;
		}
		return true;
	}

}
