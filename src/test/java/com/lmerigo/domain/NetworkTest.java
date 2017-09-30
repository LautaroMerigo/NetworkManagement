package com.lmerigo.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NetworkTest {
	// Number of elements to initialize the graph for testing purposes
	private static final Integer ANY_INVALID_NUMBER_OF_ELEMENTS = -1;
	private static final Integer A_VALID_NUMBER_OF_ELEMENTS = 5;

	// Messages
	private static final String INVALID_NUMBER_OF_ELEMENTS_MESSAGE = "Network does not have the right number of elements";
	private static final String THERE_SHOULD_NOT_HAVE_CONNECTIONS_MESSAGE = "Network should not have connections on initial state";

	// The constructor should take a positive integer value indicating the
	// number of elements in the set. Passing in an invalid value should throw an
	// exception
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionOnInvalidArgumentForConstructor() {
		@SuppressWarnings("unused")
		Network network = new Network(ANY_INVALID_NUMBER_OF_ELEMENTS);
	}

	// The internal state of the graph should reflect the right number of elements
	@Test
	public void shouldHaveRightNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		assertEquals(INVALID_NUMBER_OF_ELEMENTS_MESSAGE, A_VALID_NUMBER_OF_ELEMENTS, network.numberOfElements());
	}

	// Initially, all the elements on the matrix should be false; reflecting the
	// non-existence of connections.
	@Test
	public void shouldHaveNoConnections() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		assertFalse(THERE_SHOULD_NOT_HAVE_CONNECTIONS_MESSAGE, network.isThereAtLeastOneConnection());
	}

	// Happy path on connection.
	@Test
	public void shouldConnectRightWhenFirstIndexSmallerThanSecondIndex() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		assertTrue(network.isDirectlyConnected(1, 2));
		assertTrue(network.isDirectlyConnected(2, 1));
	}

	@Test
	public void shouldConnectRightWhenFirstIndexGreaterThanSecondIndex() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(2, 1);
		assertTrue(network.isDirectlyConnected(2, 1));
		assertTrue(network.isDirectlyConnected(1, 2));
	}

	// Corner cases

	// First index equals to number of elements
	@Test
	public void shouldConnectWhenFirstIndexEqualsToNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(A_VALID_NUMBER_OF_ELEMENTS, 1);
		assertTrue(network.isDirectlyConnected(A_VALID_NUMBER_OF_ELEMENTS, 1));
		assertTrue(network.isDirectlyConnected(1, A_VALID_NUMBER_OF_ELEMENTS));
	}

	// First index equals to number of elements
	@Test
	public void shouldConnectWhenSecondIndexEqualsToNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, A_VALID_NUMBER_OF_ELEMENTS);
		assertTrue(network.isDirectlyConnected(1, A_VALID_NUMBER_OF_ELEMENTS));
		assertTrue(network.isDirectlyConnected(A_VALID_NUMBER_OF_ELEMENTS, 1));
	}

	// Fail cases

	// First Index
	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenFirstIndexSmallerThanOne() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenFirstIndexGreaterThanNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(A_VALID_NUMBER_OF_ELEMENTS + 1, 1);
	}

	// Second Index
	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenSecondIndexSmallerThanOne() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenSecondIndexGreaterThanNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, A_VALID_NUMBER_OF_ELEMENTS + 1);
	}

	// Indirect connections happy path
	@Test
	public void shouldWorkWithIndirectConnectionsLevel1() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		
		assertTrue(network.query(1, 2));
	}

	@Test
	public void shouldWorkWithIndirectConnectionsLevel2() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		
		assertTrue(network.query(1, 3));
	}
	
	@Test
	public void shouldWorkWithIndirectConnectionsLevel3() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		network.connect(3, 4);
		
		assertTrue(network.query(1, 4));
	}

	
	@Test
	public void shouldWorkWithIndirectConnectionsLevelInverselyLevel1() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(2, 1);
		
		assertTrue(network.query(2, 1));
		assertTrue(network.query(1, 2));
	}

	
	@Test
	public void shouldWorkWithIndirectConnectionsInverselyLevel2() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(2, 1);
		network.connect(3, 2);
		
		assertTrue(network.query(1, 3));
		assertTrue(network.query(3, 1));
	}
	
	@Test
	public void shouldWorkWithIndirectConnectionsInverselyLevel3() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(2, 1);
		network.connect(3, 2);
		network.connect(4, 3);
		
		assertTrue(network.query(1, 4));
		assertTrue(network.query(4, 1));
	}

	
	@Test
	public void shouldWorkWithIndirectConnectionsInCombinationLevel3() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(3, 2);
		network.connect(3, 4);
		
		assertTrue(network.query(1, 4));
		assertTrue(network.query(4, 1));
	}

	// Query fail cases
	@Test(expected=IllegalArgumentException.class)
	public void shouldFailOnQueryWithFirstIndexLessThanOne() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		network.connect(3, 4);
		
		assertTrue(network.query(0, 4));
	}


	@Test(expected=IllegalArgumentException.class)
	public void shouldFailOnQueryWithFirstIndexGreaterThanNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		network.connect(3, 4);
		
		assertTrue(network.query(A_VALID_NUMBER_OF_ELEMENTS + 1, 4));
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldFailOnQueryWithSecondIndexLessThanOne() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		network.connect(3, 4);
		
		assertTrue(network.query(4, 0));
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldFailOnQueryWithSecondIndexGreaterThanNumberOfElements() {
		Network network = new Network(A_VALID_NUMBER_OF_ELEMENTS);
		network.connect(1, 2);
		network.connect(2, 3);
		network.connect(3, 4);
		
		assertTrue(network.query(4, A_VALID_NUMBER_OF_ELEMENTS + 1));
	}

}
