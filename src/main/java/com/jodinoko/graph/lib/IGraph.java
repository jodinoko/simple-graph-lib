package com.jodinoko.graph.lib;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Graph thread safe representation
 *
 * @param <T>
 * 		type of the graph vertex value
 */
public interface IGraph<T> {

	/**
	 * Determines graph type: directed or undirected
	 *
	 * @return true whether graph is directed
	 */
	boolean isDirected();

	/**
	 * Determines graph type: weighted or unweighted
	 *
	 * @return true whether graph is weighted
	 */
	boolean isWeighted();

	/**
	 * Inserts vertex into graph
	 *
	 * @param value
	 * 		vertex value
	 * @return added vertex
	 * @throws IllegalArgumentException
	 * 		if value is null
	 */
	Vertex<T> addVertex(T value);

	/**
	 * Inserts edge into graph
	 *
	 * @param edge
	 * 		edge
	 * @throws IllegalArgumentException
	 * 		if edge, source or destination is null
	 */
	void addEdge(Edge<T> edge);

	/**
	 * Returns collection of all vertexes in graph
	 *
	 * @return collection of all vertexes
	 */
	Collection<Vertex<T>> getVertexes();

	/**
	 * Returns collection of adjacent vertexes for vertex
	 *
	 * @param vertex
	 * 		vertex
	 * @return collection of adjacent vertexes
	 * @throws IllegalArgumentException
	 * 		if vertex is null
	 */
	Collection<Vertex<T>> getAdjacentVertexes(Vertex<T> vertex);

	/**
	 * Returns collection of adjacent vertexes for vertex with specified value
	 *
	 * @param value
	 * 		vertex value
	 * @return collection of adjacent vertexes
	 * @throws IllegalArgumentException
	 * 		if vertex value is null
	 */
	Collection<Vertex<T>> getAdjacentVertexes(T value);

	/**
	 * Returns path between source and destination vertexes
	 *
	 * @param source
	 * 		source vertex
	 * @param destination
	 * 		destination vertex
	 * @return vertexes path between source and destination vertexes. Returns empty collection if path doesn't exist
	 * @throws IllegalArgumentException
	 * 		if source or destination is null
	 */
	Collection<Vertex<T>> getPath(Vertex<T> source, Vertex<T> destination);

	/**
	 * Returns path between source and destination values
	 *
	 * @param source
	 * 		source value
	 * @param destination
	 * 		destination value
	 * @return vertexes path between source and destination values. Returns empty collection if path doesn't exist
	 */
	Collection<Vertex<T>> getPath(T source, T destination);

	/**
	 * Applies consumer on every vertex of graph
	 *
	 * @param consumer
	 * 		consumer
	 */
	void traverse(Consumer<Vertex<T>> consumer);
}
