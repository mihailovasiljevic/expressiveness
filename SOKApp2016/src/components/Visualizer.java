package components;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Composite;

import graph.models.GraphModel;
import graph.models.GraphNode;

public interface Visualizer {

	public static final String NAME = "visualizer.name";
	
	//metode za filtriranje i pretraizvanje bice pozvane nakon sto je pozvana metoda draw
	public void draw(Composite composite, GraphModel model);
	//public void filtering(GraphModel success, GraphModel fail, HashMap<String, String> parameters);
	//public void searching(GraphModel success, GraphModel fail, HashMap<String, String> parameters);
	public void filter(ArrayList<GraphNode> filteredNodes);
	public void search(ArrayList<GraphNode> searchedNodes);
	public void zoom(int type);
	public void selectGraphNode(String id);
}
