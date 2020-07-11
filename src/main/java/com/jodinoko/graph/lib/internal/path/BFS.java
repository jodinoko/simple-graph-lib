package com.jodinoko.graph.lib.internal.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import com.jodinoko.graph.lib.Messages;
import com.jodinoko.graph.lib.Vertex;
import com.jodinoko.graph.lib.internal.Graph;
import com.jodinoko.graph.lib.internal.IPathFinder;

public class BFS<T> implements IPathFinder<T> {

	@Override
	public Collection<Vertex<T>> getPath(Graph<T> graph, Vertex<T> source, Vertex<T> destination) {
		if (graph == null || graph.getGraph() == null) {
			throw new IllegalArgumentException(Messages.getString("GraphCannotBeNull"));
		}
		if (source == null || destination == null) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationCannotBeNull"));
		}
		if (!graph.getVertexes().contains(source) || !graph.getVertexes().contains(destination)) {
			throw new IllegalArgumentException(Messages.getString("SourceDestinationNotExist"));
		}
		if (Objects.equals(source, destination)) {
			return Collections.singleton(source);
		}

		List<Vertex<T>> path = new ArrayList<>();

		Collection<Vertex<T>> visited = new ArrayList<>();
		visited.add(source);

		Queue<Vertex<T>> queue = new LinkedList<>();
		queue.add(source);

		Map<Vertex<T>, List<Vertex<T>>> childParents = new HashMap<>();

		boolean pathExists = false;
		while (!queue.isEmpty()) {
			Vertex<T> vertex = queue.poll();
			if (Objects.equals(vertex, destination)) {
				pathExists = true;
				break;
			}
			visited.add(vertex);
			Collection<Vertex<T>> adjacentVertexes = graph.getAdjacentVertexes(vertex);
			adjacentVertexes.stream().filter(v -> !visited.contains(v)).forEach(child -> {
				queue.add(child);
				childParents.computeIfAbsent(child, v -> new ArrayList<>()).add(vertex);
			});
		}

		if (!pathExists) {
			return Collections.emptyList();
		}

		Vertex<T> current = destination;
		while (current != source) {
			List<Vertex<T>> parents = childParents.get(current);
			path.add(current);
			current = parents.get(0);
		}
		path.add(source);

		Collections.reverse(path);
		return path;
	}
}