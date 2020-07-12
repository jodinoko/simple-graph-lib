package com.jodinoko.graph.lib;

import com.jodinoko.graph.lib.internal.Graph;

public class GraphFactory {

	private static GraphFactory INSTANCE = new GraphFactory();

	private GraphFactory() {
	}

	public static GraphFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates simple graph: undirected and unweighted
	 */
	public <T> IGraph<T> createSimple() {
		return new Graph<>(false, false);
	}

	/**
	 * Creates weighted graph
	 */
	public <T> IGraph<T> createWeighted() {
		return new Graph<>(false, true);
	}

	/**
	 * Creates directed graph
	 */
	public <T> IGraph<T> createDirected() {
		return new Graph<>(true, false);
	}

	/**
	 * Creates directed, weighted graph
	 */
	public <T> IGraph<T> createDirectedWeighted() {
		return new Graph<>(true, true);
	}
}
