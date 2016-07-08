package manhattenvisualiser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;

public class MyListener implements SelectionListener  {

	private Manhatten manhatten;
	private boolean selected;
	
	public MyListener(Manhatten manhatten, boolean selected) {
		this.manhatten = manhatten;
		this.selected = selected;
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		this.reset();
		if(arg0 != null) {
			if(arg0.item instanceof GraphNode) {
				GraphNode node = (GraphNode) arg0.item;
				this.select(node);
				this.selected = true;
			}
		}
		
	}
	
	public void select(GraphNode node) {
		if(node.getSourceConnections() != null) {
			System.out.println(node.getSourceConnections().size());
			for(int i = 0; i < node.getTargetConnections().size(); i++) {
				if(node.getTargetConnections().get(i) instanceof GraphConnection) {
					((GraphConnection)node.getTargetConnections().get(i)).changeLineColor(new Color(Display.getDefault(),255,153,0));
					GraphNode gnode = ((GraphConnection)node.getTargetConnections().get(i)).getSource();
					gnode.setBackgroundColor(new Color(Display.getDefault(),255,204,0));
					System.out.println(gnode.getText());
				}
			}
			for(int i = 0; i < node.getSourceConnections().size(); i++) {
				if(node.getSourceConnections().get(i) instanceof GraphConnection) {
					((GraphConnection)node.getSourceConnections().get(i)).changeLineColor(new Color(Display.getDefault(),0,153,0));
					GraphNode gnode = ((GraphConnection)node.getSourceConnections().get(i)).getDestination();
					gnode.setBackgroundColor(new Color(Display.getDefault(),204,255,201));
				}
			}
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		System.out.println("default");
		
	}
	
	public void reset() {
		for(int i = 0; i < manhatten.getAll_nodes().size(); i++) { 
			manhatten.getAll_nodes().get(i).setBackgroundColor(manhatten.getInitColorNode().get(manhatten.getAll_nodes().get(i)));
		}
		for(int i = 0; i < manhatten.getAll_connections().size(); i++) {
			manhatten.getAll_connections().get(i).changeLineColor(manhatten.getInitColorLink().get(manhatten.getAll_connections().get(i)));
		}
	}

}
