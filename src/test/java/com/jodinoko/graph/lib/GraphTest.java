package com.jodinoko.graph.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest {
	private GraphFactory<Integer> factory = new GraphFactory<>();

	@Test
	public void testAddVertexes() {
		IGraph<Integer> graph = factory.createSimple();
		try {
			graph.addVertex(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// vertex value cannot be null
		}
		graph.addVertex(0);
		try {
			graph.addVertex(0);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// graph cannot contain 2 vertexes with the same value
		}
		graph.addVertex(1);
		graph.addVertex(2);
		Assert.assertEquals(graph.getVertexes().size(), 3);
	}

	@Test
	public void testAddEdges() {
		IGraph<Integer> graph = factory.createSimple();
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);
		try {
			graph.addEdge(null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// edge value cannot be null
		}
		try {
			graph.addEdge(Edge.<Integer>builder().source(0).build());
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// edge source and destination cannot be null
		}
		try {
			graph.addEdge(Edge.<Integer>builder().source(10).destination(11).build());
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// edge source and destination must exist in graph
		}
		graph.addEdge(Edge.<Integer>builder().source(0).destination(1).build());
		// because graph is undirected: vertex with value 0 contains 1 adjacent vertex and vertex with value 1 also contains 1 adjacent vertex
		Assert.assertEquals(graph.getAdjacentVertexes(0).size(), 1);
		Assert.assertEquals(graph.getAdjacentVertexes(1).size(), 1);

		graph = factory.createDirected();
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addEdge(Edge.<Integer>builder().source(0).destination(1).build());
		// because graph is directed: vertex with value 0 contains 1 adjacent vertex, but vertex with value 1 doesn't contain any
		Assert.assertEquals(graph.getAdjacentVertexes(0).size(), 1);
		Assert.assertEquals(graph.getAdjacentVertexes(1).size(), 0);
	}

	@Test
	public void testGetGraphPath() {
		IGraph<Integer> graph = factory.createSimple();
		try {
			graph.getPath((Integer) null, null);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// source and destination cannot be null
		}
		try {
			graph.getPath(0, 1);
			Assert.fail();
		} catch (IllegalArgumentException e) {
			// source and destination must be presented in the graph
		}
		graph.addVertex(0);
		graph.addVertex(1);
		graph.addEdge(Edge.<Integer>builder().source(0).destination(1).build());

		Collection<Vertex<Integer>> path = graph.getPath(0, 1);
		Assert.assertEquals(path.size(), 2);
		Assert.assertTrue(new ArrayList<>(path).equals(Arrays.asList(new Vertex<>(0), new Vertex<>(1))));
	}

	@Test
	public void testTraverse() {
		IGraph<Integer> graph = factory.createSimple();
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);

		AtomicInteger sum = new AtomicInteger(0);
		graph.traverse((v) -> {
			sum.addAndGet(v.getValue());
		});
		Assert.assertEquals(sum.intValue(), 6);
	}
}
