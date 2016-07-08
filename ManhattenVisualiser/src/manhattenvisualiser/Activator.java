package manhattenvisualiser;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import components.Source;
import components.Visualizer;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Manhatten");
		Activator.context = bundleContext;
		Manhatten manhatten = new Manhatten();
		
		Hashtable<String, Object> dict = new Hashtable<String,Object>();
		dict.put(Visualizer.NAME,"Manhatten");
		context.registerService(Visualizer.class.getName(), manhatten, dict);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
