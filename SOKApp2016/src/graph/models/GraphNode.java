package graph.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GraphNode {
	private HashMap<String, String> features;
	private ArrayList<GraphNode> parents;
	private ArrayList<GraphNode> children;
	
	public GraphNode(){
		features = new LinkedHashMap<>();
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	public GraphNode(HashMap<String, String> features, ArrayList<GraphNode> parents, ArrayList<GraphNode> children) {
		super();
		this.features = features;
		this.parents = parents;
		this.children = children;
	}
	
	public GraphNode(GraphNode node) {
		super();
		this.features = node.features;
		this.parents = node.parents;
		this.children = node.children;
	}

	public HashMap<String, String> getFeatures() {
		return features;
	}

	public void setFeatures(HashMap<String, String> features) {
		this.features = features;
	}

	public ArrayList<GraphNode> getParents() {
		return parents;
	}

	public void setParents(ArrayList<GraphNode> parents) {
		this.parents = parents;
	}

	public ArrayList<GraphNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<GraphNode> children) {
		this.children = children;
	}
	
	public int getNumberOfParents(){
		return this.parents.size();
	}
	
	public int getNumberOfChildren(){
		return this.children.size();
	}
	
	/**
	 * 
	 * @param child
	 * @return child if insertion succeeded or null if it didn't succeed
	 */
	public GraphNode addChild(GraphNode child){
		if(!this.children.contains(child)){
			this.children.add(child);
			return child;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param child
	 * @return child if removing succeeded or null if it didn't succeed
	 */	
	public GraphNode removeChild(GraphNode child){
		if(this.children.contains(child)){
			this.children.remove(child);
			return child;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param parent
	 * @return parent if insertion succeeded or null if it didn't succeed
	 */
	public GraphNode addParent(GraphNode parent){
		if(!this.parents.contains(parent)){
			this.parents.add(parent);
			return parent;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param parent
	 * @return parent if removing succeeded or null if it didn't succeed
	 */	
	public GraphNode removeParent(GraphNode parent){
		if(this.parents.contains(parent)){
			this.parents.remove(parent);
			return parent;
		}else{
			return null;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return features.get("id");
	}	
	
	
	
}
