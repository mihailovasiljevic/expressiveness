package uml.providers;

import org.eclipse.gef4.zest.core.viewers.IGraphEntityContentProvider;
import org.eclipse.jface.viewers.Viewer;

import graph.models.GraphModel;
import graph.models.GraphNode;
import graph.models.IGraph;

public class UMLContentProvider implements IGraphEntityContentProvider{
	
	private GraphModel graphModel;
	
	public UMLContentProvider(IGraph graphModel) {
		this.graphModel = (GraphModel)graphModel;
	}
	
	public Object[] getConnectedTo(Object entity) {
		return graphModel.parentToChildGraph(graphModel.getName(((GraphNode)entity))).toArray();
	}

	public Object[] getElements(Object inputElement) {
		return graphModel.getNodes().toArray();
	}

	public double getWeight(Object entity1, Object entity2) {
		return 0;
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public Object[] getChildren(Object element) {
		// TODO Auto-generated method stub
		return getConnectedTo(element);
	}

	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if (((GraphNode)element).getNumberOfChildren() > 0)
			return true;
		return false;
	}

}
