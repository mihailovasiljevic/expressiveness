package sokapp2016;

import gui.MainFrame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import components.Source;
import utils.JsonParser;

//import twitterparser.TwitterParser;

public class Activator implements BundleActivator {

	public static BundleContext context;
	public static ArrayList<ServiceReference> references = new ArrayList<ServiceReference>();
	public static ArrayList<String> sources = new ArrayList<String>();
	public static ArrayList<String> visualisators = new ArrayList<String>();

	//private SourceTracker sourceTracker;
	public static MainFrame frame;
	
	static BundleContext getContext() {
		return context;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		//System.out.println("usao");
		
		
		//(new MainFrame()).start();
		
		/*Activator.context = bundleContext;

		InputStream stream = getClass().getResourceAsStream("/config/config.json");

		File file = File.createTempFile("tmp", ".json");
		OutputStream outputStream = new FileOutputStream(file);
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = stream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.close();
		sources = JsonParser.getValues(file, "core", "sources");
		for(int i = 0; i < sources.size(); i++) {
			references.add(bundleContext.getServiceReference(sources.get(i)));
		}
		
		MainFrame.getInstance();*/
		
		//final SourceTracker sourceTracker = new SourceTracker(bundleContext, null);
		//sourceTracker.open();
		//ServiceReference ref = bundleContext.getServiceReference(Source.class.getName());
		//String name = (String) ref.getProperty(Source.NAME);
		//System.out.println("name " + name);
		
		//frame.waiting();
		/*System.out.println("ispred");
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				System.out.println("ulazi u run");
				Shell shell = new Shell(Display.getDefault());
				shell.setSize(200, 200);
			    shell.open();
			    while (!shell.isDisposed()) {
			      if (!Display.getDefault().readAndDispatch()) { 
			    	  Display.getDefault().sleep();
			      }
			    }
			    Display.getDefault().dispose();
			}
		});*/
		final BundleContext context = bundleContext;
		Runnable runable1 = new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				//frame.getShell().pack();
				frame.getShell().open();
				
				SourceTracker sourceTracker = new SourceTracker(context, frame);
				sourceTracker.open();
				VisualizerTracker visualiserTreacker = new VisualizerTracker(context, frame);
				visualiserTreacker.open();
				while (true) {
				      if (!frame.getDisplay().readAndDispatch()) {
				    	  frame.getDisplay().sleep();
				      } else {
				      }
			    }
			}
		};

		Thread thread1 = new Thread(runable1);
		thread1.start();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}


	
	
	
}
