package npmsource.model;

import com.fasterxml.jackson.databind.JsonNode;

import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphNode;
import graph.models.IGraph;
/**
 * 
 * @author Mihailo Vasiljevic RA4/2012
 * This interface is intented to have methods for creating {@link IGraph} model.
 * It is using {@link JsonNode} as methods parameters, so it needs Jackson parser to be in build path.
 */

public interface INPMSource {
	public IGraph createGraphModel(String path);
	public GraphNode createGraphNode(JsonNode node, GraphNode parentNode, GraphLinkType graphLinkType);
	public GraphLink createGraphLink(GraphNode parent, GraphNode child, GraphLinkType linkType);
	public String isPathValid(String path);
}
