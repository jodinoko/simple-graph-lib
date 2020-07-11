package com.jodinoko.graph.lib;

import com.jodinoko.graph.lib.internal.Graph;

public class GraphFactory<T> {

	/**
	 * Creates simple graph: undirected and unweighted
	 */
	public IGraph<T> createSimple() {
		return new Graph<>(false, false);
	}

	/**
	 * Creates weighted graph
	 */
	public IGraph<T> createWeighted() {
		return new Graph<>(false, true);
	}

	/**
	 * Creates directed graph
	 */
	public IGraph<T> createDirected() {
		return new Graph<>(true, false);
	}

	/**
	 * Creates directed, weighted graph
	 */
	public IGraph<T> createDirectedWeighted() {
		return new Graph<>(true, true);
	}
}
