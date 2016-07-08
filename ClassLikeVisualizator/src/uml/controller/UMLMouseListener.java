package uml.controller;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef4.layout.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.GraphWidget;
import org.eclipse.gef4.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class UMLMouseListener implements MouseListener{
	private GraphWidget graph;
	private int StartX;
	private int StartY;

	private ArrayList<Dimension> sizes;
	private boolean nodeSelected;
	public UMLMouseListener(GraphWidget graph, ArrayList<Dimension> sizes, boolean nodeSelected) {
		this.graph = graph;
		this.StartX = -1;
		this.StartY = -1;
		this.sizes = sizes;
		this.nodeSelected = nodeSelected;
	}
	@Override
	public void mouseDoubleClick(MouseEvent e) {

		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(StartX == -1 && StartX == -1){
			StartX = e.x;
			StartY = e.y;
		}		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		/*
		System.out.println("StartX, StartY: [ "+StartX +", "+StartX);
		for(GraphNode node : graph.getNodes()){
			Rectangle testRectangle = new Rectangle(StartX, StartY, 1, 1);
			System.out.println("CvorX: [ "+node.getFigure().getClientArea().x +" do "+node.getFigure().getClientArea().x+node.getFigure().getClientArea().width);
			System.out.println("CvorY: [ "+node.getFigure().getClientArea().y +" do "+node.getFigure().getClientArea().y+node.getFigure().getClientArea().height);
			
			if(node.getFigure().getClientArea().intersects(testRectangle))
			{
				System.out.println("Uhvacen cvor.");
				StartX = -1 ;
				StartY = -1;
				return;
			}
		}*/
		if(nodeSelected){
			StartX = -1 ;
			StartY = -1;
			nodeSelected = false;
			return;
		}
		if(StartX != -1 && StartY != -1){
			int razlikaX = e.x - StartX;
			int razlikaY = e.y - StartY;

			for(GraphNode node : graph.getNodes()){
				node.setLocation(node.getLocation().x+razlikaX, node.getLocation().y+razlikaY);
			}
			StartX = -1 ;
			StartY = -1;
		}
	}
	
	public boolean getNodeSelected(){
		return nodeSelected;
	}
	
	public void setNodeSelected(boolean ns){
		this.nodeSelected = ns;
	}
}
