package com.jodinoko.graph.lib.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.jodinoko.graph.lib.Edge;
import com.jodinoko.graph.lib.GraphFactory;
import com.jodinoko.graph.lib.IGraph;
import com.jodinoko.graph.lib.Vertex;
import com.jodinoko.graph.lib.internal.Graph;
import com.jodinoko.graph.lib.internal.IPathFinder;
import com.jodinoko.graph.lib.internal.path.BFS;
import junit.framework.Assert;
import org.junit.Test;

public class BFSTest {

	private IPathFinder<Integer> pathFinder = new BFS<>();
	private GraphFactory<Integer> factory = new GraphFactory<>();

	@Test
	public void testCornerCases() {
		try {
			pathFinder.getPath(null, new Vertex<>(0), new Vertex<>(1));
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// graph cannot be null
		}
		IGraph<Integer> graph = factory.createSimple();
		try {
			pathFinder.getPath((Graph<Integer>) graph, null, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// source and destination cannot be null
		}
		try {
			pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(0), new Vertex<>(1));
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// source and destination must be presented in the graph
		}
		graph.addVertex(0);
		Collection<Vertex<Integer>> path = pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(0), new Vertex<>(0));
		// if source == destination, path contain 1 vertex == source
		Assert.assertEquals(path.size(), 1);
		Assert.assertTrue(new ArrayList<>(path).equals(Collections.singletonList(new Vertex<>(0))));

		graph.addVertex(1);
		path = pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(0), new Vertex<>(1));
		// path is empty, if it doesn't exist
		Assert.assertEquals(path.size(), 0);
	}

	@Test
	public void testPathInSimpleGraph() {
		IGraph<Integer> graph = factory.createSimple();
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);

		graph.addEdge(Edge.<Integer>builder().source(0).destination(1).build());
		graph.addEdge(Edge.<Integer>builder().source(0).destination(2).build());
		graph.addEdge(Edge.<Integer>builder().source(0).destination(3).build());
		graph.addEdge(Edge.<Integer>builder().source(2).destination(3).build());

		Collection<Vertex<Integer>> path = pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(1), new Vertex<>(3));
		Assert.assertEquals(path.size(), 3);
		Assert.assertTrue(
				new ArrayList<>(path).equals(Arrays.asList(new Vertex<>(1), new Vertex<>(0), new Vertex<>(3))));
	}

	@Test
	public void testPathInDirectedGraph() {
		IGraph<Integer> graph = factory.createDirected();

		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);

		graph.addEdge(Edge.<Integer>builder().source(0).destination(1).build());
		graph.addEdge(Edge.<Integer>builder().source(0).destination(2).build());
		graph.addEdge(Edge.<Integer>builder().source(0).destination(3).build());
		graph.addEdge(Edge.<Integer>builder().source(2).destination(3).build());

		Collection<Vertex<Integer>> path = pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(1), new Vertex<>(3));
		Assert.assertEquals(path.size(), 0);

		graph.addEdge(Edge.<Integer>builder().source(1).destination(0).build());
		path = pathFinder.getPath((Graph<Integer>) graph, new Vertex<>(1), new Vertex<>(3));
		Assert.assertEquals(path.size(), 3);
		Assert.assertTrue(
				new ArrayList<>(path).equals(Arrays.asList(new Vertex<>(1), new Vertex<>(0), new Vertex<>(3))));
	}
}
