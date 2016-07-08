package graph.models;

import java.util.ArrayList;

public class GraphModel implements IGraph {

	private ArrayList<GraphNode> nodes;
	private ArrayList<GraphLink> links;

	public GraphModel() {
		nodes = new ArrayList<>();
		links = new ArrayList<>();
	}

	public GraphModel(ArrayList<GraphNode> nodes, ArrayList<GraphLink> links) {
		this.nodes = nodes;
		this.links = links;
	}

	public GraphNode addNode(GraphNode node) {
		if (!this.nodes.contains(node)) {
			this.nodes.add(node);
			return node;
		} else {
			return null;
		}
	}

	public GraphNode removeNode(GraphNode node) {
		if (this.nodes.contains(node)) {
			this.nodes.remove(node);
			return node;
		} else {
			return null;
		}
	}

	public GraphLink addLink(GraphLink link) {
		if (!this.links.contains(link)) {
			this.links.add(link);
			return link;
		} else {
			return null;
		}
	}

	public GraphLink removeLink(GraphLink link) {
		if (this.links.contains(link)) {
			this.links.remove(link);
			return link;
		} else {
			return null;
		}
	}

	public ArrayList<GraphNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<GraphNode> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<GraphLink> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<GraphLink> links) {
		this.links = links;
	}

	@Override
	public void graphOut() {

		System.out.println("************************************************");
		System.out.println("**********   ISPIS GRAFA NA KONZOLU   **********");
		System.out.println("************************************************");

		for (GraphNode node : this.getNodes()) {

			System.out.println("|_" + node.getFeatures().get("name"));
			if (node.getNumberOfParents() > 0) {
				System.out.println("|RODITELJI:\n");
				for (GraphNode parent : node.getParents()) {
					System.out.println("|__" + parent.getFeatures().get("name"));
				}
			}
			if (node.getNumberOfChildren() > 0) {
				System.out.println("|__DECA:\n");
				for (GraphNode child : node.getChildren()) {
					System.out.println("|__" + child.getFeatures().get("name"));
				}
			}
			System.out.println("-------------------------------------------");
		}
		
		for (GraphLink link : this.getLinks()) {

			System.out.println(link.toString());
			System.out.println("-------------------------------------------");
		}
	}

	@Override
	public ArrayList<String> parentToChild(String name) {
		ArrayList<String> retVal = new ArrayList<>();
		
		for(GraphLink link : links){
			if(link.getParent()!=null){
				if(getName(link.getParent()).equals(name)){
					retVal.add(link.getChild().getFeatures().get("name").toString());
				}
			}
		}	
		
		return retVal;
	}

	@Override
	public ArrayList<String> childToParent(String name) {
		ArrayList<String> retVal = new ArrayList<>();
		
		for(GraphLink link : links){
			if(link.getChild()!=null){
				if(getName(link.getChild()).equals(name)){
					retVal.add(link.getParent().getFeatures().get("name").toString());
				}
			}
		}	
		
		return retVal;
	}

	@Override
	public String getName(GraphNode node) {
		// TODO Auto-generated method stub
		return node.getFeatures().get("name").toString();
	}

	@Override
	public ArrayList<String> graphToString() {
		ArrayList<String> retVal = new ArrayList<>();
		for (GraphNode node : this.getNodes()) {
			retVal.add(node.getFeatures().get("name").toString());
		}
		return retVal;
	}

	@Override
	public ArrayList<GraphNode> parentToChildGraph(String name) {
		ArrayList<GraphNode> retVal = new ArrayList<>();
		
		for(GraphLink link : links){
			if(link.getParent()!=null){
				if(getName(link.getParent()).equals(name)){
					retVal.add(link.getChild());
				}
			}
		}	
		
		return retVal;
	}

	@Override
	public String getLinkType(GraphNode source, GraphNode dest) {
		// TODO Auto-generated method stub
		for(GraphLink link : links){
			if(link.getParent() != null)
				if(getName(source).equals(getName(link.getParent())) && getName(dest).equals(getName(link.getChild()))){
					return link.getLinkType().getLinkType();
				}
		}
		
		return null;
	}

}
