package manhattenvisualiser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import graph.models.GraphLink;
import graph.models.GraphModel;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.core.widgets.zooming.ZoomManager;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.BoxLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShiftAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpaceTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import components.Visualizer;

public class Manhatten implements Visualizer {

	private final int SEPARATOR = 30;
	private final int INACTIVITY = 10;
	
	private Graph g = null;
	private ArrayList<GraphContainer> all_nodes = new ArrayList<GraphContainer>();
	private ArrayList<GraphConnection> all_connections = new ArrayList<GraphConnection>();
	private GraphModel model;
	private HashMap<graph.models.GraphNode, GraphContainer> graphNodes = new HashMap<graph.models.GraphNode, GraphContainer>();
	private HashMap<GraphContainer, Color> initColorNode = new HashMap<GraphContainer, Color>();
	private HashMap<GraphConnection, Color> initColorLink = new HashMap<GraphConnection, Color>();
	private MyListener listener;
	private boolean selected = false;
	
	@Override
	public void draw(Composite composite, GraphModel model) {
		this.model = model;
		g = new Graph(composite, SWT.NONE);
		ArrayList<graph.models.GraphNode> nodes = model.getNodes();
		ArrayList<GraphLink> links = model.getLinks();
		
		graphNodes = new HashMap<graph.models.GraphNode, GraphContainer>();
		for(int i = 0; i < nodes.size(); i++) {
			String name = (String) nodes.get(i).getFeatures().get("name");
			GraphContainer node = new GraphContainer(g, ZestStyles.NODES_FISHEYE
					| ZestStyles.NODES_HIDE_TEXT);
			node.setText(name);
			node.setScale(22);
			listener = new MyListener(this,this.selected);
			g.addSelectionListener(listener);
			initColorNode.put(node,new Color(Display.getDefault(),216,228,248));
			node.addListener(SWT.SELECTED, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					System.out.println("selected");
					
				}
			});
			
			
			all_nodes.add(node);
			graphNodes.put(nodes.get(i), node);

			HashMap<String, String> features = nodes.get(i).getFeatures();
			Iterator it = features.entrySet().iterator();
			int height = 0; int width = 0;
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if(((String)pair.getKey()).equalsIgnoreCase("image")) {
		        	Image image = this.getImageFromUrl((String)pair.getValue());
		        	if(image != null) {
		        		node.setImage(image);
		        	}
		        	continue;
		        }
		        String text = ((String)pair.getKey()).toUpperCase() + "\n" + this.makeString((String)pair.getValue());
		        GraphNode child = new GraphNode(node,SWT.NONE,text);
		        child.setLocation(50, height);
		        height += child.getSize().height + 10;
		    }
		}
		
		for(int i = 0; i < links.size(); i++) {
			GraphContainer n1 = graphNodes.get(links.get(i).getParent());
			GraphContainer n2 = graphNodes.get(links.get(i).getChild());
			Color color = new Color(Display.getDefault(),204,102,153);
			if(n1 != null && n2 != null) {
				GraphConnection conn = new GraphConnection(g, SWT.NONE, n1, n2);
				conn.changeLineColor(color);
				all_connections.add(conn);
				initColorLink.put(conn, new Color(Display.getDefault(),204,102,153));
			}
		}
		g.setLayoutAlgorithm(new SpringLayoutAlgorithm(), true);
		g.setRouter(new ManhattanConnectionRouter());
		g.addMouseWheelListener(new ZoomMouseWheelListener(this.g));
		g.addMouseListener(new ManhattenMouseListener(g, this.selected));
		g.setCursor(new Cursor(Display.getDefault(),SWT.CURSOR_HAND));
		
	}
	
	
	@Override
	public void filter(ArrayList<graph.models.GraphNode> filteredNodes) {

		ArrayList<graph.models.GraphNode> nodes = this.model.getNodes();
		for(int i = 0; i < nodes.size(); i++) {
			boolean success = false;
			graphNodes.get(nodes.get(i)).setVisible(true);
			for(int j = 0; j < filteredNodes.size(); j++) {
				if(nodes.get(i).getFeatures().get("id").equals(filteredNodes.get(j).getFeatures().get("id"))) {
					success = true;
				}
			}
			if(success == false) {
				graphNodes.get(nodes.get(i)).setVisible(false);
			}
		}
		
	}

	@Override
	public void search(ArrayList<graph.models.GraphNode> searchedNodes) {
		ArrayList<graph.models.GraphNode> nodes = this.model.getNodes();
		for(int i = 0; i < nodes.size(); i++) {
			boolean success = false;
			//graphNodes.get(nodes.get(i)).setVisible(true);
			for(int j = 0; j < searchedNodes.size(); j++) {
				if(nodes.get(i).getFeatures().get("id").equals(searchedNodes.get(j).getFeatures().get("id"))) {
					success = true;
					initColorNode.put(graphNodes.get(nodes.get(i)),new Color(Display.getDefault(),0,102,0));
				}
			}
			if(success == false) {
				//graphNodes.get(nodes.get(i)).setVisible(false);
				initColorNode.put(graphNodes.get(nodes.get(i)),new Color(Display.getDefault(),255,51,0));
			}
		}
		this.reset();
	}
	
	private void reset() {
		for(int i = 0; i < this.getAll_nodes().size(); i++) { 
			this.getAll_nodes().get(i).setBackgroundColor(this.getInitColorNode().get(this.getAll_nodes().get(i)));
		}
		for(int i = 0; i < this.getAll_connections().size(); i++) {
			this.getAll_connections().get(i).changeLineColor(this.getInitColorLink().get(this.getAll_connections().get(i)));
		}
	}
	
	private String makeString(String expression) {
		boolean active = false;
		int inactivity = 0;
		for(int i = 0; i < expression.length(); i++) {
			if(i % this.SEPARATOR == 0 && i != 0) {
				active = true;
				if(isNear(expression, i) == false) {
					expression = expression.substring(0, i-inactivity*2) + "\n" + expression.substring(i-inactivity*2, expression.length());
					active = false;
					inactivity = 0;
				}
			}
			if(active == true && expression.charAt(i) == ' ') {
				expression = expression.substring(0, i) + "\n" + expression.substring(i, expression.length());
				active = false;
				inactivity = 0;
			} else if(active == true && expression.charAt(i-inactivity*2) == ' ') {
				expression = expression.substring(0, i-inactivity*2) + "\n" + expression.substring(i-inactivity*2, expression.length());
				active = false;
				inactivity = 0;
			} else if(inactivity>this.INACTIVITY) {
				expression = expression.substring(0, i) + "\n" + expression.substring(i, expression.length());
				active = false;
				inactivity = 0;
			}
			if(active == true) {
				inactivity++;
			}
		}
		return expression;
	}
	
	private boolean isNear(String expression, int position) {
		for(int i = 0; i < this.INACTIVITY; i++) {
			if(position + i < expression.length()) {
				if(expression.charAt(position+i) == ' ') { 
					return true;
				}
			}
			if(expression.charAt(position-i) == ' ') {
				return true;
			}
		}
		return false;
	}

	private Image getImageFromUrl(String imgUrl) {
		URL url;
		try {
			url = new URL(imgUrl);
			ImageData img = new ImageData(url.openStream());
			Image image = new Image(Display.getDefault(),img);
			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public Graph getG() {
		return g;
	}

	public void setG(Graph g) {
		this.g = g;
	}

	public ArrayList<GraphContainer> getAll_nodes() {
		return all_nodes;
	}

	public void setAll_nodes(ArrayList<GraphContainer> all_nodes) {
		this.all_nodes = all_nodes;
	}

	public ArrayList<GraphConnection> getAll_connections() {
		return all_connections;
	}

	public void setAll_connections(ArrayList<GraphConnection> all_connections) {
		this.all_connections = all_connections;
	}


	public HashMap<GraphContainer, Color> getInitColorNode() {
		return initColorNode;
	}


	public void setInitColorNode(HashMap<GraphContainer, Color> initColorNode) {
		this.initColorNode = initColorNode;
	}


	public HashMap<GraphConnection, Color> getInitColorLink() {
		return initColorLink;
	}


	public void setInitColorLink(HashMap<GraphConnection, Color> initColorLink) {
		this.initColorLink = initColorLink;
	}


	@Override
	public void zoom(int type) {
		if(type == 1){
            g.getZoomManager().zoomIn();
        }else if(type == 2){
            g.getZoomManager().zoomOut();
        }else{
            while(g.getZoomManager().getZoom() > 0.5){
                g.getZoomManager().zoomOut();
            }
        }
	}


	@Override
	public void selectGraphNode(String id) {
		System.out.println("selected " + id);
		ArrayList<graph.models.GraphNode> nodes = model.getNodes();
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).getFeatures().get("id").equals(id)) {
				GraphContainer node = graphNodes.get(nodes.get(i));
				GraphItem items[] = {node};
				listener.reset();
				g.setSelection(items);
				listener.select(node);
			}
		}
		
	}
	


	
}
