package classlikevisualizator;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import components.Visualizer;
import uml.controller.UMLController;

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
		System.out.println("Uml vizualizator started.");
		Activator.context = bundleContext;
		UMLController vis = new UMLController();
		Hashtable<String, Object> dict = new Hashtable<String,Object>();
		dict.put(Visualizer.NAME,"uml");
		context.registerService(Visualizer.class.getName(), vis, dict);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Uml vizualizator stopped.");
		Activator.context = null;
	}

}
