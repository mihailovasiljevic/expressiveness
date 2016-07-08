package uml.providers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef4.zest.core.viewers.EntityConnectionData;
import org.eclipse.gef4.zest.core.viewers.IFigureProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


import graph.models.GraphModel;
import graph.models.GraphNode;
import uml.model.UMLFigure;

public class UMLLabelProvider extends LabelProvider implements
IFigureProvider{
	private GraphModel graph;
	public UMLLabelProvider(GraphModel graphModel){
		this.graph = graphModel;
	}
	public IFigure createClassFigure1(GraphNode element) {
		
		UMLFigure classFigure = new UMLFigure(element);


		return classFigure;
	}
	public String getText(Object element) {
		if (element instanceof EntityConnectionData){
			GraphNode source = (GraphNode)((EntityConnectionData)element).source;
			GraphNode dest = (GraphNode)((EntityConnectionData)element).dest;
			String linkType = graph.getLinkType(source, dest);
			
		    if(linkType != null){
		    	return graph.getName(source)+"-- "+linkType+" --"+graph.getName(dest);
		    }else{
		    	return graph.getName(source)+"----"+graph.getName(dest);
		    }
		}
		return ((GraphNode)element).toString();
	}
	@Override
	public IFigure getFigure(Object element) {
		System.out.println("GraphNode:" + ((GraphNode)element).getFeatures().get("name").toString());
		
		return createClassFigure1((GraphNode)element);
	}
	

}
