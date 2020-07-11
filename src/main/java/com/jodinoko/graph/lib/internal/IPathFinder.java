package com.jodinoko.graph.lib.internal;

import java.util.Collection;

import com.jodinoko.graph.lib.Vertex;

public interface IPathFinder<T> {

	/**
	 * Returns path between source and destination in graph
	 *
	 * @param graph
	 * 		graph representation
	 * @param source
	 * 		source vertex
	 * @param destination
	 * 		destination vertex
	 * @return path (collection of vertexes) between source and destination. Returns empty collection if path doesn't exist
	 */
	Collection<Vertex<T>> getPath(Graph<T> graph, Vertex<T> source, Vertex<T> destination);
}
