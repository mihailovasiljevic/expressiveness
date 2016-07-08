package npmsource.builder;

import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphModel;
import graph.models.GraphNode;
import npmsource.model.NPMSource;

import java.util.ArrayList;

public class NPMSourceBuilder implements INPMSourceBuilder{
	
	private NPMSource nodeSource;
	
	public NPMSourceBuilder() {
		this.nodeSource = new NPMSource();
	}
	
	@Override
	public void setGraph(GraphModel graph) {
		// TODO Auto-generated method stub
		this.nodeSource.setGraph(graph);
	}

	@Override
	public void setGraphNodes(ArrayList<GraphNode> graphNodes) {
		// TODO Auto-generated method stub
		this.nodeSource.setGraphNodes(graphNodes);
	}

	@Override
	public void setGraphLinks(ArrayList<GraphLink> graphLinks) {
		// TODO Auto-generated method stub
		this.nodeSource.setGraphLinks(graphLinks);
	}

	@Override
	public void setGraphLinkTypes(ArrayList<GraphLinkType> graphLinkTypes) {
		// TODO Auto-generated method stub
		this.nodeSource.setGraphLinktypes(graphLinkTypes);
	}

	@Override
	public void setFeaturesNames(ArrayList<String> featuresNames) {
		// TODO Auto-generated method stub
		this.nodeSource.setFeatureNames(featuresNames);
	}

	@Override
	public void setNodeModulePath(String nodeModulePath) {
		// TODO Auto-generated method stub
		this.nodeSource.setNodeModulePath(nodeModulePath);
	}

	@Override
	public NPMSource getNodeSource() {
		// TODO Auto-generated method stub
		return this.nodeSource;
	}

}
