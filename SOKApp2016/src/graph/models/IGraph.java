package graph.models;

import java.util.ArrayList;

public interface IGraph {
	public void graphOut();
	public ArrayList<String> parentToChild(String name);
	public ArrayList<GraphNode> parentToChildGraph(String name);
	public ArrayList<String> childToParent(String name);
	public ArrayList<String> graphToString();
	public String getName(GraphNode node);
	public String getLinkType(GraphNode source, GraphNode dest);
}
