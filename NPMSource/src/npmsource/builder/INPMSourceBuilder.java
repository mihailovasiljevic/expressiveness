package npmsource.builder;

import java.util.ArrayList;

import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphModel;
import graph.models.GraphNode;
import npmsource.model.NPMSource;

public interface INPMSourceBuilder {
	public void setGraph(GraphModel graph);
	public void setGraphNodes(ArrayList<GraphNode> graphNodes);
	public void setGraphLinks(ArrayList<GraphLink> graphLinks);
	public void setGraphLinkTypes(ArrayList<GraphLinkType> graphLinkTypes);
	public void setFeaturesNames(ArrayList<String> featuresNames);
	public void setNodeModulePath(String nodeModulePath);
	public NPMSource getNodeSource();
}
