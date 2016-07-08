package tree;

import graph.models.GraphModel;
import gui.MainFrame;
import gui.Tab;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import listeners.TreeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import components.Source;
import components.Visualizer;

public class MainTree extends Tree {

	private Display display;
	private MainFrame frame;
	
	private HashMap<String, SourceItem> sourceItems;
	
	private final int imageSize = 30;
	
	public MainTree(Composite parent, int style, Display display, MainFrame frame) {
		super(parent, style);
		this.display = display;
		this.frame = frame;
	}
	
	/*public void makeTree(ArrayList<String> sources, ArrayList<String> vizualizators) {

		
		for(int i = 0; i < sources.size(); i++) {
			SourceItem source = new SourceItem(this,SWT.NONE);
			source.setText(sources.get(i));
		    ImageData img = new ImageData(getClass().getResourceAsStream("/images/folder.png"));
		    img = img.scaledTo(30,35);
		    Image image = new Image(display, img);
		    source.setImage(image);
			for(int j = 0; j < vizualizators.size(); j++) {
				VisualizatorItem vizualizator = new VisualizatorItem(source,SWT.NONE);
				vizualizator.setText(vizualizators.get(j));
			    img = new ImageData(getClass().getResourceAsStream("/images/file.png"));
			    img = img.scaledTo(22,28);
			    image = new Image(display, img);
			    vizualizator.setImage(image);
			    final Tree tree = this;
			    this.addListener(SWT.MouseDown, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
				        String string = "";
				        TreeItem[] selection = tree.getSelection();
				        for (int i = 0; i < selection.length; i++) {
				        	if(selection[i] instanceof VisualizatorItem) {
						        String name = selection[i].getText();
						        SourceItem sitem = (SourceItem) selection[i].getParentItem();
						        String parentName = sitem.getText();
						        String tabName = parentName + " (" + name + ") ";
						        if(frame.getTab(tabName) == -1) {
						        	TabItem item = new TabItem(frame.getTabFolder(), SWT.CLOSE);
						        	item.setText(tabName);
						        	ImageData img = new ImageData(getClass().getResourceAsStream("/images/closeTab.png"));
						    	    img = img.scaledTo(20, 20);
						        	Image image = new Image(display, img);
						    	    item.setImage(image);
						        } else {
						        	int index = frame.getTab(tabName);
						        	frame.getTabFolder().setSelection(index);
						        }
				        	}
				        }
					}
			    });
			    
			}
		}
		
	}*/
	
	public void addSource(Source source, String name) {
		System.out.println("name " + name);
		
		SourceItem sourceItem = new SourceItem(this, source, SWT.NONE);
		sourceItem.setText(name);
	    ImageData img = new ImageData(getClass().getResourceAsStream("/images/folder.png"));
	    img = img.scaledTo(30,35);
	    Image image = new Image(display, img);
	    sourceItem.setImage(image);
	    if(sourceItems == null) {
	    	sourceItems = new HashMap<String, SourceItem>();
	    }
	    sourceItems.put(name, sourceItem);
	}
	public void removeSource(Source source, String name) {
		System.out.println("name " + name);
	    sourceItems.remove(name);
		TreeItem[] items = this.getItems();

		for (TreeItem item : items) {
		    if(item.getText().equalsIgnoreCase(name))
		    	item.dispose();
		};
	}	
	public ViewItem addView(String where, String name, Tab tab, GraphModel graph, Visualizer visualizer) {
		System.out.println("hej " + where);
		if(sourceItems.containsKey(where)) {
			SourceItem source = sourceItems.get(where);
			ViewItem item = new ViewItem(source, SWT.NONE, tab);
			item.setText(name);
			item.setGraph(graph);
			item.setVisualizer(visualizer);
			ImageData img = new ImageData(getClass().getResourceAsStream("/images/graph.jpg"));
		    img = img.scaledTo(25,30);
		    Image image = new Image(display, img);
		    item.setImage(image);
		    this.addMouseListener(new TreeListener(this.frame));
		    return item;
		} else {
			return null;
		}

	}
	
	@Override
	protected void checkSubclass() {
		
	}
	
	public class SourceItem extends TreeItem {
		
		private String name;
		private Source source;
		
		public SourceItem(Tree parent, Source source, int style) {
			super(parent, style);
			this.source = source;
		}
		
		public SourceItem(Tree parent, Source source, int style, String name) {
			super(parent, style);
			this.name = name;
		}
		
		@Override
		protected void checkSubclass() {
			
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

		public Source getSource() {
			return source;
		}

		public void setSource(Source source) {
			this.source = source;
		}
		
	}
	
	public class ViewItem extends TreeItem {
		
		private Tab tab;
		private Composite composite;
		private GraphModel graph;
		Visualizer visualizer;
		
		public ViewItem(TreeItem parent, int style, Tab tab) {
			super(parent, style);
			this.tab = tab;
		}
		@Override
		protected void checkSubclass() {
			
		}
		public Tab getTab() {
			return tab;
		}
		public void setTab(Tab tab) {
			this.tab = tab;
		}
		public Composite getComposite() {
			return composite;
		}
		public void setComposite(Composite composite) {
			this.composite = composite;
		}
		public GraphModel getGraph() {
			return graph;
		}
		public void setGraph(GraphModel graph) {
			this.graph = graph;
		}
		public Visualizer getVisualizer() {
			return visualizer;
		}
		public void setVisualizer(Visualizer visualizer) {
			this.visualizer = visualizer;
		}
		
	}
	
	class VisualizatorItem extends TreeItem {

		public VisualizatorItem(TreeItem parent, int style) {
			super(parent, style);
		}
		@Override
		protected void checkSubclass() {
			
		}
	}
	
}
