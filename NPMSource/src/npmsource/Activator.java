package npmsource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import components.ConfigSource;
import components.ConfigSourceReq;
import components.Source;
import graph.builders.IGraphBuilder;
import graph.factories.GraphCreator;
import graph.factories.IGraphCreator;
import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphModel;
import graph.models.GraphNode;
import npmsource.builder.INPMSourceBuilder;
import npmsource.builder.NPMSourceBuilder;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private NPMGraph npmGraph;
	private String folderPath;
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("NODE SOURCE BUNDLE STARTED!");
		Activator.context = bundleContext;
		run(Activator.context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("NODE SOURCE BUNDLE STOPPED!");
	}

	public void run(BundleContext context) {
		/*
		 * TwitterParser parser = new TwitterParser();
		 * parser.makeConfiguration(); parser.enableTwitter();
		 */
		this.npmGraph = new NPMGraph();
		this.folderPath = null;
		Hashtable<String, Object> dict = new Hashtable<String, Object>();
		dict.put(Source.NAME, "npm");
		context.registerService(Source.class.getName(), this.npmGraph, dict);
	}

	public class NPMGraph implements Source {
		private INPMSourceBuilder npmBuilder;
		private IGraphCreator graphFactory;
		//private GraphModel graphModel;
		private final String PATH_SYMBOLIC_NAME = "Path to the module";
		private final String MAIN = "Node module name";
		public NPMGraph() {
			this.npmBuilder = new NPMSourceBuilder();
			this.graphFactory = new GraphCreator();
			IGraphBuilder graphBuilder = graphFactory.graphBuilder();
			graphBuilder.setGraphLinks(new ArrayList<GraphLink>());
			graphBuilder.setGraphNodes(new ArrayList<GraphNode>());

			npmBuilder.setGraph((GraphModel) graphBuilder.getGraph());

			npmBuilder.setGraphLinks(((GraphModel) graphBuilder.getGraph()).getLinks());
			npmBuilder.setGraphNodes(((GraphModel) graphBuilder.getGraph()).getNodes());

			ArrayList<GraphLinkType> linkTypes = new ArrayList<GraphLinkType>();
			linkTypes.add(new GraphLinkType("dependency"));
			linkTypes.add(new GraphLinkType("devDependency"));
			npmBuilder.setGraphLinkTypes(linkTypes);
			
			npmBuilder.setNodeModulePath(null);
		}

		@Override
		public ConfigSourceReq getConfigRequirments() {
			ConfigSourceReq config = new ConfigSourceReq();
			ArrayList<String> myConfig = new ArrayList<String>();
			myConfig.add(PATH_SYMBOLIC_NAME);
			config.setConfig(myConfig);
			config.setMain(PATH_SYMBOLIC_NAME);
			return config;
		}

		@Override
		public ArrayList<String> setConfig(ConfigSource source) {
			ArrayList<String> errors = new ArrayList<String>();
			HashMap<String, String> map = source.getConfiguration();
			
			folderPath = map.get(PATH_SYMBOLIC_NAME);
			String s = npmBuilder.getNodeSource().isPathValid(folderPath);
			
			if(s != null)
				errors.add(s);
			else
				npmBuilder.setNodeModulePath(folderPath);
			return errors;
		}

		@Override
		public GraphModel getGraph() {
			// TODO Auto-generated method stub
			//final String RAW_BODY = "C:\\Users\\mihailo\\Downloads\\WS2016-master\\node_modules\\body-parser\\node_modules\\raw-body";
			IGraphBuilder graphBuilder = graphFactory.graphBuilder();
			graphBuilder.setGraphLinks(new ArrayList<GraphLink>());
			graphBuilder.setGraphNodes(new ArrayList<GraphNode>());

			npmBuilder.setGraph((GraphModel) graphBuilder.getGraph());

			npmBuilder.setGraphLinks(((GraphModel) graphBuilder.getGraph()).getLinks());
			npmBuilder.setGraphNodes(((GraphModel) graphBuilder.getGraph()).getNodes());

			ArrayList<GraphLinkType> linkTypes = new ArrayList<GraphLinkType>();
			linkTypes.add(new GraphLinkType("dependency"));
			linkTypes.add(new GraphLinkType("devDependency"));
			npmBuilder.setGraphLinkTypes(linkTypes);			
			
			ConfigSourceReq map = npmGraph.getConfigRequirments();
			
			return (GraphModel)npmBuilder.getNodeSource().createGraphModel(npmBuilder.getNodeSource().getNodeModulePath());
		}

		@Override
		public HashMap<String, String> getFeaturesForFilteringAndSearching() {
			// TODO Auto-generated method stub
			HashMap<String, String> map = new HashMap<>();
			
			for(String s : npmBuilder.getNodeSource().getFeatureNames()){
				map.put(s, "String");
			}
			return map;
		}

	}

}
