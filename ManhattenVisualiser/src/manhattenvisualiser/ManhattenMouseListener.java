package manhattenvisualiser;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.zest.core.widgets.Graph;

public class ManhattenMouseListener implements MouseListener{
	private Graph graph;
	private int StartX;
	private int StartY;

	private ArrayList<Dimension> sizes;
	private boolean nodeSelected;
	public ManhattenMouseListener(Graph graph, boolean nodeSelected) {
		this.graph = graph;
		this.StartX = -1;
		this.StartY = -1;
		this.nodeSelected = nodeSelected;
	}
	@Override
	public void mouseDoubleClick(MouseEvent e) {

		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouse down");
		if(StartX == -1 && StartX == -1){
			StartX = e.x;
			StartY = e.y;
		}		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if(nodeSelected){
			StartX = -1 ;
			StartY = -1;
			this.nodeSelected = false;
			return;
		}
		if(StartX != -1 && StartY != -1){
			int razlikaX = e.x - StartX;
			int razlikaY = e.y - StartY;

			for(Object n : graph.getNodes()){
				org.eclipse.zest.core.widgets.GraphNode node= (org.eclipse.zest.core.widgets.GraphNode)n;
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
