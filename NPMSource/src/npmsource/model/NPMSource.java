package npmsource.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.JsonParser;
import graph.models.GraphLink;
import graph.models.GraphLinkType;
import graph.models.GraphModel;
import graph.models.GraphNode;
import graph.models.IGraph;

public class NPMSource implements INPMSource {
	private GraphModel graph;
	private ArrayList<GraphNode> graphNodes;
	private ArrayList<GraphLink> graphLinks;
	private ArrayList<GraphLinkType> graphLinktypes;
	private ArrayList<String> featureNames;
	private String nodeModulePath;
	private final String CONFIG_PATH = "/config.json";
	private final String SOURCE_NAME = "node";

	public NPMSource() {

		/**
		 * Get all feature names that will be saved and used in graph. If
		 * something went wrong, make empty list and try to fill it later.
		 */
		try {
			InputStream stream = getClass().getResourceAsStream(CONFIG_PATH);
			 
	        File file = File.createTempFile("tmp", ".json");
	        OutputStream outputStream = new FileOutputStream(file);
	        int read = 0;
	        byte[] bytes = new byte[1024];
	        while ((read = stream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }
	        outputStream.close();
			this.featureNames = JsonParser.getFeatures(file.getAbsolutePath(), SOURCE_NAME);
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			this.featureNames = new ArrayList<String>();
		}

	}

	public GraphModel getGraph() {
		return graph;
	}

	public void setGraph(GraphModel graph) {
		this.graph = graph;
	}

	public ArrayList<GraphNode> getGraphNodes() {
		return graphNodes;
	}

	public void setGraphNodes(ArrayList<GraphNode> graphNodes) {
		this.graphNodes = graphNodes;
	}

	public ArrayList<GraphLink> getGraphLinks() {
		return graphLinks;
	}

	public void setGraphLinks(ArrayList<GraphLink> graphLinks) {
		this.graphLinks = graphLinks;
	}

	public ArrayList<GraphLinkType> getGraphLinktypes() {
		return graphLinktypes;
	}

	public void setGraphLinktypes(ArrayList<GraphLinkType> graphLinktypes) {
		this.graphLinktypes = graphLinktypes;
	}

	public ArrayList<String> getFeatureNames() {
		return featureNames;
	}

	public void setFeatureNames(ArrayList<String> featureNames) {
		this.featureNames = featureNames;
	}

	public String getNodeModulePath() {
		return nodeModulePath;
	}

	public void setNodeModulePath(String nodeModulePath) {
		this.nodeModulePath = nodeModulePath;
	}

	/**
	 * This method is used for creating graph.
	 * 
	 * @param path
	 *            to the node module for which is graph creating for. You must
	 *            provide path to the folder of starting node module that you
	 *            want to see dependencies. It then creates root node that is
	 *            type of {@link JsonNode} and makes {@link IGraph}
	 *            {@link GraphNode}. When root node is prepared it is
	 *            calling @see getChildren function.
	 * @return graph that is type of {@link GraphModel}.
	 */
	@Override
	public IGraph createGraphModel(String path) {
		this.graph = new GraphModel();
		ObjectMapper mapper = new ObjectMapper();

		try {
			File nodeModulesFolder = new File(path + "/package.json");
			JsonNode root = mapper.readTree(nodeModulesFolder);
			if (!nodeModulesFolder.exists() && !nodeModulesFolder.isDirectory()) {
				System.out.println("[ERROR]: File doesn't exist or you didn't provide path to the directory");
				return null;
			}

			System.out.println("[INFO]: Root is " + root.path("name"));

			/**
			 * get all children of root element that are dependencies
			 */

			/**
			 * create root graphNode
			 */
			GraphLinkType graphLinkType = this.graphLinktypes.get(0);
			GraphNode parentNode = createGraphNode(root, null, graphLinkType);

			JsonNode dependencies = root.path("dependencies");

			getChild(dependencies, path, mapper, root, parentNode, graphLinkType);

			/**
			 * DevDependencies
			 */
			JsonNode devDependencies = root.path("devDependencies");
			graphLinkType = this.graphLinktypes.get(1);
			getChild(devDependencies, path, mapper, root, parentNode, graphLinkType);
			
			this.graph.setNodes(this.graphNodes);
			this.graph.setLinks(this.graphLinks);
			return this.graph;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[ERROR]: File doesn't exist.");
		}

		return graph;
	}

	private void getChild(JsonNode node, String path, ObjectMapper mapper, JsonNode parentNode,
			GraphNode graphParentNode, GraphLinkType graphLinkType) throws IOException {
		Iterator<String> it = node.fieldNames();
		String dependencyName = "";
		
		while (it.hasNext()) {
			dependencyName = it.next();
			File nodeModulesFolder = new File(path + "/node_modules");
			if (nodeModulesFolder.exists() && nodeModulesFolder.isDirectory()) {
				File dependencyDirectory = new File(path + "/node_modules/" + dependencyName);
				if (dependencyDirectory.exists() && dependencyDirectory.isDirectory()) {
					System.out.println("Root's " + parentNode.path("name") + " child dependency: " + dependencyName);
					JsonNode newRoot = mapper
							.readTree(new File(path + "/node_modules/" + dependencyName + "/package.json"));

					/**
					 * Create new node
					 */
					
					GraphNode newParentNode = createGraphNode(newRoot, graphParentNode, graphLinkType);

					JsonNode dependencies = newRoot.path("dependencies");
					GraphLinkType graphLinkType2 = this.graphLinktypes.get(0);
					getChild(dependencies, path + "/node_modules/" + dependencyName, mapper, newRoot, newParentNode, graphLinkType2);
					
					GraphLinkType graphLinkType3 = this.graphLinktypes.get(1);
					JsonNode devDependencies = newRoot.path("devDependencies");
					getChild(devDependencies, path + "/node_modules/" + dependencyName, mapper, newRoot, newParentNode, graphLinkType3);
					
					// procuiti za dev dependencies ovde
				}
			}

		}

	}

	/**
	 * Method is making {@link GraphNode} based on provided
	 * 
	 * @param node
	 *            that is type of {@link JsonNode}. If @param parentNode
	 *            provided is not null it will add parent/ child in node/
	 *            parentNode. It sets nodes features and adds new node in the
	 *            list of graph nodes of this class.
	 * @return retVal as {@link GraphNode}
	 */
	@Override
	public GraphNode createGraphNode(JsonNode node, GraphNode parentNode, GraphLinkType graphLinkType) {
		// TODO Auto-generated method stub
		
		GraphNode retVal = new GraphNode();
		HashMap<String, String> features = new HashMap<String, String>();

		if (featureNames.size() > 0) {

			for (String featureName : featureNames) {
				try {
					features.put(featureName, node.path(featureName).toString().substring(1,
							node.path(featureName).toString().length() - 1));
				} catch (StringIndexOutOfBoundsException ex) {
					features.put(featureName, node.path(featureName).toString());
				} catch (Exception e) {
					features.put(featureName, "");
				}
			}

		}
		
		for(GraphNode n : this.graphNodes){
			if(n.getFeatures().get("name").equals(features.get("name"))){
				retVal = n;
				break;
			}
		}
		features.put("id", features.get("name"));
		retVal.setFeatures(features);
		
		
		if (parentNode != null) {
			retVal.addParent(parentNode);
			parentNode.addChild(retVal);
		}

		if (!this.graphNodes.contains(retVal)) {
			this.graphNodes.add(retVal);
			createGraphLink(parentNode, retVal, graphLinkType);
		}

		return retVal;
	}

	@Override
	public GraphLink createGraphLink(GraphNode parent, GraphNode child, GraphLinkType linkType) {
		// TODO Auto-generated method stub
		GraphLink retVal = new GraphLink(parent, child, linkType);
		if (!this.graphLinks.contains(retVal)){
			this.graphLinks.add(retVal);
		}
		return retVal;
	}

	@Override
	public String isPathValid(String path) {
		System.out.println("USAO U PROVERU GRESKE");
		try{
			File f = new File(path);
			if(!f.exists())
				return "There is no folder on the provided path.";
			if(!f.isDirectory())
				return "You must provide folder not file.";
		}catch(Exception ex){
			return "There is no folder on the provided path.";
		}
		
		
		try {
			File test = new File(path+"\\package.json");
			if(!test.exists())
				return "This is not valid node module.\nIt doesn't have package.json.\nPlease, provide path to the valid node module.";
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return "There is no package.json in the provided directory.";
		}
			
		
		return null;
	}

}
