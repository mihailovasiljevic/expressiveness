package uml.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ScalableFigure;

import graph.models.GraphNode;
import uml.decorator.BorderDecorator;
import uml.decorator.Node;
import uml.decorator.SimpleNode;


public class UMLFigure extends Figure implements ScalableFigure{
	private Node node;
	private FieldFigure fields;
	private HashMap<String, String> features;
	private GraphNode element;
	private double scale = 1;
	
	public UMLFigure(GraphNode element) {
		this.node = new BorderDecorator(new SimpleNode());
		this.element = element;
		this.features = element.getFeatures();
		this.fields = new FieldFigure();
		this.node.decorateNode(features.get("name"),this, fields,features, scale);		

	}
	
	public FieldFigure getFields() {
		return fields;
	}

	@Override
	public double getScale() {
		// TODO Auto-generated method stub
		return scale;
	}

	@Override
	public void setScale(double scale) {
		System.out.println("Postavlja velicinu cvora: " + features.get("name") + ", scaling factor: " + scale);
		this.scale = scale;
		
	}
	
	
	
}
