package uml.controller;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.GraphWidget;
import org.eclipse.gef4.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;

public class ZoomMouseWheelListener implements MouseWheelListener {

	private GraphWidget graph;
	private ArrayList<Dimension> sizes;
	public ZoomMouseWheelListener(GraphWidget graph, ArrayList<Dimension> sizes) {
		this.graph = graph;
		this.sizes = sizes;
		prepareZoom();
	}

	public void prepareZoom() {

		ArrayList<String> list = new ArrayList<>();
		list.add(graph.getZoomManager().FIT_ALL);
		graph.getZoomManager().setZoomAnimationStyle(ZoomManager.ANIMATE_ZOOM_IN_OUT);
		graph.getZoomManager().setZoomLevelContributions(list);
		graph.getZoomManager().setZoomAsText("0.2");
		
		for (GraphNode node : graph.getNodes()) {
			node.setSize(node.getSize().preciseWidth() * 0.3, node.getSize().preciseHeight() * 0.3);
			sizes.add(node.getSize());
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
			int i = 0;
			for(GraphNode node : graph.getNodes()){
				node.setSize(sizes.get(i).preciseWidth()*scalingFactor, sizes.get(i).preciseHeight()*scalingFactor);
				i++;
			}
		}
		else if(e.count < 0){
			zoomManger.zoomOut();
			//reshapeNodes(viewer, false);
			System.out.println("Zoomout: " + scalingFactor);
			int i = 0;
			for(GraphNode node :graph.getNodes()){
				node.setSize(sizes.get(i).preciseWidth()*scalingFactor, sizes.get(i).preciseHeight()*scalingFactor);
				i++;
			}
		}
		Transform transform = new Transform();
		Point point = new Point(e.x, e.y);
		transform.setScale(zoomManger.getZoom());
		zoomManger.setViewLocation(transform.getTransformed(point));
	}

}
