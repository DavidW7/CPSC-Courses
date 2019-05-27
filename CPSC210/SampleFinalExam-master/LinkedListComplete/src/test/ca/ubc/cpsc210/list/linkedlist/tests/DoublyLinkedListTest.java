package ca.ubc.cpsc210.list.linkedlist.tests;

import ca.ubc.cpsc210.list.doublylinkedlist.MyDoubleLinkedList;
import org.junit.jupiter.api.BeforeEach;

public class DoublyLinkedListTest extends MyLinkedListTests {

	@BeforeEach
	public void setup() {
		aList = new MyDoubleLinkedList<Integer>();
	}
}
