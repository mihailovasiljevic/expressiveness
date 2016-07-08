package sokapp2016;

import gui.MainFrame;
import gui.Tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import tree.MainTree;
import components.Source;
import components.Visualizer;

public class VisualizerTracker extends ServiceTracker {

	private BundleContext context;
	private MainFrame frame;
	
	public VisualizerTracker(BundleContext context, MainFrame frame) {
		super(context,Visualizer.class.getName(),null);
		this.context = context;
		this.frame = frame;
	}
	
	@Override
    public Object addingService(ServiceReference ref) {
		System.out.println("adding visualizer");
		System.out.println(ref.getProperty(Visualizer.NAME));
		String name = (String) ref.getProperty(Visualizer.NAME);
		Visualizer visualizer = context.getService(ref);
		frame.addVisualiser(name, visualizer);

		
		return ref;
	}
	
	@Override
    public void modifiedService(ServiceReference ref, Object svc) {
		System.out.println("modify visualizer");
	}
	
    public void removedService(ServiceReference ref, Object svc) {
    	System.out.println("remove visualizer");
    	String name = (String) ref.getProperty(Visualizer.NAME);
		Visualizer visualizer = context.getService(ref);
    	
		frame.getVisualizers().remove(name);
		
    	
    }
	
    public void process() {

    }
    
    public void addFrame(MainFrame frame) {
    	this.frame = frame;
    }
    
    private void disableTabs(Visualizer visualizer) {
    	
    	for(int i = 0; i < frame.getTabFolder().getItemCount(); i++) {
    		Tab tab = (Tab) frame.getTabFolder().getItem(i);
    		MainTree.ViewItem view = tab.getItem();
    		
    	}
    }
    
}
