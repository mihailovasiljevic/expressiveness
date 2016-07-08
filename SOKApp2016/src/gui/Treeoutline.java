package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import tree.MainTree;
import components.Visualizer;
import graph.models.GraphModel;
import graph.models.GraphNode;

public class Treeoutline extends Tree{
	private Display display;
	//private MainFrame frame;
	
	//private HashMap<String, SourceItem> sourceItems;
	private GraphModel graphModel;
	private Visualizer visualizer;
	public Treeoutline(Composite parent, int style, GraphModel graphModel,final Visualizer visualizer) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		this.display = display;
		this.graphModel = graphModel;
		this.visualizer = visualizer;
		intialize();
		this.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("SELEKTOVAO CVOR"+arg0.getSource());
				Treeoutline tree = (Treeoutline)arg0.getSource();
				TreeItem[] items = tree.getSelection();
				System.out.println("velicina: "+items.length);
				for(TreeItem item : items){
					
					visualizer.selectGraphNode(((TreeoutlineItem)item).getId());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void intialize(){
		TreeoutlineItem root = new TreeoutlineItem(this, SWT.NONE,graphModel.getNodes().get(0).getFeatures().get("id"));
		root.setText(graphModel.getNodes().get(0).getFeatures().get("name").toString());
		
		recursion(root, graphModel.getNodes().get(0));
	}
	
	private void recursion(TreeItem parent, GraphNode node){
		for(final GraphNode c : node.getChildren()){
			TreeoutlineItem item = new TreeoutlineItem(parent, SWT.NONE,c.getFeatures().get("id"));
			item.setText(c.getFeatures().get("name").toString());
			item.setData("id",c.getFeatures().get("id")); //ovde je smesten id po kom se trazi cvor u stablu
			System.out.println("dosao da doda listener");

			recursion(item, c);			
		}
	}
	public GraphModel getGraphModel() {
		return graphModel;
	}
	public void setGraphModel(GraphModel graphModel) {
		this.graphModel = graphModel;
	}
	
	@Override
	protected void checkSubclass() {
		
	}
	
	public class TreeoutlineItem extends TreeItem {

		private String id;
		
		public TreeoutlineItem(Tree parent, int style, String id) {
			super(parent, style);
			this.id = id;
		}
		
		public TreeoutlineItem(TreeItem parent, int style, String id) {
			super(parent, style);
			this.id = id;
		}
		
		
		@Override
		protected void checkSubclass() {
			
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
	}

}