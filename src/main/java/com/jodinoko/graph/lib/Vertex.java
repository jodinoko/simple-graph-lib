package com.jodinoko.graph.lib;

import java.util.Objects;

/**
 * Graph vertex representation
 *
 * @param <T>
 * 		type of the vertex value
 */
public class Vertex<T> {

	private T value;

	public Vertex(T value) {
		if (value == null) {
			throw new IllegalArgumentException(Messages.getString("NullVertexValue"));
		}
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Vertex<?> other = (Vertex<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
