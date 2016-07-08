package graph.factories;

import graph.builders.GraphBuilder;
import graph.builders.IGraphBuilder;
import graph.models.IGraph;

public interface IGraphCreator {
	IGraphBuilder graphBuilder();
}
