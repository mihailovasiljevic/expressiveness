package graph.models;

public class GraphLink {
	private GraphNode parent;
	private GraphNode child;
	private GraphLinkType linkType;
	
	public GraphLink() { }
	
	public GraphLink(GraphNode parent, GraphNode child, GraphLinkType linkType) {
		this.parent = parent;
		this.child = child;
		this.linkType = linkType;
	}

	public GraphNode getParent() {
		return parent;
	}

	public void setParent(GraphNode parent) {
		this.parent = parent;
	}

	public GraphNode getChild() {
		return child;
	}

	public void setChild(GraphNode child) {
		this.child = child;
	}

	public GraphLinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(GraphLinkType linkType) {
		this.linkType = linkType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.parent == null)
			return "[LINK] null -- "+ this.linkType.getLinkType()+ " -->" + this.getChild().getFeatures().get("name");
		else
			return "[LINK]"+this.parent.getFeatures().get("name")+"-- "+ this.linkType.getLinkType()+ " -->" + this.getChild().getFeatures().get("name");
	}
	
	
	
}
