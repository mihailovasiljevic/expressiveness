package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import listeners.TabFolderListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.osgi.framework.BundleContext;

import components.Visualizer;
import sokapp2016.SourceTracker;
import tree.MainTree;
import tree.TwitterTree;

public class MainFrame extends Thread {

	private static MainFrame instance = null;
	private Shell shell;
	private Display display;
	private static TwitterTree twitterTree;
	private MainTree tree;
	
	private TabFolder tabFolder;
	
	private MyMenuBar myMenuBar;
	//private ArrayList<Visualizer> visualizers;
	private HashMap<String, Visualizer> visualizers;
	private SashForm mainSash;
	
	public MainFrame() {
		System.out.println("mainFrame");
		display = Display.getDefault();
	    shell = new Shell(display);
	    shell.setText("Graphicons");
	    shell.setLayout(new FillLayout());
	    //shell.setLayout(new GridLayout());
	    
	    ImageData img = new ImageData(getClass().getResourceAsStream("/images/main.png"));
	    Image image = new Image(display, img);
	    shell.setImage(image);
	    
	    this.mainSash = new SashForm(shell, SWT.HORIZONTAL);
	    
	    tree = new MainTree(mainSash,SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL,display, this);
	    mainSash.SASH_WIDTH = 3;
	    
	    tabFolder = new TabFolder(mainSash, SWT.BORDER);
	    tabFolder.addMouseListener(new TabFolderListener(this));
	    
	    //MyToolBar toolbar = new MyToolBar(composite, SWT.BORDER, display, shell);

	    mainSash.setWeights(new int[] { 3, 10});
	    //mainSash.setSize(200,200);
	    //rightSash.setWeights(new int[] { 1, 9});
	    
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		double widht = dimension.getWidth()*3/4;
		double height = dimension.getHeight()*3/4;
		shell.setSize((int)widht, (int)height);
		
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;

	    myMenuBar = new MyMenuBar(this, SWT.BAR);
	    
	    shell.setMenuBar(myMenuBar);
	    
	    shell.setLocation(x, y);
	    
	}

	public Tab getActualTab() {
		TabItem[] folder = (TabItem[]) tabFolder.getSelection();
		if(folder.length != 0) {
			if(folder[0] instanceof Tab) {
				return (Tab)folder[0];
			}
		}
		return null;
	}
	
	public int getTab(String tabName) {
		for(int i = 0; i < tabFolder.getItemCount(); i++) {
			if(tabName.equals(tabFolder.getItem(i).getText())) {
				return i;
			}
		}
		return -1;
	}
	
	

	public Display getDisplay() {
		return display;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

	public static TwitterTree getTwitterTree() {
		return twitterTree;
	}

	public static void setTwitterTree(TwitterTree twitterTree) {
		MainFrame.twitterTree = twitterTree;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(TabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public MyMenuBar getMyMenuBar() {
		return myMenuBar;
	}

	public void setMyMenuBar(MyMenuBar myMenuBar) {
		this.myMenuBar = myMenuBar;
	}
	
	public void addVisualiser(String name, Visualizer visualizer) {
		if(visualizers == null) {
			visualizers = new HashMap<String, Visualizer>();
		}
		visualizers.put(name,visualizer);
	}
	
	public void removeVisualiser(String name, Visualizer visualizer) {
		visualizers.remove(name);
	}

	public HashMap<String, Visualizer> getVisualizers() {
		return visualizers;
	}

	public void setVisualizers(HashMap<String, Visualizer> visualizers) {
		this.visualizers = visualizers;
	}

	public MainTree getTree() {
		return tree;
	}

	public void setTree(MainTree tree) {
		this.tree = tree;
	}

	public SashForm getMainSash() {
		return mainSash;
	}

	public void setMainSash(SashForm mainSash) {
		this.mainSash = mainSash;
	}
	


}
