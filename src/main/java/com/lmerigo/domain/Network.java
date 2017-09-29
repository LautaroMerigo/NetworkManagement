package com.lmerigo.domain;

import java.util.ArrayList;
import java.util.List;

public class Network {
	private Boolean[][] graph;

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

		if (j >= i) {
			graph[i][j] = Boolean.TRUE;
		} else {
			graph[j][i] = Boolean.TRUE;
		}
	}

	// The second method, query will also take two integers and should also throw an
	// exception as
	// appropriate. It should return true if the elements are connected,
	// directly or indirectly, and false if the elements are not connected.
	public Boolean query(final Integer start, final Integer end) {
		checkArguments(start, end);
		
		final Integer numberOfElements = numberOfElements();

		List<Integer> visited = new ArrayList<Integer>();
		List<Integer> inProcess = new ArrayList<Integer>();
		inProcess.add(start);

		while (inProcess.size() > 0) {
			int cur = inProcess.get(0);
			inProcess.remove(0);
			if (cur == end) {
				return true;
			}
			if (visited.contains(cur)) {
				continue;
			}
			visited.add(cur);
			for (int i = 1; i < numberOfElements; i++) {
				final Boolean directlyConnected = isDirectlyConnected(cur, i);
				final Boolean visitedContainsI = !visited.contains(i);
				final Boolean inProcessDoesNotContainI = !inProcess.contains(i);
				if (directlyConnected && visitedContainsI && inProcessDoesNotContainI) {
					inProcess.add(i);
				}
			}
		}
		return false;
	}

	public Boolean isDirectlyConnected(final Integer i, final Integer j) {
		checkArguments(i, j);

		Boolean directlyConnected = null;
		if (j >= i) {
			directlyConnected = graph[i][j];
		} else {
			directlyConnected = graph[j][i];
		}

		return directlyConnected;
	}

	private void checkArguments(final Integer i, final Integer j) {
		final Integer numberOfElements = numberOfElements();

		if (i < 1 || i > numberOfElements) {
			throw new IllegalArgumentException();
		}

		if (j < 1 || j > numberOfElements) {
			throw new IllegalArgumentException();
		}
	}

}
