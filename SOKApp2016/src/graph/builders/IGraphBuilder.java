package graph.builders;

import java.util.ArrayList;

import graph.models.GraphLink;
import graph.models.GraphModel;
import graph.models.GraphNode;
import graph.models.IGraph;

public interface IGraphBuilder {
	public void setGraph(GraphModel graph);
	public void setGraphNodes(ArrayList<GraphNode> graphNodes);
	public void setGraphLinks(ArrayList<GraphLink> graphLinks);
	public IGraph getGraph();
}
