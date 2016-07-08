package graph.factories;

import graph.builders.GraphBuilder;

public class GraphCreator implements IGraphCreator{

	@Override
	public GraphBuilder graphBuilder() {
		// TODO Auto-generated method stub
		return new GraphBuilder();
	}

}
