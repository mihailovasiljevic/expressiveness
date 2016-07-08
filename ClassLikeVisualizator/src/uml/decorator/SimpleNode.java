package uml.decorator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.EventDispatcher;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.EventDispatcher.AccessibilityDispatcher;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import uml.model.FieldFigure;
import uml.model.UMLFigure;

public class SimpleNode implements Node{
	
	public SimpleNode() {}

	@Override
	public void decorateNode(String nodeName, UMLFigure figure, FieldFigure fields, HashMap<String, String> features, double scale) {
		
		figure.setLayoutManager(new ToolbarLayout());
		//figure.setBorder(new LineBorder(ColorConstants.black, 1));
		//setBackgroundColor(UMLExample.classColor);
		figure.setOpaque(true);
		Font classFont = new Font(null, "Arial", 12, SWT.BOLD);
		Image classImage;
		/*
			classImage = new Image(Display.getDefault(),new FileInputStream("class_obj.gif"));
			Image privateField = new Image(Display.getDefault(),
					new FileInputStream("field_private_obj.gif"));
			Image publicField = new Image(Display.getDefault(),new FileInputStream("methpub_obj.gif"));
			*/
			Label classLabel1 = new Label(nodeName);
			classLabel1.setFont(classFont);
			figure.add(classLabel1);
			figure.add(fields);
			figure.setToolTip(new Label(nodeName));
			figure.setSize(-1,-1);
			//figure.setSize((int)(figure.getSize().getArea()*scale), (int)(figure.getSize().getArea()*scale));
			final String testname = nodeName;			
			decorateFields(figure, features, null);
			



	}

	private void decorateFields(UMLFigure figure, HashMap<String, String> features, Image privateField){
		for(String key : features.keySet()){
			if(!key.equals("name")){
				Label attribute1 = new Label(key+": "+features.get(key));
				figure.getFields().add(attribute1);
			}
		}
	}
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "zest decorator";
	}
	
	private class FeatureDecorator extends Figure{
		
		private FeatureDecorator(){
			ToolbarLayout layout = new ToolbarLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(2);
			setLayoutManager(layout);
			setBorder(new CompartmentFigureBorder());
			setSize(200, 200);
		}

		private class CompartmentFigureBorder extends AbstractBorder {
			public Insets getInsets(IFigure figure) {
				return new Insets(1, 0, 0, 0);
			}

			public void paint(IFigure figure, Graphics graphics, Insets insets) {
				graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
						tempRect.getTopRight());
			}
		}
		
	}

}
