package uml.decorator;

import java.util.HashMap;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LineBorder;

import uml.model.FieldFigure;
import uml.model.UMLFigure;

public class BorderDecorator extends NodeDecorator{

	public BorderDecorator(Node nodeForDecoration) {
		super(nodeForDecoration);
	}

	@Override
	public void decorateNode(String nodeName, UMLFigure figure, FieldFigure fields, HashMap<String, String> features, double scale) {
		// TODO Auto-generated method stub
		super.decorateNode(nodeName, figure, fields, features, scale);
		//figure.remove(fields);
		figure.setBorder(new LineBorder(ColorConstants.red, 1));
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return super.getInfo()+"Ipak krug!";
	}
	
	

}
