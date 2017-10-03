package com.lmerigo.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Network {
	private Boolean[][] graph;
	private Map<Integer, Set<Integer>> connectedWith;

	public Network(final Integer numberOfElements) {
		if (numberOfElements <= 0) {
			throw new IllegalArgumentException();
		}

		final Integer size = numberOfElements + 1;

		graph = new Boolean[size][size];

		for (int i = 0; i <= numberOfElements; i++) {
			for (int j = 0; j <= numberOfElements; j++) {
				graph[i][j] = Boolean.FALSE;
			}

		}

		connectedWith = new HashMap<Integer, Set<Integer>>();
		for (int i = 1; i <= numberOfElements; i++) {
			connectedWith.put(i, new HashSet<Integer>());
		}
	}

	public Integer numberOfElements() {
		return graph[0].length - 1;
	}

	public Boolean isThereAtLeastOneConnection() {
		final Integer size = numberOfElements();

		for (int i = 1; i <= size; i++) {
			for (int j = i; j <= size; j++) {
				if (Boolean.TRUE.equals(graph[i][j])) {
					return Boolean.TRUE;
				}
			}

		}

		return Boolean.FALSE;
	}

	// The connect method will take two integers indicating the elements to connect.
	// This method should throw exceptions as appropriate.
	public void connect(final Integer i, final Integer j) {
		checkArguments(i, j);
		
		graph[i][j] = Boolean.TRUE;
		connectedWith.get(i).add(j);
	}

	// The second method, query will also take two integers and should also throw an
	// exception as
	// appropriate. It should return true if the elements are connected,
	// directly or indirectly, and false if the elements are not connected.
	public Boolean query(final Integer start, final Integer end) {
		checkArguments(start, end);

		if (isDirectlyConnected(start, end)) {
			return Boolean.TRUE;
		}

		try {
			final Set<Integer> connections = connectedWith.get(start);
			if (connections.isEmpty()) {
				throw new NoConnectionException();
			}

			for (Integer someConnection : connections) {
				if (query(someConnection, end)) {
					return Boolean.TRUE;
				}
			}
		} catch (NoConnectionException e) {
			return Boolean.FALSE;
		}
		
		return Boolean.FALSE;
	}

	public Boolean isDirectlyConnected(final Integer i, final Integer j) {
		checkArguments(i, j);

		return graph[i][j];
	}

	private void checkArguments(final Integer i, final Integer j) {
		final Integer numberOfElements = numberOfElements();

		if (i < 1 || i > numberOfElements) {
			throw new IllegalArgumentException();
		}

		if (j < 1 || j > numberOfElements) {
			throw new IllegalArgumentException();
		}
		
		if(i > j) {
			throw new IllegalArgumentException();
		}
	}

	private class NoConnectionException extends Exception {
		private static final long serialVersionUID = 7727039616625915172L;

	}
}
