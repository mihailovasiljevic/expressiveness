package uml.decorator;

import java.util.ArrayList;
import java.util.HashMap;

import uml.model.FieldFigure;
import uml.model.UMLFigure;

public abstract class NodeDecorator implements Node{

	protected Node nodeForDecoration;

	public NodeDecorator(Node nodeForDecoration) {
		this.nodeForDecoration = nodeForDecoration;
	}

	@Override
	public void decorateNode(String nodeName, UMLFigure figure, FieldFigure fields, HashMap<String, String> features, double scale) {
		nodeForDecoration.decorateNode(nodeName, figure, fields, features, scale);
	}

	@Override
	public String getInfo() {
		return nodeForDecoration.getInfo();
	}

}
