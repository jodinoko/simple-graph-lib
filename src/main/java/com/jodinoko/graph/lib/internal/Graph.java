package com.jodinoko.graph.lib.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.jodinoko.graph.lib.Edge;
import com.jodinoko.graph.lib.IGraph;
import com.jodinoko.graph.lib.Messages;
import com.jodinoko.graph.lib.Vertex;
import com.jodinoko.graph.lib.internal.path.BFS;

public class Graph<T> implements IGraph<T> {

	private final IPathFinder<T> finder;
	private Map<Vertex<T>, Set<Edge<T>>> graph = new HashMap<>();
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private boolean isDirected;
	private boolean isWeighted;

	public Graph(boolean isDirected, boolean isWeighted) {
		this.isDirected = isDirected;
		this.isWeighted = isWeighted;
		this.finder = new BFS<>();
	}

	@Override
	public boolean isDirected() {
		return isDirected;
	}

	@Override
	public boolean isWeighted() {
		return isWeighted;
	}

	@Override
	public Vertex<T> addVertex(T value) {
		if (value == null) {
			throw new IllegalArgumentException(Messages.getString("NullVertexValue"));
		}
		lock.writeLock().lock();
		try {
			Vertex<T> vertex = new Vertex<>(value);
			if (graph.containsKey(vertex)) {
				throw new IllegalArgumentException(Messages.getString("VertexAlreadyExists"));
			}
			graph.put(vertex, new HashSet<>());
			return vertex;
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void addEdge(Edge<T> edge) {
		if (edge == null) {
			throw new IllegalArgumentException(Messages.getString("NullEdgeValue"));
		}
		if (edge.getSource() == null || edge.getDestination() == null) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationCannotBeNull"));
		}
		lock.writeLock().lock();
		try {
			Vertex<T> source = edge.getSource();
			Vertex<T> destination = edge.getDestination();
			if (!graph.containsKey(source) || !graph.containsKey(destination)) {
				throw new IllegalArgumentException(Messages.getString("SourceDestinationNotExist"));
			}
			graph.get(source).add(edge);
			if (!isDirected) {
				graph.get(destination).add(Edge.<T>builder().source(destination).destination(source).build());
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Collection<Vertex<T>> getVertexes() {
		lock.readLock().lock();
		try {
			return Collections.unmodifiableCollection(graph.keySet());
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Collection<Vertex<T>> getAdjacentVertexes(T value) {
		if (value == null) {
			throw new IllegalArgumentException(Messages.getString("NullVertexValue"));
		}
		return getAdjacentVertexes(new Vertex<>(value));
	}

	@Override
	public Collection<Vertex<T>> getAdjacentVertexes(Vertex<T> vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException(Messages.getString("NullVertexValue"));
		}
		if (!graph.containsKey(vertex)) {
			throw new IllegalArgumentException(Messages.getString("VertexNotFound"));
		}
		lock.readLock().lock();
		try {
			return Collections.unmodifiableCollection(graph.get(vertex).stream().map(Edge::getDestination).collect(
					Collectors.toSet()));
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Collection<Vertex<T>> getPath(Vertex<T> source, Vertex<T> destination) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationCannotBeNull"));
		}
		if (!graph.containsKey(source) || !graph.containsKey(destination)) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationNotExist"));
		}
		lock.readLock().lock();
		try {
			return Collections.unmodifiableCollection(finder.getPath(this, source, destination));
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public Collection<Vertex<T>> getPath(T source, T destination) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationCannotBeNull"));
		}
		return getPath(new Vertex<>(source), new Vertex<>(destination));
	}

	@Override
	public void traverse(Consumer<Vertex<T>> consumer) {
		lock.readLock().lock();
		try {
			graph.keySet().forEach(consumer);
		} finally {
			lock.readLock().unlock();
		}
	}

	public Map<Vertex<T>, Set<Edge<T>>> getGraph() {
		return Collections.unmodifiableMap(graph);
	}
}
