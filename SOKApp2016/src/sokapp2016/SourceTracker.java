package sokapp2016;

import gui.MainFrame;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import components.ConfigSourceReq;
import components.Source;

public class SourceTracker extends ServiceTracker {

	public static final int ADD = 1;
	public static final int MODIFY = 2;
	public static final int REMOVE = 3;
	
	private MainFrame frame;
	private BundleContext context;
	
	public SourceTracker(BundleContext context, MainFrame frame) {
		super(context, Source.class.getName(), null);
		this.context = context;
		this.frame = frame;
		if(frame == null) System.out.println("hala");
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
	}
	
	@Override
    public Object addingService(ServiceReference ref) {
		System.out.println("adding");
		String name = (String) ref.getProperty(Source.NAME);
		Source source = context.getService(ref);
		frame.getTree().addSource(source,name);
		frame.getMyMenuBar().addMenuItem(name, source);
		//frame.getSources().put(name, source);
		return ref;
	}
	
	@Override
    public void modifiedService(ServiceReference ref, Object svc) {
		System.out.println("modify");
	}
	
    public void removedService(ServiceReference ref, Object svc) {
    	System.out.println("remove");
    }
	
    public void process() {

    }
    
    public void addFrame(MainFrame frame) {
    	this.frame = frame;
    }
	
	
}
