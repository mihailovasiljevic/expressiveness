package uml.model;

import graph.models.GraphModel;
import uml.decorator.Link;
import uml.decorator.Node;
import uml.providers.UMLContentProvider;
import uml.providers.UMLLabelProvider;

public class UMLModel {
	private Link link;
	private Node node;
	private GraphModel graphModel;
	private UMLContentProvider umlContentProvider;
	private UMLLabelProvider umlLabelProvider;
	
	public UMLModel(){}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public GraphModel getGraphModel() {
		return graphModel;
	}

	public void setGraphModel(GraphModel graphModel) {
		this.graphModel = graphModel;
	}

	public UMLContentProvider getUmlContentProvider() {
		return umlContentProvider;
	}

	public void setUmlContentProvider(UMLContentProvider umlContentProvider) {
		this.umlContentProvider = umlContentProvider;
	}

	public UMLLabelProvider getUmlLabelProvider() {
		return umlLabelProvider;
	}

	public void setUmlLabelProvider(UMLLabelProvider umlLabelProvider) {
		this.umlLabelProvider = umlLabelProvider;
	}
	
	
	
}
