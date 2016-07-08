package graph.builders;

import java.util.ArrayList;

import graph.models.GraphLink;
import graph.models.GraphModel;
import graph.models.GraphNode;
import graph.models.IGraph;

public class GraphBuilder implements IGraphBuilder{
	private GraphModel graphModel;
	
	public GraphBuilder() {
		// TODO Auto-generated constructor stub
		this.graphModel = new GraphModel();
	}	
	
	@Override
	public void setGraph(GraphModel graph) {
		// TODO Auto-generated method stub
		this.graphModel = graph;
	}

	@Override
	public void setGraphNodes(ArrayList<GraphNode> graphNodes) {
		// TODO Auto-generated method stub
		this.graphModel.setNodes(graphNodes);
	}

	@Override
	public void setGraphLinks(ArrayList<GraphLink> graphLinks) {
		// TODO Auto-generated method stub
		this.graphModel.setLinks(graphLinks);
	}

	@Override
	public IGraph getGraph() {
		// TODO Auto-generated method stub
		return this.graphModel;
	}

}
