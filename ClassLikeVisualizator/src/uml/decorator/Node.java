package uml.decorator;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.draw2d.Label;

import uml.model.FieldFigure;
import uml.model.UMLFigure;

public interface Node {
	public void decorateNode(String nodeName, UMLFigure figure,FieldFigure fields, HashMap<String, String> features, double scale);
	public String getInfo();
}
