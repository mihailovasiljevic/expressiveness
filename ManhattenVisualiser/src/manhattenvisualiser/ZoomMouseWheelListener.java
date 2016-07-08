package manhattenvisualiser;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.zest.core.widgets.Graph;

public class ZoomMouseWheelListener implements MouseWheelListener {

	private Graph graph;
	private ArrayList<Dimension> sizes;
	public ZoomMouseWheelListener(Graph graph) {
		this.graph = graph;
		this.sizes = new ArrayList<>();
		
		prepareZoom();
	}

	public void prepareZoom() {

		ArrayList<String> list = new ArrayList<>();
		list.add(graph.getZoomManager().FIT_ALL);
		graph.getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);
		graph.getZoomManager().setZoomLevelContributions(list);
		graph.getZoomManager().setZoomAsText("0.2");
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			System.out.println("usao");
			GraphNode node = (GraphNode) graph.getNodes().get(i);
			node.setSize(node.getSize().preciseWidth(), node.getSize().preciseHeight());
			sizes.add(node.getSize());
			System.out.println("seize  + " + sizes.get(i).preciseWidth());
		}
		
	}

	@Override
	public void mouseScrolled(MouseEvent e) {
		
		System.out.println("Tockic se mrda. +"+e.count);
		ZoomManager zoomManger = graph.getZoomManager();
		double scalingFactor = zoomManger.getZoom();
		
		if(e.count > 0){
			zoomManger.zoomIn();
			//reshapeNodes(viewer, true);
			System.out.println("Zoomin: " + scalingFactor);
			for(int i = 0; i < graph.getNodes().size(); i++) {
				System.out.println("ovde je ulaz" + sizes.get(i));
				GraphNode node = (GraphNode) graph.getNodes().get(i);
				node.setSize(sizes.get(i).preciseWidth()*scalingFactor, sizes.get(i).preciseHeight()*scalingFactor);
			}
		}
		else if(e.count < 0){
			zoomManger.zoomOut();
			//reshapeNodes(viewer, false);
			System.out.println("Zoomout: " + scalingFactor);
			for(int i = 0; i < graph.getNodes().size(); i++) {
				GraphNode node = (GraphNode) graph.getNodes().get(i);
				node.setSize(sizes.get(i).preciseWidth()*scalingFactor, sizes.get(i).preciseHeight()*scalingFactor);
			}
		}
		Transform transform = new Transform();
		Point point = new Point(e.x, e.y);
		transform.setScale(zoomManger.getZoom());
		zoomManger.setViewLocation(transform.getTransformed(point));
	}

}
