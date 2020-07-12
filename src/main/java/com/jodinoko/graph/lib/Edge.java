package com.jodinoko.graph.lib;

import java.util.Objects;

/**
 * Graph edge representation
 *
 * @param <T>
 * 		type of the edge source and destination values
 */
public class Edge<T> {

	private Vertex<T> source;
	private Vertex<T> destination;
	private double weight;

	private Edge(Vertex<T> source, Vertex<T> destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	private Edge() {
	}

	public static <T> Builder<T> builder() {
		return new Builder<T>();
	}

	public Vertex<T> getSource() {
		return source;
	}

	public Vertex<T> getDestination() {
		return destination;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Edge<?> other = (Edge<?>) obj;
		return Objects.equals(source, other.source) && Objects.equals(destination, other.destination)
				&& weight == other.weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, destination, weight);
	}

	public static class Builder<T> {
		private Vertex<T> source;
		private Vertex<T> destination;
		private double weight = 1D;

		private Builder() {
		}

		public Edge<T> build() {
			return new Edge<>(source, destination, weight);
		}

		public Builder<T> source(T source) {
			return source(new Vertex<T>(source));
		}

		public Builder<T> source(Vertex<T> source) {
			this.source = source;
			return this;
		}

		public Builder<T> destination(T destination) {
			return destination(new Vertex<T>(destination));
		}

		public Builder<T> destination(Vertex<T> destination) {
			this.destination = destination;
			return this;
		}

		public Builder<T> weight(double weight) {
			this.weight = weight;
			return this;
		}
	}
}
