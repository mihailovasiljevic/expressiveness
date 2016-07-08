package uml.controller;

import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Transform;
import org.eclipse.gef4.layout.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.GraphConnection;
import org.eclipse.gef4.zest.core.widgets.GraphItem;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.GraphWidget;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.gef4.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import components.Visualizer;
import graph.models.GraphModel;
import uml.providers.UMLContentProvider;
import uml.providers.UMLLabelProvider;


public class UMLController implements Visualizer{
	private double scaling = 1.0;
	private double scalingFactor = 1.1;

	private int StartX = -1;
	private int StartY = -1;
	
	private int EndX = -1;
	private int EndY = -1;
	private ArrayList<Dimension> sizes;
	private boolean nodeSelected = false;
	private GraphViewer viewer;
	public UMLController() {
		// TODO Auto-generated constructor stub
		this.sizes = new ArrayList<>();
		
	}
	
	@Override
	public void draw(Composite composite, GraphModel model) {
		
		model.graphOut();
		this.viewer = new GraphViewer(composite, SWT.NONE);
		final GraphModel handleModel = model;
		final Composite dialog = composite;
		
		try{
			this.viewer.setContentProvider(new UMLContentProvider(model));
			this.viewer.setLabelProvider(new UMLLabelProvider(model));
			this.viewer.setLayoutAlgorithm(new RadialLayoutAlgorithm());
			this.viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
			this.viewer.getGraphControl().setCaret(new Caret(viewer.getGraphControl(), SWT.NONE));
			this.viewer.setInput(model.getNodes());
			  // double click on nodes
			this.viewer.addDoubleClickListener(new IDoubleClickListener() {
				
				@Override
				public void doubleClick(DoubleClickEvent event) {
					// TODO Auto-generated method stub
					String nodeName = event.getSelection().toString().substring(1, event.getSelection().toString().length()-1);
					System.out.println("Dupli klik na: " + nodeName);
					
					for(graph.models.GraphNode node : handleModel.getNodes()){
						if(node.getFeatures().get("id").equals(nodeName)){
					        Shell shell = new Shell(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					        shell.setText("TEST");
							System.out.println("Pronadjen i cvor!");
							
							String title = "Information about node: "+ nodeName;
							String content = "";
							
							for(String s : node.getFeatures().keySet()){
								content+=s+": "+node.getFeatures().get(s)+"\n";
							}
							
							MessageDialog.openInformation(shell, title, content);
						}
					}
				}
				
			});
			
			this.viewer.getGraphControl().addMouseWheelListener(new ZoomMouseWheelListener(this.viewer.getGraphControl(), sizes));
			final GraphModel listenerModel = model;
			final int lineWeight = viewer.getGraphControl().getConnections().get(0).getLineWidth();
			final Color lineColor = viewer.getGraphControl().getConnections().get(0).getLineColor();
			final UMLMouseListener umlMouseListener = new UMLMouseListener(viewer.getGraphControl(), sizes, nodeSelected);

			this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					// TODO Auto-generated method stub
					nodeSelected = true;
					String nodeName = event.getSelection().toString().substring(1, event.getSelection().toString().length()-1);
					graph.models.GraphNode node = null;
					for(graph.models.GraphNode n : listenerModel.getNodes()){
						if(n.getFeatures().get("id").equals(nodeName)){
							node = n;
							break;
						}
					}
					GraphNode graphNode = null;
					for(GraphNode n : viewer.getGraphControl().getNodes()){
						if(((graph.models.GraphNode)n.getData()).getFeatures().get("id").equals(nodeName)){
							graphNode = n;
							break;
						}
					}
					if(graphNode == null){
						System.out.println("Ovaj node null!");
						return;
					}
					for(GraphConnection s : viewer.getGraphControl().getConnections()){
						s.setLineWidth(lineWeight);
						s.setLineColor(lineColor);
					}
					List<GraphConnection> sources = graphNode.getSourceConnections();
					for(GraphConnection s : sources){
						s.setLineWidth(10);
						s.setLineColor(new Color(Display.getDefault(), 13, 255, 0));
					}
					List<GraphConnection> destinations = graphNode.getTargetConnections();
					for(GraphConnection s : destinations){
						s.setLineWidth(10);
						s.setLineColor(new Color(Display.getDefault(), 255, 0, 0));
					}
					umlMouseListener.setNodeSelected(true);
				}
			});
			this.viewer.getGraphControl().addMouseListener(umlMouseListener);
			final Cursor cursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
			this.viewer.getGraphControl().setCursor(cursor);
			System.out.println("Root layer: "+this.viewer.getGraphControl().getRootLayer());
			System.out.println("Root layer: "+this.viewer.getGraphControl().getViewport());
		}catch(SWTError error){
			System.out.println(error.getMessage());
			viewer.setContentProvider(new UMLContentProvider(model));
			viewer.setLabelProvider(new UMLLabelProvider(model));
			//viewer.setLayoutAlgorithm(new RadialLayoutAlgorithm());
			viewer.setInput(model.getNodes());
		}


	}
	
	private void reshapeNodes(GraphViewer viewer, boolean zoomIn){

		ZoomManager zoomManager = viewer.getGraphControl().getZoomManager();
		for(GraphNode node : viewer.getGraphControl().getNodes()){
			double width = node.getSize().preciseWidth();
			double height = node.getSize().preciseHeight();
			if(width > 2 && width < 5)
				return;
			if(width > 1000 && width < 1000)
				return;
			double scalingFactor = 1;
			if(zoomManager.getZoom() > 1){
				scalingFactor = zoomManager.getZoom()/1.5;
				if(scalingFactor < 1)
					scalingFactor = 1;
			}
			node.setSize(width*scalingFactor, height*scalingFactor);
			System.out.println("zoomManager: " +zoomManager.getZoom());
		}
	}
	
	//point zoom
	private double processScalling(MouseEvent e){
		
		double newScaling = scaling;
		// odredjujemo novo skaliranje
		if(e.count < 0){
			newScaling /= (double)e.count*(-1)/2 * scalingFactor;
		}else{
			if(e.count != 0){
				newScaling *= ((double)e.count)/2 * scalingFactor;
			}
		}
		System.out.println("newScaling" + newScaling);
		//Skaliranje odrzavamo u nekom maksimalno dozvoljenom intervalu [-50,200]
		if(newScaling < 0.2)
			newScaling = 0.2;
		else if(newScaling > 250)
			newScaling = 250;
		System.out.println("Scaling" + newScaling);
		return newScaling;
			
	}

	@Override
	public void filter(ArrayList<graph.models.GraphNode> filteredNodes) {		
		for(GraphNode n : viewer.getGraphControl().getNodes()){
			boolean found = false;
			for(graph.models.GraphNode node : filteredNodes){
				if(((graph.models.GraphNode)n.getData()).getFeatures().get("id").equals(node.getFeatures().get("id"))){
					found = true; 
					break;
				}
			}
			if(!found){
				n.setVisible(false);
			}
		}
	}

	@Override
	public void search(ArrayList<graph.models.GraphNode> searchedNodes) {
		// TODO Auto-generated method stub
		for(GraphNode n : viewer.getGraphControl().getNodes()){
			boolean found = false;
			for(graph.models.GraphNode node : searchedNodes){
				if(((graph.models.GraphNode)n.getData()).getFeatures().get("id").equals(node.getFeatures().get("id"))){
					n.getFigure().setBackgroundColor(new Color(Display.getDefault(), 13, 255, 0));
					found = true; 
					break;
				}
			}
			if(!found){
				n.getFigure().setBackgroundColor(new Color(Display.getDefault(), 255, 0, 0));
				//n.setVisible(false);
			}
		}		
	}
	
	public void zoom(int zoom){
		if(zoom == 1){
			viewer.getGraphControl().getZoomManager().zoomIn();
		}else if(zoom == 2){
			viewer.getGraphControl().getZoomManager().zoomOut();
		}else{
			while(viewer.getGraphControl().getZoomManager().getZoom() > 0.5){
				viewer.getGraphControl().getZoomManager().zoomOut();
			}
		}
	}
	final int lineWeight = 1;
	final Color lineColor = new Color(Display.getDefault(), 1,1,1);
	@Override
	public void selectGraphNode(String id) {
		// TODO Auto-generated method stub				

		for(GraphNode node : viewer.getGraphControl().getNodes()){
			if(((graph.models.GraphNode)node.getData()).getFeatures().get("id").equals(id)){
				GraphItem[] items = {node};

				if(!node.isSelected())
					viewer.getGraphControl().setSelection(items);
				
			
				IStructuredSelection selection = new StructuredSelection(node);
				viewer.setSelection(selection, true);

				for(GraphConnection s : viewer.getGraphControl().getConnections()){
					s.setLineWidth(lineWeight);
					s.setLineColor(lineColor);
				}
				List<GraphConnection> sources = node.getSourceConnections();
				for(GraphConnection s : sources){
					s.setLineWidth(10);
					s.setLineColor(new Color(Display.getDefault(), 13, 255, 0));
				}
				List<GraphConnection> destinations = node.getTargetConnections();
				for(GraphConnection s : destinations){
					s.setLineWidth(10);
					s.setLineColor(new Color(Display.getDefault(), 255, 0, 0));
				}
				//umlMouseListener.setNodeSelected(true);
				break;
			}
		}
	}
	
	
	
}
